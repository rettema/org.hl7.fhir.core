package org.hl7.fhir.validation.instance;

/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:
    
   * Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
  POSSIBILITY OF SUCH DAMAGE.
  
 */


import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Base64;

import javax.annotation.Nonnull;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.fhir.ucum.Decimal;
import org.hl7.fhir.exceptions.DefinitionException;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.exceptions.PathEngineException;
import org.hl7.fhir.exceptions.TerminologyServiceException;
import org.hl7.fhir.r5.conformance.profile.ProfileUtilities;
import org.hl7.fhir.r5.conformance.profile.ProfileUtilities.SourcedChildDefinitions;
import org.hl7.fhir.r5.context.ContextUtilities;
import org.hl7.fhir.r5.context.IWorkerContext;
import org.hl7.fhir.r5.context.IWorkerContext.OIDSummary;
import org.hl7.fhir.r5.elementmodel.Element;
import org.hl7.fhir.r5.elementmodel.Element.SpecialElement;
import org.hl7.fhir.r5.elementmodel.JsonParser;
import org.hl7.fhir.r5.elementmodel.Manager;
import org.hl7.fhir.r5.elementmodel.Manager.FhirFormat;
import org.hl7.fhir.r5.elementmodel.ObjectConverter;
import org.hl7.fhir.r5.elementmodel.ParserBase;
import org.hl7.fhir.r5.elementmodel.ParserBase.ValidationPolicy;
import org.hl7.fhir.r5.elementmodel.ResourceParser;
import org.hl7.fhir.r5.elementmodel.ValidatedFragment;
import org.hl7.fhir.r5.elementmodel.XmlParser;
import org.hl7.fhir.r5.extensions.ExtensionDefinitions;
import org.hl7.fhir.r5.extensions.ExtensionUtilities;
import org.hl7.fhir.r5.fhirpath.ExpressionNode;
import org.hl7.fhir.r5.fhirpath.ExpressionNode.CollectionStatus;
import org.hl7.fhir.r5.fhirpath.FHIRLexer.FHIRLexerException;
import org.hl7.fhir.r5.fhirpath.FHIRPathEngine;
import org.hl7.fhir.r5.fhirpath.IHostApplicationServices;
import org.hl7.fhir.r5.fhirpath.FHIRPathUtilityClasses.FunctionDetails;
import org.hl7.fhir.r5.fhirpath.FHIRPathUtilityClasses.TypedElementDefinition;
import org.hl7.fhir.r5.fhirpath.TypeDetails;
import org.hl7.fhir.r5.formats.FormatUtilities;
import org.hl7.fhir.r5.model.Address;
import org.hl7.fhir.r5.model.Attachment;
import org.hl7.fhir.r5.model.Base;
import org.hl7.fhir.r5.model.Base.ProfileSource;
import org.hl7.fhir.r5.model.Base.ValidationInfo;
import org.hl7.fhir.r5.model.Base.ValidationMode;
import org.hl7.fhir.r5.model.Base.ValidationReason;
import org.hl7.fhir.r5.model.BooleanType;
import org.hl7.fhir.r5.model.CanonicalResource;
import org.hl7.fhir.r5.model.CanonicalType;
import org.hl7.fhir.r5.model.CodeSystem;
import org.hl7.fhir.r5.model.CodeSystem.ConceptDefinitionComponent;
import org.hl7.fhir.r5.model.CodeType;
import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Coding;
import org.hl7.fhir.r5.model.Constants;
import org.hl7.fhir.r5.model.ContactPoint;
import org.hl7.fhir.r5.model.DataType;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.r5.model.DateType;
import org.hl7.fhir.r5.model.DecimalType;
import org.hl7.fhir.r5.model.ElementDefinition;
import org.hl7.fhir.r5.model.ElementDefinition.AdditionalBindingPurposeVS;
import org.hl7.fhir.r5.model.ElementDefinition.AggregationMode;
import org.hl7.fhir.r5.model.ElementDefinition.ConstraintSeverity;
import org.hl7.fhir.r5.model.ElementDefinition.DiscriminatorType;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionBindingAdditionalComponent;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionBindingComponent;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionConstraintComponent;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionMappingComponent;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionSlicingComponent;
import org.hl7.fhir.r5.model.ElementDefinition.ElementDefinitionSlicingDiscriminatorComponent;
import org.hl7.fhir.r5.model.ElementDefinition.PropertyRepresentation;
import org.hl7.fhir.r5.model.ElementDefinition.TypeRefComponent;
import org.hl7.fhir.r5.model.Enumeration;
import org.hl7.fhir.r5.model.Enumerations.BindingStrength;
import org.hl7.fhir.r5.model.Enumerations.CodeSystemContentMode;
import org.hl7.fhir.r5.model.Extension;
import org.hl7.fhir.r5.model.HumanName;
import org.hl7.fhir.r5.model.Identifier;
import org.hl7.fhir.r5.model.ImplementationGuide;
import org.hl7.fhir.r5.model.ImplementationGuide.ImplementationGuideGlobalComponent;
import org.hl7.fhir.r5.model.InstantType;
import org.hl7.fhir.r5.model.IntegerType;
import org.hl7.fhir.r5.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.hl7.fhir.r5.model.Period;
import org.hl7.fhir.r5.model.PrimitiveType;
import org.hl7.fhir.r5.model.Property;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Range;
import org.hl7.fhir.r5.model.Ratio;
import org.hl7.fhir.r5.model.Reference;
import org.hl7.fhir.r5.model.Resource;
import org.hl7.fhir.r5.model.SampledData;
import org.hl7.fhir.r5.model.SearchParameter;
import org.hl7.fhir.r5.model.StringType;
import org.hl7.fhir.r5.model.StructureDefinition;
import org.hl7.fhir.r5.model.StructureDefinition.ExtensionContextType;
import org.hl7.fhir.r5.model.StructureDefinition.StructureDefinitionContextComponent;
import org.hl7.fhir.r5.model.StructureDefinition.StructureDefinitionKind;
import org.hl7.fhir.r5.model.StructureDefinition.StructureDefinitionMappingComponent;
import org.hl7.fhir.r5.model.StructureDefinition.StructureDefinitionSnapshotComponent;
import org.hl7.fhir.r5.model.StructureDefinition.TypeDerivationRule;
import org.hl7.fhir.r5.model.TimeType;
import org.hl7.fhir.r5.model.Timing;
import org.hl7.fhir.r5.model.UriType;
import org.hl7.fhir.r5.model.UrlType;
import org.hl7.fhir.r5.model.UsageContext;
import org.hl7.fhir.r5.model.ValueSet;
import org.hl7.fhir.r5.model.ValueSet.ValueSetExpansionContainsComponent;
import org.hl7.fhir.r5.terminologies.utilities.TerminologyServiceErrorClass;
import org.hl7.fhir.r5.terminologies.utilities.ValidationResult;
import org.hl7.fhir.r5.utils.BuildExtensions;
import org.hl7.fhir.r5.utils.ResourceUtilities;
import org.hl7.fhir.r5.utils.UserDataNames;
import org.hl7.fhir.r5.utils.XVerExtensionManager;
import org.hl7.fhir.r5.utils.XVerExtensionManager.XVerExtensionStatus;
import org.hl7.fhir.r5.utils.sql.Validator;
import org.hl7.fhir.r5.utils.sql.Validator.TrueFalseOrUnknown;
import org.hl7.fhir.r5.utils.validation.BundleValidationRule;
import org.hl7.fhir.r5.utils.validation.IResourceValidator;
import org.hl7.fhir.r5.utils.validation.IValidationPolicyAdvisor.CodedContentValidationAction;
import org.hl7.fhir.r5.utils.validation.IValidationPolicyAdvisor.ElementValidationAction;
import org.hl7.fhir.r5.utils.validation.IValidationPolicyAdvisor.ReferenceDestinationType;
import org.hl7.fhir.r5.utils.validation.IValidationProfileUsageTracker;
import org.hl7.fhir.r5.utils.validation.IValidatorResourceFetcher;
import org.hl7.fhir.r5.utils.validation.ValidatorSession;
import org.hl7.fhir.r5.utils.validation.constants.BestPracticeWarningLevel;
import org.hl7.fhir.r5.utils.validation.constants.BindingKind;
import org.hl7.fhir.r5.utils.validation.constants.CheckDisplayOption;
import org.hl7.fhir.r5.utils.validation.constants.ContainedReferenceValidationPolicy;
import org.hl7.fhir.r5.utils.validation.constants.IdStatus;
import org.hl7.fhir.r5.utils.validation.constants.ReferenceValidationPolicy;
import org.hl7.fhir.utilities.*;
import org.hl7.fhir.utilities.HL7WorkGroups.HL7WorkGroup;
import org.hl7.fhir.utilities.Utilities.DecimalStatus;
import org.hl7.fhir.utilities.VersionUtilities.VersionURLInfo;
import org.hl7.fhir.utilities.fhirpath.FHIRPathConstantEvaluationMode;
import org.hl7.fhir.utilities.filesystem.ManagedFileAccess;
import org.hl7.fhir.utilities.http.HTTPResultException;
import org.hl7.fhir.utilities.i18n.I18nConstants;
import org.hl7.fhir.utilities.json.model.JsonObject;
import org.hl7.fhir.utilities.validation.IDigitalSignatureServices;
import org.hl7.fhir.utilities.validation.ValidationMessage;
import org.hl7.fhir.utilities.validation.ValidationMessage.IssueSeverity;
import org.hl7.fhir.utilities.validation.ValidationMessage.IssueType;
import org.hl7.fhir.utilities.validation.ValidationMessage.Source;
import org.hl7.fhir.utilities.validation.ValidationOptions;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.hl7.fhir.validation.BaseValidator;
import org.hl7.fhir.validation.ValidatorSettings;
import org.hl7.fhir.validation.ai.CodeAndTextValidationRequest;
import org.hl7.fhir.validation.ai.CodeAndTextValidationResult;
import org.hl7.fhir.validation.ai.CodeAndTextValidator;
import org.hl7.fhir.validation.service.model.HtmlInMarkdownCheck;
import org.hl7.fhir.validation.service.utils.QuestionnaireMode;
import org.hl7.fhir.validation.codesystem.CodingsObserver;
import org.hl7.fhir.validation.instance.type.BundleValidator;
import org.hl7.fhir.validation.instance.type.CodeSystemValidator;
import org.hl7.fhir.validation.instance.type.ConceptMapValidator;
import org.hl7.fhir.validation.instance.type.ImplementationGuideValidator;
import org.hl7.fhir.validation.instance.type.MeasureValidator;
import org.hl7.fhir.validation.instance.type.ObservationValidator;
import org.hl7.fhir.validation.instance.type.OperationDefinitionValidator;
import org.hl7.fhir.validation.instance.type.QuestionnaireValidator;
import org.hl7.fhir.validation.instance.type.SearchParameterValidator;
import org.hl7.fhir.validation.instance.type.StructureDefinitionValidator;
import org.hl7.fhir.validation.instance.type.StructureMapValidator;
import org.hl7.fhir.validation.instance.type.StructureMapValidator.VariableDefn;
import org.hl7.fhir.validation.instance.type.StructureMapValidator.VariableSet;
import org.hl7.fhir.validation.instance.type.ValueSetValidator;
import org.hl7.fhir.validation.instance.type.ViewDefinitionValidator;
import org.hl7.fhir.validation.instance.type.XhtmlValidator;
import org.hl7.fhir.validation.instance.utils.Base64Util;
import org.hl7.fhir.validation.instance.utils.CanonicalResourceLookupResult;
import org.hl7.fhir.validation.instance.utils.CanonicalTypeSorter;
import org.hl7.fhir.validation.instance.utils.ChildIterator;
import org.hl7.fhir.validation.instance.utils.ElementInfo;
import org.hl7.fhir.validation.instance.utils.EnableWhenEvaluator;
import org.hl7.fhir.validation.instance.utils.FHIRPathExpressionFixer;
import org.hl7.fhir.validation.instance.utils.IndexedElement;
import org.hl7.fhir.validation.instance.utils.NodeStack;
import org.hl7.fhir.validation.instance.utils.ResolvedReference;
import org.hl7.fhir.validation.instance.utils.ResourceValidationTracker;
import org.hl7.fhir.validation.instance.utils.StructureDefinitionSorterByUrl;
import org.hl7.fhir.validation.instance.utils.ValidationContext;
import org.slf4j.Logger;
import org.w3c.dom.Document;


/**
 * Thinking of using this in a java program? Don't!
 * You should use one of the wrappers instead. Either in HAPI, or use ValidationEngine
 * <p>
 * Validation todo:
 * - support @default slices
 *
 * @author Grahame Grieve
 */
/*
 * todo:
 * check urn's don't start oid: or uuid:
 * check MetadataResource.url is absolute
 */
@Slf4j
public class InstanceValidator extends BaseValidator implements IResourceValidator {
  
  public enum MatchetypeStatus {
    Disallowed, Allowed, Required
  }

  public enum BindingContext {
    BASE, MAXVS, ADDITIONAL

  }

  private static final String EXECUTED_CONSTRAINT_LIST = "validator.executed.invariant.list";
  private static final String EXECUTION_ID = "validator.execution.id";
  private static final String HTML_FRAGMENT_REGEX = "[a-zA-Z]\\w*(((\\s+)(\\S)*)*)";
  private static final boolean STACK_TRACE = false;
  private static final boolean DEBUG_ELEMENT = false;
  private static final boolean SAVE_INTERMEDIARIES = false; // set this to true to get the intermediary formats while we are waiting for a UI around this z(SHC/SHL)
  
  private static final HashSet<String> NO_TX_SYSTEM_EXEMPT = new HashSet<>(Arrays.asList("http://loinc.org", "http://unitsofmeasure.org", "http://hl7.org/fhir/sid/icd-9-cm", "http://snomed.info/sct", "http://www.nlm.nih.gov/research/umls/rxnorm"));
  private static final HashSet<String> NO_HTTPS_LIST = new HashSet<>(Arrays.asList("https://loinc.org", "https://unitsofmeasure.org", "https://snomed.info/sct", "https://www.nlm.nih.gov/research/umls/rxnorm"));
  private static final HashSet<String> EXTENSION_CONTEXT_LIST = new HashSet<>(Arrays.asList("ElementDefinition.example.value", "ElementDefinition.pattern", "ElementDefinition.fixed"));      
  private static final HashSet<String> ID_EXEMPT_LIST = new HashSet<>(Arrays.asList("id", "base64Binary", "markdown"));
  private static final HashSet<String> RESOURCE_X_POINTS = new HashSet<>(Arrays.asList("Bundle.entry.resource", "Bundle.entry.response.outcome", "DomainResource.contained", "Parameters.parameter.resource", "Parameters.parameter.part.resource"));
  
  private class ValidatorHostServices implements IHostApplicationServices {

    @Override
    public List<Base> resolveConstant(FHIRPathEngine engine, Object appContext, String name, FHIRPathConstantEvaluationMode mode) throws PathEngineException {
      ValidationContext c = (ValidationContext) appContext;
      if (mode == FHIRPathConstantEvaluationMode.EXPLICIT && "profile".equals(name)) {
        List<Base> b = new ArrayList<>();
        if (c.getProfile() != null) {
          b.add(c.getProfile());
        }
        return b;
      }
      if (externalHostServices != null)
        return externalHostServices.resolveConstant(engine, c.getAppContext(), name, mode);
      else
        return new ArrayList<Base>();
    }

    @Override
    public TypeDetails resolveConstantType(FHIRPathEngine engine, Object appContext, String name, FHIRPathConstantEvaluationMode mode) throws PathEngineException {
      if (appContext instanceof VariableSet) {
        VariableSet vars = (VariableSet) appContext;
        VariableDefn v = vars.getVariable(name);
        if (v != null && v.hasTypeInfo()) {
          return new TypeDetails(CollectionStatus.SINGLETON, v.getWorkingType());
        } else {
          return null;
        }
      }
      ValidationContext c = (ValidationContext) appContext;
      if (externalHostServices != null)
        return externalHostServices.resolveConstantType(engine, c.getAppContext(), name, mode);
      else
        return null;
    }

    @Override
    public boolean log(String argument, List<Base> focus) {
      if (externalHostServices != null)
        return externalHostServices.log(argument, focus);
      else
        return false;
    }

    @Override
    public FunctionDetails resolveFunction(FHIRPathEngine engine, String functionName) {
      switch (functionName) {
      case "slice": return new FunctionDetails("Returns the given slice as defined in the given structure definition. If in an invariant, First parameter can be %profile - current profile", 2, 2);
      case "getResourceKey" : return new FunctionDetails("Unique Key for resource", 0, 0);
      case "getReferenceKey" : return new FunctionDetails("Unique Key for resource that is the target of the reference", 0, 1);
      default:
        if (externalHostServices != null) {
          return externalHostServices.resolveFunction(engine, functionName);
        } else {
          return null;
        } 
      }
    }

    @Override
    public TypeDetails checkFunction(FHIRPathEngine engine, Object appContext, String functionName, TypeDetails focus, List<TypeDetails> parameters) throws PathEngineException {

      switch (functionName) {
      case "slice":
        // todo: check parameters 
        return focus;

      case "getResourceKey" : return new TypeDetails(CollectionStatus.SINGLETON, "string");
      case "getReferenceKey" : return new TypeDetails(CollectionStatus.SINGLETON, "string");
      default: 
        if (externalHostServices != null) {
          return externalHostServices.checkFunction(engine, appContext, functionName, focus, parameters);
        } else {
          throw new Error(context.formatMessage(I18nConstants.NOT_DONE_YET_VALIDATORHOSTSERVICESCHECKFUNCTION));
        }
      }
    }

    @Override
    public List<Base> executeFunction(FHIRPathEngine engine, Object appContext, List<Base> focus, String functionName, List<List<Base>> parameters) {
      switch (functionName) {
      case "slice": return executeSlice(engine, appContext, focus, parameters);case "getResourceKey" : return executeResourceKey(focus);
      case "getReferenceKey" : return executeReferenceKey(null, focus, parameters);
      default: 
        if (externalHostServices != null) {
          return externalHostServices.executeFunction(engine, appContext, focus, functionName, parameters);
        } else {
          throw new Error(context.formatMessage(I18nConstants.NOT_DONE_YET_VALIDATORHOSTSERVICESEXECUTEFUNCTION));
        }
      }
    }


    private List<Base> executeResourceKey(List<Base> focus) {
      List<Base> base = new ArrayList<Base>();
      if (focus.size() == 1) {
        Base res = focus.get(0);
        base.add(new StringType(res.fhirType()+"/"+res.getIdBase()));
      }
      return base;
    }
    
    private List<Base> executeReferenceKey(Base rootResource, List<Base> focus, List<List<Base>> parameters) {
      List<Base> base = new ArrayList<Base>();
      if (focus.size() == 1) {
        Base res = focus.get(0);
        String ref = null;
        if (res.fhirType().equals("Reference")) {
          ref = getRef(res);
        } else if (res.isPrimitive()) {
          ref = res.primitiveValue();
        } else {
          throw new FHIRException("Unable to generate a reference key based on a "+res.fhirType());
        }
        base.add(new StringType(ref));
      }
      return base;
    }

    private String getRef(Base res) {
      Property prop = res.getChildByName("reference");
      if (prop != null && prop.getValues().size() == 1) {
        return prop.getValues().get(0).primitiveValue();
      }
      return null;
    }
    
    private List<Base> executeSlice(FHIRPathEngine engine, Object appContext, List<Base> focus, List<List<Base>> parameters) {
      ValidationContext c = (ValidationContext) appContext;
      
      List<Base> res = new ArrayList<>();
      if (parameters.size() != 2 && !(appContext instanceof ValidationContext)) {
        return res;
      }
      
      StructureDefinition sd = null;
      // if present, first parameter must be a singleton that points to the current profile
      if (parameters.get(0).size() > 1) {
        return res;
      } else if (parameters.get(0).size() == 1) { // if it's there, we have to check it 
        Base b = parameters.get(0).get(0);
        if (b.isPrimitive()) {
          sd = context.fetchResource(StructureDefinition.class, b.primitiveValue());
        } else if (b instanceof StructureDefinition) {
          sd = (StructureDefinition) b;
        }
      } else {
        sd = c.getProfile();
      }

      // second parameter must be present 
      if (parameters.get(1).size() != 1) {
        return res;
      }  
      String name = parameters.get(1).get(0).primitiveValue();
      if (!Utilities.noString(name)) {
        for (Base b : focus) {
          if (b instanceof Element) {
            Element e = (Element) b;
            if (e.hasSlice(sd, name)) {
              res.add(e);
            }
          }
        }
      }
      return res;
    }

    @Override
    public Base resolveReference(FHIRPathEngine engine, Object appContext, String url, Base refContext) throws FHIRException {
      ValidationContext c = (ValidationContext) appContext;

      if (refContext != null && refContext.hasUserData(UserDataNames.validator_bundle_resolution)) {
        return (Base) refContext.getUserData(UserDataNames.validator_bundle_resolution);
      }

      if (c.getAppContext() instanceof Element) {
        Element element = (Element) c.getAppContext();
        while (element != null) {
          Base res = resolveInBundle(url, element);
          if (res != null) {
            return res;
          }
          element = element.getParentForValidator();  
        }
      }
      Base res = resolveInBundle(url, c.getResource());
      if (res != null) {
        return res;
      }
      Element element = c.getRootResource();
      while (element != null) {
        res = resolveInBundle(url, element);
        if (res != null) {
          return res;
        }
        element = element.getParentForValidator();  
      }

      if (externalHostServices != null) {
        return setParentsBase(externalHostServices.resolveReference(engine, c.getAppContext(), url, refContext));
      } else if (fetcher != null) {
        try {
          return setParents(fetcher.fetch(InstanceValidator.this, c.getAppContext(), url));
        } catch (IOException e) {
          throw new FHIRException(e);
        }
      } else {
        throw new Error(context.formatMessage(I18nConstants.NOT_DONE_YET__RESOLVE__LOCALLY_2, url));
      }
    }

   
    @Override
    public boolean conformsToProfile(FHIRPathEngine engine, Object appContext, Base item, String url) throws FHIRException {
      ValidationContext ctxt = (ValidationContext) appContext;
      StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
      if (sd == null) {
        throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_RESOLVE_, url));
      }
      InstanceValidator self = InstanceValidator.this;
      List<ValidationMessage> valerrors = new ArrayList<ValidationMessage>();
      ValidationMode mode = new ValidationMode(ValidationReason.Expression, ProfileSource.FromExpression);
      if (item instanceof Resource) {
        try {
          Element e = new ObjectConverter(context).convert((Resource) item);
          setParents(e);
          self.validateResource(new ValidationContext(ctxt.getAppContext(), e), valerrors, e, e, sd, IdStatus.OPTIONAL, new NodeStack(context, null, e, validationLanguage), null,
              mode, false, false);
        } catch (IOException e1) {
          throw new FHIRException(e1);
        }
      } else if (item instanceof Element) {
        Element e = (Element) item;
        if (e.getSpecial() == SpecialElement.CONTAINED) {
          self.validateResource(new ValidationContext(ctxt.getAppContext(), e, ctxt.getRootResource(), ctxt.getGroupingResource()), valerrors, e, e, sd, IdStatus.OPTIONAL, new NodeStack(context, null, e, validationLanguage), null, mode, false, false);          
        } else if (e.getSpecial() != null) {
          self.validateResource(new ValidationContext(ctxt.getAppContext(), e, e, ctxt.getRootResource()), valerrors, e, e, sd, IdStatus.OPTIONAL, new NodeStack(context, null, e, validationLanguage), null, mode, false, false);          
        } else {
          self.validateResource(new ValidationContext(ctxt.getAppContext(), e), valerrors, e, e, sd, IdStatus.OPTIONAL, new NodeStack(context, null, e, validationLanguage), null, mode, false, false);
        }
      } else
        throw new NotImplementedException(context.formatMessage(I18nConstants.NOT_DONE_YET_VALIDATORHOSTSERVICESCONFORMSTOPROFILE_WHEN_ITEM_IS_NOT_AN_ELEMENT));
      boolean ok = true;
      List<ValidationMessage> record = new ArrayList<>();
      for (ValidationMessage v : valerrors) {
        ok = ok && !v.getLevel().isError();
        if (v.getLevel().isError() || v.isSlicingHint()) {
          record.add(v);
        }
      }
      if (!ok && !record.isEmpty()) {
        ctxt.sliceNotes(url, record);
      }
      return ok;
    }

    @Override
    public ValueSet resolveValueSet(FHIRPathEngine engine, Object appContext, String url) {
      ValidationContext c = (ValidationContext) appContext;
      if (c.getProfile() != null && url.startsWith("#")) {
        for (Resource r : c.getProfile().getContained()) {
          if (r.getId().equals(url.substring(1))) {
            if (r instanceof ValueSet)
              return (ValueSet) r;
            else
              throw new FHIRException(context.formatMessage(I18nConstants.REFERENCE__REFERS_TO_A__NOT_A_VALUESET, url, r.fhirType()));
          }
        }
        return null;
      }
      return context.findTxResource(ValueSet.class, cu.pinValueSet(url));
    }

    @Override
    public boolean paramIsType(String name, int index) {
      return false;
    }

  }
  private FHIRPathEngine fpe;

  public FHIRPathEngine getFHIRPathEngine() {
    return fpe;
  }

  // configuration items
  private CheckDisplayOption checkDisplay;
  private boolean anyExtensionsAllowed;
  private boolean errorForUnknownProfiles;
  private boolean noInvariantChecks;
  private boolean wantInvariantInMessage;
  private boolean hintAboutNonMustSupport;
  private boolean showMessagesFromReferences;
  @Getter
  @Setter
  private String validationLanguage;
  private boolean baseOnly;
  private boolean noCheckAggregation;
  private boolean wantCheckSnapshotUnchanged;
  private boolean noUnicodeBiDiControlChars;
  private HtmlInMarkdownCheck htmlInMarkdownCheck;
  private boolean allowComments;
  private boolean allowDoubleQuotesInFHIRPath;
 
  private List<ImplementationGuide> igs = new ArrayList<>();
  private List<String> extensionDomains = new ArrayList<String>();

  private IdStatus resourceIdRule;
  private boolean allowXsiLocation;

  // used during the build process to keep the overall volume of messages down
  private boolean suppressLoincSnomedMessages;

  // time tracking
  private boolean noBindingMsgSuppressed;
  private Map<String, Element> fetchCache = new HashMap<>();
  private HashMap<Element, ResourceValidationTracker> resourceTracker = new HashMap<>();
  long time = 0;
  long start = 0;
  long lastlog = 0;
  @Getter
  private IHostApplicationServices externalHostServices;
  private boolean noExtensibleWarnings;
  private String serverBase;

  private EnableWhenEvaluator myEnableWhenEvaluator = new EnableWhenEvaluator();
  private String executionId;
  private IValidationProfileUsageTracker tracker;
  private ValidatorHostServices validatorServices;
  private boolean securityChecks;
  private ProfileUtilities profileUtilities;
  private boolean crumbTrails;
  private List<BundleValidationRule> bundleValidationRules = new ArrayList<>();
  private boolean validateValueSetCodesOnTxServer = true;
  private QuestionnaireMode questionnaireMode;
  private Map<String, CanonicalResourceLookupResult> crLookups = new HashMap<>();
  private boolean logProgress;
  private CodingsObserver codingObserver;
  public List<ValidatedFragment> validatedContent;
  public boolean testMode;
  private boolean example ;
  private IDigitalSignatureServices signatureServices;
  private boolean unknownCodeSystemsCauseErrors;
  private boolean noExperimentalContent;
  private List<CodeAndTextValidationRequest> textsToCheck = new ArrayList<>();
  private String aiService;
  private Set<Integer> textsToCheckKeys = new HashSet<>();
  private String cacheFolder;
  private MatchetypeStatus matchetypeStatus = MatchetypeStatus.Disallowed;
  private OIDUtilities oids;

  public InstanceValidator(@Nonnull IWorkerContext theContext, @Nonnull IHostApplicationServices hostServices, @Nonnull XVerExtensionManager xverManager, ValidatorSession session, @Nonnull ValidatorSettings settings) {
    super(theContext, settings, xverManager, session);
    start = System.currentTimeMillis();
    this.externalHostServices = hostServices;
    this.profileUtilities = new ProfileUtilities(theContext, null, null);
    fpe = new FHIRPathEngine(context);
    validatorServices = new ValidatorHostServices();
    fpe.setHostServices(validatorServices);
    if (theContext.getVersion().startsWith("3.0") || theContext.getVersion().startsWith("1.0"))
      fpe.setLegacyMode(true);
    settings.setSource(Source.InstanceValidator);
    fpe.setDoNotEnforceAsSingletonRule(!VersionUtilities.isR5VerOrLater(theContext.getVersion()));
    fpe.setAllowDoubleQuotes(allowDoubleQuotesInFHIRPath);
    codingObserver = new CodingsObserver(theContext, settings, xverManager, session);
    oids = new OIDUtilities();
  }

  @Override
  public boolean isNoExtensibleWarnings() {
    return noExtensibleWarnings;
  }

  @Override
  public IResourceValidator setNoExtensibleWarnings(boolean noExtensibleWarnings) {
    this.noExtensibleWarnings = noExtensibleWarnings;
    return this;
  }

  @Override
  public boolean isShowMessagesFromReferences() {
    return showMessagesFromReferences;
  }

  @Override
  public void setShowMessagesFromReferences(boolean showMessagesFromReferences) {
    this.showMessagesFromReferences = showMessagesFromReferences;
  }

  @Override
  public boolean isNoInvariantChecks() {
    return noInvariantChecks;
  }

  @Override
  public IResourceValidator setNoInvariantChecks(boolean value) {
    this.noInvariantChecks = value;
    return this;
  }

  @Override
  public boolean isWantInvariantInMessage() {
    return wantInvariantInMessage;
  }

  @Override
  public IResourceValidator setWantInvariantInMessage(boolean wantInvariantInMessage) {
    this.wantInvariantInMessage = wantInvariantInMessage;
    return this;
  }

  public IValidationProfileUsageTracker getTracker() {
    return this.tracker;
  }

  public IResourceValidator setTracker(IValidationProfileUsageTracker value) {
    this.tracker = value;
    return this;
  }


  public boolean isHintAboutNonMustSupport() {
    return hintAboutNonMustSupport;
  }

  public void setHintAboutNonMustSupport(boolean hintAboutNonMustSupport) {
    this.hintAboutNonMustSupport = hintAboutNonMustSupport;
  }

  public boolean isAssumeValidRestReferences() {
    return settings.isAssumeValidRestReferences();
  }

  public void setAssumeValidRestReferences(boolean value) {
    settings.setAssumeValidRestReferences(value);
  }

  public boolean isAllowComments() {
    return allowComments;
  }

  public void setAllowComments(boolean allowComments) {
    this.allowComments = allowComments;
  }

  public boolean isCrumbTrails() {
    return crumbTrails;
  }

  public void setCrumbTrails(boolean crumbTrails) {
    this.crumbTrails = crumbTrails;
  }

  public boolean isDoImplicitFHIRPathStringConversion() {
    return fpe.isDoImplicitStringConversion();
  }

  public void setDoImplicitFHIRPathStringConversion(boolean doImplicitFHIRPathStringConversion) {
    fpe.setDoImplicitStringConversion(doImplicitFHIRPathStringConversion);
  }

  private boolean allowUnknownExtension(String url) {
    if ((settings.isAllowExamples() && (url.contains("example.org") || url.contains("acme.com"))) || url.contains("nema.org") || url.startsWith("http://hl7.org/fhir/tools/StructureDefinition/") || url.equals("http://hl7.org/fhir/StructureDefinition/structuredefinition-expression"))
      // Added structuredefinition-expression explicitly because it wasn't defined in the version of the spec it needs to be used with
      return true;
    for (String s : extensionDomains)
      if (url.startsWith(s))
        return true;
    return anyExtensionsAllowed;
  }

  private boolean isKnownExtension(String url) {
    // Added structuredefinition-expression and following extensions explicitly because they weren't defined in the version of the spec they need to be used with
    if ((settings.isAllowExamples() && (url.contains("example.org") || url.contains("acme.com"))) || url.contains("nema.org") || 
        url.startsWith("http://hl7.org/fhir/tools/StructureDefinition/") || url.equals("http://hl7.org/fhir/StructureDefinition/structuredefinition-expression") ||
        url.equals("http://hl7.org/fhir/StructureDefinition/codesystem-properties-mode"))
      return true;
    for (String s : extensionDomains)
      if (url.startsWith(s))
        return true;
    return false;
  }
  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, InputStream stream, FhirFormat format) throws FHIRException {
    return validate(appContext, errors, stream, format, new ArrayList<>());
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, InputStream stream, FhirFormat format, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    return validate(appContext, errors, stream, format, profiles);
  }

  private StructureDefinition getSpecifiedProfile(String profile) {
    StructureDefinition sd = context.fetchResource(StructureDefinition.class, profile);
    if (sd == null) {
      throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_LOCATE_THE_PROFILE__IN_ORDER_TO_VALIDATE_AGAINST_IT, profile));
    }
    return sd;
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, InputStream stream, FhirFormat format, List<StructureDefinition> profiles) throws FHIRException {
    ParserBase parser = Manager.makeParser(context, format);
    List<StructureDefinition> logicals = new ArrayList<>();
    for (StructureDefinition sd : profiles) {
      if (sd.getKind() == StructureDefinitionKind.LOGICAL) {
        logicals.add(sd);
      }
    }
    if (logicals.size() > 0) {
      if (rulePlural(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, "Configuration", logicals.size() == 1, logicals.size(), I18nConstants.MULTIPLE_LOGICAL_MODELS, ResourceUtilities.listUrls(logicals))) {
        parser.setLogical(logicals.get(0));              
      } 
    }
    if (parser instanceof XmlParser) {
      ((XmlParser) parser).setAllowXsiLocation(allowXsiLocation);
    }
    parser.setupValidation(ValidationPolicy.EVERYTHING);
    if (parser instanceof XmlParser) {
      ((XmlParser) parser).setAllowXsiLocation(allowXsiLocation);
    }
    if (parser instanceof JsonParser) {
      ((JsonParser) parser).setAllowComments(allowComments);
    }
    parser.setSignatureServices(signatureServices);
    
    long t = System.nanoTime();
    validatedContent = null;
    try {
      validatedContent = parser.parse(stream);
    } catch (IOException e1) {
      throw new FHIRException(e1);
    }
    timeTracker.load(t);
    if (validatedContent != null && !validatedContent.isEmpty()) {
      if (SAVE_INTERMEDIARIES) {
        int index = 0;
        for (ValidatedFragment ne : validatedContent) {
          index++;
          saveValidatedContent(ne, index);
        }
      }
      String url = parser.getImpliedProfile();
      if (url != null) {
        StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
        if (sd == null) {
          rule(errors, NO_RULE_DATE, IssueType.NOTFOUND, "Payload", false, "Implied profile "+url+" not known to validator");          
        } else {
          profiles.add(sd);
        }
      }
      for (ValidatedFragment ne : validatedContent) {
        if (ne.getElement() != null) {
          validate(appContext, ne.getErrors(), validatedContent.size() > 1 ? ne.path() : null, ne.getElement(), profiles);
        } 
        errors.addAll(ne.getErrors());         
      }
    }
    return (validatedContent == null || validatedContent.isEmpty()) ? null : validatedContent.get(0).getElement(); // todo: this is broken, but fixing it really complicates things elsewhere, so we do this for now
  }

  private void saveValidatedContent(ValidatedFragment ne, int index) {
    String tgt = null;
    try {
      tgt = Utilities.path("[tmp]", "validator", "content");
      FileUtilities.createDirectory(tgt);
      tgt = Utilities.path(tgt, "content-"+index+"-"+ne.getFilename());
      FileUtilities.bytesToFile(ne.getContent(), tgt);
    } catch (Exception e) {
      log.error("Error saving internal content to '"+tgt+"': "+e.getLocalizedMessage());
    }
    
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Resource resource) throws FHIRException {
    return validate(appContext, errors, resource, new ArrayList<>());
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Resource resource, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    return validate(appContext, errors, resource, profiles);
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Resource resource, List<StructureDefinition> profiles) throws FHIRException {
    long t = System.nanoTime();
    Element e = new ResourceParser(context).parse(resource);
    timeTracker.load(t);
    validate(appContext, errors, null, e, profiles);
    return e;
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, org.w3c.dom.Element element) throws FHIRException {
    return validate(appContext, errors, element, new ArrayList<>());
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, org.w3c.dom.Element element, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    return validate(appContext, errors, element, profiles);
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, org.w3c.dom.Element element, List<StructureDefinition> profiles) throws FHIRException {
    XmlParser parser = new XmlParser(context);
    parser.setupValidation(ValidationPolicy.EVERYTHING);
    long t = System.nanoTime();
    Element e;
    try {
      e = parser.parse(errors, element);
    } catch (IOException e1) {
      throw new FHIRException(e1);
    }
    timeTracker.load(t);
    if (e != null) {
      validate(appContext, errors, null, e, profiles);
    }
    return e;
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Document document) throws FHIRException {
    return validate(appContext, errors, document, new ArrayList<>());
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Document document, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    return validate(appContext, errors, document, profiles);
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, Document document, List<StructureDefinition> profiles) throws FHIRException {
    XmlParser parser = new XmlParser(context);
    parser.setupValidation(ValidationPolicy.EVERYTHING);
    long t = System.nanoTime();
    Element e;
    try {
      e = parser.parse(errors, document);
    } catch (IOException e1) {
      throw new FHIRException(e1);
    }
    timeTracker.load(t);
    if (e != null)
      validate(appContext, errors, null, e, profiles);
    return e;
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, JsonObject object) throws FHIRException {
    return validate(appContext, errors, object, new ArrayList<>());
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, JsonObject object, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    return validate(appContext, errors, object, profiles);
  }

  @Override
  public org.hl7.fhir.r5.elementmodel.Element validate(Object appContext, List<ValidationMessage> errors, JsonObject object, List<StructureDefinition> profiles) throws FHIRException {
    JsonParser parser = new JsonParser(context, new ProfileUtilities(context, null, null, fpe));
    parser.setupValidation(ValidationPolicy.EVERYTHING);
    long t = System.nanoTime();
    Element e = parser.parse(errors, object);
    timeTracker.load(t);
    if (e != null)
      validate(appContext, errors, null, e, profiles);
    return e;
  }

  @Override
  public void validate(Object appContext, List<ValidationMessage> errors, String initialPath, Element element) throws FHIRException {
    validate(appContext, errors, initialPath, element, new ArrayList<>());
  }

  @Override
  public void validate(Object appContext, List<ValidationMessage> errors, String initialPath, Element element, String profile) throws FHIRException {
    ArrayList<StructureDefinition> profiles = new ArrayList<>();
    if (profile != null) {
      profiles.add(getSpecifiedProfile(profile));
    }
    validate(appContext, errors, initialPath, element, profiles);
  }

  @Override
  public void validate(Object appContext, List<ValidationMessage> errors, String path, Element element, List<StructureDefinition> profiles) throws FHIRException {
    // this is the main entry point; all the other public entry points end up here coming here...
    // so the first thing to do is to clear the internal state
    fetchCache.clear();
    fetchCache.put(element.fhirType() + "/" + element.getIdBase(), element);
    resourceTracker.clear();
    trackedMessages.clear();
    messagesToRemove.clear();
    executionId = UUID.randomUUID().toString();
    baseOnly = profiles.isEmpty();
    setParents(element);

    long t = System.nanoTime();
    NodeStack stack = new NodeStack(context, null, element, validationLanguage);
    if (profiles == null || profiles.isEmpty()) {
      validateResource(new ValidationContext(appContext, element), errors, element, element, null, resourceIdRule, stack.resetIds(), null, new ValidationMode(ValidationReason.Validation, ProfileSource.BaseDefinition), false, false);
    } else {
      int i = 0;
      while (i < profiles.size()) {
        StructureDefinition sd = profiles.get(i);
        if (sd.hasExtension(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
          for (Extension ext : sd.getExtensionsByUrl(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
            StructureDefinition dep = context.fetchResource( StructureDefinition.class, ext.getValue().primitiveValue(), sd);
            if (dep == null) {
              warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_DEPENDS_NOT_RESOLVED, ext.getValue().primitiveValue(), sd.getVersionedUrl());                
            } else if (!profiles.contains(dep)) {
              profiles.add(dep);
            }
          }
        }
        i++;
      }
      for (StructureDefinition defn : profiles) {
        stack.getIds().clear();
        validateResource(new ValidationContext(appContext, element), errors, element, element, defn, resourceIdRule, stack.resetIds(), null, new ValidationMode(ValidationReason.Validation, ProfileSource.ConfigProfile), false, false);
      }
    }
    if (hintAboutNonMustSupport && !profiles.isEmpty()) {
      checkElementUsage(errors, element, stack);
    }
    codingObserver.finish(errors, stack);
    errors.removeAll(messagesToRemove);
    timeTracker.overall(t);
    if (aiService != null && !textsToCheck.isEmpty()) {
      t = System.nanoTime();
      List<CodeAndTextValidationRequest> list = new ArrayList<>();
      for (CodeAndTextValidationRequest tt : textsToCheck) {
        ValidationResult vr = context.validateCode(settings.setDisplayWarningMode(false).setLanguages(tt.getLang()), tt.getSystem(), null, tt.getCode(), tt.getText());
        if (!vr.isOk()) {
          list.add(tt);          
        }
      }
      if (!list.isEmpty()) {
        CodeAndTextValidator ctv = new CodeAndTextValidator(cacheFolder, aiService);
        List<CodeAndTextValidationResult> results = null;
        try {
          results = ctv.validateCodings(list);
        } catch (Exception e) {
          if (e.getCause() != null && e.getCause() instanceof HTTPResultException) {
            warning(errors, "2025-01-14", IssueType.EXCEPTION, stack, false,
                I18nConstants.VALIDATION_AI_FAILED_LOG, e.getMessage(), ((HTTPResultException)e.getCause()).logPath);   
          } else {
            warning(errors, "2025-01-14", IssueType.EXCEPTION, stack, false,
                I18nConstants.VALIDATION_AI_FAILED, e.getMessage()); 
          }
        }
        if (results != null) {
          for (CodeAndTextValidationResult vr : results) {
            if (!vr.isValid()) {
              warning(errors, "2025-01-14", IssueType.BUSINESSRULE, vr.getRequest().getLocation().line(), vr.getRequest().getLocation().col(), vr.getRequest().getLocation().getLiteralPath(), false,
                  I18nConstants.VALIDATION_AI_TEXT_CODE, vr.getRequest().getCode(), vr.getRequest().getText(), vr.getConfidence(), vr.getExplanation());                
            }
          }
        }
      }
      timeTracker.ai(t);
    }
    
    if (settings.isDebug()) {
      try {
        debugElement(element, log);
      } catch (IOException e) {
       log.error(e.getMessage(), e);
      }
    }
  }

  protected void debugElement(Element element, Logger log) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    element.printToOutput(printStream);
    log.debug(outputStream.toString());
  }
  private void checkElementUsage(List<ValidationMessage> errors, Element element, NodeStack stack) {
    if (element.getPath()==null
      || (element.getName().equals("id")  && !element.getPath().substring(0, element.getPath().length()-3).contains("."))
      || (element.getName().equals("text")  && !element.getPath().substring(0, element.getPath().length()-5).contains(".")))
      return;
    String hasFixed = element.getUserString(UserDataNames.keyview_hasFixed);
    if (element.getPath().contains(".") && (hasFixed== null || !hasFixed.equals("Y"))) {
      String elementUsage = element.getUserString(UserDataNames.keyview_elementSupported);
      hint(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), elementUsage != null && (elementUsage.equals("Y") || elementUsage.equals("NA")), I18nConstants.MUSTSUPPORT_VAL_MUSTSUPPORT, element.getName(), element.getProperty().getStructure().getVersionedUrl());
      if (elementUsage==null || !elementUsage.equals("Y"))
        return;
    }

    if (element.hasChildren() && (hasFixed== null || !hasFixed.equals("Y"))) {
      String prevName = "";
      int elementCount = 0;
      for (Element ce : element.getChildren()) {
        if (ce.getName().equals(prevName))
          elementCount++;
        else {
          elementCount = 1;
          prevName = ce.getName();
        }
        checkElementUsage(errors, ce, stack.push(ce, elementCount, null, null));
      }
    }
  }

  private boolean check(String v1, String v2) {
    boolean res = v1 == null ? Utilities.noString(v1) : v1.equals(v2);
    return res;
  }

  private boolean checkAddress(List<ValidationMessage> errors, String path, Element focus, Address fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".use", focus.getNamedChild("use", false), fixed.getUseElement(), fixedSource, "use", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".text", focus.getNamedChild("text", false), fixed.getTextElement(), fixedSource, "text", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".city", focus.getNamedChild("city", false), fixed.getCityElement(), fixedSource, "city", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".state", focus.getNamedChild("state", false), fixed.getStateElement(), fixedSource, "state", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".country", focus.getNamedChild("country", false), fixed.getCountryElement(), fixedSource, "country", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".zip", focus.getNamedChild("zip", false), fixed.getPostalCodeElement(), fixedSource, "postalCode", focus, pattern, context) && ok;

    List<Element> lines = new ArrayList<Element>();
    focus.getNamedChildren("line", lines);
    boolean lineSizeCheck;
    
    if (pattern) {
      lineSizeCheck = lines.size() >= fixed.getLine().size();
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, lineSizeCheck, I18nConstants.FIXED_TYPE_CHECKS_DT_ADDRESS_LINE, Integer.toString(fixed.getLine().size()),
        Integer.toString(lines.size()))) {
        for (int i = 0; i < fixed.getLine().size(); i++) {
          StringType fixedLine = fixed.getLine().get(i);
          boolean found = false;
          List<ValidationMessage> allErrorsFixed = new ArrayList<>();
          List<ValidationMessage> errorsFixed = null;
          for (int j = 0; j < lines.size() && !found; ++j) {
            errorsFixed = new ArrayList<>();
            checkFixedValue(errorsFixed, path + ".line", lines.get(j), fixedLine, fixedSource, "line", focus, pattern, context);
            if (!hasErrors(errorsFixed)) {
              found = true;
            } else {
              errorsFixed.stream().filter(t -> t.getLevel().ordinal() >= IssueSeverity.ERROR.ordinal()).forEach(t -> allErrorsFixed.add(t));
            }
          }
          if (!found) {
            ok = rule(errorsFixed, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, false, I18nConstants.PATTERN_CHECK_STRING, fixedLine.getValue(), fixedSource, allErrorsFixed) && ok;
          }
        }
      } else {
        ok = false;
      }
    } else if (!pattern) {
      lineSizeCheck = lines.size() == fixed.getLine().size();
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, lineSizeCheck, I18nConstants.FIXED_TYPE_CHECKS_DT_ADDRESS_LINE,
        Integer.toString(fixed.getLine().size()), Integer.toString(lines.size()))) {
        for (int i = 0; i < lines.size(); i++) {
          ok = checkFixedValue(errors, path + ".line", lines.get(i), fixed.getLine().get(i), fixedSource, "line", focus, pattern, context) && ok;
        }
      } else {
        ok = false;
      }
    }  
    return ok;
  }

  private boolean checkAttachment(List<ValidationMessage> errors, String path, Element focus, Attachment fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".contentType", focus.getNamedChild("contentType", false), fixed.getContentTypeElement(), fixedSource, "contentType", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".language", focus.getNamedChild("language", false), fixed.getLanguageElement(), fixedSource, "language", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".data", focus.getNamedChild("data", false), fixed.getDataElement(), fixedSource, "data", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".url", focus.getNamedChild("url", false), fixed.getUrlElement(), fixedSource, "url", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".size", focus.getNamedChild("size", false), fixed.getSizeElement(), fixedSource, "size", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".hash", focus.getNamedChild("hash", false), fixed.getHashElement(), fixedSource, "hash", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".title", focus.getNamedChild("title", false), fixed.getTitleElement(), fixedSource, "title", focus, pattern, context) && ok;

    return ok;
  }

  // public API
  private boolean checkCode(List<ValidationMessage> errors, Element element, String path, String code, String system, String version, String display, boolean checkDisplay, NodeStack stack) throws TerminologyServiceException {
    boolean ok = true;
    long t = System.nanoTime();
    boolean ss = context.supportsSystem(system);
    timeTracker.tx(t, "ss "+system);
    if (ss) {
      t = System.nanoTime();
      ValidationResult s = checkCodeOnServer(stack, code, system, version, display, checkDisplay);
      timeTracker.tx(t, "vc "+system+"#"+code+" '"+display+"'");
      if (s == null)
        return true;
      ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, s, element, path, false, null, null) & ok;

      if (s.isOk()) {
        if (s.getMessage() != null && !s.messageIsInIssues()) {
          txWarning(errors, NO_RULE_DATE, s.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, s == null, I18nConstants.TERMINOLOGY_PASSTHROUGH_TX_MESSAGE, s.getMessage(), system, code);
        }
        return ok;
      }
      if (!s.messageIsInIssues()) {
        if (s.getErrorClass() != null && s.getErrorClass().isInfrastructure())
          txWarning(errors, NO_RULE_DATE, s.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, s == null, s.getMessage());
        else if (s.getSeverity() == IssueSeverity.INFORMATION)
          txHint(errors, NO_RULE_DATE, s.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, s == null, s.getMessage());
        else if (s.getSeverity() == IssueSeverity.WARNING)
          txWarning(errors, NO_RULE_DATE, s.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, s == null, s.getMessage());
        else 
          return txRule(errors, NO_RULE_DATE, s.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, s == null, I18nConstants.TERMINOLOGY_PASSTHROUGH_TX_MESSAGE, s.getMessage(), system, code) && ok;
      }
      return ok;
    } else if (system.startsWith("http://build.fhir.org") || system.startsWith("https://build.fhir.org")) {
      rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_WRONG_BUILD, system, suggestSystemForBuild(system));        
      return false;
    } else if (system.startsWith("http://hl7.org/fhir") || system.startsWith("https://hl7.org/fhir") || system.startsWith("http://www.hl7.org/fhir") || system.startsWith("https://www.hl7.org/fhir")) {
      if (SIDUtilities.isknownCodeSystem(system)) {
        return ok; // else don't check these (for now)
      } else if (system.startsWith("http://hl7.org/fhir/test")) {
        return ok; // we don't validate these
      } else if (system.endsWith(".html")) {
        rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_WRONG_HTML, system, suggestSystemForPage(system));        
        return false;
      } else {
        CodeSystem cs = getCodeSystem(system);
        if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs != null, I18nConstants.TERMINOLOGY_TX_SYSTEM_UNKNOWN, system)) {
          ConceptDefinitionComponent def = getCodeDefinition(cs, code);
          if (warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, def != null, I18nConstants.UNKNOWN_CODESYSTEM, system, code))
            return warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, display == null || display.equals(def.getDisplay()), I18nConstants.TERMINOLOGY_TX_DISPLAY_WRONG, def.getDisplay()) && ok;
        }
        return false;
      }
    } else if (context.isNoTerminologyServer() && NO_TX_SYSTEM_EXEMPT.contains(system)) {
      txWarning(errors, NO_RULE_DATE, null, IssueType.BUSINESSRULE, element.line(), element.col(), path, false, I18nConstants.ERROR_VALIDATING_CODE_RUNNING_WITHOUT_TERMINOLOGY_SERVICES, code, system);
      return ok; // no checks in this case
    } else if (startsWithButIsNot(system, "http://snomed.info/sct", "http://loinc.org", "http://unitsofmeasure.org", "http://www.nlm.nih.gov/research/umls/rxnorm")) {
      rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_INVALID, system);
      return false;
    } else {
      try {
        if (context.fetchResourceWithException(ValueSet.class, system, element.getProperty().getStructure()) != null) {
          ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_VALUESET, system) && ok;
          // Lloyd: This error used to prohibit checking for downstream issues, but there are some cases where that checking needs to occur.  Please talk to me before changing the code back.
        }
        boolean done = false;
        if (system.startsWith("https:") && system.length() > 7) {
          String ns = "http:"+system.substring(6);
          CodeSystem cs = getCodeSystem(ns);
          if (cs != null || NO_HTTPS_LIST.contains(system)) {
            rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_HTTPS, system);
            done = true;
          }           
        } 
        if (!isAllowExamples() || !Utilities.startsWithInList(system, "http://example.org", "https://example.org")) {
          CodeSystem cs = context.fetchCodeSystem(system);
          if (cs == null) {
            hint(errors, NO_RULE_DATE, IssueType.UNKNOWN, element.line(), element.col(), path, done, I18nConstants.UNKNOWN_CODESYSTEM, system);
          } else {
            if (hint(errors, NO_RULE_DATE, IssueType.UNKNOWN, element.line(), element.col(), path, cs.getContent() != CodeSystemContentMode.NOTPRESENT, I18nConstants.TERMINOLOGY_TX_SYSTEM_NOT_USABLE, system)) {
              ok = rule(errors, NO_RULE_DATE, IssueType.UNKNOWN, element.line(), element.col(), path, false, "Error - this should not happen? (Consult GG)") && ok; 
            }
          }
        }
        return ok;
      } catch (Exception e) {
        return ok;
      }
    }
  }

  private Object suggestSystemForPage(String system) {
    if (system.contains("/codesystem-")) {
      String s = system.substring(system.indexOf("/codesystem-")+12);
      String url = "http://hl7.org/fhir/"+s.replace(".html", "");
      if (context.fetchCodeSystem(url) != null) {
        return url;
      } else {
        return "{unable to determine intended url}";
      }
    }
    if (system.contains("/valueset-")) {
      String s = system.substring(system.indexOf("/valueset-")+8);
      String url = "http://hl7.org/fhir/"+s.replace(".html", "");
      if (context.fetchCodeSystem(url) != null) {
        return url;
      } else {
        return "{unable to determine intended url}";
      }
    }
    return "{unable to determine intended url}";
  }

  private Object suggestSystemForBuild(String system) {
    if (system.contains("/codesystem-")) {
      String s = system.substring(system.indexOf("/codesystem-")+12);
      String url = "http://hl7.org/fhir/"+s.replace(".html", "");
      if (context.fetchCodeSystem(url) != null) {
        return url;
      } else {
        return "{unable to determine intended url}";
      }
    }
    if (system.contains("/valueset-")) {
      String s = system.substring(system.indexOf("/valueset-")+8);
      String url = "http://hl7.org/fhir/"+s.replace(".html", "");
      if (context.fetchCodeSystem(url) != null) {
        return url;
      } else {
        return "{unable to determine intended url}";
      }
    }
    system = system.replace("https://", "http://");
    if (system.length() < 22) {
      return "{unable to determine intended url}";
    }
    system = "http://hl7.org/fhir/"+system.substring(22).replace(".html", "");
    if (context.fetchCodeSystem(system) != null) {
      return system;
    } else {
      return "{unable to determine intended url}";
    }
  }
  
  private boolean startsWithButIsNot(String system, String... uri) {
    for (String s : uri)
      if (!system.equals(s) && system.startsWith(s))
        return true;
    return false;
  }


  private boolean hasErrors(List<ValidationMessage> errors) {
    if (errors != null) {
      for (ValidationMessage vm : errors) {
        if (vm.getLevel() == IssueSeverity.FATAL || vm.getLevel() == IssueSeverity.ERROR) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkCodeableConcept(List<ValidationMessage> errors, String path, Element focus, CodeableConcept fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".text", focus.getNamedChild("text", false), fixed.getTextElement(), fixedSource, "text", focus, pattern, context) && ok;
    List<Element> codings = new ArrayList<Element>();
    focus.getNamedChildren("coding", codings);
    if (pattern) {
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, codings.size() >= fixed.getCoding().size(), I18nConstants.TERMINOLOGY_TX_CODING_COUNT, Integer.toString(fixed.getCoding().size()), Integer.toString(codings.size()))) {
        for (int i = 0; i < fixed.getCoding().size(); i++) {
          Coding fixedCoding = fixed.getCoding().get(i);
          boolean found = false;
          List<ValidationMessage> allErrorsFixed = new ArrayList<>();
          List<ValidationMessage> errorsFixed;
          for (int j = 0; j < codings.size() && !found; ++j) {
            errorsFixed = new ArrayList<>();
            checkFixedValue(errorsFixed, path + ".coding", codings.get(j), fixedCoding, fixedSource, "coding", focus, pattern, context);
            if (!hasErrors(errorsFixed)) {
              found = true;
            } else {
              errorsFixed
                .stream()
                .filter(t -> t.getLevel().ordinal() >= IssueSeverity.ERROR.ordinal())
                .forEach(t -> allErrorsFixed.add(t));
            }
          }
          if (!found) {
            // The argonaut DSTU2 labs profile requires userSelected=false on the category.coding and this
            // needs to produce an understandable error message
//            String message = "Expected CodeableConcept " + (pattern ? "pattern" : "fixed value") + " not found for" +
//              " system: " + fixedCoding.getSystemElement().asStringValue() +
//              " code: " + fixedCoding.getCodeElement().asStringValue() +
//              " display: " + fixedCoding.getDisplayElement().asStringValue();
//            if (fixedCoding.hasUserSelected()) {
//              message += " userSelected: " + fixedCoding.getUserSelected();
//            }
//            message += " - Issues: " + allErrorsFixed;
//            TYPE_CHECKS_PATTERN_CC = The pattern [system {0}, code {1}, and display "{2}"] defined in the profile {3} not found. Issues: {4}
//            TYPE_CHECKS_PATTERN_CC_US = The pattern [system {0}, code {1}, display "{2}" and userSelected {5}] defined in the profile {3} not found. Issues: {4} 
//            TYPE_CHECKS_FIXED_CC = The pattern [system {0}, code {1}, and display "{2}"] defined in the profile {3} not found. Issues: {4}
//            TYPE_CHECKS_FIXED_CC_US = The pattern [system {0}, code {1}, display "{2}" and userSelected {5}] defined in the profile {3} not found. Issues: {4} 

            if (fixedCoding.hasUserSelected()) {
              ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, false, pattern ? I18nConstants.TYPE_CHECKS_PATTERN_CC_US : I18nConstants.TYPE_CHECKS_FIXED_CC_US, 
                  fixedCoding.getSystemElement().asStringValue(), fixedCoding.getCodeElement().asStringValue(), fixedCoding.getDisplayElement().asStringValue(),
                  fixedSource, allErrorsFixed, fixedCoding.getUserSelected()) && ok;
              
            } else {
              ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, false, pattern ? I18nConstants.TYPE_CHECKS_PATTERN_CC : I18nConstants.TYPE_CHECKS_FIXED_CC, 
                  fixedCoding.getSystemElement().asStringValue(), fixedCoding.getCodeElement().asStringValue(), fixedCoding.getDisplayElement().asStringValue(),
                  fixedSource, allErrorsFixed) && ok;
            }
          }
        }
      } else {
        ok = false;
      }      
    } else {
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, codings.size() == fixed.getCoding().size(), I18nConstants.TERMINOLOGY_TX_CODING_COUNT, Integer.toString(fixed.getCoding().size()), Integer.toString(codings.size()))) {
        for (int i = 0; i < codings.size(); i++)
          ok = checkFixedValue(errors, path + ".coding", codings.get(i), fixed.getCoding().get(i), fixedSource, "coding", focus, false, context) && ok;
      } else {
        ok = false;
      }
    }
    return ok;
  }

  private boolean checkCodeableConcept(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition theElementCntext, NodeStack stack, BooleanHolder bh) {
    boolean checkDisp = true;
    BooleanHolder checked = new BooleanHolder(false);
    if (!noTerminologyChecks && theElementCntext != null && theElementCntext.hasBinding()) {
      ElementDefinitionBindingComponent binding = theElementCntext.getBinding();
      if (warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, binding != null, I18nConstants.TERMINOLOGY_TX_BINDING_MISSING, path)) {
        try {
          CodeableConcept cc = ObjectConverter.readAsCodeableConcept(element);
          if (cc.hasText() && cc.hasCoding()) {
            for (Coding c : cc.getCoding()) {
              recordCodeTextCombo(stack, theElementCntext.getBase().getPath(), c, cc.getText());
            }
          }
          if (binding.hasValueSet()) {
            String vsRef = binding.getValueSet();
            ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
            BindingStrength strength = binding.getStrength();
            Extension maxVS = binding.getExtensionByUrl(ExtensionDefinitions.EXT_MAX_VALUESET);
            
            checkDisp = validateBindingCodeableConcept(errors, path, element, profile, stack, bh, checkDisp, checked, cc, vsRef, valueset, strength, maxVS, true, null);
//          } else if (binding.hasValueSet()) {
//            hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_CANTCHECK);
          } else if (!noBindingMsgSuppressed) {
            hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSOURCE, path);
          }
          for (ElementDefinitionBindingAdditionalComponent ab : binding.getAdditional()) {
            StringBuilder b = new StringBuilder();
            if (isTestableBinding(ab) && isInScope(ab, profile, getResource(stack), b)) {
              String vsRef = ab.getValueSet();
              ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
              BindingStrength strength = convertPurposeToStrength(ab.getPurpose());
              
              checkDisp = validateBindingCodeableConcept(errors, path, element, profile, stack, bh, checkDisp, checked, cc, vsRef, valueset, strength, null, false, b.toString()) && checkDisp;
            }
          }
        } catch (CheckCodeOnServerException e) {
          if (STACK_TRACE) e.getCause().printStackTrace();
          warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT, e.getCause().getMessage());
        }
      } 
    }
    if (!noTerminologyChecks && theElementCntext != null && !checked.ok()) { // no binding check, so we just check the CodeableConcept generally
      try {
        CodeableConcept cc = ObjectConverter.readAsCodeableConcept(element);
        if (cc.hasText() && cc.hasCoding()) {
          for (Coding c : cc.getCoding()) {
            recordCodeTextCombo(stack, theElementCntext.getBase().getPath(), c, cc.getText());
          }
        }
        if (cc.hasCoding()) {
          long t = System.nanoTime();
          ValidationResult vr = checkCodeOnServer(stack, null, cc);
          bh.see(calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, null, null));
          timeTracker.tx(t, "vc " + cc.toString());
        }
      } catch (CheckCodeOnServerException e) {
        if (STACK_TRACE) e.getCause().printStackTrace();
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT, e.getCause().getMessage());
      }
    }
    return checkDisp;
  }

  private void recordCodeTextCombo(NodeStack node, String path, Coding c, String text) {
    if (!c.hasDisplay() || !c.getDisplay().equals(text)) {
      int key = (c.getSystem()+"||"+c.getCode()+"||"+text).hashCode();
      if (!textsToCheckKeys.contains(key)) {
        textsToCheckKeys.add(key);
        textsToCheck.add(new CodeAndTextValidationRequest(node, path, node.getWorkingLang() == null ? context.getLocale().toLanguageTag() : node.getWorkingLang(), c.getSystem(), c.getCode(), c.getDisplay(), text));
      }
    }
  }

  private boolean isInScope(ElementDefinitionBindingAdditionalComponent ab, StructureDefinition profile, Element resource, StringBuilder b) {
    if (ab.getUsage().isEmpty()) {
      return true;
    }
    boolean ok = true;
    for (UsageContext usage : ab.getUsage()) {
      if (!isInScope(usage, profile, resource, b)) {
        ok = false;
      }
    }
    return ok;
  }

  private boolean isInScope(UsageContext usage, StructureDefinition profile, Element resource, StringBuilder b) {
    if (isKnownUsage(usage)) {
      return true;
    }
    if (usage.getCode().hasSystem() && (usage.getCode().getSystem().equals(profile.getUrl()) || usage.getCode().getSystem().equals(profile.getVersionedUrl()))) {
      // if it's not a defined usage from external sources, it might match something in the data content
      List<Element> items = findDataValue(resource, usage.getCode().getCode());
      if (matchesUsage(items, usage.getValue())) {
        b.append(context.formatMessage(I18nConstants.BINDING_ADDITIONAL_USAGE, displayCoding(usage.getCode()), display(usage.getValue())));
        return true;
      }
    }
    return false;
  }

  private String displayCoding(Coding value) {
    return value.getCode();
  }

  private String displayCodeableConcept(CodeableConcept value) {
    for (Coding c : value.getCoding()) {
      String s = displayCoding(c);
      if (s != null) {
        return s;
      }
    }    
    return value.getText();
  }

  private String display(DataType value) {
    switch (value.fhirType()) {
    case "Coding" : return displayCoding((Coding) value);
    case "CodeableConcept" : return displayCodeableConcept((CodeableConcept) value);
    }
    return value.fhirType();
  }
  
  private boolean matchesUsage(List<Element> items, DataType value) {
    for (Element item : items) {
      if (matchesUsage(item, value)) {
        return true;
      }
    }
    return false;
  }



  private String display(List<Element> items) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (Element item : items) {
      display(b, item);
    }
    return b.toString();
  }

  private void display(CommaSeparatedStringBuilder b, Element item) {
    if (item.isPrimitive()) {
      b.append(item.primitiveValue());
    } else if (item.fhirType().equals("CodeableConcept")) {
      for (Element c : item.getChildren("coding")) {
        b.append(c.getNamedChildValue("code"));
      }
    } else {
      b.append(item.toString());
    }
      
  }

  private boolean matchesUsage(Element item, DataType value) {
    switch (value.fhirType()) {
    case "CodeableConcept": return matchesUsageCodeableConcept(item, (CodeableConcept) value);
    case "Quantity": return false;
    case "Range": return false;
    case "Reference": return false;
    default: return false;
    }
  }

  private boolean matchesUsageCodeableConcept(Element item, CodeableConcept value) {
    switch (item.fhirType()) {
    case "CodeableConcept": return matchesUsageCodeableConceptCodeableConcept(item, value);
    case "Coding": return matchesUsageCodeableConceptCoding(item, value);
    default: return false;
    }
  }

  private boolean matchesUsageCodeableConceptCoding(Element item, CodeableConcept value) {
    String system = item.getNamedChildValue("system");
    String version = item.getNamedChildValue("version");
    String code = item.getNamedChildValue("code");
    for (Coding c : value.getCoding()) {
      if (system == null || !system.equals(c.getSystem())) {
        return false;
      }
      if (code == null || !code.equals(c.getCode())) {
        return false;
      }
      if (c.hasVersion()) {
        if (version == null || !version.equals(c.getVersion())) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  private boolean matchesUsageCodeableConceptCodeableConcept(Element item, CodeableConcept value) {
    for (Element code : item.getChildren("coding")) {
      if (matchesUsageCodeableConceptCoding(code, value)) {
        return true;
      }
    }
    return false;    
  }

  private List<Element> findDataValue(Element resource, String code) {
    List<Element> items = new ArrayList<Element>();
    if (resource != null) {
      findDataValues(items, resource, code);
    }
    return items;
  }

  private void findDataValues(List<Element> items, Element element, String path) {
    if (element.getPath() == null) {
      return;
    }
    if (pathMatches(element.getPath(), path)) {
      items.add(element);
    } else if (element.hasChildren() && path.startsWith(element.getPath())) {
      for (Element child : element.getChildren()) {
        findDataValues(items, child, path);
      }
    }    
  }

  private boolean pathMatches(String actualPath, String pathSpec) {
    String[] ap = actualPath.split("\\.");
    String[] ps = pathSpec.split("\\.");
    if (ap.length != ps.length) {
      return false;
    }
    for (int i = 0; i < ap.length; i++) {
      if (!pathSegmentMatches(ap[i], ps[i])) {
        return false;
      }
    }
    return true;
  }

  private boolean pathSegmentMatches(String ap, String ps) {
    if (ps.contains("[")) {
      return ap.equals(ps);
    } else {
      if (ap.contains("[")) {
        ap = ap.substring(0, ap.indexOf("["));
      }
      return ap.equals(ps);
    } 
  }

  private BindingStrength convertPurposeToStrength(AdditionalBindingPurposeVS purpose) {
    switch (purpose) {
    case MAXIMUM: return BindingStrength.REQUIRED;
    case EXTENSIBLE: return BindingStrength.EXTENSIBLE;
    case PREFERRED: return BindingStrength.PREFERRED;
    case REQUIRED: return BindingStrength.REQUIRED;
    default: return null;    
    }
  }

  private boolean isTestableBinding(ElementDefinitionBindingAdditionalComponent ab) {
    return ab.hasValueSet() && convertPurposeToStrength(ab.getPurpose()) != null;
  }

  private boolean validateBindingCodeableConcept(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, NodeStack stack, BooleanHolder bh, boolean checkDisp, BooleanHolder checked,
      CodeableConcept cc, String vsRef, ValueSet valueset, BindingStrength strength, Extension maxVS, boolean base, String usageNote) throws CheckCodeOnServerException {
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(vsRef);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(vsRef))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(vsRef));
      } else {
        bh.fail();
      }
    } else {
      BindingContext bc = base ? BindingContext.BASE : BindingContext.ADDITIONAL;
      if (!cc.hasCoding()) {
        if (strength == BindingStrength.REQUIRED)
          bh.see(rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CODE_VALUESET, describeReference(vsRef, valueset, bc, usageNote)));
        else if (strength == BindingStrength.EXTENSIBLE) {
          if (maxVS != null)
            bh.see(rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CODE_VALUESETMAX, describeReference(ExtensionUtilities.readStringFromExtension(maxVS)), valueset.getVersionedUrl()));
          else if (!noExtensibleWarnings) {
            warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CODE_VALUESET_EXT, describeReference(vsRef, valueset, bc, usageNote));
          }
        }
      } else {
        long t = System.nanoTime();

        // Check whether the codes are appropriate for the type of binding we have
        boolean bindingsOk = true;
        if (strength != BindingStrength.EXAMPLE) {
          if (strength == BindingStrength.REQUIRED) {
            removeTrackedMessagesForLocation(errors, element, path);
          }
          boolean atLeastOneSystemIsSupported = false;
          for (Coding nextCoding : cc.getCoding()) {
            String nextSystem = nextCoding.getSystem();
            if (isNotBlank(nextSystem) && context.supportsSystem(nextSystem)) {
              atLeastOneSystemIsSupported = true;
              break;
            }
          }

          if (!atLeastOneSystemIsSupported && strength == BindingStrength.EXAMPLE) {
            // ignore this since we can't validate but it doesn't matter..
          } else {
            checked.set(true);
            ValidationResult vr = checkCodeOnServer(stack, valueset, cc);
            bh.see(calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, vsRef, strength));
            if (!vr.isOk()) {
              bindingsOk = false;
              if (vr.getErrorClass() != null && vr.getErrorClass() == TerminologyServiceErrorClass.NOSERVICE) { 
                if (strength == BindingStrength.REQUIRED || (strength == BindingStrength.EXTENSIBLE && maxVS != null)) {
                  txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOSVC_BOUND_REQ, describeReference(vsRef));
                } else if (strength == BindingStrength.EXTENSIBLE) {
                  txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOSVC_BOUND_EXT, describeReference(vsRef));
                }
              } else if (vr.getErrorClass() != null && vr.getErrorClass().isInfrastructure()) {
                if (strength == BindingStrength.REQUIRED)
                  txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_1_CC, describeReference(vsRef), vr.getErrorClass().toString());
                else if (strength == BindingStrength.EXTENSIBLE) {
                  if (maxVS != null)
                    bh.see(checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(maxVS), cc, stack));
                  else if (!noExtensibleWarnings)
                    txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_2_CC, describeReference(vsRef), vr.getErrorClass().toString());
                } else if (strength == BindingStrength.PREFERRED) {
                  if (baseOnly) {
                    txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_3_CC, describeReference(vsRef), vr.getErrorClass().toString());
                  }
                }
              } else if (vr.getErrorClass() != null && vr.getErrorClass() == TerminologyServiceErrorClass.CODESYSTEM_UNSUPPORTED) {
                // we've already handled the warnings / errors about this, and set the status correctly. We don't need to do anything more?
              } else if (vr.getErrorClass() != TerminologyServiceErrorClass.SERVER_ERROR) { // (should have?) already handled server error
                if (strength == BindingStrength.REQUIRED) {
                  bh.see(txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_1_CC, describeReference(vsRef, valueset, bc, usageNote), ccSummary(cc)));
                } else if (strength == BindingStrength.EXTENSIBLE) {
                  if (maxVS != null)
                    bh.see(checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(maxVS), cc, stack));
                  if (!noExtensibleWarnings)
                    txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_2_CC, describeReference(vsRef, valueset, bc, usageNote), ccSummary(cc));
                } else if (strength == BindingStrength.PREFERRED) {
                  if (baseOnly) {
                    txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_3_CC, describeReference(vsRef, valueset, bc, usageNote), ccSummary(cc));
                  }
                }
              }
            } else if (vr.getMessage() != null) {
              if (vr.getTrimmedMessage() != null) {
                if (vr.getSeverity() == IssueSeverity.INFORMATION) {
                  txHint(errors, "2023-07-03", vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
                } else {
                  checkDisp = false;
                  txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getTrimmedMessage());
                }
              }
            } else {
              if (strength == BindingStrength.EXTENSIBLE) {
                removeTrackedMessagesForLocation(errors, element, path);
              }
              checkDisp = false;
            }
          }
          // Then, for any codes that are in code systems we are able
          // to validate, we'll validate that the codes actually exist
          if (bindingsOk && false) {
            for (Coding nextCoding : cc.getCoding()) {
              bh.see(checkBindings(errors, path, element, stack, valueset, nextCoding));
            }
          }
          timeTracker.tx(t, "vc "+cc.toString());
        }
      }
    }
    return checkDisp;
  }

//  private String notFoundSeverityNoteForBinding(BindingStrength strength, Set<String> systems) {
//    if (strength == BindingStrength.REQUIRED && 
//        (Utilities.listValueStartsWith("http://hl7.org/fhir", systems) || Utilities.listValueStartsWith("http://terminology.hl7.org", systems))) {
//      return "error because this is a required binding to an HL7 code system";
//    } else {
//      return null;     
//    }
//  }
//
//  /**
//   * The terminology server will report an error for an unknown code system or version, or a dependent valueset
//   * 
//   * but we only care for validation if the binding strength is strong enough. 
//   * @param binding
//   * @return
//   */
//  private org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity notFoundSeverityForBinding(BindingStrength strength, String systems) {
//    if (strength == BindingStrength.REQUIRED && 
//        (Utilities.listValueStartsWith("http://hl7.org/fhir", systems) || Utilities.listValueStartsWith("http://terminology.hl7.org", systems))) {
//      return org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.ERROR;
//    } else if (strength == BindingStrength.REQUIRED || strength == BindingStrength.EXTENSIBLE) {
//      return org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING;
//    } else {
//      return org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.INFORMATION;      
//    }
//  }

  /**
   * For each issue in the validationResult, determine first if it should be ignored, and if not, calculate its severity
   * and add it to the errors list.
   *
   * @param errors a list of validation messages that are errors
   * @param validationResult the validation result containing terminology issues to be evaluated
   * @param element the element being validated
   * @param path the path of the element being validated
   * @param ignoreCantInfer if true, the element will be ignored
   * @param vsurl the value set url
   * @param bindingStrength the binding strength of the code/codeable concept
   * @return true if no errors were added to the errors list, false otherwise
   */
  private boolean calculateSeverityForTxIssuesAndUpdateErrors(List<ValidationMessage> errors, ValidationResult validationResult, Element element, String path, boolean ignoreCantInfer, String vsurl, BindingStrength bindingStrength) {
    if (validationResult == null) {
      return true;
    }
    boolean noErrorsFound = true;
    for (OperationOutcomeIssueComponent issue : validationResult.getIssues()) {
      if (!isIgnoredTxIssueType(issue, ignoreCantInfer)) {
        OperationOutcomeIssueComponent issueWithCalculatedSeverity = getTxIssueWithCalculatedSeverity(issue, bindingStrength);
        var validationMessage = buildValidationMessage(validationResult.getTxLink(), element.line(), element.col(), path, issueWithCalculatedSeverity);
        if (!isSuppressedValidationMessage(validationMessage.getLocation(), validationMessage.getMessageId())) {
          errors.add(validationMessage);
          if (validationMessage.isError()) {
            noErrorsFound = false;
          }
        }
      }
    }
    return noErrorsFound;
  }

  /**
   * Check if the issueComponent should not be included when evaluating if terminology issues contain errors.
   * <p/>
   * The following tx-issue-types are ignored:
   * <ul>
   * <li>not-in-vs</li>
   * <li>this-code-not-in-vs</li>
   * <li>cannot-infer</li>
   * </ul>
   * Note that cannot-infer is only ignored if ignoreCantInfer is true.
   *
   * @param issueComponent the issue
   * @param ignoreCantInfer if true, ignore
   * @return true if the issue should be ignored when evaluating if terminology issues contain errors, false otherwise
   */
  private boolean isIgnoredTxIssueType(OperationOutcomeIssueComponent issueComponent, boolean ignoreCantInfer) {
    if (issueComponent.getDetails().hasCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", "not-in-vs")) {
      return true;
    }
    if (issueComponent.getDetails().hasCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", "this-code-not-in-vs")) {
      return true;
    }
    if (issueComponent.getDetails().hasCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", "cannot-infer") && ignoreCantInfer) {
        return true;
    }
    if (!isForPublication() && "MSG_EXPERIMENTAL".equals(ExtensionUtilities.readStringExtension(issueComponent, ExtensionDefinitions.EXT_ISSUE_MSG_ID))) {
      return true;
    }
    return false;
  }

  /**
   * Derive a new issue from the passed issueComponent with appropriate severity.
   * <p/>
   * If the issueComponent has a tx-issue-type of "not-found", the returned value will have an appropriate severity
   * based on bindingStrength. The issueComponent text will also be updated to include the reason for error level
   * severity.
   * <p/>
   * If the issueComponent has a tx-issue-type of "invalid-display" the returned value will have an appropriate severity
   * based on displayWarningMode.
   * <p/>
   * All other issues will be returned unchanged.
   * @param issueComponent the issue component
   * @param bindingStrength the binding strength of the code/codeable concept
   * @return the new issue component with appropriate severity
   */
  private OperationOutcomeIssueComponent getTxIssueWithCalculatedSeverity(OperationOutcomeIssueComponent issueComponent, BindingStrength bindingStrength) {
    OperationOutcomeIssueComponent newIssueComponent = issueComponent.copy();
    if (newIssueComponent.getDetails().hasCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", "not-found")) {
      String text = issueComponent.getDetails().getText();
      boolean isHL7 = text == null ? false : text.contains("http://hl7.org/fhir") || text.contains("http://terminology.hl7.org");
      org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity notFoundLevel = null;
      String notFoundNote = null;
      if (bindingStrength == null) {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING;
        notFoundNote = null; // "binding=null";
      } else if (bindingStrength == BindingStrength.REQUIRED && isHL7) {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.ERROR;
        notFoundNote = "error because this is a required binding to an HL7 code system";
      } else if (bindingStrength == BindingStrength.REQUIRED && unknownCodeSystemsCauseErrors) {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.ERROR;
        notFoundNote = "error because this is a required binding";
      } else if (bindingStrength == BindingStrength.REQUIRED) {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING;
        notFoundNote = null; // "binding=required";
      } else if (bindingStrength == BindingStrength.EXTENSIBLE) {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING;
        notFoundNote = null; // "binding=extensible";
      } else {
        notFoundLevel = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING;
        notFoundNote = null; // "binding="+bs.toCode();
      }
      if (notFoundLevel != null && newIssueComponent.getSeverity().isHigherThan(notFoundLevel)) { // && (vsurl != null && i.getDetails().getText().contains(vsurl))) {
        newIssueComponent.setSeverity(notFoundLevel);
        if (notFoundNote != null) {
          newIssueComponent.getDetails().setText(newIssueComponent.getDetails().getText() + " (" + notFoundNote + ")");
        }
      }
    }
    if (newIssueComponent.getDetails().hasCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", "invalid-display") && settings.isDisplayWarningMode() && newIssueComponent.getSeverity() == org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.ERROR) {
      newIssueComponent.setSeverity(org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING);
    }
    return newIssueComponent;
  }

  public boolean checkBindings(List<ValidationMessage> errors, String path, Element element, NodeStack stack, ValueSet valueset, Coding nextCoding) {
    boolean ok = true;
    if (isNotBlank(nextCoding.getCode()) && isNotBlank(nextCoding.getSystem()) && context.supportsSystem(nextCoding.getSystem())) {
      ValidationResult vr = checkCodeOnServer(stack, valueset, nextCoding);
      ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, null, null) && ok;

      if (vr.getSeverity() != null && !vr.messageIsInIssues()) {
        if (vr.getSeverity() == IssueSeverity.INFORMATION) {
          txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
        } else if (vr.getSeverity() == IssueSeverity.WARNING) {
          txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
        } else {
          ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage()) && ok;
        }
      }
    }
    return ok;
  }


  private boolean checkCDACodeableConcept(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition theElementCntext, NodeStack stack, StructureDefinition logical) {
    BooleanHolder ok = new BooleanHolder(true);
    if (!noTerminologyChecks && theElementCntext != null && theElementCntext.hasBinding()) {
      try {
        CodeableConcept cc = new CodeableConcept();
        ok.see(convertCDACodeToCodeableConcept(errors, path, element, logical, cc));
        if (cc.hasText() && cc.hasCoding()) {
          for (Coding c : cc.getCoding()) {
            recordCodeTextCombo(stack, theElementCntext.getBase().getPath(), c, cc.getText());
          }
        }
        ElementDefinitionBindingComponent binding = theElementCntext.getBinding();
        if (warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, binding != null, I18nConstants.TERMINOLOGY_TX_BINDING_MISSING, path)) {
          if (binding.hasValueSet()) {
            String vsRef = binding.getValueSet();
            ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
            BindingStrength strength = binding.getStrength();
            Extension vsMax = binding.getExtensionByUrl(ExtensionDefinitions.EXT_MAX_VALUESET);
            
            validateBindingCodeableConcept(errors, path, element, profile, stack, ok, false, new BooleanHolder(), cc, vsRef, valueset, strength, vsMax, true, null);

            // special case: if the logical model has both CodeableConcept and Coding mappings, we'll also check the first coding.
            if (getMapping("http://hl7.org/fhir/terminology-pattern", logical, logical.getSnapshot().getElementFirstRep()).contains("Coding")) {
              ok.see(checkTerminologyCoding(errors, path, element, profile, theElementCntext, true, true, stack, logical));
            }
//          } else if (binding.hasValueSet()) {
//            hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_CANTCHECK);
          } else if (!noBindingMsgSuppressed) {
            hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSOURCE, path);
          }
          for (ElementDefinitionBindingAdditionalComponent ab : binding.getAdditional()) {
            StringBuilder b = new StringBuilder();
            if (isTestableBinding(ab) && isInScope(ab, profile, getResource(stack), b)) {
              String vsRef = ab.getValueSet();
              ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
              BindingStrength strength = convertPurposeToStrength(ab.getPurpose());
              validateBindingCodeableConcept(errors, path, element, profile, stack, ok, false, new BooleanHolder(), cc, vsRef, valueset, strength, null, false, b.toString());
            }
          }
        }
      } catch (CheckCodeOnServerException e) {
        if (STACK_TRACE) e.getCause().printStackTrace();
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT, e.getCause().getMessage());
      }
    }
    return ok.ok();
  }

  private boolean checkTerminologyCoding(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition theElementCntext, boolean inCodeableConcept, boolean checkDisplay, NodeStack stack, StructureDefinition logical) {
    boolean ok = false;
    Coding c = convertToCoding(element, logical);
    String code = c.getCode();
    String system = c.getSystem();
    String display = c.getDisplay();
    String version = c.getVersion();
    ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, system == null || isCodeSystemReferenceValid(system), I18nConstants.TERMINOLOGY_TX_SYSTEM_RELATIVE) && ok;

    if (system != null && code != null && !noTerminologyChecks) {
      ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, !isValueSet(system), I18nConstants.TERMINOLOGY_TX_SYSTEM_VALUESET2, system) && ok;
      try {
        if (checkCode(errors, element, path, code, system, version, display, checkDisplay, stack)) {
          if (theElementCntext != null && theElementCntext.hasBinding()) {
            ElementDefinitionBindingComponent binding = theElementCntext.getBinding();
            if (warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, binding != null, I18nConstants.TERMINOLOGY_TX_BINDING_MISSING2, path)) {
              if (binding.hasValueSet()) {
                String vsRef = binding.getValueSet();
                ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
                BindingStrength strength = binding.getStrength();
                Extension vsMax = binding.getExtensionByUrl(ExtensionDefinitions.EXT_MAX_VALUESET);
                
                ok = validateBindingTerminologyCoding(errors, path, element, profile, stack, ok, c, code, system, display, vsRef, valueset, strength, vsMax, true, null);
              } else if (binding.hasValueSet()) {
                hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_CANTCHECK);
              } else if (!inCodeableConcept && !noBindingMsgSuppressed) {
                hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSOURCE, path);
              }
              for (ElementDefinitionBindingAdditionalComponent ab : binding.getAdditional()) {
                StringBuilder b = new StringBuilder();
                if (isTestableBinding(ab) && isInScope(ab, profile, getResource(stack), b)) {
                  String vsRef = ab.getValueSet();
                  ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
                  BindingStrength strength = convertPurposeToStrength(ab.getPurpose());
                  ok = validateBindingTerminologyCoding(errors, path, element, profile, stack, ok, c, code, system, display, vsRef, valueset, strength, null, true, b.toString()) && ok;
                }
              }
            }
          }
        } else {
          ok = false;
        }
      } catch (Exception e) {
        if (STACK_TRACE) e.printStackTrace();
        rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODING2, e.getMessage(), e.toString());
        ok = false;
      }
    }
    return ok;
  }

  private boolean validateBindingTerminologyCoding(List<ValidationMessage> errors, String path, Element element,
      StructureDefinition profile, NodeStack stack, boolean ok, Coding c, String code, String system, String display,
      String vsRef, ValueSet valueset, BindingStrength strength, Extension vsMax, boolean base, String usageNote) {
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(vsRef);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(vsRef))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(vsRef));
      } else {
        ok = false;
      }
    } else {
      BindingContext bc = base ? BindingContext.BASE : BindingContext.ADDITIONAL;
      try {
        long t = System.nanoTime();
        ValidationResult vr = null;
        if (strength != BindingStrength.EXAMPLE) {
          vr = checkCodeOnServer(stack, valueset, c);
        }
        if (strength == BindingStrength.REQUIRED) {
          removeTrackedMessagesForLocation(errors, element, path);
        }
        ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, vsRef, strength) && ok;
        timeTracker.tx(t, "vc "+system+"#"+code+" '"+display+"'");
        if (vr != null && !vr.isOk()) {
          if (vr.IsNoService())
            txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSERVER, system+"#"+code);
          else if (vr.getErrorClass() != null && vr.getErrorClass().isInfrastructure()) {
            if (strength == BindingStrength.REQUIRED)
              txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_4a, describeReference(vsRef, valueset, bc, usageNote), vr.getMessage(), system+"#"+code);
            else if (strength == BindingStrength.EXTENSIBLE) {
              if (vsMax != null)
                checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(vsMax), c, stack);
              else if (!noExtensibleWarnings)
                txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_5, describeReference(vsRef, valueset, bc, usageNote));
            } else if (strength == BindingStrength.PREFERRED) {
              if (baseOnly) {
                txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_6, describeReference(vsRef, valueset, bc, usageNote));
              }
            }
          } else if (strength == BindingStrength.REQUIRED)
            ok= txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_4, describeReference(vsRef, valueset, bc, usageNote), (vr.getMessage() != null ? " (error message = " + vr.getMessage() + ")" : ""), system+"#"+code) && ok;
          else if (strength == BindingStrength.EXTENSIBLE) {
            if (vsMax != null)
              ok = checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(vsMax), c, stack) && ok;
            else
              txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_5, describeReference(vsRef, valueset, bc, usageNote), (vr.getMessage() != null ? " (error message = " + vr.getMessage() + ")" : ""), system+"#"+code);
          } else if (strength == BindingStrength.PREFERRED) {
            if (baseOnly) {
              txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_6, describeReference(vsRef, valueset, bc, usageNote), (vr.getMessage() != null ? " (error message = " + vr.getMessage() + ")" : ""), system+"#"+code);
            }
          }
        } else if (vr != null && vr.getMessage() != null){
          if (vr.getSeverity() == IssueSeverity.INFORMATION) {
            txHint(errors, "2023-07-04", vr.getTxLink(), IssueType.INFORMATIONAL, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_HINT, code, vr.getMessage());
          } else {
            txWarning(errors, "2023-07-04", vr.getTxLink(), IssueType.INFORMATIONAL, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_WARNING, code, vr.getMessage());
          }
        }
      } catch (Exception e) {
        if (STACK_TRACE) e.printStackTrace();
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODING1, e.getMessage());
      }
    }
    return ok;
  }

  private boolean convertCDACodeToCodeableConcept(List<ValidationMessage> errors, String path, Element element, StructureDefinition logical, CodeableConcept cc) {
    boolean ok = true;
    cc.setText(element.getNamedChildValue("originalText", false));
    if (element.hasChild("nullFlavor", false)) {
      cc.addExtension("http://hl7.org/fhir/StructureDefinition/iso21090-nullFlavor", new CodeType(element.getNamedChildValue("nullFlavor", false)));
    }
    if (element.hasChild("code", false) || element.hasChild("codeSystem", false)) {
      Coding c = cc.addCoding();
      
      String oid = element.getNamedChildValue("codeSystem", false);
      if (oid != null) {
        c.setSystem("urn:oid:"+oid);
        OIDSummary urls = context.urlsForOid(oid, "CodeSystem");
        if (urls.urlCount() == 1) {
          c.setSystem(urls.getUrl());
        } else if (urls.urlCount() == 0) {
          warning(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_UNKNOWN_OID, oid);
        } else {
          String prefUrl = urls.chooseBestUrl();
          if (prefUrl == null) {
            rule(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_OID_MULTIPLE_MATCHES, oid, urls.describe());
            ok = false;
          } else {
            c.setSystem(prefUrl);
            warning(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_OID_MULTIPLE_MATCHES_CHOSEN, oid, prefUrl, urls.describe());
          }
        }
      } else {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_NO_CODE); 
      }
      
      c.setCode(element.getNamedChildValue("code", false));
      c.setVersion(element.getNamedChildValue("codeSystemVersion", false));
      c.setDisplay(element.getNamedChildValue("displayName", false));
    }
    // todo: translations
    return ok;
  }

  private Coding convertToCoding(Element element, StructureDefinition logical) {
    Coding res = new Coding();
    for (ElementDefinition ed : logical.getSnapshot().getElement()) {
      if (Utilities.charCount(ed.getPath(), '.') == 1) {
        List<String> maps = getMapping("http://hl7.org/fhir/terminology-pattern", logical, ed);
        for (String m : maps) {
          String name = tail(ed.getPath());
          List<Element> list = new ArrayList<>();
          element.getNamedChildren(name, list);
          if (!list.isEmpty()) {
            if ("Coding.code".equals(m)) {
              res.setCode(list.get(0).primitiveValue());
            } else if ("Coding.system[fmt:OID]".equals(m)) {
              String oid = list.get(0).primitiveValue();
              String url = new ContextUtilities(context).oid2Uri(oid);
              if (url != null) {
                res.setSystem(url);
              } else {
                res.setSystem("urn:oid:" + oid);
              }
            } else if ("Coding.version".equals(m)) {
              res.setVersion(list.get(0).primitiveValue());
            } else if ("Coding.display".equals(m)) {
              res.setDisplay(list.get(0).primitiveValue());
            }
          }
        }
      }
    }
    return res;
  }

  private boolean checkMaxValueSet(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, String maxVSUrl, CodeableConcept cc, NodeStack stack) {
    boolean ok = true;
    ValueSet valueset = resolveBindingReference(profile, maxVSUrl, profile.getUrl(), profile);
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(maxVSUrl);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(maxVSUrl))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(maxVSUrl));
      } else {
        ok = false;
      }
    } else {
      try {
        long t = System.nanoTime();
        ValidationResult vr = checkCodeOnServer(stack, valueset, cc);
        ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, maxVSUrl, null) && ok;
        timeTracker.tx(t, "vc "+cc.toString());
        if (!vr.isOk()) {
          if (vr.getErrorClass() != null && vr.getErrorClass().isInfrastructure())
            txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_7, describeReference(maxVSUrl, valueset, BindingContext.MAXVS, null), vr.getMessage());
          else
            ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_8, describeReference(maxVSUrl, valueset, BindingContext.MAXVS, null), ccSummary(cc)) && ok;
        }
      } catch (CheckCodeOnServerException e) {
        if (STACK_TRACE) e.getCause().printStackTrace();
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT_MAX, e.getCause().getMessage());
      }
    }
    return ok;
  }

//  private String describeValueSet(String url) {
//    ValueSet vs = context.findTxResource(ValueSet.class, url);
//    if (vs != null) {
//      return "'"+vs.present()+"' ("+url+")";
//    } else {
//      return "("+url+")";
//    }
//  }

  private boolean checkMaxValueSet(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, String maxVSUrl, Coding c, NodeStack stack) {
    boolean ok = true;
    ValueSet valueset = resolveBindingReference(profile, maxVSUrl, profile.getUrl(), profile);
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(maxVSUrl);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(maxVSUrl))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(maxVSUrl));
      } else {
        ok = false;
      }
    } else {
      try {
        long t = System.nanoTime();
        ValidationResult vr = checkCodeOnServer(stack, valueset, c);
        ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, maxVSUrl, null) && ok;

        timeTracker.tx(t, "vc "+c.getSystem()+"#"+c.getCode()+" '"+c.getDisplay()+"'");
        if (!vr.isOk()) {
          if (vr.getErrorClass() != null && vr.getErrorClass().isInfrastructure())
            txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_9, describeReference(maxVSUrl, valueset, BindingContext.MAXVS, null), vr.getMessage(), c.getSystem()+"#"+c.getCode());
          else
            ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_10, describeReference(maxVSUrl, valueset, BindingContext.MAXVS, null), c.getSystem(), c.getCode()) && ok;
        }
      } catch (Exception e) {
        if (STACK_TRACE) e.printStackTrace();
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT_MAX, e.getMessage());
      }
    }
    return ok;
  }

  private boolean checkMaxValueSet(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, String maxVSUrl, String value, NodeStack stack) {
    boolean ok = true;
    ValueSet valueset = resolveBindingReference(profile, maxVSUrl, profile.getUrl(), profile);
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(maxVSUrl);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(maxVSUrl))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(maxVSUrl));
      } else {
        ok = false;
      }
    } else {
      try {
        long t = System.nanoTime();
        ValidationResult vr = checkCodeOnServer(stack, valueset, value, settings);
        ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, false, maxVSUrl, null) && ok;
        timeTracker.tx(t, "vc "+value);
        if (!vr.isOk()) {
          if (vr.getErrorClass() != null && vr.getErrorClass().isInfrastructure())
            txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_9, describeReference(maxVSUrl, valueset, BindingContext.BASE, null), vr.getMessage(), value);
          else {
            ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_11, describeReference(maxVSUrl, valueset, BindingContext.BASE, null), vr.getMessage()) && ok;
          }
        }
      } catch (Exception e) {
        if (STACK_TRACE) e.printStackTrace();
          warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODEABLECONCEPT_MAX, e.getMessage());
      }
    }
    return ok;
  }

  private String ccSummary(CodeableConcept cc) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (Coding c : cc.getCoding())
      b.append(c.getSystem() + "#" + c.getCode());
    return b.toString();
  }

  private boolean checkCoding(List<ValidationMessage> errors, String path, Element focus, Coding fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".system", focus.getNamedChild("system", false), fixed.getSystemElement(), fixedSource, "system", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".version", focus.getNamedChild("version", false), fixed.getVersionElement(), fixedSource, "version", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".code", focus.getNamedChild("code", false), fixed.getCodeElement(), fixedSource, "code", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".display", focus.getNamedChild("display", false), fixed.getDisplayElement(), fixedSource, "display", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".userSelected", focus.getNamedChild("userSelected", false), fixed.getUserSelectedElement(), fixedSource, "userSelected", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkCoding(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition theElementCntext, boolean inCodeableConcept, boolean checkDisplay, NodeStack stack) {
    if (!inCodeableConcept || theElementCntext.hasBinding()) {
      String code = element.getNamedChildValue("code", false);
      String system = element.getNamedChildValue("system", false);
      if (code != null && system == null) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_SYSTEM_NO_CODE); 
      }
      String version = element.getNamedChildValue("version", false);
      String display = element.getNamedChildValue("display", false);
      return checkCodedElement(errors, path, element, profile, theElementCntext, inCodeableConcept, checkDisplay, stack, code, system, version, display);
    } else {
      return true;
    }
  }

  private boolean checkCodedElement(List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition theElementCntext, boolean inCodeableConcept, boolean checkDisplay, NodeStack stack,
      String theCode, String theSystem, String theVersion, String theDisplay) {
    boolean ok = true;
    BooleanHolder checked = new BooleanHolder(false);

    if (theSystem != null && theCode != null && !noTerminologyChecks) {
      try {
        if (theElementCntext != null && theElementCntext.hasBinding()) {
          ElementDefinitionBindingComponent binding = theElementCntext.getBinding();
          if (warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, binding != null, I18nConstants.TERMINOLOGY_TX_BINDING_MISSING2, path)) {
            try {
              Coding c = ObjectConverter.readAsCoding(element);
              if (binding.hasValueSet()) {
                String vsRef = binding.getValueSet();
                ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
                BindingStrength strength = binding.getStrength();
                Extension vsMax = binding.getExtensionByUrl(ExtensionDefinitions.EXT_MAX_VALUESET);
                
                ok = validateBindingCodedElement(errors, path, element, profile, stack, theCode, theSystem, ok, checked, c, vsRef, valueset, strength, vsMax, true, null);
//              } else if (binding.hasValueSet()) {
//                hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_CANTCHECK);
                
              } else if (!inCodeableConcept && !noBindingMsgSuppressed) {
                hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSOURCE, path);
              }

              for (ElementDefinitionBindingAdditionalComponent ab : binding.getAdditional()) {
                StringBuilder b = new StringBuilder();
                if (isTestableBinding(ab) && isInScope(ab, profile, getResource(stack), b)) {
                  String vsRef = ab.getValueSet();
                  ValueSet valueset = resolveBindingReference(profile, vsRef, profile.getUrl(), profile);
                  BindingStrength strength = convertPurposeToStrength(ab.getPurpose());
                  
                  ok = validateBindingCodedElement(errors, path, element, profile, stack, theCode, theSystem, ok, checked, c, vsRef, valueset, strength, null, false, b.toString()) && ok;
                }
              }
            } catch (Exception e) {
              if (STACK_TRACE) e.printStackTrace();
              warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODING1, e.getMessage());
            }
          }
        } 
      } catch (Exception e) {
        if (STACK_TRACE) e.printStackTrace();
        rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_ERROR_CODING2, e.getMessage(), e.toString());
        ok = false;
      }
      if (!checked.ok()) {
        ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, theSystem == null || isCodeSystemReferenceValid(theSystem), I18nConstants.TERMINOLOGY_TX_SYSTEM_RELATIVE) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, !isValueSet(theSystem), I18nConstants.TERMINOLOGY_TX_SYSTEM_VALUESET2, theSystem) && ok;
        if (ok) {
          ok = checkCode(errors, element, path, theCode, theSystem, theVersion, theDisplay, checkDisplay, stack);
        }
      }
    }
    return ok;
  }

  private Element getResource(NodeStack stack) {
    if (stack.getElement().isResource()) {
      return stack.getElement();
    }
    if (stack.getParent() == null) {
      return null;
    }
    return getResource(stack.getParent());
  }

  private boolean validateBindingCodedElement(List<ValidationMessage> errors, String path, Element element,
      StructureDefinition profile, NodeStack stack, String theCode, String theSystem, boolean ok, BooleanHolder checked,
      Coding c, String vsRef, ValueSet valueset, BindingStrength strength, Extension vsMax, boolean base, String usageNote) {
    if (valueset == null) {
      CodeSystem cs = context.fetchCodeSystem(vsRef);
      if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(vsRef))) {
        warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, valueset != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(vsRef));
      } else {
        ok = false;
      }
    } else {  
      BindingContext bc = base ? BindingContext.BASE : BindingContext.ADDITIONAL;
      long t = System.nanoTime();
      ValidationResult vr = null;
      if (strength != BindingStrength.EXAMPLE) {
        checked.set(true);
        vr = checkCodeOnServer(stack, valueset, c);
      }
      ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, strength == BindingStrength.EXTENSIBLE, vsRef, strength) && ok;

      timeTracker.tx(t, "vc "+c.getSystem()+"#"+c.getCode()+" '"+c.getDisplay()+"'");
      if (strength == BindingStrength.REQUIRED) {
        removeTrackedMessagesForLocation(errors, element, path);
      }

      if (vr != null && !vr.isOk()) {
        if (vr.IsNoService())
          txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_BINDING_NOSERVER, theSystem+"#"+theCode);
        else if (vr.getErrorClass() != null && !vr.getErrorClass().isInfrastructure()) {
          if (strength == BindingStrength.REQUIRED)
            ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_4a, describeReference(vsRef, valueset, bc, usageNote), vr.getMessage(), theSystem+"#"+theCode) && ok;
          else if (strength == BindingStrength.EXTENSIBLE) {
            if (vsMax != null)
              checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(vsMax), c, stack);
            else if (!noExtensibleWarnings)
              txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_5, describeReference(vsRef, valueset, bc, usageNote), theSystem+"#"+theCode);
          } else if (strength == BindingStrength.PREFERRED) {
            if (baseOnly) {
              txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_CONFIRM_6, describeReference(vsRef, valueset, bc, usageNote), theSystem+"#"+theCode);
            }
          }
        } else if (strength == BindingStrength.REQUIRED)
          ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_12, describeReference(vsRef, valueset, bc, usageNote), getErrorMessage(vr.getMessage()), theSystem+"#"+theCode) && ok;
        else if (strength == BindingStrength.EXTENSIBLE) {
          if (vsMax != null)
            ok = checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringFromExtension(vsMax), c, stack) && ok;
          else if (!noExtensibleWarnings) {
            txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_13, describeReference(vsRef, valueset, bc, usageNote), getErrorMessage(vr.getMessage()), c.getSystem()+"#"+c.getCode());
          }
        } else if (strength == BindingStrength.PREFERRED) {
          if (baseOnly) {
            txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_14, describeReference(vsRef, valueset, bc, usageNote), getErrorMessage(vr.getMessage()), theSystem+"#"+theCode);
          }
        }
      } else if (vr != null && vr.getMessage() != null) {
        if (vr.getSeverity() == IssueSeverity.INFORMATION) {
          txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
        } else {
          txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
        }
      }
    }
    return ok;
  }

  private boolean isValueSet(String url) {
    try {
      ValueSet vs = context.fetchResourceWithException(ValueSet.class, url);
      return vs != null;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean checkContactPoint(List<ValidationMessage> errors, String path, Element focus, ContactPoint fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".system", focus.getNamedChild("system", false), fixed.getSystemElement(), fixedSource, "system", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".value", focus.getNamedChild("value", false), fixed.getValueElement(), fixedSource, "value", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".use", focus.getNamedChild("use", false), fixed.getUseElement(), fixedSource, "use", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".period", focus.getNamedChild("period", false), fixed.getPeriod(), fixedSource, "period", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkExtension(ValidationContext valContext, List<ValidationMessage> errors, String path, Element resource, Element container, Element element, ElementDefinition def, StructureDefinition profile, NodeStack stack, NodeStack containerStack, String extensionUrl, ResourcePercentageLogger pct, ValidationMode mode) throws FHIRException {
    boolean ok = true;
    String url = element.getNamedChildValue("url", false);
    String u = url.contains("|") ? url.substring(0, url.indexOf("|")) : url;
    boolean isModifier = element.getName().equals("modifierExtension");
    assert def.getIsModifier() == isModifier;

    ok = rule(errors, "2025-01-28", IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']",
        valContext.isMatchetype() || !Utilities.existsInList(url, "http://hl7.org/fhir/tools/StructureDefinition/matchetype-optional", 
            "http://hl7.org/fhir/tools/StructureDefinition/matchetype-count", "http://hl7.org/fhir/tools/StructureDefinition/matchetype-value"),
        I18nConstants.RESOURCE_NOT_MATCHETYPE_EXTENSION, url) && ok;
    
    long t = System.nanoTime();
    StructureDefinition ex = Utilities.isAbsoluteUrl(u) ? context.fetchResource(StructureDefinition.class, u) : null;
    if (ex == null) {
      ex = getXverExt(errors, path, element, url);
    }
    if (url.contains("|")) {
      if (ex == null) {
        ex = Utilities.isAbsoluteUrl(url) ? context.fetchResource(StructureDefinition.class, url) : null;
        if (ex == null) {
          warning(errors, "2022-12-17", IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", false, I18nConstants.EXT_VER_URL_NO_MATCH);
        } else {
          ok = rule(errors, "2022-12-17", IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", false, I18nConstants.EXT_VER_URL_IGNORE) && ok;
        }      
      } else {
        if (url.equals(ex.getUrl())) { 
          warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", false, I18nConstants.EXT_VER_URL_MISLEADING);
        } else if (url.equals(ex.getVersionedUrl())) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", false, I18nConstants.EXT_VER_URL_NOT_ALLOWED) && ok;
        } else { // 
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", false, I18nConstants.EXT_VER_URL_REVERSION, ex.getVersion()) && ok;
        }
      }
    }
        
    timeTracker.sd(t);
    if (ex == null) {
      if (extensionUrl != null && !isAbsolute(url)) {
        if (extensionUrl.equals(profile.getUrl())) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path + "[url='" + url + "']", hasExtensionSlice(profile, url), I18nConstants.EXTENSION_EXT_SUBEXTENSION_INVALID, url, profile.getVersionedUrl()) && ok;
        }
      } else if (SpecialExtensions.isKnownExtension(url)) {
        ex = SpecialExtensions.getDefinition(url);
      } else if (BuildExtensions.allConsts().contains(url)) {
        // nothing
      } else if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, allowUnknownExtension(url), I18nConstants.EXTENSION_EXT_UNKNOWN_NOTHERE, url)) {
        hint(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, isKnownExtension(url), I18nConstants.EXTENSION_EXT_UNKNOWN, url);
      } else {
        ok = false;
      }
    }
    if (ex != null) {
      trackUsage(ex, valContext, element);
      // check internal definitions are coherent
      if (isModifier) {
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path + "[url='" + url + "']", def.getIsModifier() == isModifier, I18nConstants.EXTENSION_EXT_MODIFIER_MISMATCHY) && ok;
      } else {
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path + "[url='" + url + "']", def.getIsModifier() == isModifier, I18nConstants.EXTENSION_EXT_MODIFIER_MISMATCHN) && ok;
      }

      // 1. can this extension be used here?
      ok = checkExtensionContext(valContext.getAppContext(), errors, resource, container, ex, containerStack, valContext, isModifier) && ok;
      ok = checkDefinitionStatus(errors, element, path, ex, profile, context.formatMessage(I18nConstants.MSG_DEPENDS_ON_EXTENSION)) && ok;

      if (isModifier)
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path + "[url='" + url + "']", ex.getSnapshot().getElement().get(0).getIsModifier(), I18nConstants.EXTENSION_EXT_MODIFIER_Y, url) && ok;
      else
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path + "[url='" + url + "']", !ex.getSnapshot().getElement().get(0).getIsModifier(), I18nConstants.EXTENSION_EXT_MODIFIER_N, url) && ok;

      // check the type of the extension:
      Set<String> allowedTypes = listExtensionTypes(ex);
      String actualType = getExtensionType(element);
      if (actualType != null)
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, allowedTypes.contains(actualType), I18nConstants.EXTENSION_EXT_TYPE, url, allowedTypes.toString(), actualType) && ok;
      else if (element.hasChildren("extension"))
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, allowedTypes.isEmpty(), I18nConstants.EXTENSION_EXT_SIMPLE_WRONG, url) && ok;
      else
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, allowedTypes.isEmpty(), I18nConstants.EXTENSION_EXT_SIMPLE_ABSENT, url) && ok;

      // 3. is the content of the extension valid?
      ok = validateElement(valContext, errors, ex, ex.getSnapshot().getElement().get(0), null, null, resource, element, "Extension", stack, false, true, url, pct, mode) && ok;

    }
    return ok;
  }

  private boolean hasExtensionSlice(StructureDefinition profile, String sliceName) {
    for (ElementDefinition ed : profile.getSnapshot().getElement()) {
      if (ed.getPath().equals("Extension.extension.url") && ed.hasFixed() && sliceName.equals(ed.getFixed().primitiveValue())) {
        return true;
      }
    }
    return false;
  }

  private String getExtensionType(Element element) {
    for (Element e : element.getChildren()) {
      if (e.getName().startsWith("value")) {
        String tn = e.getName().substring(5);
        String ltn = Utilities.uncapitalize(tn);
        if (isPrimitiveType(ltn))
          return ltn;
        else
          return tn;
      }
    }
    return null;
  }

  private Set<String> listExtensionTypes(StructureDefinition ex) {
    ElementDefinition vd = null;
    for (ElementDefinition ed : ex.getSnapshot().getElement()) {
      if (ed.getPath().startsWith("Extension.value")) {
        vd = ed;
        break;
      }
    }
    Set<String> res = new HashSet<String>();
    if (vd != null && !"0".equals(vd.getMax())) {
      for (TypeRefComponent tr : vd.getType()) {
        res.add(tr.getWorkingCode());
      }
    }
    // special hacks 
    if (ex.getUrl().equals("http://hl7.org/fhir/StructureDefinition/structuredefinition-fhir-type")) {
      res.add("uri");
      res.add("url");
    }
    return res;
  }

  private boolean checkExtensionContext(Object appContext, List<ValidationMessage> errors, Element resource, Element container, StructureDefinition definition, NodeStack stack, ValidationContext valContext, boolean modifier) {
    String extUrl = definition.getUrl();
    boolean ok = false;
    Set<String> pset = new HashSet<>();
    CommaSeparatedStringBuilder contexts = new CommaSeparatedStringBuilder();
    pset.add(stripIndexes(stripRefs(stack.getLiteralPath())));
    for (String s : stack.getLogicalPaths()) {
      String p = stripIndexes(s);
      // all extensions are always allowed in ElementDefinition.example.value, and in fixed and pattern values. TODO: determine the logical paths from the path stated in the element definition....
      if (EXTENSION_CONTEXT_LIST.contains(p)) {
        return true;
      }
      pset.add(p);
    }
    List<String> plist = Utilities.sorted(pset);// logical paths are a set, but we want a predictable order for error messages
    if (definition.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE)) {
      Extension ext = definition.getExtensionByUrl(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE);
      if (ext.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_START)) {
        String v = ExtensionUtilities.readStringExtension(ext, ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_START);
        ok = rule(errors, "2025-01-07", IssueType.BUSINESSRULE, container.line(), container.col(), stack.getLiteralPath(), 
            VersionUtilities.compareVersions(VersionUtilities.getMajMin(context.getVersion()), v) >= 0,
            I18nConstants.EXTENSION_FHIR_VERSION_EARLIEST, extUrl, VersionUtilities.getNameForVersion(v), v, VersionUtilities.getNameForVersion(context.getVersion()), context.getVersion()) && ok;
      }
      if (ext.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_END)) {
        String v = ExtensionUtilities.readStringExtension(ext, ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_END);
        ok = rule(errors, "2025-01-07", IssueType.BUSINESSRULE, container.line(), container.col(), stack.getLiteralPath(), 
            VersionUtilities.compareVersions(VersionUtilities.getMajMin(context.getVersion()), v) <= 0,
            I18nConstants.EXTENSION_FHIR_VERSION_LATEST, extUrl, VersionUtilities.getNameForVersion(v), v, VersionUtilities.getNameForVersion(context.getVersion()), context.getVersion()) && ok;
      }
    }
    boolean vv = false;
    for (StructureDefinitionContextComponent ctxt : fixContexts(extUrl, definition.getContext())) {
      if (ok) {
        break;
      }
      boolean cok = true;
      if (ctxt.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE)) {
        Extension ext = ctxt.getExtensionByUrl(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE);
        vv = true;
        if (ext.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_START)) {
          String v = ExtensionUtilities.readStringExtension(ext, ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_START);
          cok = VersionUtilities.compareVersions(VersionUtilities.getMajMin(context.getVersion()), v) >= 0 && cok;
        }
        if (ext.hasExtension(ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_END)) {
          String v = ExtensionUtilities.readStringExtension(ext, ExtensionDefinitions.EXT_FHIRVERSION_SPECIFIC_USE_END);
          cok = VersionUtilities.compareVersions(VersionUtilities.getMajMin(context.getVersion()), v) <= 0 && cok;
        }
      }
      if (cok) {
        if (ctxt.getType() == ExtensionContextType.ELEMENT) {
          String en = ctxt.getExpression();
          String pu = null;
          if (en == null) {
            // nothing? It's an error in the extension definition, but that's properly reported elsewhere 
          } else {
            contexts.append("e:" + en);
            if (en.contains("#")) {
              pu = en.substring(0, en.indexOf("#"));
              en = en.substring(en.indexOf("#")+1);          
            } else {
              //pu = en;
            }
            if (Utilities.existsInList(en, "Element", "Any")) {
              ok = true;
            } else if (en.equals("Resource") && container.isResource()) {
              ok = true;
            } else if (en.equals("CanonicalResource") && containsAny(VersionUtilities.getExtendedCanonicalResourceNames(context.getVersion()), plist)) {
              ok = true;
            } else if (hasElementName(plist, en) && pu == null) {
              ok = true;
            }
          }

          if (!ok) {
            if (checkConformsToProfile(appContext, errors, resource, container, stack, extUrl, ctxt.getExpression(), pu)) {
              for (String p : plist) {
                if (ok) {
                  break;
                }
                if (p.equals(en)) {
                  ok = true;
                } else {
                  String pn = p;
                  String pt = "";
                  if (p.contains(".")) {
                    pn = p.substring(0, p.indexOf("."));
                    pt = p.substring(p.indexOf("."));
                  }
                  StructureDefinition sd = context.fetchTypeDefinition(pn);
                  while (sd != null) {
                    if ((sd.getType() + pt).equals(en)) {
                      ok = true;
                      break;
                    }
                    if (sd.getBaseDefinition() != null) {
                      sd = context.fetchResource(StructureDefinition.class, sd.getBaseDefinition(), sd);
                    } else {
                      sd = null;
                    }
                  }
                }
              }
            }
          }
        } else if (ctxt.getType() == ExtensionContextType.EXTENSION) {
          contexts.append("x:" + ctxt.getExpression());
          String ext = null;
          if (stack.getElement().getName().startsWith("value")) {
            NodeStack estack = stack.getParent();
            if (estack != null && estack.getElement().fhirType().equals("Extension")) {
              ext = estack.getElement().getNamedChildValue("url", false);
            }
          } else {
            ext = stack.getElement().getNamedChildValue("url", false);
          }
          if (ctxt.getExpression().equals(ext)) {
            ok = true;
          } else if (ext != null) {
            plist.add(ext);
          }
        } else if (ctxt.getType() == ExtensionContextType.FHIRPATH) {
          contexts.append("p:" + ctxt.getExpression());
          // The context is all elements that match the FHIRPath query found in the expression.
          List<Base> res = fpe.evaluate(valContext, resource, valContext.getRootResource(), resource, fpe.parse(ctxt.getExpression()));
          if (res.contains(container)) {
            ok = true;
          }
        } else {
          throw new Error(context.formatMessage(I18nConstants.UNRECOGNISED_EXTENSION_CONTEXT_, ctxt.getTypeElement().asStringValue()));
        }
      }
    }
    if (!ok) {
      if (definition.hasUserData(XVerExtensionManager.XVER_EXT_MARKER)) {
        warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, container.line(), container.col(), stack.getLiteralPath(), false,
            modifier ? I18nConstants.EXTENSION_EXTM_CONTEXT_WRONG_XVER : I18nConstants.EXTENSION_EXTP_CONTEXT_WRONG_XVER, extUrl, contexts.toString(), plist.toString(), definition.getUserString(XVerExtensionManager.XVER_VER_MARKER));
      } else if (vv) {
        rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, container.line(), container.col(), stack.getLiteralPath(), false,
            modifier ? I18nConstants.EXTENSION_EXTM_CONTEXT_WRONG_VER : I18nConstants.EXTENSION_EXTP_CONTEXT_WRONG_VER, extUrl, contexts.toString(), plist.toString(), definition.getUserString(XVerExtensionManager.XVER_VER_MARKER), context.getVersion());        
      } else {
        rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, container.line(), container.col(), stack.getLiteralPath(), false,
            modifier ? I18nConstants.EXTENSION_EXTM_CONTEXT_WRONG : I18nConstants.EXTENSION_EXTP_CONTEXT_WRONG, extUrl, contexts.toString(), plist.toString(), definition.getUserString(XVerExtensionManager.XVER_VER_MARKER));        
      }
      return false;
    } else {
      if (definition.hasContextInvariant()) {
        for (StringType s : definition.getContextInvariant()) {
          if (!fpe.evaluateToBoolean(valContext, resource, valContext.getRootResource(), container, fpe.parse(s.getValue()))) {
            if (definition.hasUserData(XVerExtensionManager.XVER_EXT_MARKER)) {
              warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, container.line(), container.col(), stack.getLiteralPath(), false, I18nConstants.PROFILE_EXT_NOT_HERE, extUrl, s.getValue());              
              return true;
            } else {
              rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, container.line(), container.col(), stack.getLiteralPath(), false, I18nConstants.PROFILE_EXT_NOT_HERE, extUrl, s.getValue());
              return false;
            }
          }
        }
      }
      return true;
    }
  }

  private String stripRefs(String literalPath) {
    if (literalPath.contains(".resolve().ofType(")) {
      String s = literalPath.substring(literalPath.lastIndexOf(".resolve().")+18);
      int i = s.indexOf(")");
      s = s.substring(0, i)+s.substring(i+1);
      return s;
    } else {
      return literalPath;
    }
  }

  private boolean containsAny(Set<String> set, List<String> list) {
    for (String p : list) {
      if (set.contains(p)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasElementName(List<String> plist, String en) {
    String[] ep = en.split("\\.");
    for (String s : plist) {
      if (s.equals(en)) {
        return true;
      }
      String[] sp = s.split("\\.");
      int si = 0;
      int ei = 0;
      boolean mismatch = false;
      while (si < sp.length && ei < ep.length) {
        var ps = sp[si];
        var pe = ep[ei]; 
        if (!ps.equals(pe)) {
          if (pe.endsWith("[x]")) {
            if (ps.equals(pe.substring(0, pe.length()-3)) && si < sp.length - 1 && sp[si+1].startsWith("ofType(")) {
              si++; 
            } else {
              mismatch = true;
            }
          } else {
            mismatch = true;
          }
        }
        si++;
        ei++;
      }
      if (!mismatch && si == sp.length && ei == ep.length) {
        return true;
      }
    }
    return false;
  }

  private boolean checkConformsToProfile(Object appContext, List<ValidationMessage> errors, Element resource, Element container, NodeStack stack, String extUrl, String expression, String pu) {
    if (pu == null) {
      return true;
    }
    if (pu.equals("http://hl7.org/fhir/StructureDefinition/"+resource.fhirType())) {
      return true;
    }
    StructureDefinition sd = context.fetchResource(StructureDefinition.class, pu);
    if (!rule(errors, "2023-07-03", IssueType.UNKNOWN, container.line(), container.col(), stack.getLiteralPath(), sd != null,I18nConstants.EXTENSION_CONTEXT_UNABLE_TO_FIND_PROFILE, extUrl, expression)) {
      return false;
    } else if (sd.getType().equals(resource.fhirType())) {
      List<ValidationMessage> valerrors = new ArrayList<ValidationMessage>();
      ValidationMode mode = new ValidationMode(ValidationReason.Expression, ProfileSource.FromExpression);
      validateResource(new ValidationContext(appContext, resource), valerrors, resource, resource, sd, IdStatus.OPTIONAL, new NodeStack(context, null, resource, validationLanguage), null, mode, false, false);
      boolean ok = true;
      List<ValidationMessage> record = new ArrayList<>();
      for (ValidationMessage v : valerrors) {
        ok = ok && !v.getLevel().isError();
        if (v.getLevel().isError() || v.isSlicingHint()) {
          record.add(v);
        }
      }
      return ok;
    } else {
      warning(errors, "2023-07-03", IssueType.UNKNOWN, container.line(), container.col(), stack.getLiteralPath(), false,
          I18nConstants.EXTENSION_CONTEXT_UNABLE_TO_CHECK_PROFILE, extUrl, expression, pu);
      return true;
    }
  }

  private List<StructureDefinitionContextComponent> fixContexts(String extUrl, List<StructureDefinitionContextComponent> list) {
    List<StructureDefinitionContextComponent> res = new ArrayList<>();
    for (StructureDefinitionContextComponent ctxt : list) {
      res.add(ctxt.copy());
    }
    if (ExtensionDefinitions.EXT_FHIR_TYPE.equals(extUrl)) {
      list.get(0).setExpression("ElementDefinition.type");
    }
    // the history of this is a mess - see https://jira.hl7.org/browse/FHIR-13328
    // we in practice we will support it in either place, but the specification says on ElementDefinition, not on ElementDefinition.type
    // but this creates validation errors people can't fix all over the place if we don't do this.
    if ("http://hl7.org/fhir/StructureDefinition/regex".equals(extUrl)) {
      StructureDefinitionContextComponent e = new StructureDefinitionContextComponent();
      e.setExpression("ElementDefinition.type");
      e.setType(ExtensionContextType.ELEMENT);
      list.add(e);
    }
    if ("http://hl7.org/fhir/StructureDefinition/structuredefinition-normative-version".equals(extUrl)) {
      list.get(0).setExpression("Element"); // well, it can't be used anywhere but the list of places it can be used is quite long
    }
    if (!VersionUtilities.isThisOrLater("4.6", context.getVersion())) {
      if (Utilities.existsInList(extUrl, "http://hl7.org/fhir/StructureDefinition/capabilitystatement-expectation", "http://hl7.org/fhir/StructureDefinition/capabilitystatement-prohibited")) {
        list.get(0).setExpression("Element"); // well, they can't be used anywhere but the list of places they can be used is quite long        
      }
    }
    return list;
  }

  private String stripIndexes(String path) {
    boolean skip = false;
    StringBuilder b = new StringBuilder();
    for (char c : path.toCharArray()) {
      if (skip) {
        if (c == ']') {
          skip = false;
        }
      } else if (c == '[') {
        skip = true;
      } else {
        b.append(c);
      }
    }
    return b.toString();
  }

  @SuppressWarnings("rawtypes")
  public boolean checkFixedValue(List<ValidationMessage> errors, String path, Element focus, org.hl7.fhir.r5.model.Element fixed, String fixedSource, String propName, Element parent, boolean pattern, String context) {
    boolean ok = true;
    if ((fixed == null || fixed.isEmpty()) && focus == null) {
      ; // this is all good
    } else if ((fixed == null || fixed.isEmpty()) && focus != null) {
      ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, pattern, I18nConstants.PROFILE_VAL_NOTALLOWED, focus.getName(), (pattern ? "pattern" : "fixed value"));
    } else if (fixed != null && !fixed.isEmpty() && focus == null) {
      ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, parent == null ? -1 : parent.line(), parent == null ? -1 : parent.col(), path, false, I18nConstants.PROFILE_VAL_MISSINGELEMENT, propName, fixedSource);
    } else {
      String value = focus.primitiveValue();
      if (fixed instanceof org.hl7.fhir.r5.model.BooleanType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.BooleanType) fixed).asStringValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.BooleanType) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.IntegerType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.IntegerType) fixed).asStringValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.IntegerType) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.DecimalType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.DecimalType) fixed).asStringValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.DecimalType) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.Base64BinaryType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.Base64BinaryType) fixed).asStringValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.Base64BinaryType) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.InstantType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.InstantType) fixed).getValue().toString(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.InstantType) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.CodeType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.CodeType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.CodeType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.Enumeration)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.Enumeration) fixed).asStringValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.Enumeration) fixed).asStringValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.StringType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.StringType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.StringType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.UriType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.UriType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.UriType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.DateType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.DateType) fixed).getValue().toString(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.DateType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.DateTimeType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.DateTimeType) fixed).getValue().toString(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.DateTimeType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.OidType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.OidType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.OidType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.UuidType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.UuidType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.UuidType) fixed).getValue(), context);
      else if (fixed instanceof org.hl7.fhir.r5.model.IdType)
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, check(((org.hl7.fhir.r5.model.IdType) fixed).getValue(), value), I18nConstants._DT_FIXED_WRONG, value, ((org.hl7.fhir.r5.model.IdType) fixed).getValue(), context);
      else if (fixed instanceof Quantity)
        ok = checkQuantity(errors, path, focus, (Quantity) fixed, fixedSource, pattern, context) && ok;
      else if (fixed instanceof Address)
        ok = checkAddress(errors, path, focus, (Address) fixed, fixedSource, pattern, context);
      else if (fixed instanceof ContactPoint)
        ok = checkContactPoint(errors, path, focus, (ContactPoint) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Attachment)
        ok = checkAttachment(errors, path, focus, (Attachment) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Identifier)
        ok = checkIdentifier(errors, path, focus, (Identifier) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Coding)
        ok = checkCoding(errors, path, focus, (Coding) fixed, fixedSource, pattern, context);
      else if (fixed instanceof HumanName)
        ok = checkHumanName(errors, path, focus, (HumanName) fixed, fixedSource, pattern, context);
      else if (fixed instanceof CodeableConcept)
        ok = checkCodeableConcept(errors, path, focus, (CodeableConcept) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Timing)
        ok = checkTiming(errors, path, focus, (Timing) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Period)
        ok = checkPeriod(errors, path, focus, (Period) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Range)
        ok = checkRange(errors, path, focus, (Range) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Ratio)
        ok = checkRatio(errors, path, focus, (Ratio) fixed, fixedSource, pattern, context);
      else if (fixed instanceof SampledData)
        ok = checkSampledData(errors, path, focus, (SampledData) fixed, fixedSource, pattern, context);
      else if (fixed instanceof Reference)
        ok = checkReference(errors, path, focus, (Reference) fixed, fixedSource, pattern, context);
      else
        ok = rule(errors, NO_RULE_DATE, IssueType.EXCEPTION, focus.line(), focus.col(), path, false, I18nConstants.INTERNAL_INT_BAD_TYPE, fixed.fhirType());
      List<Element> extensions = new ArrayList<Element>();
      focus.getNamedChildren("extension", extensions);
      if (fixed.getExtension().size() == 0) {
        ok = rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, extensions.size() == 0 || pattern == true, I18nConstants.EXTENSION_EXT_FIXED_BANNED) && ok;
      } else if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, extensions.size() == fixed.getExtension().size(), I18nConstants.EXTENSION_EXT_COUNT_MISMATCH, Integer.toString(fixed.getExtension().size()), Integer.toString(extensions.size()))) {
        for (Extension e : fixed.getExtension()) {
          Element ex = getExtensionByUrl(extensions, e.getUrl());
          if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, ex != null, I18nConstants.EXTENSION_EXT_COUNT_NOTFOUND, e.getUrl())) {
            ok = checkFixedValue(errors, path, ex.getNamedChild("extension", false).getNamedChild("value", false), e.getValue(), fixedSource, "extension.value", ex.getNamedChild("extension", false), false, context) && ok;
          } else {
            ok = false;
          }
        }
      } else {
        ok = false;
      }
    }
    return ok;
  }

  private boolean checkHumanName(List<ValidationMessage> errors, String path, Element focus, HumanName fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".use", focus.getNamedChild("use", false), fixed.getUseElement(), fixedSource, "use", focus, pattern, context);
    ok = checkFixedValue(errors, path + ".text", focus.getNamedChild("text", false), fixed.getTextElement(), fixedSource, "text", focus, pattern, context);
    ok = checkFixedValue(errors, path + ".period", focus.getNamedChild("period", false), fixed.getPeriod(), fixedSource, "period", focus, pattern, context);

    List<Element> parts = new ArrayList<Element>();
    if (!pattern || fixed.hasFamily()) {
      focus.getNamedChildren("family", parts);
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, parts.size() > 0 == fixed.hasFamily(), I18nConstants.FIXED_TYPE_CHECKS_DT_NAME_FAMILY, (fixed.hasFamily() ? "1" : "0"), Integer.toString(parts.size()))) {
        for (int i = 0; i < parts.size(); i++)
          ok = checkFixedValue(errors, path + ".family", parts.get(i), fixed.getFamilyElement(), fixedSource, "family", focus, pattern, context) && ok;
      } else {
        ok = false;
      }
    }
    if (!pattern || fixed.hasGiven()) {
      focus.getNamedChildren("given", parts);
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, parts.size() == fixed.getGiven().size(), I18nConstants.FIXED_TYPE_CHECKS_DT_NAME_GIVEN, Integer.toString(fixed.getGiven().size()), Integer.toString(parts.size()))) {
        for (int i = 0; i < parts.size(); i++)
          ok = checkFixedValue(errors, path + ".given", parts.get(i), fixed.getGiven().get(i), fixedSource, "given", focus, pattern, context) && ok;
      } else {
        ok = false;
      }
    }
    if (!pattern || fixed.hasPrefix()) {
      focus.getNamedChildren("prefix", parts);
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, parts.size() == fixed.getPrefix().size(), I18nConstants.FIXED_TYPE_CHECKS_DT_NAME_PREFIX, Integer.toString(fixed.getPrefix().size()), Integer.toString(parts.size()))) {
        for (int i = 0; i < parts.size(); i++)
          ok = checkFixedValue(errors, path + ".prefix", parts.get(i), fixed.getPrefix().get(i), fixedSource, "prefix", focus, pattern, context) && ok;
      } else {
        ok = false;
      }
    }
    if (!pattern || fixed.hasSuffix()) {
      focus.getNamedChildren("suffix", parts);
      if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, parts.size() == fixed.getSuffix().size(), I18nConstants.FIXED_TYPE_CHECKS_DT_NAME_SUFFIX, Integer.toString(fixed.getSuffix().size()), Integer.toString(parts.size()))) {
        for (int i = 0; i < parts.size(); i++)
          ok = checkFixedValue(errors, path + ".suffix", parts.get(i), fixed.getSuffix().get(i), fixedSource, "suffix", focus, pattern, context) && ok;
      } else {
        ok = false;
      }
    }
    return ok;
  }

  private boolean checkIdentifier(List<ValidationMessage> errors, String path, Element element, ElementDefinition context) {
    boolean ok = true;
    String system = element.getNamedChildValue("system", false);
    ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, system == null || isIdentifierSystemReferenceValid(system), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_IDENTIFIER_SYSTEM) && ok;
    if ("urn:ietf:rfc:3986".equals(system)) {
      String value = element.getNamedChildValue("value", false);
      ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, value == null || isAbsolute(value), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_IDENTIFIER_IETF_SYSTEM_VALUE, value) && ok; 
    }
    if ("https://tools.ietf.org/html/rfc4122".equals(system)) {
      String value = element.getNamedChildValue("value", false);
      if (value != null) {
        if (value.startsWith("urn:uuid:")) {
          warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_IDENTIFIER_IETF_SYSTEM_WRONG_3, value);
        } else if (UUIDUtilities.isValidUUID(value)) {
          warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false,  I18nConstants.TYPE_SPECIFIC_CHECKS_DT_IDENTIFIER_IETF_SYSTEM_WRONG_2, value);
        } else {
          ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_IDENTIFIER_IETF_SYSTEM_WRONG_1, value) && ok;
        }
      }
    }
    return ok;
  }

  private boolean checkIdentifier(List<ValidationMessage> errors, String path, Element focus, Identifier fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".use", focus.getNamedChild("use", false), fixed.getUseElement(), fixedSource, "use", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".type", focus.getNamedChild(TYPE, false), fixed.getType(), fixedSource, TYPE, focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".system", focus.getNamedChild("system", false), fixed.getSystemElement(), fixedSource, "system", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".value", focus.getNamedChild("value", false), fixed.getValueElement(), fixedSource, "value", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".period", focus.getNamedChild("period", false), fixed.getPeriod(), fixedSource, "period", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".assigner", focus.getNamedChild("assigner", false), fixed.getAssigner(), fixedSource, "assigner", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkPeriod(List<ValidationMessage> errors, String path, Element focus, Period fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".start", focus.getNamedChild("start", false), fixed.getStartElement(), fixedSource, "start", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".end", focus.getNamedChild("end", false), fixed.getEndElement(), fixedSource, "end", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkPrimitive(ValidationContext valContext, List<ValidationMessage> errors, String path, String type, ElementDefinition context, Element e, StructureDefinition profile, NodeStack node, NodeStack parentNode, Element resource) throws FHIRException {
    boolean ok = true;

    // sanity check. The only children allowed are id and extension, but value might slip through in some circumstances. 
    for (Element child : e.getChildren()) {
      ok = rule(errors, "2024-02-28", IssueType.INVALID, child.line(), child.col(), path, !"value".equals(child.getName()), I18nConstants.ILLEGAL_PROPERTY, "value") && ok;
    }
    
    if (isBlank(e.primitiveValue())) {
      if (e.primitiveValue() == null)
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.hasChildren(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_VALUEEXT) && ok;
      else if (e.primitiveValue().length() == 0)
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.hasChildren(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_NOTEMPTY) && ok;
      else if (Utilities.isAllWhitespace(e.primitiveValue()))
        warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.hasChildren(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_WS);
      if (context.hasBinding()) {
        ok = rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, e.line(), e.col(), path, context.getBinding().getStrength() != BindingStrength.REQUIRED, I18nConstants.Terminology_TX_Code_ValueSet_MISSING) && ok;
      }
      ok = rule(errors, "2023-06-18", IssueType.INVALID, e.line(), e.col(), path, !context.getMustHaveValue(), I18nConstants.PRIMITIVE_MUSTHAVEVALUE_MESSAGE, context.getId(), profile.getVersionedUrl()) && ok;
      if (context.hasValueAlternatives()) {
        boolean found = false;
        CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
        for (CanonicalType ct : context.getValueAlternatives()) {
          found = found || e.hasExtension(ct.getValue());
          b.append(ct.getValue());
        }
        ok = rulePlural(errors, "2023-06-18", IssueType.INVALID, e.line(), e.col(), path, found, context.getValueAlternatives().size(), I18nConstants.PRIMITIVE_VALUE_ALTERNATIVES_MESSAGE, context.getId(), profile.getVersionedUrl(), b.toString()) && ok;
      }
      return ok;
    } else {
      boolean hasBiDiControls = UnicodeUtilities.hasBiDiChars(e.primitiveValue());
      if (hasBiDiControls) {
        if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, e.line(), e.col(), path, !noUnicodeBiDiControlChars, I18nConstants.UNICODE_BIDI_CONTROLS_CHARS_DISALLOWED, UnicodeUtilities.replaceBiDiChars(e.primitiveValue()))) {
          String msg = UnicodeUtilities.checkUnicodeWellFormed(e.primitiveValue());
          warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, msg == null, I18nConstants.UNICODE_BIDI_CONTROLS_CHARS_MATCH, msg);
        } else {
          ok = false;
        }
      }
      Set<String> badChars = new HashSet<>();
      for (char ch : e.primitiveValue().toCharArray()) {
        if (ch < 32 && !(ch == '\r' || ch == '\n' || ch == '\t')) {
          // can't get to here with xml - the parser fails if you try
          badChars.add(Integer.toHexString(ch));
        }        
      }
      warningPlural(errors, "2023-07-26", IssueType.INVALID, e.line(), e.col(), path, badChars.isEmpty(), badChars.size(), I18nConstants.UNICODE_XML_BAD_CHARS, badChars.toString());      
    }
    
    if (valContext.isMatchetype() && e.primitiveValue().startsWith("$")) {
      switch (e.primitiveValue()) {
      case "$semver$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "code"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$url$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "url", "uri", "uuid", "oid", "canonical"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$token$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "code"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$string$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "string"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$date$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "date", "dateTime"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$version$":
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "code"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$id$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "id"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$instant$": 
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "dateTime", "instant"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$uuid$":
        warning(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, Utilities.existsInList(type, "url", "uri", "uuid", "canonical"), I18nConstants.RESOURCE_MATCHETYPE_SUSPECT_TYPE, type, e.primitiveValue());
        break;
      case "$$":
        break;
      default:
        if (e.primitiveValue().startsWith("$external:")) {

        } else if (e.primitiveValue().startsWith("$choice:")) {

        } else if (e.primitiveValue().startsWith("$fragments:")) {

        } else {
          rule(errors, "2025-01-28", IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.RESOURCE_MATCHETYPE_UNKNOWN_PATTERN, e.primitiveValue());
        }
      }
    } else {
      if (context.hasExtension(ExtensionDefinitions.EXT_MIN_LENGTH) && e.hasPrimitiveValue()) {
        int length = e.primitiveValue().length();  
        int spec = ExtensionUtilities.readIntegerExtension(context, ExtensionDefinitions.EXT_MIN_LENGTH, 0);
        ok = rule(errors, "2024-11-02", IssueType.INVALID, e.line(), e.col(), path, length >= spec, I18nConstants.PRIMITIVE_TOO_SHORT, length, spec) && ok;

      }
      String regex = context.getExtensionString(ExtensionDefinitions.EXT_REGEX);
      // there's a messy history here - this extension snhould only be on the element definition itself, but for historical reasons 
      //( see task 13328) it might also be found on one the types
      if (regex != null) {
        for (TypeRefComponent tr : context.getType()) {
          if (tr.hasExtension(ExtensionDefinitions.EXT_REGEX)) {
            regex = tr.getExtensionString(ExtensionDefinitions.EXT_REGEX);
            break;
          }
        }      
      }
      if (regex != null) {
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue().matches(regex), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_REGEX, e.primitiveValue(), regex) && ok;
      }

      if (!"xhtml".equals(type)) {
        if (securityChecks) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !containsHtmlTags(e.primitiveValue()), I18nConstants.SECURITY_STRING_CONTENT_ERROR) && ok;
        } else if (!"markdown".equals(type)){
          hint(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !containsHtmlTags(e.primitiveValue()), I18nConstants.SECURITY_STRING_CONTENT_WARNING);
        }
      }


      if (type.equals("boolean")) {
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, "true".equals(e.primitiveValue()) || "false".equals(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_BOOLEAN_VALUE) && ok;
      }
      if (type.equals("uri") || type.equals("oid") || type.equals("uuid") || type.equals("url") || type.equals("canonical")) {
        String url = e.primitiveValue();
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !url.startsWith("oid:"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URI_OID) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !url.startsWith("uuid:"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URI_UUID) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, url.equals(Utilities.trimWS(url).replace(" ", ""))
            // work around an old invalid example in a core package
            || "http://www.acme.com/identifiers/patient or urn:ietf:rfc:3986 if the Identifier.value itself is a full uri".equals(url), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URI_WS, url) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || url.length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || e.primitiveValue().length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && ok;

        if (type.equals("oid")) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, url.startsWith("urn:oid:"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_OID_START) && ok;
        }
        if (type.equals("uuid")) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, url.startsWith("urn:uuid:"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_UUID_STRAT) && ok;
        }
        if (type.equals("canonical")) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, url.startsWith("#") || Utilities.isAbsoluteUrl(url), I18nConstants.TYPE_SPECIFIC_CHECKS_CANONICAL_ABSOLUTE, url) && ok;        
        }

        if (url != null && url.startsWith("urn:uuid:")) {

          String s = url;
          if (s.contains("#")) {
            s = s.substring(0, s.indexOf("#"));
          }
          if (type.equals("canonical") && s.contains("|")) {
            s = s.substring(0, s.lastIndexOf("|")); 
          }
          s = s.substring(9);
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, UUIDUtilities.isValidUUID(s), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_UUID_VALID, s) && ok;
        }
        if (url != null && url.startsWith("urn:oid:")) {
          String cc = url.substring(8);
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, 
              // OIDs roots shorter than 4 chars are almost never valid for namespaces except for 1.3.x
              OIDUtilities.isValidOID(cc) && ((cc.lastIndexOf('.') >= 4 || cc.startsWith("1.3"))),
              I18nConstants.TYPE_SPECIFIC_CHECKS_DT_OID_VALID, cc) && ok;
        }

        if (isCanonicalURLElement(e, node)) {
          // we get to here if this is a defining canonical URL (e.g. CodeSystem.url)
          // the URL must be an IRI if present
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, Utilities.isAbsoluteUrl(url), 
              node.isContained() ? I18nConstants.TYPE_SPECIFIC_CHECKS_CANONICAL_CONTAINED : I18nConstants.TYPE_SPECIFIC_CHECKS_CANONICAL_ABSOLUTE, url) && ok;                  
        } else if (!e.getProperty().getDefinition().getPath().equals("Bundle.entry.fullUrl")) { // we don't check fullUrl here; it's not a reference, it's a definition. It'll get checked as part of checking the bundle
          ok = validateReference(valContext, errors, path, type, context, e, parentNode == null ? null : parentNode.getElement(), url) && ok;
        }
      }
      if (type.equals(ID) && !"Resource.id".equals(context.getBase().getPath())) {
        // work around an old issue with ElementDefinition.id
        if (!context.getPath().equals("ElementDefinition.id")) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, FormatUtilities.isValidId(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ID_VALID, e.primitiveValue()) && ok;
        }
      }
      if (type.equalsIgnoreCase("string") && e.hasPrimitiveValue()) {
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue() == null || e.primitiveValue().length() > 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_NOTEMPTY)) {
          if (warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue() == null || !Utilities.isAllWhitespace(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_STRING_WS_ALL, prepWSPresentation(e.primitiveValue()))) {
            warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue() == null || Utilities.trimWS(e.primitiveValue()).equals(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_STRING_WS, prepWSPresentation(e.primitiveValue()));
          }
          if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue().length() <= 1048576, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_STRING_LENGTH)) {
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || e.primitiveValue().length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && ok;
          } else {
            ok = false;
          }
        } else {
          ok = false;
        }
      }
      if (type.equals("dateTime")) {
        boolean dok = ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path,
            e.primitiveValue()
            .matches("([0-9]([0-9]([0-9][1-9]|[1-9]0)|[1-9]00)|[1-9]000)(-(0[1-9]|1[0-2])(-(0[1-9]|[1-2][0-9]|3[0-1])(T([01][0-9]|2[0-3]):[0-5][0-9]:([0-5][0-9]|60)(\\.[0-9]+)?(Z|(\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))?)?)?)?"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_VALID, "'"+e.primitiveValue()+"' doesn't meet format requirements for dateTime") && ok;
        if (isCoreDefinition(profile) || (context.hasExtension(ExtensionDefinitions.EXT_DATE_RULES) && ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_DATE_RULES).contains("tz-for-time"))) {
          dok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !hasTime(e.primitiveValue()) || hasTimeZone(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_TZ) && dok;
        }
        dok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || e.primitiveValue().length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && dok;
        if (dok) {
          try {
            DateTimeType dt = new DateTimeType(e.primitiveValue());
            if (isCoreDefinition(profile) || !context.hasExtension(ExtensionDefinitions.EXT_DATE_RULES) || ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_DATE_RULES).contains("year-valid")) {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, yearIsValid(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_REASONABLE, e.primitiveValue());
            }
          } catch (Exception ex) {
            rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_VALID, ex.getMessage());
            dok = false;
          }
        }
        ok = ok && dok;
      }
      if (type.equals("time")) {
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path,
            e.primitiveValue()
            .matches("([01][0-9]|2[0-3]):[0-5][0-9]:([0-5][0-9]|60)"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_TIME_VALID) && ok;
        try {
          TimeType dt = new TimeType(e.primitiveValue());
        } catch (Exception ex) {
          rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_TIME_VALID, ex.getMessage());
          ok = false;
        }
      }
      if (type.equals("date")) {
        boolean dok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, e.primitiveValue().matches("([0-9]([0-9]([0-9][1-9]|[1-9]0)|[1-9]00)|[1-9]000)(-(0[1-9]|1[0-2])(-(0[1-9]|[1-2][0-9]|3[0-1]))?)?"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATE_VALID, "'"+e.primitiveValue()+"' doesn't meet format requirements for date");
        dok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || e.primitiveValue().length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && dok;
        if (dok) {
          try {
            DateType dt = new DateType(e.primitiveValue());
            if (isCoreDefinition(profile) || (context.hasExtension(ExtensionDefinitions.EXT_DATE_RULES) && ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_DATE_RULES).contains("year-valid"))) {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, yearIsValid(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_REASONABLE, e.primitiveValue());
            }
          } catch (Exception ex) {
            rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATE_VALID, ex.getMessage());
            dok = false;
          }
        }
        ok = ok && dok;
      }
      if (type.equals("base64Binary")) {
        String encoded = e.primitiveValue();
        if (isNotBlank(encoded)) {
          boolean bok = Base64Util.isValidBase64(encoded);
          if (!bok) {
            String value = encoded.length() < 100 ? encoded : "(snip)";
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_BASE64_VALID, value) && ok;
          } else {
            boolean wsok = !Base64Util.base64HasWhitespace(encoded);
            if (VersionUtilities.isR5VerOrLater(this.context.getVersion())) {
              ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, wsok, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_BASE64_NO_WS_ERROR) && ok;            
            } else {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, wsok, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_BASE64_NO_WS_WARNING);            
            }
          }
          if (bok && context.hasExtension(ExtensionDefinitions.EXT_MAX_SIZE)) {
            int size = Base64Util.countBase64DecodedBytes(encoded);
            long def = Long.parseLong(ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_MAX_SIZE));
            ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, e.line(), e.col(), path, size <= def, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_BASE64_TOO_LONG, size, def) && ok;
          }

        }
      }
      if (type.equals("integer") || type.equals("unsignedInt") || type.equals("positiveInt")) {
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, Utilities.isInteger(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_VALID, e.primitiveValue())) {
          Integer v = Integer.valueOf(e.getValue());
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxValueIntegerType() || !context.getMaxValueIntegerType().hasValue() || (context.getMaxValueIntegerType().getValue() >= v), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_GT, (context.hasMaxValueIntegerType() ? context.getMaxValueIntegerType() : "")) && ok;
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMinValueIntegerType() || !context.getMinValueIntegerType().hasValue() || (context.getMinValueIntegerType().getValue() <= v), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT, (context.hasMinValueIntegerType() ? context.getMinValueIntegerType() : "")) && ok;
          if (type.equals("unsignedInt"))
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, v >= 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT0) && ok;
          if (type.equals("positiveInt"))
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, v > 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT1) && ok;
        } else {
          ok = false;
        }
      }
      if (type.equals("integer64")) {
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, Utilities.isLong(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER64_VALID, e.primitiveValue())) {
          Long v = Long.valueOf(e.getValue());
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxValueInteger64Type() || !context.getMaxValueInteger64Type().hasValue() || (context.getMaxValueInteger64Type().getValue() >= v), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_GT, (context.hasMaxValueInteger64Type() ? context.getMaxValueInteger64Type() : "")) && ok;
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMinValueInteger64Type() || !context.getMinValueInteger64Type().hasValue() || (context.getMinValueInteger64Type().getValue() <= v), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT, (context.hasMinValueInteger64Type() ? context.getMinValueInteger64Type() : "")) && ok;
          if (type.equals("unsignedInt"))
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, v >= 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT0) && ok;
          if (type.equals("positiveInt"))
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, v > 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INTEGER_LT1) && ok;
        } else {
          ok = false;
        }
      }
      if (type.equals("decimal")) {
        if (e.primitiveValue() != null) {
          DecimalStatus ds = Utilities.checkDecimal(e.primitiveValue(), true, false);
          if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, ds == DecimalStatus.OK || ds == DecimalStatus.RANGE, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_VALID, e.primitiveValue())) {
            warning(errors, NO_RULE_DATE, IssueType.VALUE, e.line(), e.col(), path, ds != DecimalStatus.RANGE, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_RANGE, e.primitiveValue());
            try {            
              Decimal v = new Decimal(e.getValue());
              if (context.hasMaxValueDecimalType() && context.getMaxValueDecimalType().hasValue()) {
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, checkDecimalMaxValue(v, context.getMaxValueDecimalType().getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_GT, context.getMaxValueDecimalType()) && ok;
              } else if (context.hasMaxValueIntegerType() && context.getMaxValueIntegerType().hasValue()) {
                // users can also provide a max integer type. It's not clear whether that's actually valid, but we'll check for it anyway
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, checkDecimalMaxValue(v, new BigDecimal(context.getMaxValueIntegerType().getValue())), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_GT, context.getMaxValueIntegerType()) && ok;
              }

              if (context.hasMinValueDecimalType() && context.getMinValueDecimalType().hasValue()) {
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, checkDecimalMinValue(v, context.getMinValueDecimalType().getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_LT, context.getMinValueDecimalType()) && ok;
              } else if (context.hasMinValueIntegerType() && context.getMinValueIntegerType().hasValue()) {
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, checkDecimalMinValue(v, new BigDecimal(context.getMinValueIntegerType().getValue())), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_LT, context.getMinValueIntegerType()) && ok;
              }
            } catch (Exception ex) {
              // should never happen?
            }
          } else {
            ok = false;
          }
        }
        if (context.hasExtension(ExtensionDefinitions.EXT_MAX_DECIMALS)) {
          int dp = e.primitiveValue().contains(".") ? e.primitiveValue().substring(e.primitiveValue().indexOf(".")+1).length() : 0;
          int def = Integer.parseInt(ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_MAX_DECIMALS));
          ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, e.line(), e.col(), path, dp <= def, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_CHARS, dp, def) && ok;
        }
      }
      if (type.equals("instant")) {
        boolean dok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path,
            e.primitiveValue().matches("-?[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])T([01][0-9]|2[0-3]):[0-5][0-9]:([0-5][0-9]|60)(\\.[0-9]+)?(Z|(\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_REGEX, "'"+e.primitiveValue()+"' doesn't meet format requirements for instant)");
        if (dok) {
          try {
            InstantType dt = new InstantType(e.primitiveValue());
            if (isCoreDefinition(profile) || (context.hasExtension(ExtensionDefinitions.EXT_DATE_RULES) && ExtensionUtilities.readStringExtension(context, ExtensionDefinitions.EXT_DATE_RULES).contains("year-valid"))) {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, yearIsValid(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DATETIME_REASONABLE, e.primitiveValue());
            }
          } catch (Exception ex) {
            rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_INSTANT_VALID, ex.getMessage());
            dok = false;
          }
        }
        ok = ok && dok;
      }

      if (type.equals("code") && e.primitiveValue() != null) {
        // Technically, a code is restricted to string which has at least one character and no leading or trailing whitespace, and where there is no whitespace
        // other than single spaces in the contents
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, passesCodeWhitespaceRules(e.primitiveValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CODE_WS, e.primitiveValue()) && ok;
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, !context.hasMaxLength() || context.getMaxLength() == 0 || e.primitiveValue().length() <= context.getMaxLength(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_LENGTH, context.getMaxLength()) && ok;
      }

      if (context.hasBinding() && e.primitiveValue() != null) {
        // special cases
        if ("StructureDefinition.type".equals(context.getPath()) && "http://hl7.org/fhir/StructureDefinition/StructureDefinition".equals(profile.getUrl())) {
          ok = checkTypeValue(errors, path, e, parentNode.getElement());
        } else {
          ok = checkPrimitiveBinding(valContext, errors, path, type, context, e, profile, node) && ok;
        }
      }

      if (type.equals("markdown") && htmlInMarkdownCheck != HtmlInMarkdownCheck.NONE) {
        if (!(path.endsWith(".comment") && parentNode.getElement().hasChild("path") && parentNode.getElement().getNamedChildValue("path").endsWith(".text.div"))) { 
          String raw = e.primitiveValue();
          String processed = MarkDownProcessor.preProcess(raw);
          if (!raw.equals(processed)) {
            int i = 0;
            while (i < raw.length() && raw.charAt(i) == processed.charAt(i)) {
              i++;
            }
            if (i < raw.length()-1 ) {
              if (!warningOrError(htmlInMarkdownCheck == HtmlInMarkdownCheck.ERROR, errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_MARKDOWN_HTML, raw.subSequence(i, i+2))) {
                ok = (htmlInMarkdownCheck != HtmlInMarkdownCheck.ERROR) && ok;
              }
            } else {
              if (!warningOrError(htmlInMarkdownCheck == HtmlInMarkdownCheck.ERROR, errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_MARKDOWN_HTML, raw)) {
                ok = (htmlInMarkdownCheck != HtmlInMarkdownCheck.ERROR) && ok;
              }
            }
          }
        }
      }
      if (type.equals("xhtml")) {
        XhtmlNode xhtml = e.getXhtml();
        if (xhtml != null) { // if it is null, this is an error already noted in the parsers
          ok = new XhtmlValidator(this, errors, node).validate(valContext, e, resource, path, xhtml) && ok;
          ok = checkReferences(valContext, errors, e, path, "div", xhtml, resource) && ok;
          ok = checkImageSources(valContext, errors, e, path, "div", xhtml, resource) && ok;
        }
      }

      if (context.hasFixed()) {
        ok = checkFixedValue(errors, path, e, context.getFixed(), profile.getVersionedUrl(), context.getSliceName(), null, false, profile.getVersionedUrl()+"#"+context.getId()) && ok;
      }
      if (context.hasPattern()) {
        ok = checkFixedValue(errors, path, e, context.getPattern(), profile.getVersionedUrl(), context.getSliceName(), null, true, profile.getVersionedUrl()+"#"+context.getId()) && ok;
      }

      if (ok && !ID_EXEMPT_LIST.contains(e.fhirType())) { // ids get checked elsewhere
        String regext = FHIRPathExpressionFixer.fixRegex(getRegexFromType(e.fhirType()));
        if (regext != null) {
          try {
            String pt = e.primitiveValue();
            String ptFmt = null;
            if (e.getProperty().getDefinition().hasExtension(ExtensionDefinitions.EXT_DATE_FORMAT)) {
              ptFmt = convertForDateFormatToExternal(ExtensionUtilities.readStringExtension(e.getProperty().getDefinition(), ExtensionDefinitions.EXT_DATE_FORMAT), pt);
            }
            boolean matches = pt.matches(regext) || (ptFmt != null && ptFmt.matches(regext));
            if (!matches) {
              if (ptFmt == null) {
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, matches, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_REGEX_TYPE, pt, e.fhirType(), regext) && ok;
              } else {
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, matches, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_REGEX_TYPE_ALT, pt, ptFmt, e.fhirType(), regext) && ok;
              }
            }
          } catch (Throwable ex) {
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_PRIMITIVE_REGEX_EXCEPTION, regext, e.fhirType(), ex.getMessage()) && ok;          
          }
        }
      }
    }
    // for nothing to check
    return ok;
  }

  private String convertForDateFormatToExternal(String fmt, String av) throws FHIRException {
    if ("v3".equals(fmt) || "YYYYMMDDHHMMSS.UUUU[+|-ZZzz]".equals(fmt)) {
      DateTimeType d = new DateTimeType(av);
      return d.getAsV3();
    } else
      throw new FHIRException(context.formatMessage(I18nConstants.UNKNOWN_DATE_FORMAT_, fmt));
  }
  
  private boolean isCoreDefinition(StructureDefinition profile) {
    return profile.getUrl().startsWith("http://hl7.org/fhir/StructureDefinition/") && profile.getKind() != StructureDefinitionKind.LOGICAL;
  }

  private String getRegexFromType(String fhirType) {
    StructureDefinition sd = context.fetchTypeDefinition(fhirType);
    if (sd != null) {
      for (ElementDefinition ed : sd.getSnapshot().getElement()) {
        if (ed.getPath().endsWith(".value")) {
          String regex = ed.getExtensionString(ExtensionDefinitions.EXT_REGEX);
          if (regex != null) {
            return regex;
          }
          for (TypeRefComponent td : ed.getType()) {
            regex = td.getExtensionString(ExtensionDefinitions.EXT_REGEX);
            if (regex != null) {
              return regex;
            }
          }
        }
      }
    }
    return null;
  }

  private boolean checkTypeValue(List<ValidationMessage> errors, String path, Element e, Element sd) {
    String v = e.primitiveValue();
    if (v == null) {
      return rule(errors, "2022-11-02", IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.SD_TYPE_MISSING);
    }
    String url = sd.getChildValue("url");
    String d = sd.getChildValue("derivation"); 
    String k = sd.getChildValue("kind"); 
    if (Utilities.isAbsoluteUrl(v)) {
      warning(errors, "2022-11-02", IssueType.INVALID, e.line(), e.col(), path, d.equals("constraint") || ns(v).equals(ns(url)) || ns(v).equals(ns(url).replace("StructureDefinition/", "")), I18nConstants.SD_TYPE_NOT_MATCH_NS, v, url);
      return rule(errors, "2022-11-02", IssueType.INVALID, e.line(), e.col(), path, "logical".equals(k), I18nConstants.SD_TYPE_NOT_LOGICAL, v, k);
    } else {
      boolean tok = false;
      for (StructureDefinition t : context.fetchResourcesByType(StructureDefinition.class)) {
        if (t.hasSourcePackage() && t.getSourcePackage().getId().startsWith("hl7.fhir.r") && v.equals(t.getType())) {
          tok = true;
        }
      }
      if (tok) {
        if (!(("http://hl7.org/fhir/StructureDefinition/"+v).equals(url))) {
          return rule(errors, "2022-11-02", IssueType.INVALID, e.line(), e.col(), path, "constraint".equals(d), I18nConstants.SD_TYPE_NOT_DERIVED, v);
        } else {
          return true;
        }
      } else {
        return rule(errors, "2022-11-02", IssueType.INVALID, e.line(), e.col(), path, tok, I18nConstants.SD_TYPE_NOT_LOCAL, v);
      }
    }
  }

  private String ns(String url) {
    return url.contains("/") ? url.substring(0, url.lastIndexOf("/")) : url;
  }

  private Object prepWSPresentation(String s) {
    if (Utilities.noString(s)) {
      return "";
    }
    return Utilities.escapeJson(s);
  }

  public boolean validateReference(ValidationContext valContext, List<ValidationMessage> errors, String path, String type, ElementDefinition context, Element e, Element parent, String url) {
    boolean ok = true;
    boolean internal = false;
    String eurl = parent != null && parent.fhirType().equals("Extension") ? parent.getNamedChildValue("url") : null;
    ReferenceDestinationType refType = ReferenceDestinationType.EXTERNAL;
    if (url.startsWith("#")) {
      valContext.getInternalRefs().add(url.substring(1));
      refType = ReferenceDestinationType.CONTAINED;
      internal = true;
    }
    // now, do we check the URI target?
    if (fetcher != null && !type.equals("uuid")) {
      boolean found;
      try {
        if (url.startsWith("#")) {
          List<String> refs = new ArrayList<>();
          int count = countTargetMatches(valContext.getRootResource(), url.substring(1), true, "$", refs);
          found = count > 0;
        } else {
          found = isDefinitionURL(url) || (settings.isAllowExamples() && isExampleUrl(url)) /* || (url.startsWith("http://hl7.org/fhir/tools")) */ || isCommunicationsUrl(url) ||
              SpecialExtensions.isKnownExtension(url) || isXverUrl(url) || SIDUtilities.isKnownSID(url) || isKnownNamespaceUri(url) || isRfcRef(url) || isKnownMappingUri(url) || oids.isKnownOID(url);
          if (!found) {
            found = fetcher.resolveURL(this, valContext, context.getBase().getPath(), url, type, type.equals("canonical"), context.getByType(type) != null ? context.getByType(type).getTargetProfile() : null);
          }
        }
      } catch (IOException e1) {
        found = false;
      }
      if (!found) {
        if (internal) {
          boolean extMatters = isExtensionThatMatters(parent);
          if (isNotHardErrorPathForInternal(context.getBase().getPath()) && Utilities.existsInList(type, "uri", "url") && !extMatters) {
            if (isForPublication()) {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_INTERNAL_WARN, url);
            } else {
              hint(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_INTERNAL_WARN, url);              
            }
          } else {
            if (isForPublication() || Utilities.existsInList(type, "canonical", "Reference") || isHardErrorPathForInternal(context.getBase().getPath()) || extMatters) {
              ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_INTERNAL, url) && ok;
            } else {
              warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_INTERNAL_WARN, url);
            }
          }
        } else if (type.equals("canonical")) {
          if (isExampleUrl(url) && isAllowExamples()) {
            // nothing - these do need to resolve
          } else {
            ReferenceValidationPolicy rp = policyAdvisor.policyForReference(this, valContext, path, url, refType);
            if (rp == ReferenceValidationPolicy.CHECK_EXISTS || rp == ReferenceValidationPolicy.CHECK_EXISTS_AND_TYPE || internal) {
              ok = warningOrError(isKnownSpace(url), errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CANONICAL_RESOLVE, url) && ok;
            } else if (isExampleUrl(url)) {
              ok = rule(errors, "2025-04-03", IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_EXAMPLE, url) && ok;
            } else {
              hint(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CANONICAL_RESOLVE, url);
            }
          }
        } else {
          if (isExampleUrl(url) && isAllowExamples()) {
            // nothing - these do need to resolve
          } else if (isExemptPathForUrlChecking(context, isAbsolute(url), eurl, e, true)) {
            // nothing
          } else if (isKnownSpace(url)) {
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_RESOLVE, url) && ok;;
          } else if (isExampleUrl(url)) {
            ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_EXAMPLE, url) && ok;;
          } else if (isExemptPathForUrlChecking(context, isAbsolute(url), eurl, e, false)) {
            // nothing
          } else if (isHintableElementForUrlChecking(path)) {
            hint(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_RESOLVE, url);
          } else {
            warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_URL_RESOLVE, url);
          }
        }
      } else {
        if (type.equals("canonical")) {
          ReferenceValidationPolicy rp = policyAdvisor.policyForReference(this, valContext, path, url, refType);
          if (rp == ReferenceValidationPolicy.CHECK_EXISTS_AND_TYPE || rp == ReferenceValidationPolicy.CHECK_TYPE_IF_EXISTS || rp == ReferenceValidationPolicy.CHECK_VALID) {
            try {
              Resource r = null;
              if (url.startsWith("#")) {
                r = loadContainedResource(errors, path, valContext.getRootResource(), url.substring(1), Resource.class);
              }
              if (r == null) {
               r = fetcher.fetchCanonicalResource(this, valContext.getAppContext(), url);
              }
              if (r == null) {
                r = this.context.fetchResource(Resource.class, url);
              }
              if (r == null) {
                warningOrError(internal, errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, rp != ReferenceValidationPolicy.CHECK_VALID, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CANONICAL_RESOLVE_NC, url);                    
              } else if (rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, isCorrectCanonicalType(r, context), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CANONICAL_TYPE, url, r.fhirType(), listExpectedCanonicalTypes(context))) {
                if (rp == ReferenceValidationPolicy.CHECK_VALID) {
                  // todo....
                }
                // we resolved one, but if there's no version, check if the reference is potentially ambiguous
                if (!url.contains("|") && r instanceof CanonicalResource) {
                  if (!Utilities.existsInList(context.getBase().getPath(), "ImplementationGuide.dependsOn.uri", "ConceptMap.group.source", "ConceptMap.group.target")) {
                    // ImplementationGuide.dependsOn.version is mandatory, and ConceptMap is checked in the ConceptMap validator
                    Set<String> possibleVersions = fetcher.fetchCanonicalResourceVersions(this, valContext.getAppContext(), url);
                    warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, possibleVersions.size() <= 1, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_CANONICAL_MULTIPLE_POSSIBLE_VERSIONS, 
                        url, ((CanonicalResource) r).getVersion(), CommaSeparatedStringBuilder.join(", ", Utilities.sorted(possibleVersions)));
                  }
                }
              } else {
                ok = false;
              }
            } catch (Exception ex) {
              // won't happen 
            }
          }
        }            
      }
    }
    return ok;
  }

  private boolean isExtensionThatMatters(Element element) {
    String url = null;
    while (url == null || !Utilities.isAbsoluteUrl(url)) {
      if (element == null || !element.fhirType().equals("Extension")) {
        return false;
      }
      url = element.getNamedChildValue("url"); 
      element = element.getParentForValidator();
    }
      
    return Utilities.existsInList(url, ExtensionDefinitions.EXT_TEXT_LINK);
  }

  private boolean isHardErrorPathForInternal(String path) {
    return Utilities.existsInList(path, "ValueSet.compose.include.system", "ValueSet.compose.exclude.system");
  }

  private boolean isNotHardErrorPathForInternal(String path) {
    return Utilities.existsInList(path, "Meta.source"); // exemption for HAPI
  }

  private boolean isHintableElementForUrlChecking(String path) {
     if (Utilities.endsWithInList(path, ".meta.source")) {
       return true;
     } else {
       return false;
     }
  }

  private boolean isCommunicationsUrl(String url) {
    return Utilities.startsWithInList(url, "tel:", "mailto:", "llp:");
  }

  private boolean isKnownMappingUri(String url) {
    return Utilities.existsInList(url, "urn:hl7-org:v3/mif2", 
        "http://cap.org/ecc", "http://clinicaltrials.gov", "http://fda.gov/UDI", "http://github.com/MDMI/ReferentIndexContent", "http://hl7.org/fhir/auditevent", "http://hl7.org/fhir/composition", 
        "http://hl7.org/fhir/consent", "http://hl7.org/fhir/documentreference", "http://hl7.org/fhir/fivews", "http://hl7.org/fhir/interface", "http://hl7.org/fhir/logical", "http://hl7.org/fhir/object-implementation",
        "http://hl7.org/fhir/provenance", "http://hl7.org/fhir/rr", "http://hl7.org/fhir/w5", "http://hl7.org/fhir/workflow", "http://hl7.org/orim", "http://hl7.org/qidam", "http://hl7.org/v2", "http://hl7.org/v3/cda",
        "http://hl7.org/v3", "http://ietf.org/rfc/2445", "http://ihe.net/data-element-exchange", "http://loinc.org", "http://metadata-standards.org/11179/", "http://ncpdp.org/SCRIPT10_6", "http://nema.org/dicom",
        "http://openehr.org", "http://siframework.org/cqf", "http://siframework.org/ihe-sdc-profile", "http://snomed.info/conceptdomain", "http://snomed.org/attributebinding", "http://w3.org/vcard", 
        "http://www.cda-adc.ca/en/services/cdanet/", "http://www.cdisc.org/define-xml", "http://www.cdisc.org/define-xml", "http://www.healthit.gov/quality-data-model", "http://www.hl7.org/v3/PORX_RM020070UV", 
        "http://www.omg.org/spec/ServD/1.0/", "http://www.pharmacists.ca/", "http://www.w3.org/ns/prov", "https://bridgmodel.nci.nih.gov", "https://dicomstandard.org/current", "https://profiles.ihe.net/ITI/TF/Volume3",
        "https://www.iso.org/obp/ui/#iso:std:iso:11238", "https://www.iso.org/obp/ui/#iso:std:iso:11615", "urn:iso:std:iso:11073:10201", "urn:iso:std:iso:11073:10207", "urn:iso:std:iso:11073:20701", "https://www.isbt128.org/uri/");
  }

  private boolean isRfcRef(String url) {
    return url != null && Utilities.startsWithInList(url, "https://tools.ietf.org/html/rfc", "http://tools.ietf.org/html/rfc") && Utilities.isInteger(url.substring(url.indexOf("rfc")+3));
  }

  private boolean isKnownNamespaceUri(String url) {
    return Utilities.existsInList(url, "urn:hl7-org:v3", "urn:hl7-org:sdtc", "http://unstats.un.org/unsd/methods/m49/m49.htm");
  }

  private boolean isExemptPathForUrlChecking(ElementDefinition context, boolean absolute, String eurl, Element element, boolean exampleStep) {
    if (!context.hasBase()) {
      return false;
    }
    
    if (Utilities.existsInList(eurl, "http://hl7.org/fhir/tools/StructureDefinition/ig-page-name")) {
      return true;
    }
    if ("Extension.value[x]".equals(context.getBase().getPath())) {
      String ext = null;
      Element p = element.getParentForValidator();
      while (!Utilities.isAbsoluteUrl(ext) && p != null) {
        if (p.fhirType().equals("Extension")) {
          ext = p.getNamedChildValue("url");
        }
        p = p.getParentForValidator();
      }
      if (ext == null) {
        return true;
      } else {
        
        return Utilities.existsInList(ext, 
            ExtensionDefinitions.EXT_TEXT_LINK  // we're going to check that elsewhere
            );
      }
    }
    if (exampleStep) {
      return false;
    }
    
    if (absolute) {
      return Utilities.existsInList(context.getBase().getPath(),
          "Coding.system",
          "ImplementationGuide.definition.page.source[x]", "ImplementationGuide.definition.page.name",  "ImplementationGuide.definition.page.name[x]",
          "Requirements.statement.satisfiedBy", "Bundle.entry.request.url",
          "Attachment.url",
          "StructureDefinition.type", "ElementDefinition.fixed[x]", "ElementDefinition.pattern[x]", "ImplementationGuide.dependsOn.uri", "StructureDefinition.mapping.uri",
          "MessageHeader.source.endpoint", "MessageHeader.source.endpoint[x]", "MessageHeader.destination.endpoint", "MessageHeader.destination.endpoint[x]",
          "QuestionnaireResponse.item.definition");
      
    } else {
      return Utilities.existsInList(context.getBase().getPath(),
          "Extension.url", // extension urls are validated elsewhere
         "ImplementationGuide.definition.page.source[x]", "ImplementationGuide.definition.page.name", "ImplementationGuide.definition.page.name[x]",
         "Requirements.statement.satisfiedBy", "Bundle.entry.request.url", 
         "StructureDefinition.type", "ElementDefinition.fixed[x]", "ElementDefinition.pattern[x]"
         );
    }
  }

  private boolean isKnownSpace(String url) {
    return url.startsWith("http://hl7.org/fhir") || url.startsWith("http://terminology.hl7.org/") || url.startsWith("http://fhir.org/guides");
  }

  private Set<String> listExpectedCanonicalTypes(ElementDefinition context) {
    Set<String> res = new HashSet<>();
    TypeRefComponent tr = context.getType("canonical");
    if (tr != null) {
      for (CanonicalType p : tr.getTargetProfile()) {
        String url = p.getValue();
        StructureDefinition sd = this.context.fetchResource(StructureDefinition.class, url);
        if (sd != null) {
          res.add(sd.getType());
        } else {
          if (url != null && url.startsWith("http://hl7.org/fhir/StructureDefinition/")) {
            res.add(url.substring("http://hl7.org/fhir/StructureDefinition/".length()));
          }
        }
      }
    }
    return res;
  }

  private boolean isCorrectCanonicalType(Resource r, ElementDefinition context) {
    TypeRefComponent tr = context.getType("canonical");
    if (tr != null) {
      for (CanonicalType p : tr.getTargetProfile()) {
        if (isCorrectCanonicalType(r, p)) {
          return true;
        }
      }
      if (tr.getTargetProfile().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private boolean isCorrectCanonicalType(Resource r, CanonicalType p) {
    String url = p.getValue();
    String t = null;
    StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
    if (sd != null) {
      t = sd.getType();
    } else if (url.startsWith("http://hl7.org/fhir/StructureDefinition/")) {
      t = url.substring("http://hl7.org/fhir/StructureDefinition/".length());
    } else {
      return false;
    }
    return Utilities.existsInList(t, "Resource", "CanonicalResource") || t.equals(r.fhirType());
  }

  private boolean isCanonicalURLElement(Element e, NodeStack parent) {
    if (parent != null && parent.getElement().getName().equals("extension")) {
      String url = parent.getElement().getChildValue("url");
      if (xverManager.status(url) == XVerExtensionStatus.Valid && url.contains("-")) {
        String path = url.substring(url.lastIndexOf("-")+1);
        if (path.contains(".")) {
          String type = path.substring(0, path.indexOf('.'));
          String tail = path.substring(path.indexOf('.')+1);
          if ("url".equals(tail) && VersionUtilities.getCanonicalResourceNames(context.getVersion()).contains(type)) {
            return true;
          }
        }
      }
    }
    if (e.getProperty() == null || e.getProperty().getDefinition() == null) {
      return false;
    }
    String path = e.getProperty().getDefinition().getBase().getPath();
    if (path == null) {
      return false;
    }
    String[] p = path.split("\\."); 
    if (p.length != 2) {
      return false;
    }
    if (!"url".equals(p[1])) {
      return false;
    }
    return VersionUtilities.getCanonicalResourceNames(context.getVersion()).contains((p[0]));
  }

  private boolean containsHtmlTags(String cnt) {
    int i = cnt.indexOf("<");
    while (i > -1) {
      cnt = cnt.substring(i+1);
      i = cnt.indexOf("<");
      int e = cnt.indexOf(">");
      if (e > -1 && e < i) {
        String s = cnt.substring(0, e);
        if (s.matches(HTML_FRAGMENT_REGEX)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isDefinitionURL(String url) {
    return Utilities.existsInList(url,
        
        "http://hl7.org/fhirpath/System.Boolean", "http://hl7.org/fhirpath/System.String", "http://hl7.org/fhirpath/System.Integer", "http://hl7.org/fhirpath/System.Decimal", 
        "http://hl7.org/fhirpath/System.Date", "http://hl7.org/fhirpath/System.Time", "http://hl7.org/fhirpath/System.DateTime", "http://hl7.org/fhirpath/System.Quantity",

        "urn:ietf:bcp:13",

        "http://hl7.org/fhir/CompartmentDefinition/Patient", "http://hl7.org/fhir/CompartmentDefinition/Practitioner", "http://hl7.org/fhir/CompartmentDefinition/Group",
        "http://hl7.org/fhir/CompartmentDefinition/Device", "http://hl7.org/fhir/CompartmentDefinition/Patient", "http://hl7.org/fhir/CompartmentDefinition/Encounter", 
        "http://hl7.org/fhir/SearchParameter/Resource-filter");
  }

  private boolean checkReferences(ValidationContext valContext, List<ValidationMessage> errors, Element e, String path, String xpath, XhtmlNode node, Element resource) {
    boolean ok = true;
    if (node.getNodeType() == NodeType.Element & "a".equals(node.getName()) && node.getAttribute("href") != null) {
      String href = node.getAttribute("href");
      if (rule(errors, "2024-07-20", IssueType.INVALID, e.line(), e.col(), path, !Utilities.noString(href), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_EMPTY_HREF, xpath, Utilities.stripEoln(node.allText()))) {
        if (href.startsWith("#") && !href.equals("#")) {
          String ref = href.substring(1);
          valContext.getInternalRefs().add(ref);
          List<String> refs = new ArrayList<>();
          int count = countTargetMatches(resource, ref, true, "$", refs);
          if (count == 0) {
            rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_RESOLVE, href, xpath, Utilities.stripEoln(node.allText()).trim());
          } else if (count > 1) {
            warning(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_MULTIPLE_MATCHES, href, xpath, node.allText(), CommaSeparatedStringBuilder.join(", ", refs));
          }
        } else if (href.contains(":") && Utilities.isAbsoluteUrl(href)) {
          String scheme = href.substring(0, href.indexOf(":"));
          if (rule(errors, "2024-07-20", IssueType.INVALID, e.line(), e.col(), path, !isActiveScheme(scheme), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_ACTIVE_HREF, href, xpath, Utilities.stripEoln(node.allText()).trim(), scheme)) {
            if (rule(errors, "2024-07-20", IssueType.INVALID, e.line(), e.col(), path, isLiteralScheme(scheme), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_LITERAL_HREF, href, xpath, Utilities.stripEoln(node.allText()).trim(), scheme)) {
              hint(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, isKnownScheme(scheme), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_UNKNOWN_HREF, href, xpath, node.allText().trim(), scheme);
            } else {
              ok = false;
            }
          } else {
            ok = false;
          }
        } else {
          // we can't validate at this point. Come back and revisit this some time in the future
        }
      }
    }
    if (node.hasChildren()) {
      for (XhtmlNode child : node.getChildNodes()) {
        checkReferences(valContext, errors, e, path, xpath+"/"+child.getName(), child, resource);
      }        
    }
    return ok;
  }
  

  private boolean isActiveScheme(String scheme) {
    return Utilities.existsInList(scheme, "javascript", "vbscript");
  }

  private boolean isLiteralScheme(String scheme) {
    return !Utilities.existsInList(scheme, "urn", "cid");
  }

  private boolean isKnownScheme(String scheme) {
    return Utilities.existsInList(scheme, "http", "https", "tel", "mailto", "data");
  }

  protected int countTargetMatches(Element element, String fragment, boolean checkBundle, String path, List<String> refs) {
    int count = 0;
    if (fragment.equals(element.getIdBase())) {
      count++;
      refs.add(path+"/id");
    }
    if (element.getXhtml() != null) {
      count = count + countTargetMatches(element.getXhtml(), fragment, path, refs);
    }
    if (element.hasChildren()) {
      for (Element child : element.getChildren()) {
        count = count + countTargetMatches(child, fragment, false, path+"/"+child.getName(), refs);
      }
    }
    if (count == 0 && checkBundle) {
      Element e = element.getParentForValidator();
      while (e != null) {
        if (e.fhirType().equals("Bundle")) {
          count = count + countTargetMatches(e, fragment, false, path+"/..", refs);
        }
        e = e.getParentForValidator();
      }
    }
    return count;
  }

  private int countTargetMatches(XhtmlNode node, String fragment, String path, List<String> refs) {
    int count = 0;
    if (fragment.equals(node.getAttribute("id"))) {
      count++;
      refs.add(path+"/@id");
    }
    if ("a".equals(node.getName()) && fragment.equals(node.getAttribute("name"))) {
      count++;
      refs.add(path+"/@name");
    }
    if (node.hasChildren()) {
      for (int i = 0; i < node.getChildNodes().size(); i++) {
        XhtmlNode child = node.getChildNodes().get(i);
        String cn = child.getPathName();
        int total = node.countByPathName(child);
        int index = node.indexByPathName(child);
        count = count + countTargetMatches(child, fragment, path+"/"+cn+(total > 1 ? "["+index+"]" : ""), refs);
      }
    }
    return count;
  }


  private boolean checkImageSources(ValidationContext valContext, List<ValidationMessage> errors, Element e, String path, String xpath, XhtmlNode node, Element resource) {
    boolean ok = true;
    if (node.getNodeType() == NodeType.Element & "img".equals(node.getName()) && node.getAttribute("src") != null) {
      String src = node.getAttribute("src");
      if (src.startsWith("#")) {
        String ref = src.substring(1);
        valContext.getInternalRefs().add(ref);
        int count = countFragmentMatches(resource, ref);
        if (count == 0) {
          rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_RESOLVE_IMG, src, xpath);
        } else if (count > 1) {
          rule(errors, NO_RULE_DATE, IssueType.INVALID, e.line(), e.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_XHTML_MULTIPLE_MATCHES, src, xpath);
        }
      } else {
        // we can't validate at this point. Come back and revisit this some time in the future
      }
    }
    if (node.hasChildren()) {
      for (XhtmlNode child : node.getChildNodes()) {
        checkImageSources(valContext, errors, e, path, path+"/"+child.getName(), child, resource);
      }        
    }
    return ok;
  }


  private boolean checkPrimitiveBinding(ValidationContext valContext, List<ValidationMessage> errors, String path, String type, ElementDefinition elementContext, Element element, StructureDefinition profile, NodeStack stack) {
    // We ignore bindings that aren't on string, uri or code
    if (!element.hasPrimitiveValue() || !("code".equals(type) || "string".equals(type) || "uri".equals(type) || "url".equals(type) || "canonical".equals(type))) {
      return true;
    }
    if (noTerminologyChecks)
      return true;

    boolean ok = true;
    String value = element.primitiveValue();

    // firstly, resolve the value set
    ElementDefinitionBindingComponent binding = elementContext.getBinding();
    
    if (binding.hasValueSet()) {
      ValueSet vs = resolveBindingReference(profile, binding.getValueSet(), profile.getUrl(), profile);
      if (vs == null) { 
        CodeSystem cs = context.fetchCodeSystem(binding.getValueSet());
        if (rule(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, cs == null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND_CS, describeReference(binding.getValueSet()))) {
          warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, vs != null, I18nConstants.TERMINOLOGY_TX_VALUESET_NOTFOUND, describeReference(binding.getValueSet()));
        } else {
          ok = false;
        }
      } else {
        EnumSet<CodedContentValidationAction> validationPolicy = policyAdvisor.policyForCodedContent(this, valContext, stack.getLiteralPath(), elementContext, profile, BindingKind.PRIMARY, null, vs, new ArrayList<>());

        if (!validationPolicy.isEmpty()) {
          long t = System.nanoTime();
          ValidationResult vr = null;
          if (binding.getStrength() != BindingStrength.EXAMPLE) {
            ValidationOptions options = settings.withGuessSystem();
            if (!validationPolicy.contains(CodedContentValidationAction.InvalidCode) && !validationPolicy.contains(CodedContentValidationAction.InvalidDisplay)) {
              options = options.withCheckValueSetOnly();              
            }
            vr = checkCodeOnServer(stack, vs, value, options);
          }
          ok = calculateSeverityForTxIssuesAndUpdateErrors(errors, vr, element, path, binding.getStrength() != BindingStrength.REQUIRED, binding.getValueSet(), binding.getStrength()) && ok;

          timeTracker.tx(t, "vc "+value+"");
          if (binding.getStrength() == BindingStrength.REQUIRED) {
            removeTrackedMessagesForLocation(errors, element, path);
          }
          if (vr != null && !vr.isOk()) {
            if (vr.IsNoService()) {
              txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_15, value);
            } else if (vr.getErrorClass() != null && vr.getErrorClass() == TerminologyServiceErrorClass.CODESYSTEM_UNSUPPORTED) {
              txWarning(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, vr.getMessage());
            } else if (binding.getStrength() == BindingStrength.REQUIRED) {
              ok = txRule(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_16, value, describeReference(binding.getValueSet(), vs, BindingContext.BASE, null), getErrorMessage(vr.getMessage())) && ok;
            } else if (binding.getStrength() == BindingStrength.EXTENSIBLE) {
              if (binding.hasExtension(ExtensionDefinitions.EXT_MAX_VALUESET))
                ok = checkMaxValueSet(errors, path, element, profile, ExtensionUtilities.readStringExtension(binding, ExtensionDefinitions.EXT_MAX_VALUESET), value, stack) && ok;
              else if (!noExtensibleWarnings && !isOkExtension(value, vs))
                txWarningForLaterRemoval(element, errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_17, value, describeReference(binding.getValueSet(), vs, BindingContext.BASE, null), getErrorMessage(vr.getMessage()));
            } else if (binding.getStrength() == BindingStrength.PREFERRED) {
              if (baseOnly) {
                txHint(errors, NO_RULE_DATE, vr.getTxLink(), IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_NOVALID_18, value, describeReference(binding.getValueSet(), vs, BindingContext.BASE, null), getErrorMessage(vr.getMessage()));
              }
            }
          } else if (vr != null && vr.getMessage() != null){
            if (vr.getSeverity() == IssueSeverity.INFORMATION) {
              txHint(errors, "2023-07-04", vr.getTxLink(), IssueType.INFORMATIONAL, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_HINT, value, vr.getMessage());
            } else {
              txWarning(errors, "2023-07-04", vr.getTxLink(), IssueType.INFORMATIONAL, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_WARNING, value, vr.getMessage());
            }
          }
        }
      }
    } else if (!noBindingMsgSuppressed) {
      hint(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, !type.equals("code"), I18nConstants.TERMINOLOGY_TX_BINDING_NOSOURCE2);
    }
    return ok;
  }

  private boolean isOkExtension(String value, ValueSet vs) {
    if ("http://hl7.org/fhir/ValueSet/defined-types".equals(vs.getUrl())) {
      return value.startsWith("http://hl7.org/fhirpath/System.");
    }
    return false;
  }

  private boolean checkQuantity(List<ValidationMessage> errors, String path, Element focus, Quantity fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".value", focus.getNamedChild("value", false), fixed.getValueElement(), fixedSource, "value", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".comparator", focus.getNamedChild("comparator", false), fixed.getComparatorElement(), fixedSource, "comparator", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".unit", focus.getNamedChild("unit", false), fixed.getUnitElement(), fixedSource, "unit", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".system", focus.getNamedChild("system", false), fixed.getSystemElement(), fixedSource, "system", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".code", focus.getNamedChild("code", false), fixed.getCodeElement(), fixedSource, "code", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkQuantity(List<ValidationMessage> errors, String path, Element element, StructureDefinition theProfile, ElementDefinition definition, NodeStack theStack) {
    boolean ok = true;
    String value = element.hasChild("value", false) ? element.getNamedChild("value", false).getValue() : null;
    String unit = element.hasChild("unit", false) ? element.getNamedChild("unit", false).getValue() : null;
    String system = element.hasChild("system", false) ? element.getNamedChild("system", false).getValue() : null;
    String code = element.hasChild("code", false) ? element.getNamedChild("code", false).getValue() : null;

    // todo: allowedUnits http://hl7.org/fhir/StructureDefinition/elementdefinition-allowedUnits - codeableConcept, or canonical(ValueSet)
    // todo: http://hl7.org/fhir/StructureDefinition/iso21090-PQ-translation

    if (!Utilities.noString(value) && definition.hasExtension(ExtensionDefinitions.EXT_MAX_DECIMALS)) {
      int dp = value.contains(".") ? value.substring(value.indexOf(".")+1).length() : 0;
      int def = Integer.parseInt(ExtensionUtilities.readStringExtension(definition, ExtensionDefinitions.EXT_MAX_DECIMALS));
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, dp <= def, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_DECIMAL_CHARS, dp, def) && ok;
    }

    if (system != null || code != null ) {
      ok = checkCodedElement(errors, path, element, theProfile, definition, false, false, theStack, code, system, null, unit) && ok;
    }

    if (code != null && "http://unitsofmeasure.org".equals(system)) {
      int b = code.indexOf("{");
      int e = code.indexOf("}");
      if (b >= 0 && e > 0 && b < e) {
        String annotation = code.substring(b, e+1);
        String annotationValue = code.substring(b+1, e);
        if (unit == null) {
          ok = bpCheck(errors, IssueType.BUSINESSRULE, element.line(), element.col(), path, !code.contains("{"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_UCUM_ANNOTATIONS_NO_UNIT, annotation) && ok;          
        } else if (!unit.toLowerCase().contains(annotationValue.toLowerCase())) {
          ok = bpCheck(errors, IssueType.BUSINESSRULE, element.line(), element.col(), path, !code.contains("{"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_UCUM_ANNOTATIONS_NOT_IN_UNIT, annotation, unit) && ok;          
        } else {        
          ok = bpCheck(errors, IssueType.BUSINESSRULE, element.line(), element.col(), path, !code.contains("{"), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_UCUM_ANNOTATIONS, annotation, unit) && ok;
        }
      }
    }

    if (definition.hasMinValue()) {
      if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(value), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_VALUE_NO_VALUE)) {
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, definition.getMinValue() instanceof Quantity, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_NO_QTY, definition.getMinValue().fhirType())) {
          Quantity min = definition.getMinValueQuantity();
          if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(min.getSystem()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_MIN_NO_SYSTEM) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(system), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_VALUE_NO_SYSTEM) && 
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, system.equals(min.getSystem()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_SYSTEM_MISMATCH, system, min.getSystem()) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(min.getCode()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_MIN_NO_CODE) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(code), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_VALUE_NO_CODE)) {
            if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, definition.getMinValueQuantity().hasValue(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_MIN_NO_VALUE)) {
              if (code.equals(min.getCode())) {
                // straight value comparison
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, checkDecimalMinValue(value, min.getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_VALUE_WRONG, value, min.getValue().toString()) && ok;
              } else if ("http://unitsofmeasure.org".equals(system)) {
                if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, context.getUcumService() != null, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_NO_UCUM_SVC)) {
                  Decimal v = convertUcumValue(value, code, min.getCode());
                  if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, v != null, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_MIN_NO_CONVERT, value, code, min.getCode())) {
                    ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, checkDecimalMinValue(v, min.getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_VALUE_WRONG_UCUM, value, code, min.getValue().toString(), min.getCode()) && ok;
                  } else {
                    ok = false;
                  }
                }
              } else {
                warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MIN_CODE_MISMATCH, code, min.getCode());
              }
            } else {
              ok = false;
            }
          }
        } else {
          ok = false;
        }
      }
    }
    
    if (definition.hasMaxValue()) {
      if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(value), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_VALUE_NO_VALUE)) {
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, definition.getMaxValue() instanceof Quantity, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_NO_QTY, definition.getMaxValue().fhirType())) {
          Quantity max = definition.getMaxValueQuantity();
          if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(max.getSystem()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_MIN_NO_SYSTEM) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(system), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_VALUE_NO_SYSTEM) && 
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, system.equals(max.getSystem()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_SYSTEM_MISMATCH, system, max.getSystem()) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(max.getCode()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_MIN_NO_CODE) &&
              warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, !Utilities.noString(code), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_VALUE_NO_CODE)) {
            if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, definition.getMaxValueQuantity().hasValue(), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_MIN_NO_VALUE)) {
              if (code.equals(max.getCode())) {
                // straight value comparison
                ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, checkDecimalMaxValue(value, max.getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_VALUE_WRONG, value, max.getValue().toString()) && ok;
              } else if ("http://unitsofmeasure.org".equals(system)) {
                if (warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, context.getUcumService() != null, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_NO_UCUM_SVC)) {
                  Decimal v = convertUcumValue(value, code, max.getCode());
                  if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, v != null, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_MIN_NO_CONVERT, value, code, max.getCode())) {
                    ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, checkDecimalMaxValue(v, max.getValue()), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_VALUE_WRONG_UCUM, value, code, max.getValue().toString(), max.getCode()) && ok;
                  } else {
                    ok = false;
                  }
                }
              } else {
                warning(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), path, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_QTY_MAX_CODE_MISMATCH, code, max.getCode());
              }
            } else {
              ok = false;
            }
          }
        } else {
          ok = false;
        }
      }
    }
    return ok;
  }
  
  private Decimal convertUcumValue(String value, String code, String minCode) {
    try {
      Decimal v = new Decimal(value);
      return context.getUcumService().convert(v, code, minCode);
    } catch (Exception e) {
      return null;
    }
  }

  private boolean checkDecimalMaxValue(Decimal value, BigDecimal min) {
    try {
      Decimal m = new Decimal(min.toString());
      return value.comparesTo(m) <= 0;
    } catch (Exception e) {
      return false; // this will be another error somewhere else?
    }
  }

  private boolean checkDecimalMaxValue(String value, BigDecimal min) {
    try {
      BigDecimal v = new BigDecimal(value);
      return v.compareTo(min) <= 0;      
    } catch (Exception e) {
      return false; // this will be another error somewhere else
    }
  }

  private boolean checkDecimalMinValue(Decimal value, BigDecimal min) {
    try {
      Decimal m = new Decimal(min.toString());
      return value.comparesTo(m) >= 0;
    } catch (Exception e) {
      return false; // this will be another error somewhere else?
    }
  }

  private boolean checkDecimalMinValue(String value, BigDecimal min) {
    try {
      BigDecimal v = new BigDecimal(value);
      return v.compareTo(min) >= 0;      
    } catch (Exception e) {
      return false; // this will be another error somewhere else
    }
  }

  private boolean checkAttachment(List<ValidationMessage> errors, String path, Element element, StructureDefinition theProfile, ElementDefinition definition, boolean theInCodeableConcept, boolean theCheckDisplayInContext, NodeStack theStack) {
    boolean ok = true;
    long size = -1;
    long max = -1;
    byte[] cnt = null;
    byte[] hash = null;
    String fetchError = null;
    
    if (element.hasChild("size", false)) {
      String sz = element.getChildValue("size");
      if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, Utilities.isLong(sz), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_SIZE_INVALID, sz)) {
        size = Long.parseLong(sz);
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, size >= 0, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_SIZE_INVALID, sz) && ok;
      } else {
        ok = false;
      }
    }
    if (element.hasChild("hash")) {
      String h64 = element.getChildValue("hash");
      hash = readBase64Data(errors, theStack, "hash", h64);
      if (hash == null) {
        ok = false;
      }
    }
    if (definition.hasExtension(ExtensionDefinitions.EXT_MAX_SIZE)) {
      max = Long.parseLong(ExtensionUtilities.readStringExtension(definition, ExtensionDefinitions.EXT_MAX_SIZE));
    }
    
    if (element.hasChild("data", false)) {
      String b64 = element.getChildValue("data");
      // Note: If the value isn't valid, we're not adding an error here, as the test to the
      // child Base64Binary will catch it and we don't want to log it twice
      boolean bok = Base64Util.isValidBase64(b64);
      if (bok) {
        cnt = readBase64Data(errors, theStack, "data", b64);
        if (cnt == null) {
          ok = false;
        }
      }
    } else if (element.hasChild("url", false)) {
      String url = element.getChildValue("url"); 
      if (size > -1 || max > -1 || hash != null) {
        try {
          if (url.startsWith("http://") || url.startsWith("https://")) {
            if (fetcher == null) {
              fetchError = context.formatMessage(I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_NO_FETCHER, url);  
            } else {
              cnt = fetcher.fetchRaw(this, url);
            }
          } else if (url.startsWith("file:")) {
            cnt = FileUtilities.streamToBytes(new FileInputStream(ManagedFileAccess.file(url.substring(5))));
          } else {
            fetchError = context.formatMessage(I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_UNKNOWN_URL_SCHEME, url);          }
        } catch (Exception e) {
          if (STACK_TRACE) e.printStackTrace();
          fetchError = context.formatMessage(I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_URL_ERROR, url, e.getMessage());
        }
      }
    }
    warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, (element.hasChild("data", false) || element.hasChild("url", false)) || (element.hasChild("contentType", false) || element.hasChild("language", false)), 
        I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_NO_CONTENT);
    
    if (max > -1 && cnt != null) {
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, cnt.length <= max, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_TOO_LONG, cnt.length, max) && ok;
    }

    if (size > -1 && cnt != null) {
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, cnt.length == size, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_SIZE_CORRECT, size, cnt.length) && ok;
    }

    if (hash != null && cnt != null) {
      try {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] c = sha1.digest(cnt); 
        ok = rule(errors, "2025-06-25", IssueType.STRUCTURE, theStack, Arrays.equals(c, hash), I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_HASH_MISMATCH) && ok;
      } catch (NoSuchAlgorithmException e) {
      }
    } else {
      ok = true;
    }

    return ok;
  }

  private byte[] readBase64Data(List<ValidationMessage> errors, NodeStack theStack, String name, String b64) {
    try {
      return Base64.getDecoder().decode(b64);
    } catch (Exception e) {
      rule(errors, "2025-06-25", IssueType.STRUCTURE, theStack, false, I18nConstants.TYPE_SPECIFIC_CHECKS_DT_ATT_B64_DECODE_FAIL, name, e.getMessage());
    }
    return null;
  }

  // implementation

  private boolean checkRange(List<ValidationMessage> errors, String path, Element focus, Range fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".low", focus.getNamedChild("low", false), fixed.getLow(), fixedSource, "low", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".high", focus.getNamedChild("high", false), fixed.getHigh(), fixedSource, "high", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkRatio(List<ValidationMessage> errors, String path, Element focus, Ratio fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".numerator", focus.getNamedChild("numerator", false), fixed.getNumerator(), fixedSource, "numerator", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".denominator", focus.getNamedChild("denominator", false), fixed.getDenominator(), fixedSource, "denominator", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkNarrative(ValidationContext valContext, List<ValidationMessage> errors, String path,Element element, Element resource, StructureDefinition profile,
      ElementDefinition definition, String parentType, NodeStack stack, ResourcePercentageLogger pct, ValidationMode vmode) throws FHIRException {
    boolean ok = true;
    XhtmlNode div = element.hasChild("div") ? element.getNamedChild("div").getXhtml() : null;
    if (definition.hasExtension(ExtensionDefinitions.EXT_NARRATIVE_SOURCE_CONTROL)) {
      String level = ExtensionUtilities.readStringExtension(definition, ExtensionDefinitions.EXT_NARRATIVE_SOURCE_CONTROL);
      if (div != null) {
        for (XhtmlNode x : div.getChildNodes()) {
          if (x.hasContent()) {
            if (!hasClass(x.getAttribute("class"), "generated", "boilerplate", "original")) {
              switch (level) {
              case "hint" :
              case "information" :
                hint(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                    false, I18nConstants.XHTML_CONTROL_NO_SOURCE, Utilities.limitString(x.allText(), 30), profile);
              case "warning" :
                warning(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                    false, I18nConstants.XHTML_CONTROL_NO_SOURCE, Utilities.limitString(x.allText(), 30), profile);
              case "error" :
                ok = rule(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                    false, I18nConstants.XHTML_CONTROL_NO_SOURCE, Utilities.limitString(x.allText(), 30), profile) && ok;
              }
            }
          }
        }
      }
    }
    if (definition.hasExtension(ExtensionDefinitions.EXT_NARRATIVE_LANGUAGE_CONTROL)) {
      Set<String> langs = new HashSet<>();
      for (XhtmlNode node : div.getChildNodes()) {
        if (node.getNodeType() == NodeType.Element && "div".equals(node.getName()) && XhtmlValidator.isLangDiv(node)) {
          langs.add(node.getAttribute("xml:lang"));
        }
      }
      for (Extension ext : definition.getExtensionsByUrl(ExtensionDefinitions.EXT_NARRATIVE_LANGUAGE_CONTROL)) {
        if (ext.hasValue() && ext.getValue().primitiveValue() != null) {
          String code = ext.getValue().primitiveValue();
          switch (code) {
          case "_no" :
            ok = rule(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                langs.isEmpty(), I18nConstants.XHTML_CONTROL_NO_LANGS, CommaSeparatedStringBuilder.join(", ", langs), profile) && ok;
            break;
          case "_yes" :
            ok = rule(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                !langs.isEmpty(), I18nConstants.XHTML_CONTROL_LANGS_REQUIRED, CommaSeparatedStringBuilder.join(", ", langs), profile) && ok;
            break;
          case "_resource" :
            String rl = resource.getNamedChildValue("language");
            if (langs.isEmpty()) {
              warning(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                  !langs.isEmpty(), rl == null ? I18nConstants.XHTML_CONTROL_LANGS_NONE_NO_DEFAULT : I18nConstants.XHTML_CONTROL_LANGS_NONE, rl, profile);
            } else if (rl == null) {
              warning(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                  !langs.isEmpty(), I18nConstants.XHTML_CONTROL_LANGS_NO_DEFAULT, CommaSeparatedStringBuilder.join(", ", langs), profile);

            } else {
              ok = rule(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                langs.contains(rl), I18nConstants.XHTML_CONTROL_LANGS_REQUIRED_DEF, rl, CommaSeparatedStringBuilder.join(", ", langs), profile) && ok;
            }
            break;
         default :
            if (langs.isEmpty()) {
              warning(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                  !langs.isEmpty(), I18nConstants.XHTML_CONTROL_LANGS_NONE2, code, profile);
            } else {
              ok = rule(errors, "2025-06-07", IssueType.BUSINESSRULE, element.line(), element.col(), path,
                langs.contains(code), I18nConstants.XHTML_CONTROL_LANGS_REQUIRED_LANG, code, CommaSeparatedStringBuilder.join(", ", langs), profile) && ok;
            }
          }
        }
      }
    }
    if (element.hasExtension(ExtensionDefinitions.EXT_TEXT_LINK)) {
      if (div == null) {
        ok = rule(errors, "2025-05-17", IssueType.STRUCTURE, element.line(), element.col(), path,
            !Utilities.noString(element.getNamedChildValue("display", false)), I18nConstants.TEXT_LINK_NO_DIV) && ok;      
      } else for (Element ex : element.getExtensions(ExtensionDefinitions.EXT_TEXT_LINK)) {
        NodeStack estack = stack.push(ex, ex.getIndex(), definition, definition);
        for (Element htmlid : ex.getExtensions("htmlid")) {
          String id = htmlid.getNamedChildValue("value");
          if (!divHasId(div, id)) {
            ok = rule(errors, "2025-05-17", IssueType.STRUCTURE, element.line(), element.col(), path,
                !Utilities.noString(element.getNamedChildValue("display", false)), I18nConstants.TEXT_LINK_NO_ID, id) && ok;
          }
        }
        String data = ex.getExtensionString("data");
        String type = null;
        if (data == null) {
          ok = rule(errors, "2025-05-17", IssueType.STRUCTURE, element.line(), element.col(), path,
              !Utilities.noString(element.getNamedChildValue("display", false)), I18nConstants.TEXT_LINK_NO_DATA) && ok;            
        } else if (data.startsWith("#")) {
          String idref = data.substring(1);
          List<Element> matches = getFragmentMatches(resource, idref, stack);

          if (matches.size() == 0) {
            ok = rule(errors, "2025-05-17", IssueType.NOTFOUND, stack, false, I18nConstants.TEXT_LINK_DATA_NOT_FOUND, data) && ok;                
          } else {
            if (matches.size() > 1) {
              ok = rule(errors, "2025-05-17", IssueType.INVALID, stack, false, I18nConstants.TEXT_LINK_DATA_MULTIPLE_MATCHES, data) && ok;
            }
            type = matches.get(0).fhirType();
          } 
        } else {
          String fragment = null;
          String url = null;
          if (data.contains("#")) {
            url = data.substring(0, data.indexOf("#"));
            fragment = data.substring(data.indexOf("#")+1);
          } else {
            url = data;
          }
          List<Element> matches = getUrlMatches(resource, url, stack);
          if (matches.size() == 0) {
            ok = rule(errors, "2025-05-17", IssueType.NOTFOUND, stack, false, I18nConstants.TEXT_LINK_DATA_NOT_FOUND, data) && ok;                
          } else {
            if (matches.size() > 1) {
              ok = rule(errors, "2025-05-17", IssueType.INVALID, stack, false, I18nConstants.TEXT_LINK_DATA_MULTIPLE_MATCHES, data) && ok;
            }
            type = matches.get(0).fhirType();
            if (fragment != null) {
              matches = getFragmentMatches(matches.get(0), fragment, stack);
              if (matches.size() == 0) {
                ok = rule(errors, "2025-05-17", IssueType.NOTFOUND, stack, false, I18nConstants.TEXT_LINK_DATA_NOT_FOUND, data) && ok;                
              } else {
                if (matches.size() > 1) {
                  ok = rule(errors, "2025-05-17", IssueType.INVALID, stack, false, I18nConstants.TEXT_LINK_DATA_MULTIPLE_MATCHES, data) && ok;
                }
                type = matches.get(0).fhirType();
              } 
            }
          } 
        }
        if (ex.hasExtension("selector")) {
          String expression = ex.getExtensionString("selector");
          try {
            ExpressionNode expr = fpe.parse(expression);
            if (type != null) {
              fpe.check(null, resource.fhirType(), resource.fhirType(), type, expr);
            }
          } catch (Exception e) {
            ok = rule(errors, "2025-05-17", IssueType.INVALID, stack, false, I18nConstants.TEXT_LINK_SELECTOR_INVALID, expression, e.getMessage()) && ok;            
          }
        }
      }
    }
      /*

    Complex Extension: Used to denote which portions of the narrative are linked to (usually, generated from) structured data in resources. This information might be used in several different ways, including translating and regenerating narrative in applications that are using/presenting the narrative.

    html: string: The id attribute on an element in the xhtml narrative
    data: uri: The id attribute on a resource element (#{id}, relative#{id} or https://absolute#{id})
    selector: string: FHIRPath that selects a subset of the identified data. This sub-extension exists because in some circumstances, the specific data items are in resources where the constructor of the narrative can't introduce specific ids on the relevent elements
    */
    return ok;
  }

  private boolean hasClass(String clss, String string, String string2, String string3) {
    if (clss == null) {
      return false;
    }
    String[] s = clss.split("\\ ");
    return Utilities.existsInList(clss, s);
  }
  private boolean divHasId(XhtmlNode node, String id) {
    if (node.hasAttribute("id") && id.equals(node.getAttribute("id"))) {
      return true;
    }
    for (XhtmlNode c : node.getChildNodes()) {
      if (divHasId(c, id)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkReference(ValidationContext valContext,
                                 List<ValidationMessage> errors,
                                 String path,
                                 Element element,
                                 StructureDefinition profile,
                                 ElementDefinition container,
                                 String parentType,
                              NodeStack stack, ResourcePercentageLogger pct, ValidationMode vmode) throws FHIRException {
    boolean ok = true;
    Reference reference = ObjectConverter.readAsReference(element);

    String ref = reference.getReference();
    if (Utilities.noString(ref)) {
      if (!path.contains("element.pattern")) { // this business rule doesn't apply to patterns
        if (Utilities.noString(reference.getIdentifier().getSystem()) && Utilities.noString(reference.getIdentifier().getValue())) {
          warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path,
            !Utilities.noString(element.getNamedChildValue("display", false)), I18nConstants.REFERENCE_REF_NODISPLAY);
        }
      }
      return true;
    } else if (Utilities.existsInList(ref, "http://tools.ietf.org/html/bcp47")) {
      // special known URLs that can't be validated but are known to be valid
      return true;
    } else {
      ok = rule(errors, "2025-05-07", IssueType.INVALID, element.line(), element.col(), path, ref.matches("\\S+"), I18nConstants.REFERENCE_REF_INVALID_REF, ref) && ok;
    }

    if (ref.matches("\\S+")) {
      warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, !isSuspiciousReference(ref), I18nConstants.REFERENCE_REF_SUSPICIOUS, ref);
    }

    BooleanHolder bh = new BooleanHolder();
    BooleanHolder stop = new BooleanHolder(false);
    ResolvedReference we = localResolve(ref, stack, errors, path, valContext.getRootResource(), valContext.getGroupingResource(), element, bh, stop);
    ok = bh.ok() && ok;
    ReferenceDestinationType refType;
    if (ref.startsWith("#")) {
      valContext.getInternalRefs().add(ref.substring(1));
      refType = ReferenceDestinationType.CONTAINED;
    } else {
      if (we == null) {
        refType = ReferenceDestinationType.EXTERNAL;
      } else {
        refType = ReferenceDestinationType.INTERNAL;
      }
    }
    boolean conditional = ref.contains("?") && context.getResourceNamesAsSet().contains(ref.substring(0, ref.indexOf("?")));
    ReferenceValidationPolicy pol;
    if (refType == ReferenceDestinationType.CONTAINED || refType == ReferenceDestinationType.INTERNAL) {
      pol = ReferenceValidationPolicy.CHECK_VALID;
    } else {
      pol = policyAdvisor.policyForReference(this, valContext.getAppContext(), path, ref, refType);
    }

    if (conditional) {
      boolean test = isSearchUrl(context, ref);
          //("^\\?([\\w-]+(=[\\w-]*)?(&[\\w-]+(=[\\w-]*)?)*)?$"),
      ok = rule(errors, "2023-02-20", IssueType.INVALID, element.line(), element.col(), path, test, I18nConstants.REFERENCE_REF_QUERY_INVALID, ref) && ok;
    } else if (stop.ok()) {
      hint(errors, "2025-04-08", IssueType.INFORMATIONAL, element.line(), element.col(), path, false, I18nConstants.REFERENCE_REF_REL_UNSOLVEABLE, ref);      
    } else if (pol.checkExists()) {
      if (we == null) {
        if (refType != ReferenceDestinationType.CONTAINED) {
          if (fetcher == null) {
            throw new FHIRException(context.formatMessage(I18nConstants.RESOURCE_RESOLUTION_SERVICES_NOT_PROVIDED));
          } else {
            Element ext = null;
            if (fetchCache.containsKey(ref)) {
              ext = fetchCache.get(ref);
            } else {
              try {
                ext = fetcher.fetch(this, valContext.getAppContext(), ref);
              } catch (Exception e) {
                if (STACK_TRACE) e.printStackTrace();
                ext = null;

                // it's probably an error, but here we're just giving the user information about why resolution failed
                hint(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), path,
                  false, I18nConstants.REFERENCE_RESOLUTION_FAILED, ref, e.getClass().getName(), e.getMessage());

              }
              if (ext != null) {
                setParents(ext);
                fetchCache.put(ref, ext);
              }
            }
            we = ext == null ? null : makeExternalRef(ext, path);
          }
        }
      }
      boolean rok = (settings.isAllowExamples() && (ref.contains("example.org") || ref.contains("acme.com")))
        || (we != null || pol == ReferenceValidationPolicy.CHECK_TYPE_IF_EXISTS);
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, rok, I18nConstants.REFERENCE_REF_CANTRESOLVE, ref) && ok;
    }

    String ft;
    if (we != null) {
      ft = we.getType();
    } else {
      ft = tryParse(ref);
    }

    if (reference.hasType()) { // R4 onwards...
      // the type has to match the specified
      String tu = isAbsolute(reference.getType()) ? reference.getType() : "http://hl7.org/fhir/StructureDefinition/" + reference.getType();
      TypeRefComponent containerType = container.getType("Reference");
      if (!containerType.hasTargetProfile(tu)
        && !containerType.hasTargetProfile("http://hl7.org/fhir/StructureDefinition/Resource")
        && !containerType.getTargetProfile().isEmpty()
      ) {
        boolean matchingResource = false;
        for (CanonicalType target : containerType.getTargetProfile()) {
          StructureDefinition sd = resolveProfile(profile, target.asStringValue());
          if (rule(errors, NO_RULE_DATE, IssueType.NOTFOUND, element.line(), element.col(), path, sd != null,
              I18nConstants.REFERENCE_REF_CANTRESOLVEPROFILE, target.asStringValue())) {
            if (("http://hl7.org/fhir/StructureDefinition/" + sd.getType()).equals(tu)) {
              matchingResource = true;
              break;
            }
          } else {
            ok = false;
          }
        }
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, matchingResource,
          I18nConstants.REFERENCE_REF_WRONGTARGET, reference.getType(), container.getType("Reference").getTargetProfile()) && ok;

      }
      // the type has to match the actual
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path,
        ft == null || ft.equals(reference.getType()), I18nConstants.REFERENCE_REF_BADTARGETTYPE, reference.getType(), ft) && ok;
    }

    if (we != null && pol.checkType()) {
      if (warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, ft != null,
        I18nConstants.REFERENCE_REF_NOTYPE)) {
        // we validate as much as we can. First, can we infer a type from the profile?
        boolean rok = false;
        TypeRefComponent type = getReferenceTypeRef(container.getType());
        if (type.hasTargetProfile() && !type.hasTargetProfile("http://hl7.org/fhir/StructureDefinition/Resource")) {
          Set<String> types = new HashSet<>();
          List<StructureDefinition> profiles = new ArrayList<>();
          for (UriType u : type.getTargetProfile()) {
            StructureDefinition sd = resolveProfile(profile, u.getValue());
            if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, sd != null,
              I18nConstants.REFERENCE_REF_CANTRESOLVEPROFILE, u.getValue())) {
              types.add(sd.getType());
              if (ft.equals(sd.getType())) {
                rok = true;
                profiles.add(sd);
              }
            } else {
              ok = false;
            }
          }
          if (!pol.checkValid()) {
            ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, profiles.size() > 0,
              I18nConstants.REFERENCE_REF_CANTMATCHTYPE, ref, StringUtils.join("; ", sorted(type.getTargetProfile()))) && ok;
          } else {
            Map<StructureDefinition, List<ValidationMessage>> badProfiles = new HashMap<>();
            Map<StructureDefinition, List<ValidationMessage>> goodProfiles = new HashMap<>();
            int goodCount = 0;
            for (StructureDefinition pr : profiles) {
              List<ValidationMessage> profileErrors = new ArrayList<ValidationMessage>();
              validateResource(we.valContext(valContext, pr), profileErrors, we.getResource(), we.getFocus(), pr,
                IdStatus.OPTIONAL, we.getStack().resetIds(), pct, vmode.withReason(ValidationReason.MatchingSlice), true, false); 
              if (!hasErrors(profileErrors)) {
                goodCount++;
                goodProfiles.put(pr, profileErrors);
                trackUsage(pr, valContext, element);
              } else {
                badProfiles.put(pr, profileErrors);
              }
            }
            if (goodCount == 1) {
              if (showMessagesFromReferences) {
                for (ValidationMessage vm : goodProfiles.values().iterator().next()) {
                  if (!errors.contains(vm)) {
                    errors.add(vm);
                    ok = false;
                  }
                }
              }

            } else if (goodProfiles.size() == 0) {
              if (!isShowMessagesFromReferences()) {
                ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, areAllBaseProfiles(profiles),
                  I18nConstants.REFERENCE_REF_CANTMATCHCHOICE, ref, asList(type.getTargetProfile())) && ok;
                for (StructureDefinition sd : badProfiles.keySet()) {
                  slicingHint(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false, false, 
                    context.formatMessage(I18nConstants.DETAILS_FOR__MATCHING_AGAINST_PROFILE_, ref, sd.getVersionedUrl()), 
                    errorSummaryForSlicingAsHtml(badProfiles.get(sd)), badProfiles.get(sd));
                }
              } else {
                ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, profiles.size() == 1,
                  I18nConstants.REFERENCE_REF_CANTMATCHCHOICE, ref, asList(type.getTargetProfile())) && ok;
                for (List<ValidationMessage> messages : badProfiles.values()) {
                  for (ValidationMessage vm : messages) {
                    if (!errors.contains(vm)) {
                      errors.add(vm);
                      ok = false;
                    }
                  }
                }
              }
            } else {
              if (!isShowMessagesFromReferences()) {
                warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false,
                  I18nConstants.REFERENCE_REF_MULTIPLEMATCHES, ref, asListByUrl(goodProfiles.keySet()));
                for (StructureDefinition sd : badProfiles.keySet()) {
                  slicingHint(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false,
                    false,  context.formatMessage(I18nConstants.DETAILS_FOR__MATCHING_AGAINST_PROFILE_, ref, sd.getVersionedUrl()),
                      errorSummaryForSlicingAsHtml(badProfiles.get(sd)), badProfiles.get(sd));
                }
              } else {
                warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false,
                  I18nConstants.REFERENCE_REF_MULTIPLEMATCHES, ref, asListByUrl(goodProfiles.keySet()));
                for (List<ValidationMessage> messages : goodProfiles.values()) {
                  for (ValidationMessage vm : messages) {
                    if (!errors.contains(vm)) {
                      errors.add(vm);
                      ok = false;
                    }
                  }
                }
              }
            }
          }
          ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, rok,
            I18nConstants.REFERENCE_REF_BADTARGETTYPE, ft, types.toString()) && ok;
        }
        if (type.hasAggregation() && !noCheckAggregation) {
          boolean modeOk = false;
          CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
          for (Enumeration<AggregationMode> mode : type.getAggregation()) {
            b.append(mode.getCode());
            if (mode.getValue().equals(AggregationMode.CONTAINED) && refType == ReferenceDestinationType.CONTAINED)
              modeOk = true;
            else if (mode.getValue().equals(AggregationMode.BUNDLED) && refType == ReferenceDestinationType.INTERNAL)
              modeOk = true;
            else if (mode.getValue().equals(AggregationMode.REFERENCED) && (refType != ReferenceDestinationType.CONTAINED))
              modeOk = true;
          }
          ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, modeOk,
            I18nConstants.REFERENCE_REF_AGGREGATION, refType.toCode(), b.toString()) && ok;
        }
      }
    }
    if (we == null) {
      TypeRefComponent type = getReferenceTypeRef(container.getType());
      boolean okToRef = !type.hasAggregation() || type.hasAggregation(AggregationMode.REFERENCED);
      ok = rule(errors, NO_RULE_DATE, IssueType.REQUIRED, -1, -1, path, okToRef, I18nConstants.REFERENCE_REF_NOTFOUND_BUNDLE, ref) && ok;
    }
    if (we == null && ft != null && settings.isAssumeValidRestReferences()) {
      // if we == null, we inferred ft from the reference. if we are told to treat this as gospel
      TypeRefComponent type = getReferenceTypeRef(container.getType());
      Set<String> types = new HashSet<>();
      StructureDefinition sdFT = context.fetchResource(StructureDefinition.class, "http://hl7.org/fhir/StructureDefinition/"+ft);
      boolean rok = false;
      for (CanonicalType tp : type.getTargetProfile()) {
        StructureDefinition sd = context.fetchResource(StructureDefinition.class, tp.getValue(), profile);
        if (sd != null) {
          types.add(sd.getType());
          StructureDefinition sdF = sdFT;
          while (sdF != null) {
            if (sdF.getType().equals(sd.getType())) {
              rok = true;
              break;
            }
            sdF = sdF.hasBaseDefinition() ? context.fetchResource(StructureDefinition.class, sdF.getBaseDefinition(), sdF) : null;
          }
        }
      }
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, types.isEmpty() || rok,
        I18nConstants.REFERENCE_REF_BADTARGETTYPE2, ft, ref, types) && ok;

    }
    if (pol == ReferenceValidationPolicy.CHECK_VALID) {
      // todo....
    }
    
    // todo: if the content is a resource, check that Reference.type is describing a resource
    return ok;
  }

  private List<String> sorted(List<CanonicalType> list) {
    List<String> res = new ArrayList<String>();
    for (CanonicalType ct : list) {
      res.add(ct.asStringValue());
    }
    Collections.sort(res);
     return res;
  }

  private boolean isSuspiciousReference(String url) {
    if (!settings.isAssumeValidRestReferences() || url == null || Utilities.isAbsoluteUrl(url) || url.startsWith("#")) {
      return false;
    }
    String[] parts = url.split("\\/");
    if (parts.length == 2 && context.getResourceNames().contains(parts[0]) && Utilities.isValidId(parts[1])) {
      return false;
    }
    if (parts.length == 4 && context.getResourceNames().contains(parts[0]) && Utilities.isValidId(parts[1]) && "_history".equals(parts[2]) && Utilities.isValidId(parts[3])) {
      return false;
    }
    return true;
  }

  private String asListByUrl(Collection<StructureDefinition> coll) {
    List<StructureDefinition> list = new ArrayList<>();
    list.addAll(coll);
    Collections.sort(list, new StructureDefinitionSorterByUrl());
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (StructureDefinition sd : list) {
      b.append(sd.getVersionedUrl());
    }
    return b.toString();
  }

  private String asList(Collection<CanonicalType> coll) {
    List<CanonicalType> list = new ArrayList<>();
    list.addAll(coll);
    Collections.sort(list, new CanonicalTypeSorter());
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (CanonicalType c : list) {
      b.append(c.getValue());
    }
    return b.toString();
  }

  private boolean areAllBaseProfiles(List<StructureDefinition> profiles) {
    for (StructureDefinition sd : profiles) {
      if (!sd.getUrl().startsWith("http://hl7.org/fhir/StructureDefinition/")) {
        return false;
      }
    }
    return true;
  }

  private String errorSummaryForSlicing(List<ValidationMessage> list) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (ValidationMessage vm : list) {
      if (vm.getLevel() == IssueSeverity.ERROR || vm.getLevel() == IssueSeverity.FATAL || vm.isSlicingHint()) {
        b.append(vm.getLocation() + ": " + vm.getMessage());
      }
    }
    return b.toString();
  }

  private String errorSummaryForSlicingAsHtml(List<ValidationMessage> list) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (ValidationMessage vm : list) {
      if (vm.isSlicingHint()) {
        b.append("<li>" + vm.getLocation() + ": " + vm.getSliceHtml() + "</li>");
      } else if (vm.getLevel() == IssueSeverity.ERROR || vm.getLevel() == IssueSeverity.FATAL) {
        b.append("<li>" + vm.getLocation() + ": " + vm.getHtml() + "</li>");
      }
    }
    return "<ul>" + b.toString() + "</ul>";
  }

  private boolean isCritical(List<ValidationMessage> list) {
    for (ValidationMessage vm : list) {
      if (vm.isSlicingHint() && vm.isCriticalSignpost()) {
        return true;
      }
    }
    return false;
  }
  
  private TypeRefComponent getReferenceTypeRef(List<TypeRefComponent> types) {
    for (TypeRefComponent tr : types) {
      if ("Reference".equals(tr.getCode())) {
        return tr;
      }
    }
    return null;
  }

  private String checkResourceType(String type) {
    long t = System.nanoTime();
    try {
      if (context.fetchResource(StructureDefinition.class, "http://hl7.org/fhir/StructureDefinition/" + type) != null)
        return type;
      else
        return null;
    } finally {
      timeTracker.sd(t);
    }
  }

  private boolean checkSampledData(List<ValidationMessage> errors, String path, Element focus, SampledData fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".origin", focus.getNamedChild("origin", false), fixed.getOrigin(), fixedSource, "origin", focus, pattern, context) && ok;
    if (VersionUtilities.isR5VerOrLater(this.context.getVersion())) {
      ok = checkFixedValue(errors, path + ".interval", focus.getNamedChild("period", false), fixed.getIntervalElement(), fixedSource, "interval", focus, pattern, context) && ok;
      ok = checkFixedValue(errors, path + ".intervalUnit", focus.getNamedChild("period", false), fixed.getIntervalUnitElement(), fixedSource, "intervalUnit", focus, pattern, context) && ok;
    } else {
      ok = checkFixedValue(errors, path + ".period", focus.getNamedChild("period", false), fixed.getIntervalElement(), fixedSource, "period", focus, pattern, context) && ok;
    }
    ok = checkFixedValue(errors, path + ".factor", focus.getNamedChild("factor", false), fixed.getFactorElement(), fixedSource, "factor", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".lowerLimit", focus.getNamedChild("lowerLimit", false), fixed.getLowerLimitElement(), fixedSource, "lowerLimit", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".upperLimit", focus.getNamedChild("upperLimit", false), fixed.getUpperLimitElement(), fixedSource, "upperLimit", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".dimensions", focus.getNamedChild("dimensions", false), fixed.getDimensionsElement(), fixedSource, "dimensions", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".data", focus.getNamedChild("data", false), fixed.getDataElement(), fixedSource, "data", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkReference(List<ValidationMessage> errors, String path, Element focus, Reference fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".reference", focus.getNamedChild("reference", false), fixed.getReferenceElement_(), fixedSource, "reference", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".type", focus.getNamedChild("type", false), fixed.getTypeElement(), fixedSource, "type", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".identifier", focus.getNamedChild("identifier", false), fixed.getIdentifier(), fixedSource, "identifier", focus, pattern, context) && ok;
    ok = checkFixedValue(errors, path + ".display", focus.getNamedChild("display", false), fixed.getDisplayElement(), fixedSource, "display", focus, pattern, context) && ok;
    return ok;
  }

  private boolean checkTiming(List<ValidationMessage> errors, String path, Element focus, Timing fixed, String fixedSource, boolean pattern, String context) {
    boolean ok = true;
    ok = checkFixedValue(errors, path + ".repeat", focus.getNamedChild("repeat", false), fixed.getRepeat(), fixedSource, "value", focus, pattern, context) && ok;

    List<Element> events = new ArrayList<Element>();
    focus.getNamedChildren("event", events);
    if (rule(errors, NO_RULE_DATE, IssueType.VALUE, focus.line(), focus.col(), path, events.size() == fixed.getEvent().size(), I18nConstants.BUNDLE_MSG_EVENT_COUNT, Integer.toString(fixed.getEvent().size()), Integer.toString(events.size()))) {
      for (int i = 0; i < events.size(); i++)
        ok = checkFixedValue(errors, path + ".event", events.get(i), fixed.getEvent().get(i), fixedSource, "event", focus, pattern, context) && ok;
    } else {
      ok = false;
    }
    return ok;
  }

  private boolean codeinExpansion(ValueSetExpansionContainsComponent cnt, String system, String code) {
    for (ValueSetExpansionContainsComponent c : cnt.getContains()) {
      if (code.equals(c.getCode()) && system.equals(c.getSystem().toString()))
        return true;
      if (codeinExpansion(c, system, code))
        return true;
    }
    return false;
  }

  private boolean codeInExpansion(ValueSet vs, String system, String code) {
    for (ValueSetExpansionContainsComponent c : vs.getExpansion().getContains()) {
      if (code.equals(c.getCode()) && (system == null || system.equals(c.getSystem())))
        return true;
      if (codeinExpansion(c, system, code))
        return true;
    }
    return false;
  }

  private String describeReference(String reference, CanonicalResource target, BindingContext ctxt, String usageNote) {
    if (reference == null && target == null)
      return "null";
    String res = null;
    if (reference == null) {
      res = target.getVersionedUrl();
    } else if (target == null) {
      res = reference;
    } else {
      String uref = reference.contains("|") ? reference.substring(0, reference.lastIndexOf("|")) : reference;
      String vref = reference.contains("|") ? reference.substring(reference.lastIndexOf("|")+1) : null;
      if (uref.equals(target.getUrl()) && (vref == null || vref.equals(target.getVersion()))) {
        res = "'"+target.present()+"' ("+target.getVersionedUrl()+")";
      } else {
        res =  reference + "(which actually refers to '"+target.present()+"' (" + target.getVersionedUrl() + "))";
      }
    }
    switch (ctxt) {
    case ADDITIONAL: return context.formatMessage(Utilities.noString(usageNote) ? I18nConstants.BINDING_ADDITIONAL_D : I18nConstants.BINDING_ADDITIONAL_UC, res, usageNote);
    case MAXVS: return context.formatMessage(I18nConstants.BINDING_MAX, res);
    default: return res;
    }
  }

  private String describeTypes(List<TypeRefComponent> types) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (TypeRefComponent t : types) {
      b.append(t.getWorkingCode());
    }
    return b.toString();
  }

  protected ElementDefinition findElement(StructureDefinition profile, String name) {
    for (ElementDefinition c : profile.getSnapshot().getElement()) {
      if (c.getPath().equals(name)) {
        return c;
      }
    }
    return null;
  }

  @Override
  public CheckDisplayOption getCheckDisplay() {
    return checkDisplay;
  }

  private ConceptDefinitionComponent getCodeDefinition(ConceptDefinitionComponent c, String code) {
    if (code.equals(c.getCode()))
      return c;
    for (ConceptDefinitionComponent g : c.getConcept()) {
      ConceptDefinitionComponent r = getCodeDefinition(g, code);
      if (r != null)
        return r;
    }
    return null;
  }

  private ConceptDefinitionComponent getCodeDefinition(CodeSystem cs, String code) {
    for (ConceptDefinitionComponent c : cs.getConcept()) {
      ConceptDefinitionComponent r = getCodeDefinition(c, code);
      if (r != null)
        return r;
    }
    return null;
  }

  private IndexedElement getContainedById(Element container, String id) {
    List<Element> contained = new ArrayList<Element>();
    container.getNamedChildren("contained", contained);
    for (int i = 0; i < contained.size(); i++) {
      Element we = contained.get(i);
      if (id.equals(we.getNamedChildValue(ID, false))) {
        return new IndexedElement(i, we, null);
      }
    }
    return null;
  }

  public IWorkerContext getContext() {
    return context;
  }

  private List<ElementDefinition> getCriteriaForDiscriminator(String path, ElementDefinition element, String discriminator, StructureDefinition profile, boolean removeResolve, StructureDefinition srcProfile) throws FHIRException {
    List<ElementDefinition> elements = new ArrayList<ElementDefinition>();
    if ("value".equals(discriminator) && element.hasFixed()) {
      elements.add(element);
      return elements;
    }

    boolean dontFollowReference = false;
    
    if (removeResolve) {  // if we're doing profile slicing, we don't want to walk into the last resolve.. we need the profile on the source not the target
      if (discriminator.equals("resolve()")) {
        elements.add(element);
        return elements;
      }
      if (discriminator.endsWith(".resolve()")) {
        discriminator = discriminator.substring(0, discriminator.length() - 10);
        dontFollowReference = true;
      }
    }

    TypedElementDefinition ted = null;
    String fp = FHIRPathExpressionFixer.fixExpr(discriminator, null, context.getVersion());
    ExpressionNode expr = null;
    try {
      expr = fpe.parse(fp);
    } catch (Exception e) {
      if (STACK_TRACE) e.printStackTrace();
      throw new FHIRException(context.formatMessage(I18nConstants.DISCRIMINATOR_BAD_PATH, e.getMessage(), fp), e);
    }
    long t2 = System.nanoTime();
    ted = fpe.evaluateDefinition(expr, profile, new TypedElementDefinition(element), srcProfile, dontFollowReference);
    timeTracker.sd(t2);
    if (ted != null)
      elements.add(ted.getElement());

    for (TypeRefComponent type : element.getType()) {
      for (CanonicalType p : type.getProfile()) {
        String id = p.hasExtension(ExtensionDefinitions.EXT_PROFILE_ELEMENT) ? p.getExtensionString(ExtensionDefinitions.EXT_PROFILE_ELEMENT) : null;
        StructureDefinition sd = context.fetchResource(StructureDefinition.class, p.getValue(), profile);
        if (sd == null)
          throw new DefinitionException(context.formatMessage(I18nConstants.SD_ED_TYPE_PROFILE_UNKNOWN, p));
        profile = sd;
        if (id == null)
          element = sd.getSnapshot().getElementFirstRep();
        else {
          element = null;
          for (ElementDefinition t : sd.getSnapshot().getElement()) {
            if (id.equals(t.getId()))
              element = t;
          }
          if (element == null)
            throw new DefinitionException(context.formatMessage(I18nConstants.UNABLE_TO_RESOLVE_ELEMENT__IN_PROFILE_, id, p));
        }
        expr = fpe.parse(fp);
        t2 = System.nanoTime();
        ted = fpe.evaluateDefinition(expr, profile, new TypedElementDefinition(element), srcProfile, dontFollowReference);
        timeTracker.sd(t2);
        if (ted != null)
          elements.add(ted.getElement());
      }
    }
    return elements;
  }


  private Element getExtensionByUrl(List<Element> extensions, String urlSimple) {
    for (Element e : extensions) {
      if (urlSimple.equals(e.getNamedChildValue("url", false)))
        return e;
    }
    return null;
  }

  public List<String> getExtensionDomains() {
    return extensionDomains;
  }

  public List<ImplementationGuide> getImplementationGuides() {
    return igs;
  }

  private StructureDefinition getProfileForType(String type, List<TypeRefComponent> list, Resource src) {
    for (TypeRefComponent tr : list) {
      String url = tr.getWorkingCode();
      if (!Utilities.isAbsoluteUrl(url))
        url = "http://hl7.org/fhir/StructureDefinition/" + url;
      long t = System.nanoTime();
      StructureDefinition sd = context.fetchResource(StructureDefinition.class, url, src);
      timeTracker.sd(t);
      if (sd != null && (sd.getTypeTail().equals(type) || sd.getUrl().equals(type)) && sd.hasSnapshot()) {
        return sd;
      }
      if (sd != null && (sd.getAbstract() || sd.getUrl().startsWith(Constants.NS_CDA_ROOT))) {
        StructureDefinition sdt = context.fetchTypeDefinition(type);
        StructureDefinition tt = sdt;
        while (tt != null) {
          if (sd.getUrl().equals(tt.getBaseDefinition())) {
            return sdt;
          }
          tt = context.fetchResource(StructureDefinition.class, tt.getBaseDefinition());
        }
      }
    }
    return null;
  }

  private Element getValueForDiscriminator(Object appContext, List<ValidationMessage> errors, Element element, String discriminator, ElementDefinition criteria, NodeStack stack, BooleanHolder bh) throws FHIRException, IOException {
    String p = stack.getLiteralPath() + "." + element.getName();
    Element focus = element;
    String[] dlist = discriminator.split("\\.");
    for (String d : dlist) {
      if (focus.fhirType().equals("Reference") && d.equals("reference")) {
        String url = focus.getChildValue("reference");
        if (Utilities.noString(url))
          throw new FHIRException(context.formatMessage(I18nConstants.NO_REFERENCE_RESOLVING_DISCRIMINATOR__FROM_, discriminator, element.getProperty().getName()));
        // Note that we use the passed in stack here. This might be a problem if the discriminator is deep enough?
        Element target = resolve(appContext, url, stack, errors, p, bh);
        if (target == null)
          throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_FIND_RESOURCE__AT__RESOLVING_DISCRIMINATOR__FROM_, url, d, discriminator, element.getProperty().getName()));
        focus = target;
      } else if (d.equals("value") && focus.isPrimitive()) {
        return focus;
      } else {
        List<Element> children = focus.getChildren(d);
        if (children.isEmpty())
          throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_FIND__RESOLVING_DISCRIMINATOR__FROM_, d, discriminator, element.getProperty().getName()));
        if (children.size() > 1)
          throw new FHIRException(context.formatMessage(I18nConstants.FOUND__ITEMS_FOR__RESOLVING_DISCRIMINATOR__FROM_, Integer.toString(children.size()), d, discriminator, element.getProperty().getName()));
        focus = children.get(0);
        p = p + "." + d;
      }
    }
    return focus;
  }

  private CodeSystem getCodeSystem(String system) {
    long t = System.nanoTime();
    try {
      return context.fetchCodeSystem(system);
    } finally {
      timeTracker.tx(t, "cs "+system);
    }
  }

  private boolean hasTime(String fmt) {
    return fmt.contains("T");
  }

  private boolean hasTimeZone(String fmt) {
    return fmt.length() > 10 && (fmt.substring(10).contains("-") || fmt.substring(10).contains("+") || fmt.substring(10).contains("Z"));
  }

  private boolean isAbsolute(String uri) {
    String protocol = null;
    String tail = null;
    if (uri.contains(":")) {
      protocol = uri.substring(0, uri.indexOf(":"));
      tail = uri.substring(uri.indexOf(":")+1);
    }
    if (Utilities.isToken(protocol)) {
      if ("file".equals(protocol)) {
        return tail.startsWith("/") || tail.contains(":");
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

  private boolean isCodeSystemReferenceValid(String uri) {
    return isSystemReferenceValid(uri);    
  }

  private boolean isIdentifierSystemReferenceValid(String uri) {
    return isSystemReferenceValid(uri) || uri.startsWith("ldap:");
  }

  private boolean isSystemReferenceValid(String uri) {
    return uri.startsWith("http:") || uri.startsWith("https:") || uri.startsWith("urn:");
  }

  public boolean isAnyExtensionsAllowed() {
    return anyExtensionsAllowed;
  }

  public boolean isErrorForUnknownProfiles() {
    return errorForUnknownProfiles;
  }

  public void setErrorForUnknownProfiles(boolean errorForUnknownProfiles) {
    this.errorForUnknownProfiles = errorForUnknownProfiles;
  }

  private boolean isParametersEntry(String path) {
    String[] parts = path.split("\\.");
    return parts.length > 2 && parts[parts.length - 1].equals(RESOURCE) && (pathEntryHasName(parts[parts.length - 2], "parameter") || pathEntryHasName(parts[parts.length - 2], "part"));
  }

  private boolean isBundleEntry(String path) {
    String[] parts = path.split("\\.");
    return parts.length > 2 && parts[parts.length - 1].equals(RESOURCE) && pathEntryHasName(parts[parts.length - 2], ENTRY);
  }

  private boolean isBundleOutcome(String path) {
    String[] parts = path.split("\\.");
    return parts.length > 2 && parts[parts.length - 1].equals("outcome") && pathEntryHasName(parts[parts.length - 2], "response");
  }


  private static boolean pathEntryHasName(String thePathEntry, String theName) {
    if (thePathEntry.equals(theName)) {
      return true;
    }
    if (thePathEntry.length() >= theName.length() + 3) {
      if (thePathEntry.startsWith(theName)) {
        if (thePathEntry.charAt(theName.length()) == '[') {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isPrimitiveType(String code) {
    return context.isPrimitiveType(code);
  }

  private String getErrorMessage(String message) {
     return message != null ? " (error message = " + message + ")" : "";
  }

  public boolean isSuppressLoincSnomedMessages() {
    return suppressLoincSnomedMessages;
  }

  private boolean nameMatches(String name, String tail) {
    if (tail.endsWith("[x]"))
      return name.startsWith(tail.substring(0, tail.length() - 3));
    else
      return (name.equals(tail));
  }

  private boolean passesCodeWhitespaceRules(String v) {
    if (!Utilities.trimWS(v).equals(v))
      return false;
    boolean lastWasSpace = true;
    for (char c : v.toCharArray()) {
      if (c == ' ') {
        if (lastWasSpace)
          return false;
        else
          lastWasSpace = true;
      } else if (Utilities.isWhitespace(c))
        return false;
      else
        lastWasSpace = false;
    }
    return true;
  }

  public ResolvedReference localResolve(String ref, NodeStack stack, List<ValidationMessage> errors, String path, Element rootResource, Element groupingResource, Element source, BooleanHolder bh, BooleanHolder stop) {
    if (ref.startsWith("#")) {
      // work back through the parent list, tracking the stack as we go 
      // really, there should only be one level for this (contained resources cannot contain
      // contained resources), but we'll leave that to some other code to worry about
      boolean wasContained = false;
      Element focus = stack.getElement();
      NodeStack nstack = stack;
      while (focus != null) {
        if (focus.getProperty().isResource()) {
          // ok, we'll try to find the contained reference
          if (ref.equals("#") && focus.getSpecial() != SpecialElement.CONTAINED && wasContained) {
            ResolvedReference rr = new ResolvedReference();
            rr.setResource(focus);
            rr.setFocus(focus);
            rr.setExternal(false);
            rr.setStack(nstack);
            return rr;            
          }
          if (focus.getSpecial() == SpecialElement.CONTAINED) {
            wasContained = true;
          }
          IndexedElement res = getContainedById(focus, ref.substring(1));
          if (res != null) {
            ResolvedReference rr = new ResolvedReference();
            rr.setResource(focus);
            rr.setFocus(res.getMatch());
            rr.setExternal(false);
            rr.setStack(nstack.push(res.getMatch(), res.getIndex(), res.getMatch().getProperty().getDefinition(), res.getMatch().getProperty().getDefinition()));
            rr.getStack().pathComment(res.getMatch().fhirType()+"/"+res.getMatch().getIdBase());
            return rr;
          }
        }
        if (focus.getSpecial() == SpecialElement.BUNDLE_ENTRY || focus.getSpecial() == SpecialElement.PARAMETER) {
          return null; // we don't try to resolve contained references across this boundary
        }
        focus = focus.getParentForValidator();
        nstack = nstack.getParent();
        if (focus != null && nstack == null) {
          // we have run off the bottom of the stack, need to fake something
          nstack = new NodeStack(context, focus, focus.fhirType(), validationLanguage);
        }
      }
      return null;
    } else {
      // work back through the parent list - if any of them are bundles, try to resolve
      // the resource in the bundle
      
      // 2024-04-05 - must work through the element parents not the stack parents, as the stack is not necessarily reflective of the full parent list
      Element focus = stack.getElement();
      String fullUrl = null; // we're going to try to work this out as we go up
      while (focus != null) {
        // track the stack while we can
        if (focus.getSpecial() == SpecialElement.BUNDLE_ENTRY && fullUrl == null && focus != null && focus.getParentForValidator() != null && focus.getParentForValidator().getName().equals(ENTRY)) {
          String type = focus.getParentForValidator().getParentForValidator().getChildValue(TYPE);
          fullUrl = focus.getParentForValidator().getChildValue(FULL_URL); // we don't try to resolve contained references across this boundary
          if (fullUrl == null) {
            bh.see(rule(errors, NO_RULE_DATE, IssueType.REQUIRED, focus.getParentForValidator().line(), focus.getParentForValidator().col(), focus.getParentForValidator().getPath(),
              Utilities.existsInList(type, "batch-response", "transaction-response") || fullUrl != null, I18nConstants.BUNDLE_BUNDLE_ENTRY_NOFULLURL));
          } else if (!fullUrl.matches(Constants.URI_REGEX) && !Utilities.existsInList(type, "transaction", "batch") && !Utilities.isAbsoluteUrl(ref) && applyR5BundleRelativePolicy()) {
            stop.set(true);
          }
        }
        if (BUNDLE.equals(focus.getType())) {
          String type = focus.getChildValue(TYPE);
          if (!stop.ok()) {
            IndexedElement res = getFromBundle(focus, ref, fullUrl, errors, path, type, Utilities.existsInList(type, "transaction", "batch"), bh);
            if (res == null) {
              return null;
            } else {
              ResolvedReference rr = new ResolvedReference();
              rr.setResource(res.getMatch());
              rr.setFocus(res.getMatch());
              rr.setExternal(false);
              rr.setStack(new NodeStack(context, null, res.getMatch(), validationLanguage));
              rr.setVia(stack);
              rr.getStack().pathComment(rr.getResource().fhirType()+"/"+rr.getResource().getIdBase());
              return rr;
            }
          }
        }
        if (focus.getSpecial() == SpecialElement.PARAMETER && focus.getParentForValidator() != null) {
          NodeStack tgt = findInParams(findParameters(focus), ref, stack);
          if (tgt != null) {
            ResolvedReference rr = new ResolvedReference();
            rr.setResource(tgt.getElement());
            rr.setFocus(tgt.getElement());
            rr.setExternal(false);
            rr.setStack(tgt);
            rr.getStack().pathComment(tgt.getElement().fhirType()+"/"+tgt.getElement().getIdBase());
            return rr;            
          }
        }
        focus = focus.getParentForValidator();
      }
      // we can get here if we got called via FHIRPath conformsTo which breaks the stack continuity.
      if (groupingResource != null && BUNDLE.equals(groupingResource.fhirType())) { // it could also be a Parameters resource - that case isn't handled yet
        String type = groupingResource.getChildValue(TYPE);
        Element entry = getEntryForSource(groupingResource, source);
        fullUrl = entry.getChildValue(FULL_URL);
        if (!fullUrl.matches(org.hl7.fhir.r5.tools.Constants.URI_REGEX) && !Utilities.existsInList(type, "transaction", "batch") && !Utilities.isAbsoluteUrl(ref) && applyR5BundleRelativePolicy()) {
          stop.set(true);
        } else {
          IndexedElement res = getFromBundle(groupingResource, ref, fullUrl, errors, path, type, Utilities.existsInList(type, "transaction", "batch"), bh);
          if (res == null) {
            return null;
          } else {
            ResolvedReference rr = new ResolvedReference();
            rr.setResource(res.getMatch());
            rr.setFocus(res.getMatch());
            rr.setExternal(false);
            rr.setStack(new NodeStack(context, null, rootResource, validationLanguage).push(res.getEntry(), res.getIndex(), res.getEntry().getProperty().getDefinition(),
                res.getEntry().getProperty().getDefinition()).push(res.getMatch(), -1,
                    res.getMatch().getProperty().getDefinition(), res.getMatch().getProperty().getDefinition()));
            rr.getStack().pathComment(rr.getResource().fhirType()+"/"+rr.getResource().getIdBase());
            return rr;
          }
        }
      }
    }
    return null;
  }

  private boolean applyR5BundleRelativePolicy() {
    switch (settings.getR5BundleRelativeReferencePolicy()) {
    case ALWAYS: return true;
    case NEVER: return false;
    case DEFAULT:
    default:
      return VersionUtilities.isR5Plus(context.getVersion());
    }
  }

  private Element findParameters(Element focus) {
    while (focus != null) {
      if ("Parameters".equals(focus.fhirType())) {
        return focus;
      }
      focus = focus.getParentForValidator();
    }
    return null;
  }

  private NodeStack findInParams(Element params, String ref, NodeStack stack) {
    if (params == null) {
      return null;
    }
    int i = 0;
    for (Element child : params.getChildren("parameter")) {
      NodeStack p = stack.push(child, i, child.getProperty().getDefinition(), child.getProperty().getDefinition());
      if (child.hasChild("resource", false)) {
        Element res = child.getNamedChild("resource", false);
        if ((res.fhirType()+"/"+res.getIdBase()).equals(ref)) {
          return p.push(res, -1, res.getProperty().getDefinition(), res.getProperty().getDefinition());
        }
      }
      NodeStack pc = findInParamParts(p, child, ref);
      if (pc != null) {
        return pc;
      }
    }
    return null;
  }

  private NodeStack findInParamParts(NodeStack pp, Element param, String ref) {
    int i = 0;
    for (Element child : param.getChildren("part")) {
      NodeStack p = pp.push(child, i, child.getProperty().getDefinition(), child.getProperty().getDefinition());
      if (child.hasChild("resource", false)) {
        Element res = child.getNamedChild("resource", false);
        if ((res.fhirType()+"/"+res.getIdBase()).equals(ref)) {
          return p.push(res, -1, res.getProperty().getDefinition(), res.getProperty().getDefinition());
        }
      }
      NodeStack pc = findInParamParts(p, child, ref);
      if (pc != null) {
        return pc;
      }
    }
    return null;
  }

  private Element getEntryForSource(Element bundle, Element element) {
    List<Element> entries = new ArrayList<Element>();
    bundle.getNamedChildren(ENTRY, entries);
    for (Element entry : entries) {
      if (entry.hasDescendant(element)) {
        return entry;
      }
    }
    return null;
  }

  private ResolvedReference makeExternalRef(Element external, String path) {
    ResolvedReference res = new ResolvedReference();
    res.setResource(external);
    res.setFocus(external);
    res.setExternal(true);
    res.setStack(new NodeStack(context, external, path, validationLanguage));
    return res;
  }


  private Element resolve(Object appContext, String ref, NodeStack stack, List<ValidationMessage> errors, String path, BooleanHolder bh) throws IOException, FHIRException {
    Element local = localResolve(ref, stack, errors, path, null, null, null, bh, new BooleanHolder()).getFocus();
    if (local != null)
      return local;
    if (fetcher == null)
      return null;
    if (fetchCache.containsKey(ref)) {
      return fetchCache.get(ref);
    } else {
      Element res = fetcher.fetch(this, appContext, ref);
      setParents(res);
      fetchCache.put(ref, res);
      return res;
    }
  }


  private ElementDefinition resolveNameReference(StructureDefinitionSnapshotComponent snapshot, String contentReference) {
    for (ElementDefinition ed : snapshot.getElement())
      if (contentReference.equals("#" + ed.getId()))
        return ed;
    return null;
  }

  private StructureDefinition resolveProfile(StructureDefinition profile, String pr) {
    if (pr.startsWith("#")) {
      for (Resource r : profile.getContained()) {
        if (r.getId().equals(pr.substring(1)) && r instanceof StructureDefinition)
          return (StructureDefinition) r;
      }
      return null;
    } else {
      long t = System.nanoTime();
      StructureDefinition fr = context.fetchResource(StructureDefinition.class, pr, profile);
      timeTracker.sd(t);
      return fr;
    }
  }

  private ElementDefinition resolveType(String type, List<TypeRefComponent> list) {
    for (TypeRefComponent tr : list) {
      String url = tr.getWorkingCode();
      if (!Utilities.isAbsoluteUrl(url))
        url = "http://hl7.org/fhir/StructureDefinition/" + url;
      long t = System.nanoTime();
      StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
      timeTracker.sd(t);
      if (sd != null && (sd.getType().equals(type) || sd.getUrl().equals(type)) && sd.hasSnapshot())
        return sd.getSnapshot().getElement().get(0);
      if (sd != null) {
        StructureDefinition sdt = context.fetchTypeDefinition(type);
        if (inheritsFrom(sdt, sd) && sdt.hasSnapshot()) {
          return sdt.getSnapshot().getElement().get(0);
        }        
      }
    }
    return null;
  }

  private boolean inheritsFrom(StructureDefinition sdt, StructureDefinition sd) {
    while (sdt != null) {
      if (sdt == sd) {
        return true;
      }
      sdt = context.fetchResource(StructureDefinition.class, sdt.getBaseDefinition());
    }
    return false;
  }

  public void setAnyExtensionsAllowed(boolean anyExtensionsAllowed) {
    this.anyExtensionsAllowed = anyExtensionsAllowed;
  }


  @Override
  public void setCheckDisplay(CheckDisplayOption checkDisplay) {
    this.checkDisplay = checkDisplay;
  }

  public void setSuppressLoincSnomedMessages(boolean suppressLoincSnomedMessages) {
    this.suppressLoincSnomedMessages = suppressLoincSnomedMessages;
  }

  public IdStatus getResourceIdRule() {
    return resourceIdRule;
  }

  public void setResourceIdRule(IdStatus resourceIdRule) {
    this.resourceIdRule = resourceIdRule;
  }


  public boolean isAllowXsiLocation() {
    return allowXsiLocation;
  }

  public void setAllowXsiLocation(boolean allowXsiLocation) {
    this.allowXsiLocation = allowXsiLocation;
  }

  /**
   * @param element - the candidate that might be in the slice
   * @param path    - for reporting any errors. the XPath for the element
   * @param slicer  - the definition of how slicing is determined
   * @param ed      - the slice for which to test membership
   * @param errors
   * @param stack
   * @param srcProfile 
   * @return
   * @throws DefinitionException
   * @throws DefinitionException
   * @throws IOException
   * @throws FHIRException
   */
  private boolean sliceMatches(ValidationContext valContext, Element element, String path, ElementDefinition slicer, List<ElementDefinition> slicerSlices, ElementDefinition ed, StructureDefinition profile, List<ValidationMessage> errors, List<ValidationMessage> sliceInfo, NodeStack stack, StructureDefinition srcProfile) throws DefinitionException, FHIRException {
    if (!slicer.getSlicing().hasDiscriminator())
      return false; // cannot validate in this case

    ExpressionNode n = (ExpressionNode) ed.getUserData(UserDataNames.validator_slice_expression_cache);
    if (n == null) {
      long t = System.nanoTime();
      // GG: this approach is flawed because it treats discriminators individually rather than collectively
      StringBuilder expression = new StringBuilder("true");
      boolean anyFound = false;
      Set<String> discriminators = new HashSet<>();
      for (ElementDefinitionSlicingDiscriminatorComponent s : slicer.getSlicing().getDiscriminator()) {
        String discriminator = s.getPath();
        discriminators.add(discriminator);

        List<ElementDefinition> criteriaElements = getCriteriaForDiscriminator(path, ed, discriminator, profile, s.getType() == DiscriminatorType.PROFILE, srcProfile);
        boolean found = false;
        for (ElementDefinition criteriaElement : criteriaElements) {
          found = true;
          if ("0".equals(criteriaElement.getMax())) {
            expression.append(" and " + discriminator + ".empty()");            
          } else if (s.getType() == DiscriminatorType.TYPE) {
            if (!criteriaElement.getPath().contains("[") && discriminator.contains("[")) {
              discriminator = discriminator.substring(0, discriminator.indexOf('['));
              String lastNode = tail(discriminator);
              String type = makeTypeForFHIRPath(criteriaElement.getPath()).substring(lastNode.length());
              expression.append(" and " + discriminator + " is " + type);
            } else if (!criteriaElement.hasType() || criteriaElement.getType().size() == 1) {
              String type = null;
              if (discriminator.contains("["))
                discriminator = discriminator.substring(0, discriminator.indexOf('['));
              if (criteriaElement.hasType()) {
                type = makeTypeForFHIRPath(criteriaElement.getType().get(0).getWorkingCode());
              } else if (!criteriaElement.getPath().contains(".")) {
                type = makeTypeForFHIRPath(criteriaElement.getPath());
              } else {
                throw new DefinitionException(context.formatMessage(I18nConstants.DISCRIMINATOR__IS_BASED_ON_TYPE_BUT_SLICE__IN__HAS_NO_TYPES, discriminator, ed.getId(), profile.getVersionedUrl()));
              }
              expression.append(" and " + discriminator + " is " + type);
            } else if (criteriaElement.getType().size() > 1) {
              CommaSeparatedStringBuilder cb = new CommaSeparatedStringBuilder(" or ");
              for (TypeRefComponent tr : criteriaElement.getType()) {
                String type = makeTypeForFHIRPath(tr.getWorkingCode());
                cb.append(discriminator + " is " + type);
              }
              expression.append(" and (" + cb.toString()+")");
            } else
              throw new DefinitionException(context.formatMessage(I18nConstants.DISCRIMINATOR__IS_BASED_ON_TYPE_BUT_SLICE__IN__HAS_NO_TYPES, discriminator, ed.getId(), profile.getVersionedUrl()));
          } else if (s.getType() == DiscriminatorType.PROFILE) {
            if (criteriaElement.getType().size() == 0) {
              throw new DefinitionException(context.formatMessage(I18nConstants.PROFILE_BASED_DISCRIMINATORS_MUST_HAVE_A_TYPE__IN_PROFILE_, criteriaElement.getId(), profile.getVersionedUrl()));
            }
            List<CanonicalType> list = new ArrayList<>();
            boolean ref = discriminator.endsWith(".resolve()") || discriminator.equals("resolve()");
            for (TypeRefComponent tr : criteriaElement.getType()) {
              list.addAll(ref ? tr.getTargetProfile() : tr.getProfile()); 
            }
            if (list.size() == 0) {
              // we don't have to find something 
              // throw new DefinitionException(context.formatMessage(I18nConstants.PROFILE_BASED_DISCRIMINATORS_MUST_HAVE_A_TYPE_WITH_A_PROFILE__IN_PROFILE_, criteriaElement.getId(), profile.getVersionedUrl()));
            } else if (list.size() > 1) {
              CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder(" or ");
              for (CanonicalType c : list) {
                b.append(discriminator + ".conformsTo('" + c.getValue() + "')");
              }
              expression.append(" and (" + b + ")");
            } else {
              expression.append(" and " + discriminator + ".conformsTo('" + list.get(0).getValue() + "')");
            }
          } else if (s.getType() == DiscriminatorType.EXISTS) {
            if (criteriaElement.hasMin() && criteriaElement.getMin() >= 1) {
              expression.append(" and (" + discriminator + ".exists())");
            } else if (criteriaElement.hasMax() && criteriaElement.getMax().equals("0")) {
              expression.append(" and (" + discriminator + ".exists().not())");
            } else {
              throw new FHIRException(context.formatMessage(I18nConstants.DISCRIMINATOR__IS_BASED_ON_ELEMENT_EXISTENCE_BUT_SLICE__NEITHER_SETS_MIN1_OR_MAX0, discriminator, ed.getId()));
            }
          } else if (s.getType() == DiscriminatorType.POSITION) {
            // we don't evaluate this one using FHIRPath, and it can't share 
            if (slicer.getSlicing().getDiscriminator().size() != 1) {
              throw new DefinitionException(context.formatMessagePlural(slicer.getSlicing().getDiscriminator().size(), I18nConstants.Could_not_match_discriminator_for_slice_in_profile, discriminators, ed.getId(), profile.getVersionedUrl(), discriminators));
            } else {
              int offset = 0;
              for (ElementDefinition ts : slicerSlices) {
                if (ts == ed) {
                  break;
                } else if (!ts.getMax().equals(Integer.toString(ts.getMin()))) {
                  throw new DefinitionException(context.formatMessagePlural(slicer.getSlicing().getDiscriminator().size(), I18nConstants.Could_not_match_discriminator_for_slice_in_profile, discriminators, ed.getId(), profile.getVersionedUrl(), discriminators));                  
                } else {
                  offset = offset + ts.getMin();
                }
              }
              int maxPos = (ed.getMax().equals("*") ? Integer.MAX_VALUE : offset + Integer.parseInt(ed.getMax()));
              int position = path.endsWith("]") ? Integer.parseInt(path.substring(path.lastIndexOf("[")+1).replace("]", "")) : 0;
              return position >= offset && position < maxPos;
            }                        
          } else if (criteriaElement.hasFixed()) {
            buildFixedExpression(ed, expression, discriminator, criteriaElement);
          } else if (criteriaElement.hasPattern()) {
            buildPattternExpression(ed, expression, discriminator, criteriaElement);
          } else if (criteriaElement.hasBinding() && criteriaElement.getBinding().hasStrength() && criteriaElement.getBinding().getStrength().equals(BindingStrength.REQUIRED) && criteriaElement.getBinding().hasValueSet()) {
            expression.append(" and (" + discriminator + " memberOf '" + criteriaElement.getBinding().getValueSet() + "')");
          } else {
            found = false;
          }
          if (found)
            break;
        }
        if (found)
          anyFound = true;
      }
      if (!anyFound) {
          throw new DefinitionException(context.formatMessagePlural(slicer.getSlicing().getDiscriminator().size(), I18nConstants.Could_not_match_discriminator_for_slice_in_profile, 
              CommaSeparatedStringBuilder.join("|", discriminators), ed.getId(), profile.getVersionedUrl(), discriminators));
      }

      try {
        n = fpe.parse(FHIRPathExpressionFixer.fixExpr(expression.toString(), null, context.getVersion()));
      } catch (FHIRLexerException e) {
        if (STACK_TRACE) e.printStackTrace();
        throw new FHIRException(context.formatMessage(I18nConstants.PROBLEM_PROCESSING_EXPRESSION__IN_PROFILE__PATH__, expression, profile.getVersionedUrl(), path, e.getMessage()));
      }
      timeTracker.fpe(t);
      ed.setUserData(UserDataNames.validator_slice_expression_cache, n);
    } else {
    }

    ValidationContext shc = valContext.forSlicing();
    boolean pass = evaluateSlicingExpression(shc, element, path, profile, n);
    if (!pass) {
      slicingHint(sliceInfo, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false, isProfile(slicer), (context.formatMessage(I18nConstants.DOES_NOT_MATCH_SLICE_, ed.getSliceName(), n.toString().substring(8).trim())), "discriminator = " + Utilities.escapeXml(n.toString()), null);
      for (String url : shc.getSliceRecords().keySet()) {
        StructureDefinition sdt = context.fetchResource(StructureDefinition.class, url);
        slicingHint(sliceInfo, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), path, false, isProfile(slicer), 
          context.formatMessage(I18nConstants.DETAILS_FOR__MATCHING_AGAINST_PROFILE_, stack.getLiteralPath(), sdt == null ?  url : sdt.getVersionedUrl()),
          context.formatMessage(I18nConstants.PROFILE__DOES_NOT_MATCH_FOR__BECAUSE_OF_THE_FOLLOWING_PROFILE_ISSUES__,
              url,
              stack.getLiteralPath(), errorSummaryForSlicingAsHtml(shc.getSliceRecords().get(url))), shc.getSliceRecords().get(url));
      }
    }
    return pass;
  }

  private String makeTypeForFHIRPath(String type) {
    if (Utilities.isAbsoluteUrl(type)) {
      if (type.startsWith("http://hl7.org/fhir/StructureDefinition/")) {
        return typeTail(type);
      } else if (type.startsWith("http://hl7.org/cda/stds/core/StructureDefinition/")) {
        String tt = typeTail(type);
        if (tt.contains("-")) {
          tt = '`'+tt.replace("-", "_")+'`';
        }
        return "CDA."+tt; 
      } else {
        return typeTail(type); // todo?
      }
    } else {
      String ptype = type.substring(0, 1).toLowerCase() + type.substring(1);
      if (context.isPrimitiveType(ptype)) {
        return ptype;        
      } else {
        return type;
      }
    }
  }

  private String typeTail(String type) {
    return type.contains("/") ? type.substring(type.lastIndexOf("/")+1) : type;
  }

  private boolean isBaseDefinition(String url) {
    boolean b = url.startsWith("http://hl7.org/fhir/") && !url.substring(40).contains("/");
    return b;
  }

  private String descSD(String url) {
    StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
    return sd == null ? url : sd.present();
  }

  private boolean isProfile(ElementDefinition slicer) {
    if (slicer == null || !slicer.hasSlicing()) {
      return false;
    }
    for (ElementDefinitionSlicingDiscriminatorComponent t : slicer.getSlicing().getDiscriminator()) {
      if (t.getType() == DiscriminatorType.PROFILE) {
        return true;
      }
    }
    return false;
  }

  public boolean evaluateSlicingExpression(ValidationContext valContext, Element element, String path, StructureDefinition profile, ExpressionNode n) throws FHIRException {
    String msg;
    boolean pass;
    try {
      long t = System.nanoTime();
      pass = fpe.evaluateToBoolean(valContext.forProfile(profile), valContext.getResource(), valContext.getRootResource(), element, n);
      timeTracker.fpe(t);
      msg = fpe.forLog();
    } catch (Exception ex) {
      if (STACK_TRACE) ex.printStackTrace();
      throw new FHIRException(context.formatMessage(I18nConstants.PROBLEM_EVALUATING_SLICING_EXPRESSION_FOR_ELEMENT_IN_PROFILE__PATH__FHIRPATH___, profile.getVersionedUrl(), path, n, ex.getMessage()));
    }
    return pass;
  }

  private void buildPattternExpression(ElementDefinition ed, StringBuilder expression, String discriminator, ElementDefinition criteriaElement) throws DefinitionException {
    DataType pattern = criteriaElement.getPattern();
    if (pattern instanceof CodeableConcept) {
      CodeableConcept cc = (CodeableConcept) pattern;
      expression.append(" and ");
      buildCodeableConceptExpression(ed, expression, discriminator, cc);
    } else if (pattern instanceof Coding) {
      Coding c = (Coding) pattern;
      expression.append(" and ");
      buildCodingExpression(ed, expression, discriminator, c);
    } else if (pattern instanceof BooleanType || pattern instanceof IntegerType || pattern instanceof DecimalType) {
      expression.append(" and ");
      buildPrimitiveExpression(ed, expression, discriminator, pattern, false);
    } else if (pattern instanceof PrimitiveType) {
      expression.append(" and ");
      buildPrimitiveExpression(ed, expression, discriminator, pattern, true);
    } else if (pattern instanceof Identifier) {
      Identifier ii = (Identifier) pattern;
      expression.append(" and ");
      buildIdentifierExpression(ed, expression, discriminator, ii);
    } else if (pattern instanceof HumanName) {
      HumanName name = (HumanName) pattern;
      expression.append(" and ");
      buildHumanNameExpression(ed, expression, discriminator, name);
    } else if (pattern instanceof Address) {
      Address add = (Address) pattern;
      expression.append(" and ");
      buildAddressExpression(ed, expression, discriminator, add);
    } else {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_FIXED_PATTERN_TYPE_FOR_DISCRIMINATOR_FOR_SLICE__, discriminator, ed.getId(), pattern.fhirType()));
    }
  }

  private void buildIdentifierExpression(ElementDefinition ed, StringBuilder expression, String discriminator, Identifier ii)
    throws DefinitionException {
    if (ii.hasExtension())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    boolean first = true;
    expression.append(discriminator + ".where(");
    if (ii.hasSystem()) {
      first = false;
      expression.append("system = '" + ii.getSystem() + "'");
    }
    if (ii.hasValue()) {
      if (first)
        first = false;
      else
        expression.append(" and ");
      expression.append("value = '" + ii.getValue() + "'");
    }
    if (ii.hasUse()) {
      if (first)
        first = false;
      else
        expression.append(" and ");
      expression.append("use = '" + ii.getUse() + "'");
    }
    if (ii.hasType()) {
      if (first)
        first = false;
      else
        expression.append(" and ");
      buildCodeableConceptExpression(ed, expression, TYPE, ii.getType());
    }
    if (first) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_NO_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), ii.fhirType()));
    }
    expression.append(").exists()");
  }

  private void buildHumanNameExpression(ElementDefinition ed, StringBuilder expression, String discriminator, HumanName name) throws DefinitionException {
    if (name.hasExtension())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    boolean first = true;
    expression.append(discriminator + ".where(");
    if (name.hasUse()) {
      first = false;
      expression.append("use = '" + name.getUse().toCode() + "'");
    }
    if (name.hasText()) {
      if (first)
        first = false;
      else
        expression.append(" and ");
      expression.append("text = '" + name.getText() + "'");
    }
    if (name.hasFamily()) {
      if (first)
        first = false;
      else
        expression.append(" and ");
      expression.append("family = '" + name.getFamily() + "'");
    }
    if (name.hasGiven()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), name.fhirType(), "given"));
    }
    if (name.hasPrefix()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), name.fhirType(), "prefix"));
    }
    if (name.hasSuffix()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), name.fhirType(), "suffix"));
    }
    if (name.hasPeriod()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), name.fhirType(), "period"));
    }
    if (first) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_NO_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), name.fhirType()));
    }

    expression.append(").exists()");
  }

  private void buildAddressExpression(ElementDefinition ed, StringBuilder expression, String discriminator, Address add) throws DefinitionException {
    if (add.hasExtension()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    }
    boolean first = true;
    expression.append(discriminator + ".where(");
    if (add.hasUse()) {
      first = false;
      expression.append("use = '" + add.getUse().toCode() + "'");
    }
    if (add.hasType()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("type = '" + add.getType().toCode() + "'");
    }
    if (add.hasText()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("text = '" + add.getText() + "'");
    }
    if (add.hasCity()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("city = '" + add.getCity() + "'");
    }
    if (add.hasDistrict()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("district = '" + add.getDistrict() + "'");
    }
    if (add.hasState()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("state = '" + add.getState() + "'");
    }
    if (add.hasPostalCode()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("postalCode = '" + add.getPostalCode() + "'");
    }
    if (add.hasCountry()) {
      if (first) first = false; else expression.append(" and ");
      expression.append("country = '" + add.getCountry() + "'");
    }       
    if (add.hasLine()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), add.fhirType(), "line"));
    }
    if (add.hasPeriod()) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), add.fhirType(), "period"));
    }
    if (first) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_NO_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), add.fhirType()));
    }
    expression.append(").exists()");
  }

  private void buildCodeableConceptExpression(ElementDefinition ed, StringBuilder expression, String discriminator, CodeableConcept cc)
    throws DefinitionException {
    if (cc.hasText())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__USING_TEXT__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    if (!cc.hasCoding())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__MUST_HAVE_AT_LEAST_ONE_CODING__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    if (cc.hasExtension())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    boolean firstCoding = true;
    for (Coding c : cc.getCoding()) {
      if (c.hasExtension())
        throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
      if (firstCoding) firstCoding = false;
      else expression.append(" and ");
      expression.append(discriminator + ".coding.where(");
      boolean first = true;
      if (c.hasSystem()) {
        first = false;
        expression.append("system = '" + c.getSystem() + "'");
      }
      if (c.hasVersion()) {
        if (first) first = false;
        else expression.append(" and ");
        expression.append("version = '" + c.getVersion() + "'");
      }
      if (c.hasCode()) {
        if (first) first = false;
        else expression.append(" and ");
        expression.append("code = '" + c.getCode() + "'");
      }
      if (c.hasDisplay()) {
        if (first) first = false;
        else expression.append(" and ");
        expression.append("display = '" + c.getDisplay() + "'");
      }
      if (first) {
        throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_NO_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), cc.fhirType()));
      }
      expression.append(").exists()");
    }
  }

  private void buildCodingExpression(ElementDefinition ed, StringBuilder expression, String discriminator, Coding c)
    throws DefinitionException {
    if (c.hasExtension())
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
    expression.append(discriminator + ".where(");
    boolean first = true;
    if (c.hasSystem()) {
      first = false;
      expression.append("system = '" + c.getSystem() + "'");
    }
    if (c.hasVersion()) {
      if (first) first = false;
      else expression.append(" and ");
      expression.append("version = '" + c.getVersion() + "'");
    }
    if (c.hasCode()) {
      if (first) first = false;
      else expression.append(" and ");
      expression.append("code = '" + c.getCode() + "'");
    }
    if (c.hasDisplay()) {
      if (first) first = false;
      else expression.append(" and ");
      expression.append("display = '" + c.getDisplay() + "'");
    }
    if (first) {
      throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_IDENTIFIER_PATTERN_NO_PROPERTY_NOT_SUPPORTED_FOR_DISCRIMINATOR_FOR_SLICE, discriminator, ed.getId(), c.fhirType()));
    }
    expression.append(").exists()");
  }

  private void buildPrimitiveExpression(ElementDefinition ed, StringBuilder expression, String discriminator, DataType p, boolean quotes) throws DefinitionException {
      if (p.hasExtension())
        throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_CODEABLECONCEPT_PATTERN__EXTENSIONS_ARE_NOT_ALLOWED__FOR_DISCRIMINATOR_FOR_SLICE_, discriminator, ed.getId()));
      if (quotes) {        
        expression.append(discriminator + ".where(value = '" + p.primitiveValue() + "'");
      } else {
        expression.append(discriminator + ".where(value = " + p.primitiveValue() + "");
      }
      expression.append(").exists()");
    }

  private void buildFixedExpression(ElementDefinition ed, StringBuilder expression, String discriminator, ElementDefinition criteriaElement) throws DefinitionException {
    DataType fixed = criteriaElement.getFixed();
    if (fixed instanceof CodeableConcept) {
      CodeableConcept cc = (CodeableConcept) fixed;
      expression.append(" and ");
      buildCodeableConceptExpression(ed, expression, discriminator, cc);
    } else if (fixed instanceof Identifier) {
      Identifier ii = (Identifier) fixed;
      expression.append(" and ");
      buildIdentifierExpression(ed, expression, discriminator, ii);
    } else if (fixed instanceof Coding) {
      Coding c = (Coding) fixed;
      expression.append(" and ");
      buildCodingExpression(ed, expression, discriminator, c);
    } else {
      expression.append(" and (");
      if (fixed instanceof StringType) {
        String es = Utilities.escapeJson(fixed.primitiveValue());
        expression.append("'" + es + "'");
      } else if (fixed instanceof UriType) {
        expression.append("'" + ((UriType) fixed).asStringValue() + "'");
      } else if (fixed instanceof IntegerType) {
        expression.append(((IntegerType) fixed).asStringValue());
      } else if (fixed instanceof DecimalType) {
        expression.append(((IntegerType) fixed).asStringValue());
      } else if (fixed instanceof BooleanType) {
        expression.append(((BooleanType) fixed).asStringValue());
      } else
        throw new DefinitionException(context.formatMessage(I18nConstants.UNSUPPORTED_FIXED_VALUE_TYPE_FOR_DISCRIMINATOR_FOR_SLICE__, discriminator, ed.getId(), fixed.getClass().getName()));
      expression.append(" in " + discriminator + ")");
    }
  }

  // checkSpecials = we're only going to run these tests if we are actually validating this content (as opposed to we looked it up)
  private boolean start(ValidationContext valContext, List<ValidationMessage> errors, Element resource, Element element, StructureDefinition defn, NodeStack stack, ResourcePercentageLogger pct, ValidationMode mode, boolean fromContained) throws FHIRException {
    boolean ok = !hasErrors(errors);
    
    checkLang(resource, stack);
    if (crumbTrails) {
      element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST, defn.getVersionedUrl()));
    }
    boolean pctOwned = false;
    if (pct == null) { 
      // this method is reentrant, but also the right place to tell the user what is going on if it's the root. 
      // if we're not at the root, we don't report progress
      pctOwned = true;
      pct = new ResourcePercentageLogger(log, resource.countDescendents(), resource.fhirType(), defn.getVersionedUrl(), logProgress);
    }
    if (BUNDLE.equals(element.fhirType())) {
      log.debug("Resolve Bundle Entries "+time());
      resolveBundleReferences(element, new ArrayList<Element>());
    }
    ok = startInner(valContext, errors, resource, element, defn, stack, valContext.isCheckSpecials(), pct, mode, fromContained) && ok;
    if (pctOwned) {
      pct.done();
    }
 
    if (defn.hasExtension(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
      for (Extension ext : defn.getExtensionsByUrl(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
        StructureDefinition sdi = context.fetchResource(StructureDefinition.class, ext.getValue().primitiveValue());
        if (sdi == null) {
          warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_DEPENDS_NOT_RESOLVED, ext.getValue().primitiveValue(), defn.getVersionedUrl());                
        } else {
          if (crumbTrails) {
            element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST_DEP, sdi.getUrl(), defn.getVersionedUrl()));
          }
          stack.resetIds();
          if (pctOwned) {
            pct = new ResourcePercentageLogger(log, resource.countDescendents(), resource.fhirType(), sdi.getUrl(), logProgress);
          }
          ok = startInner(valContext, errors, resource, element, sdi, stack, false, pct, mode.withSource(ProfileSource.ProfileDependency), fromContained) && ok;
          if (pctOwned) {
            pct.done();
          }
          
        }
      }
    }
  
  
    Element meta = element.getNamedChild(META, false);
    if (meta != null) {
      List<Element> profiles = new ArrayList<Element>();
      meta.getNamedChildren("profile", profiles);
      int i = 0;
      for (Element profile : profiles) {
        StructureDefinition sd = context.fetchResource(StructureDefinition.class, profile.primitiveValue());
        if (!defn.getUrl().equals(profile.primitiveValue())) {
          // is this a version specific reference? 
          VersionURLInfo vu = VersionUtilities.parseVersionUrl(profile.primitiveValue());
          if (vu != null) {
            if (!VersionUtilities.versionsCompatible(vu.getVersion(),  context.getVersion())) {
              hint(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_OTHER_VERSION, vu.getVersion());
            } else if (vu.getUrl().equals(defn.getUrl())) {
              hint(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_THIS_VERSION_OK);              
            } else {
              StructureDefinition sdt = context.fetchResource(StructureDefinition.class, vu.getUrl());
              ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_THIS_VERSION_OTHER, sdt == null ? "null" : sdt.getType()) && ok;                            
            }
          } else {
            if (sd == null) {
              // we'll try fetching it directly from it's source, but this is likely to fail later even if the resolution succeeds
              if (fetcher == null) {
                warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN, profile.primitiveValue());
              } else if (!fetcher.fetchesCanonicalResource(this, profile.primitiveValue())) {
                warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN_NOT_POLICY, profile.primitiveValue());                
              } else {
                sd = lookupProfileReference(valContext, errors, element, stack, i, profile, sd);
              }
            }
            if (sd != null) {
              if (crumbTrails) {
                element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST_META, sd.getVersionedUrl()));
              }
              stack.resetIds();
              if (pctOwned) {
                pct = new ResourcePercentageLogger(log, resource.countDescendents(), resource.fhirType(), sd.getUrl(), logProgress);
              }
              ok = startInner(valContext, errors, resource, element, sd, stack, false, pct, mode.withSource(ProfileSource.MetaProfile), fromContained) && ok;
              if (pctOwned) {
                pct.done();
              }
              if (sd.hasExtension(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
                for (Extension ext : sd.getExtensionsByUrl(ExtensionDefinitions.EXT_SD_IMPOSE_PROFILE)) {
                  StructureDefinition sdi = context.fetchResource(StructureDefinition.class, ext.getValue().primitiveValue());
                  if (sdi == null) {
                    warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_DEPENDS_NOT_RESOLVED, ext.getValue().primitiveValue(), sd.getVersionedUrl());                
                  } else {
                    if (crumbTrails) {
                      element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST_DEP, sdi.getUrl(), sd.getVersionedUrl()));
                    }
                    stack.resetIds();
                    if (pctOwned) {
                      pct = new ResourcePercentageLogger(log, resource.countDescendents(), resource.fhirType(), sdi.getUrl(), logProgress);
                    }
                    ok = startInner(valContext, errors, resource, element, sdi, stack, false, pct, mode.withSource(ProfileSource.ProfileDependency), fromContained) && ok;
                    if (pctOwned) {
                      pct.done();
                    }
                    
                  }
                }
              }
            }
          }
        }
        i++;
      }
    }
    String rt = element.fhirType();
    for (ImplementationGuide ig : igs) {
      for (ImplementationGuideGlobalComponent gl : ig.getGlobal()) {
        if (rt.equals(gl.getType())) {
          StructureDefinition sd = context.fetchResource(StructureDefinition.class, gl.getProfile(), ig);
          if (warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), sd != null, I18nConstants.VALIDATION_VAL_GLOBAL_PROFILE_UNKNOWN, gl.getProfile(), ig.getVersionedUrl())) {
            if (crumbTrails) {
              element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST_GLOBAL, sd.getVersionedUrl(), ig.getVersionedUrl()));
            }
            stack.resetIds();
            if (pctOwned) {
              pct = new ResourcePercentageLogger(log, resource.countDescendents(), resource.fhirType(), sd.getVersionedUrl(), logProgress);
            }
            ok = startInner(valContext, errors, resource, element, sd, stack, false, pct, mode.withSource(ProfileSource.GlobalProfile), fromContained) && ok;
            if (pctOwned) {
              pct.done();
            }
          }
        }
      }
    }
    return ok;
  }

  private StructureDefinition lookupProfileReference(ValidationContext valContext, List<ValidationMessage> errors, Element element, NodeStack stack,
      int i, Element profile, StructureDefinition sd) {
    String url = profile.primitiveValue();
    CanonicalResourceLookupResult cr = crLookups.get(url);
    if (cr != null) {
      if (cr.getError() != null) {
        warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN_ERROR, url, cr.getError());                
      } else {
        sd = (StructureDefinition) cr.getResource();
      }
    } else {
      try {
        sd = (StructureDefinition) fetcher.fetchCanonicalResource(this, valContext.getAppContext(), url);
        crLookups.put(url, new CanonicalResourceLookupResult(sd));
      } catch (Exception e) {
        if (STACK_TRACE) { e.printStackTrace(); }
        crLookups.put(url, new CanonicalResourceLookupResult(e.getMessage()));
        if (e.getMessage() != null && e.getMessage().startsWith("java.net.UnknownHostException:")) {
          try {
            warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN_ERROR_NETWORK, profile.primitiveValue(), new URI(url).getHost());
          } catch (URISyntaxException e1) {
            warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN_ERROR_NETWORK, profile.primitiveValue(), "??");
          }
        } else {
          warning(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath() + ".meta.profile[" + i + "]", false, I18nConstants.VALIDATION_VAL_PROFILE_UNKNOWN_ERROR, profile.primitiveValue(), e.getMessage());
        }
      }
      if (sd != null) {
        context.cacheResource(sd);
      }
    }
    return sd;
  }

  private void resolveBundleReferences(Element element, List<Element> bundles) {
    if (!element.hasUserData(UserDataNames.validator_bundle_resolved)) {
      element.setUserData(UserDataNames.validator_bundle_resolved, true);
      List<Element> list = new ArrayList<Element>();
      list.addAll(bundles);
      list.add(0, element);
      List<Element> entries = element.getChildrenByName(ENTRY);
      for (Element entry : entries) {
        String fu = entry.getChildValue(FULL_URL);
        Element r = entry.getNamedChild(RESOURCE, false);
        if (r != null) {
          resolveBundleReferencesInResource(list, r, fu);
        }
      }
    }
  }

  private void resolveBundleReferencesInResource(List<Element> bundles, Element r, String fu) {
    if (BUNDLE.equals(r.fhirType())) {
      resolveBundleReferences(r, bundles);
    } else {
      for (Element child : r.getChildren()) {
        resolveBundleReferencesForElement(bundles, r, fu, child);
      }
    }
  }

  private void resolveBundleReferencesForElement(List<Element> bundles, Element resource, String fu, Element element) {
    if ("Reference".equals(element.fhirType())) {
      String ref = element.getChildValue("reference");
      if (!Utilities.noString(ref)) {
        for (Element bundle : bundles) {
          List<Element> entries = bundle.getChildren(ENTRY);
          Element tgt = resolveInBundle(bundle, entries, ref, fu, resource.fhirType(), resource.getIdBase(), null, null, null, element, false, false);
          if (tgt != null) {
            element.setUserData(UserDataNames.validator_bundle_resolution, tgt.getNamedChild(RESOURCE, false));
            return;
          }
        }
      }
    } else {
      for (Element child : element.getChildren()) {
        resolveBundleReferencesForElement(bundles, resource, fu, child);
      }
    }
  }

  private static void addMessagesReplaceExistingIfMoreSevere(List<ValidationMessage> errors, List<ValidationMessage> newErrors) {
    for (ValidationMessage newError : newErrors) {
      int index = indexOfMatchingMessageAndLocation(errors, newError);
      if (index == -1) {
        errors.add(newError);
      } else {
        ValidationMessage existingError = errors.get(index);
        if (newError.getLevel().ordinal() < existingError.getLevel().ordinal()) {
          errors.set(index, newError);
        }
      }
    }
  }

  private static int indexOfMatchingMessageAndLocation(List<ValidationMessage> messages, ValidationMessage message) {
    for (int i = 0; i < messages.size(); i++) {
      ValidationMessage iMessage = messages.get(i);
      if (message.getMessage() != null && message.getMessage().equals(iMessage.getMessage())
        && message.getLocation() != null && message.getLocation().equals(iMessage.getLocation())) {
        return i;
      }
    }
    return -1;
  }

  public boolean startInner(ValidationContext valContext, List<ValidationMessage> errors, Element resource, Element element, StructureDefinition defn, NodeStack stack, boolean checkSpecials, ResourcePercentageLogger pct, ValidationMode mode, boolean fromContained) {
    
    
    // the first piece of business is to see if we've validated this resource against this profile before.
    // if we have (*or if we still are*), then we'll just return our existing errors
    boolean ok = true;
    ResourceValidationTracker resTracker = getResourceTracker(element);
    List<ValidationMessage> cachedErrors = resTracker.getOutcomes(defn);
    if (cachedErrors != null) {
      for (ValidationMessage vm : cachedErrors) {
        if (!errors.contains(vm)) {
          errors.add(vm);
          ok = vm.getLevel() != IssueSeverity.ERROR && vm.getLevel() != IssueSeverity.FATAL && ok; 
        }
      }
      return ok;
    }
    if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), defn.hasSnapshot(), I18nConstants.VALIDATION_VAL_PROFILE_NOSNAPSHOT, defn.getVersionedUrl())) {
      List<ValidationMessage> localErrors = new ArrayList<ValidationMessage>();
      resTracker.startValidating(defn);
      trackUsage(defn, valContext, element);
      ok = validateElement(valContext, localErrors, defn, defn.getSnapshot().getElement().get(0), null, null, resource, element, element.getName(), stack, false, true, null, pct, mode) && ok;
      resTracker.storeOutcomes(defn, localErrors);
      addMessagesReplaceExistingIfMoreSevere(errors, localErrors);
    } else {
      ok = false;
    }
    if (checkSpecials) {      
      ok = checkSpecials(valContext, errors, element, stack, checkSpecials, pct, mode, fromContained, ok) && ok;
      ok = validateResourceRules(errors, element, stack) && ok;
    }
    return ok;
  }

  public boolean checkSpecials(ValidationContext valContext, List<ValidationMessage> errors, Element element, NodeStack stack, boolean checkSpecials, ResourcePercentageLogger pct, ValidationMode mode, boolean contained, boolean isOk) {
    boolean ok = true;

    // first, does the policy advisor have profiles it wants us to check? 
    List<StructureDefinition> profiles = policyAdvisor.getImpliedProfilesForResource(this, valContext.getAppContext(), stack.getLiteralPath(), 
        element.getProperty().getDefinition(), element.getProperty().getStructure(), element, isOk, this, errors);
    for (StructureDefinition sd : profiles) {
      ok = startInner(valContext, errors, element, element, sd, stack, false, pct, mode, false) && ok;
    }

    long t = System.nanoTime();
    try {
      if (VersionUtilities.getCanonicalResourceNames(context.getVersion()).contains(element.getType())) {
        Base base = element.getExtensionValue(ExtensionDefinitions.EXT_STANDARDS_STATUS);
        String standardsStatus = base != null && base.isPrimitive() ? base.primitiveValue() : null;
        String status = element.getNamedChildValue("status", false);
        if (!Utilities.noString(status) && !Utilities.noString(standardsStatus)) {
          if (warning(errors, "2023-08-14", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), statusCodesConsistent(status, standardsStatus), I18nConstants.VALIDATION_VAL_STATUS_INCONSISTENT, status, standardsStatus)) {
            hint(errors, "2023-08-14", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), statusCodesDeeplyConsistent(status, standardsStatus), I18nConstants.VALIDATION_VAL_STATUS_INCONSISTENT_HINT, status, standardsStatus);          
          }
        }
        if (element.getNamedChildValue("description") != null && !"deprecated".equals(standardsStatus)) {
          hint(errors, "2023-08-14", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), !element.getNamedChildValue("description").toLowerCase().contains("deprecated"), "Resource is not deprecated, but the description mentions deprecated - check whether it should be deprecated");
        }
        if (noExperimentalContent) {
          String exp = element.getNamedChildValue("experimental");
          ok = rule(errors, "2024-09-17", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), !"true".equals(exp), I18nConstants.VALIDATION_NO_EXPERIMENTAL_CONTENT) && ok;
        }
        
        if (isHL7Org(element) && !isExample()) {
          ok = checkPublisherConsistency(valContext, errors, element, stack, contained) && ok;  
        }
        String version = element.getNamedChildValue("version");
        if (!Utilities.noString(version)) {
          warning(errors, "2024-10-18", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), !version.contains("#"), I18nConstants.VALIDATION_VAL_VERSION_NOHASH, version);
        }        
      }
      if (element.getType().equals(BUNDLE)) {
        return new BundleValidator(this, serverBase).validateBundle(errors, element, stack, checkSpecials, valContext, pct, mode) && ok;
      } else if (element.getType().equals("Observation")) {
        return new ObservationValidator(this).validateObservation(valContext, errors, element, stack, pct, mode) && ok;
      } else if (element.getType().equals("Questionnaire")) {
        return new QuestionnaireValidator(this, myEnableWhenEvaluator, fpe, questionnaireMode).validateQuestionannaire(errors, element, element, stack) && ok;
      } else if (element.getType().equals("QuestionnaireResponse")) {
        return new QuestionnaireValidator(this, myEnableWhenEvaluator, fpe, questionnaireMode).validateQuestionannaireResponse(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("Measure")) {
        return new MeasureValidator(this).validateMeasure(valContext, errors, element, stack) && ok;      
      } else if (element.getType().equals("MeasureReport")) {
        return new MeasureValidator(this).validateMeasureReport(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("CapabilityStatement")) {
        return validateCapabilityStatement(errors, element, stack) && ok;
      } else if (element.getType().equals("CodeSystem")) {
        return new CodeSystemValidator(this).validateCodeSystem(valContext, errors, element, stack, settings.withLanguage(stack.getWorkingLang())) && ok;
      } else if (element.getType().equals("ConceptMap")) {
        return new ConceptMapValidator(this).validateConceptMap(valContext, errors, element, stack, settings.withLanguage(stack.getWorkingLang())) && ok;
      } else if (element.getType().equals("OperationDefinition")) {
        return new OperationDefinitionValidator(this, fpe).validateOperationDefinition(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("SearchParameter")) {
        return new SearchParameterValidator(this, fpe).validateSearchParameter(errors, element, stack) && ok;
      } else if (element.getType().equals("StructureDefinition")) {
        return new StructureDefinitionValidator(this, fpe, wantCheckSnapshotUnchanged).validateStructureDefinition(errors, element, stack) && ok;
      } else if (element.getType().equals("StructureMap")) {
        return new StructureMapValidator(this, fpe, profileUtilities).validateStructureMap(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("ValueSet")) {
        return new ValueSetValidator(this).validateValueSet(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("ViewDefinition")) {
        return new ViewDefinitionValidator(this).validateViewDefinition(valContext, errors, element, stack) && ok;
      } else if (element.getType().equals("ImplementationGuide")) {
        return new ImplementationGuideValidator(this).validateImplementationGuide(valContext, errors, element, stack) && ok;
      } else if ("http://hl7.org/fhir/uv/sql-on-fhir/StructureDefinition/ViewDefinition".equals(element.getProperty().getStructure().getUrl())) {
        if (element.getNativeObject() != null && element.getNativeObject() instanceof JsonObject) {
          JsonObject json = (JsonObject) element.getNativeObject();
          Validator sqlv = new Validator(context, fpe, new ArrayList<>(), TrueFalseOrUnknown.UNKNOWN, TrueFalseOrUnknown.UNKNOWN, TrueFalseOrUnknown.UNKNOWN);
          sqlv.checkViewDefinition(stack.getLiteralPath(), json);
          errors.addAll(sqlv.getIssues());
          ok = sqlv.isOk() && ok;
        } 
        return ok;        
      } else {
        return ok;
      }
    } finally {
      timeTracker.spec(t);
    }
  }

  private boolean checkPublisherConsistency(ValidationContext valContext, List<ValidationMessage> errors, Element element, NodeStack stack, boolean contained) {

    boolean ok = true;
    String pub = element.getNamedChildValue("publisher", false);
    
    ok = rule(errors, "2024-08-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), element.getExtensions(ExtensionDefinitions.EXT_WORKGROUP).size() <= 1, I18nConstants.VALIDATION_HL7_PUBLISHER_MULTIPLE_WGS) && ok;
    Base wgT = element.getExtensionValue(ExtensionDefinitions.EXT_WORKGROUP);
    String wg = wgT == null ? null : wgT.primitiveValue();
    String url = element.getNamedChildValue("url");

    if (contained && wg == null) {
      Element container = valContext.getRootResource();
      if (element.hasExtension(ExtensionDefinitions.EXT_WORKGROUP)) {
        // container already specified the HL7 WG, so we don't need to test
        // but we're still going to test pub if it exists
        if (pub != null) {
          wgT = container.getExtensionValue(ExtensionDefinitions.EXT_WORKGROUP);
          wg = wgT == null ? null : wgT.primitiveValue();
          HL7WorkGroup wgd = HL7WorkGroups.find(wg);
          if (wgd != null) {
            String rpub = "HL7 International / "+wgd.getName();
            ok = rpub.equals(pub);
            if (!ok && wgd.getName2() != null) {
              ok = ("HL7 International / "+wgd.getName2()).equals(pub);
              warningOrError(pub.contains("/"), errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), ok, I18nConstants.VALIDATION_HL7_PUBLISHER_MISMATCH2, wg, rpub, "HL7 International / "+wgd.getName2(), pub);
            } else {
              warningOrError(pub.contains("/"), errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), ok, I18nConstants.VALIDATION_HL7_PUBLISHER_MISMATCH, wg, rpub, pub);
            }
          }          
        }
        return ok;
      }
    }

    List<String> urls = new ArrayList<>();
    for (Element c : element.getChildren("contact")) {      
      for (Element t : c.getChildren("telecom")) {
        if ("url".equals(t.getNamedChildValue("system", false)) && t.getNamedChildValue("value", false) != null) {
          urls.add(t.getNamedChildValue("value", false));
        }
      }
    }

    if (rule(errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), wg != null || url.contains("http://hl7.org/fhir/sid") || !settings.isForPublication(), I18nConstants.VALIDATION_HL7_WG_NEEDED, ExtensionDefinitions.EXT_WORKGROUP)) {
      if (wg != null) {
        HL7WorkGroup wgd = HL7WorkGroups.find(wg);      
        if (rule(errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), wgd != null, I18nConstants.VALIDATION_HL7_WG_UNKNOWN, wg)) {
          String rpub = "HL7 International / "+wgd.getName();
          if (warning(errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), pub != null, I18nConstants.VALIDATION_HL7_PUBLISHER_MISSING, wg, rpub)) {
            ok = rpub.equals(pub) && ok;
            if (!ok && wgd.getName2() != null) {
              ok = ("HL7 International / "+wgd.getName2()).equals(pub) && ok;
              warningOrError(pub.contains("/"), errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), ok, I18nConstants.VALIDATION_HL7_PUBLISHER_MISMATCH2, wg, rpub, "HL7 International / "+wgd.getName2(), pub);
            } else {
              warningOrError(pub.contains("/"), errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), ok, I18nConstants.VALIDATION_HL7_PUBLISHER_MISMATCH, wg, rpub, pub);
            }
          }
          warning(errors, "2023-09-15", IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), 
              Utilities.startsWithInList( wgd.getLink(), urls), I18nConstants.VALIDATION_HL7_WG_URL, wg, wgd.getLink());
          return ok;
        }      
      } else {
        return ok; // HL7 sid.
      }
    }
    return ok;
  }

  private boolean statusCodesConsistent(String status, String standardsStatus) {
    switch (standardsStatus) {
    case "draft": return Utilities.existsInList(status, "draft");
    case "normative": return Utilities.existsInList(status, "active");
    case "trial-use": return Utilities.existsInList(status, "draft", "active");
    case "informative": return Utilities.existsInList(status, "draft", "active", "retired");
    case "deprecated": return Utilities.existsInList(status, "retired");
    case "withdrawn": return Utilities.existsInList(status, "retired");
    case "external": return Utilities.existsInList(status, "draft", "active", "retired");
    }
    return true;
  }

  private boolean statusCodesDeeplyConsistent(String status, String standardsStatus) {
    switch (standardsStatus) {
    case "draft": return Utilities.existsInList(status, "draft");
    case "normative": return Utilities.existsInList(status, "active");
    case "trial-use": return Utilities.existsInList(status, "active");
    case "informative": return Utilities.existsInList(status, "draft", "active");
    case "deprecated": return Utilities.existsInList(status, "retired");
    case "withdrawn": return Utilities.existsInList(status, "retired");
    case "external": return Utilities.existsInList(status, "draft", "active");
    }
    return true;
  }

  private ResourceValidationTracker getResourceTracker(Element element) {
    ResourceValidationTracker res = resourceTracker.get(element);
    if (res == null) {
      res = new ResourceValidationTracker();
      resourceTracker.put(element, res);
    }
    return res;
  }

  private void checkLang(Element resource, NodeStack stack) {
    String lang = resource.getNamedChildValue("language", false);
    if (!Utilities.noString(lang))
      stack.setWorkingLang(lang);
  }

  private boolean validateResourceRules(List<ValidationMessage> errors, Element element, NodeStack stack) {
    boolean ok= true;
    String lang = element.getNamedChildValue("language", false);
    Element text = element.getNamedChild("text", false);
    if (text != null) {
      Element div = text.getNamedChild("div", false);
      if (lang != null && div != null) {
        XhtmlNode xhtml = div.getXhtml();
        if (xhtml != null) {
          String l = xhtml.getAttribute("lang");
          String xl = xhtml.getAttribute("xml:lang");
          if (l == null && xl == null && allChildrenAreLangDivs(xhtml)) {
            XhtmlNode ld = firstLangDiv(xhtml);
            l = ld.getAttribute("lang");
            xl = ld.getAttribute("xml:lang");
          }
          if (l == null && xl == null) {
            warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, div.line(), div.col(), stack.getLiteralPath(), false, I18nConstants.LANGUAGE_XHTML_LANG_MISSING1);
          } else {
            if (l == null) {
              warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, div.line(), div.col(), stack.getLiteralPath(), false, I18nConstants.LANGUAGE_XHTML_LANG_MISSING2);
            } else if (!l.equals(lang)) {
              warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, div.line(), div.col(), stack.getLiteralPath(), false, I18nConstants.LANGUAGE_XHTML_LANG_DIFFERENT1, lang, l);
            }
            if (xl == null) {
              warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, div.line(), div.col(), stack.getLiteralPath(), false, I18nConstants.LANGUAGE_XHTML_LANG_MISSING3);
            } else if (!xl.equals(lang)) {
              warning(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, div.line(), div.col(), stack.getLiteralPath(), false, I18nConstants.LANGUAGE_XHTML_LANG_DIFFERENT2, lang, xl);
            }
          }
        }
      }
    }
    // security tags are a set (system|code)
    Element meta = element.getNamedChild(META, false);
    if (meta != null) {
      Set<String> tags = new HashSet<>();
      List<Element> list = new ArrayList<>();
      meta.getNamedChildren("security", list);
      int i = 0;
      for (Element e : list) {
        String s = e.getNamedChildValue("system", false) + "#" + e.getNamedChildValue("code", false);
        ok = rule(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, e.line(), e.col(), stack.getLiteralPath() + ".meta.profile[" + Integer.toString(i) + "]", !tags.contains(s), I18nConstants.META_RES_SECURITY_DUPLICATE, s) && ok;
        tags.add(s);
        i++;
      }
    }
    return ok;
  }

  private XhtmlNode firstLangDiv(XhtmlNode xhtml) {
    for (XhtmlNode n : xhtml.getChildNodes()) {
      if (XhtmlValidator.isLangDiv(n)) {
        return n;
      }
    }
    return null;
  }

  private boolean allChildrenAreLangDivs(XhtmlNode xhtml) {
    for (XhtmlNode n : xhtml.getChildNodes()) {
      if (!XhtmlValidator.isLangDiv(n) && n.hasContent()) {
        return false;
      }
    }
    return xhtml.getChildNodes().size() > 0;
  }
  private boolean validateCapabilityStatement(List<ValidationMessage> errors, Element cs, NodeStack stack) {
    boolean ok = true;
    int iRest = 0;
    for (Element rest : cs.getChildrenByName("rest")) {
      int iResource = 0;
      for (Element resource : rest.getChildrenByName(RESOURCE)) {
        int iSP = 0;
        for (Element searchParam : resource.getChildrenByName("searchParam")) {
          String ref = searchParam.getChildValue("definition");
          String type = searchParam.getChildValue(TYPE);
          if (!Utilities.noString(ref)) {
            SearchParameter sp = context.fetchResource(SearchParameter.class, ref);
            if (sp != null) {
              ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, searchParam.line(), searchParam.col(), stack.getLiteralPath() + ".rest[" + iRest + "].resource[" + iResource + "].searchParam[" + iSP + "]",
                sp.getType().toCode().equals(type), I18nConstants.CAPABALITYSTATEMENT_CS_SP_WRONGTYPE, sp.getVersionedUrl(), sp.getType().toCode(), type) && ok;
            }
          }
          iSP++;
        }
        iResource++;
      }
      iRest++;
    }
    return ok;
  }
 
  private boolean validateContains(ValidationContext valContext, List<ValidationMessage> errors, String path,
                                   ElementDefinition child, ElementDefinition context, Element resource,
                                   Element element, NodeStack stack, IdStatus idstatus, StructureDefinition parentProfile, ResourcePercentageLogger pct, ValidationMode mode) throws FHIRException {
    boolean ok = true;

    if (element.isNull()) {
      if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), ExtensionUtilities.readBooleanExtension(child, ExtensionDefinitions.EXT_JSON_NULLABLE),
          I18nConstants.ELEMENT_CANNOT_BE_NULL)) {
        // nothing else to validate?
      } else {
        ok = false;
      }

    } else {
      SpecialElement special = element.getSpecial();

      ContainedReferenceValidationPolicy containedValidationPolicy = policyAdvisor.policyForContained(this,
              valContext, parentProfile, child, context.fhirType(), context.getId(), special, path, parentProfile.getUrl());

      if (containedValidationPolicy.ignore()) {
        return ok;
      }

      String resourceName = element.getType();
      TypeRefComponent typeForResource = null;
      CommaSeparatedStringBuilder bt = new CommaSeparatedStringBuilder();

      // Iterate through all possible types
      for (TypeRefComponent type : child.getType()) {
        bt.append(type.getCode());
        if (type.getCode().equals("Resource") || type.getCode().equals(resourceName) ) {
          typeForResource = type;
          break;
        }
      }

      stack.pathComment(resourceName+"/"+element.getIdBase());

      if (typeForResource == null) {
        ok = rule(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(),
            false, I18nConstants.BUNDLE_BUNDLE_ENTRY_TYPE, resourceName, bt.toString()) && ok;
      } else if (isValidResourceType(resourceName, typeForResource)) {
        if (containedValidationPolicy.checkValid()) {
          // special case: resource wrapper is reset if we're crossing a bundle boundary, but not otherwise
          ValidationContext hc = null;
          if (special == SpecialElement.BUNDLE_ENTRY || special == SpecialElement.BUNDLE_OUTCOME || special == SpecialElement.BUNDLE_ISSUES || special == SpecialElement.PARAMETER) {
            resource = element;
            assert Utilities.existsInList(valContext.getResource().fhirType(), "Bundle", "Parameters") : "Containing Resource is "+valContext.getResource().fhirType()+", expected Bundle or Parameters at "+stack.getLiteralPath();
            hc = valContext.forEntry(element, valContext.getResource()); // root becomes the grouping resource (should be either bundle or parameters)
          } else {
            hc = valContext.forContained(element);
          }

          if (special == SpecialElement.CONTAINED) {
            if (!session.getSessionId().equals(element.getUserData(UserDataNames.validator_contained_Id))) {
              element.setUserData(UserDataNames.validator_contained_Id, session.getSessionId());
              String id = element.getNamedChildValue("id");
              if (id == null) {
                // this is an error handled elsewhere
              } else {
                if (stack.getIds().containsKey("!"+id)) {
                  ok = rule(errors, "2025-01-28", IssueType.DUPLICATE, element.line(), element.col(), stack.getLiteralPath(),
                      false, I18nConstants.RESOURCE_DUPLICATE_CONTAINED_ID, id) && ok;
                }
                stack.getIds().put("!"+id, element);
              }
            }
          }
          stack.resetIds();
          if (special != null) {
            switch (special) {
            case BUNDLE_ENTRY:
            case BUNDLE_OUTCOME:
            case PARAMETER:
              idstatus = IdStatus.OPTIONAL;
              break;
            case CONTAINED:
              stack.setContained(true);
              idstatus = IdStatus.REQUIRED;
              break;
            default:
              break;
            }
          }
          
          checkSpecials(valContext, errors, element, stack, ok, pct, mode, true, ok);

          if (typeForResource.getProfile().size() == 1) {
            long t = System.nanoTime();
            StructureDefinition profile = this.context.fetchResource(StructureDefinition.class, typeForResource.getProfile().get(0).asStringValue(), parentProfile);
            timeTracker.sd(t);
            if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(),
                profile != null, I18nConstants.BUNDLE_BUNDLE_ENTRY_NOPROFILE_EXPL, special == null ? "??" : special.toHuman(), resourceName, typeForResource.getProfile().get(0).asStringValue())) {
              trackUsage(profile, valContext, element);
              ok = validateResource(hc, errors, resource, element, profile, idstatus, stack, pct, mode, false, special == SpecialElement.CONTAINED) && ok;
            } else {
              ok = false;
            }
          } else if (typeForResource.getProfile().isEmpty()) {
            long t = System.nanoTime();
            StructureDefinition profile = this.context.fetchResource(StructureDefinition.class,
                "http://hl7.org/fhir/StructureDefinition/" + resourceName);
            timeTracker.sd(t);
            trackUsage(profile, valContext, element);
            if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(),
                profile != null, I18nConstants.BUNDLE_BUNDLE_ENTRY_NOPROFILE_TYPE, special == null ? "??" : special.toHuman(), resourceName)) {
              ok = validateResource(hc, errors, resource, element, profile, idstatus, stack, pct, mode, false, special == SpecialElement.CONTAINED) && ok;
            } else {
              ok = false;
            }
          } else {
            // it has to conform to one of them
            // iterate them in order, if none of them are valid, then report errors 
            CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
            CommaSeparatedStringBuilder bm = new CommaSeparatedStringBuilder();
            List<List<ValidationMessage>> errorsList = new ArrayList<>();
            int matched = 0;
            for (CanonicalType u : typeForResource.getProfile()) {              
              b.append(u.asStringValue());
              long t = System.nanoTime();
              StructureDefinition profile = this.context.fetchResource(StructureDefinition.class, u.asStringValue());
              timeTracker.sd(t);
              if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(),
                  profile != null, I18nConstants.BUNDLE_BUNDLE_ENTRY_NOPROFILE_TYPE, special == null ? "??" : special.toHuman(), u.asStringValue())) {
                trackUsage(profile, valContext, element);
                List<ValidationMessage> perrors = new ArrayList<>();
                errorsList.add(perrors);
                if (validateResource(hc, perrors, resource, element, profile, idstatus, stack, pct, mode, false, special == SpecialElement.CONTAINED)) {
                  bm.append(u.asStringValue());
                  matched++;
                } else {
                  CommaSeparatedStringBuilder bb = new CommaSeparatedStringBuilder();
                  int errorCount = 0;
                  for (ValidationMessage vm : perrors) {
                    bb.append(vm.summary());
                    if (vm.isError()) {
                      errorCount++;
                    }
                  }
                  if (errorCount == 0) {
                     throw new Error("failed to validate, but no errors. profile = " + profile.getVersionedUrl() + ", issues = " + bb.toString());
                  }
                }
              } else {
                ok = false;
              }
            }
            if (rule(errors, "2023-09-07", IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(),
                matched > 0, I18nConstants.BUNDLE_BUNDLE_ENTRY_MULTIPLE_PROFILES_NO_MATCH, "", special.toHuman(), typeForResource.getCode(), b.toString())) {
              hint(errors, "2023-09-07", IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), matched == 1, I18nConstants.BUNDLE_BUNDLE_ENTRY_MULTIPLE_PROFILES_MULTIPLE_MATCHES, "", special.toHuman(), typeForResource.getCode(), bm.toString());
            } else {
              ok = false;
              for (int i = 0; i < typeForResource.getProfile().size(); i++) {
                hint(errors, "2023-09-07", IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(),
                    matched > 0, I18nConstants.BUNDLE_BUNDLE_ENTRY_MULTIPLE_PROFILES_NO_MATCH_REASON, "", special.toHuman(), typeForResource.getProfile().get(i).asStringValue(), summariseErrors(errorsList.get(i)));                
              }
            }
          }
        }
      } else {
        List<String> types = new ArrayList<>();
        for (UriType u : typeForResource.getProfile()) {
          StructureDefinition sd = this.context.fetchResource(StructureDefinition.class, u.getValue(), parentProfile);
          if (sd != null && !types.contains(sd.getType())) {
            types.add(sd.getType());
          }
        }
        if (types.size() == 1) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(),
              false, I18nConstants.BUNDLE_BUNDLE_ENTRY_TYPE2, resourceName, types.get(0)) && ok;
          
        } else {
          ok = rulePlural(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(),
              false, types.size(), I18nConstants.BUNDLE_BUNDLE_ENTRY_TYPE3, resourceName, types) && ok;
        }
      }
    }
    return ok;
  }

  private String summariseErrors(List<ValidationMessage> list) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder(", ", " and ");
    for (ValidationMessage vm : list ) {
      if (vm.isError()) {
        b.append(vm.getMessage());
      }
    }
    return b.toString();
  }

  private boolean isValidResourceType(String type, TypeRefComponent def) {
    if (!def.hasProfile() && def.getCode().equals("Resource")) {
      return true;
    }
    if (def.getCode().equals(type)) {
      return true;
    }
    List<StructureDefinition> list = new ArrayList<>();
    for (UriType u : def.getProfile()) {
      StructureDefinition sdt = context.fetchResource(StructureDefinition.class, u.getValue());
      if (sdt != null) {
        list.add(sdt);
      }
    }

    StructureDefinition sdt = context.fetchTypeDefinition(type);
    while (sdt != null) {
      if (def.getWorkingCode().equals("Resource")) {
        for (StructureDefinition sd : list) {
          if (sd.getUrl().equals(sdt.getUrl())) {
            return true;
          }
          if (sd.getType().equals(sdt.getType())) {
            return true;
          }
        }
      }
      sdt = context.fetchResource(StructureDefinition.class, sdt.getBaseDefinition(), sdt);
    }
    return false;
  }


  private boolean validateElement(ValidationContext valContext, List<ValidationMessage> errors, StructureDefinition profile, ElementDefinition definition, StructureDefinition cprofile, ElementDefinition context,
                                  Element resource, Element element, String actualType, NodeStack stack, boolean inCodeableConcept, boolean checkDisplayInContext, String extensionUrl, ResourcePercentageLogger pct, ValidationMode mode) throws FHIRException {
    boolean ok = true;
    
    pct.seeElement(element);
    
    String id = element.getChildValue("id");
    if (!Utilities.noString(id)) {
      if (stack.getIds().containsKey(id) && stack.getIds().get(id) != element) {
        ok = rule(errors, NO_RULE_DATE, IssueType.BUSINESSRULE, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.DUPLICATE_ID, id) && ok;
      }
      if (!stack.isResetPoint()) {
        stack.getIds().put(id, element);
      }
    }
    if (definition.getPath().equals("StructureDefinition.snapshot")) {
      // work around a known issue in the spec, that ids are duplicated in snapshot and differential 
      stack.resetIds();
    }
    ValidationInfo vi = element.addDefinition(profile, definition, mode);
    
    if (definition.getFixed() != null) {
      ok = checkFixedValue(errors, stack.getLiteralPath(), element, definition.getFixed(), profile.getVersionedUrl(), definition.getSliceName(), null, false, profile.getVersionedUrl()+"#"+definition.getId()) && ok;
    } 
    if (definition.getPattern() != null) {
      ok = checkFixedValue(errors, stack.getLiteralPath(), element, definition.getPattern(), profile.getVersionedUrl(), definition.getSliceName(), null, true, profile.getVersionedUrl()+"#"+definition.getId()) && ok;
    }
    
    // get the list of direct defined children, including slices
    SourcedChildDefinitions childDefinitions = profileUtilities.getChildMap(profile, definition, false);
    if (childDefinitions.getList().isEmpty()) {
      if (actualType == null) {
        vi.setValid(false);
        return false; // there'll be an error elsewhere in this case, and we're going to stop.
      }
      childDefinitions = getActualTypeChildren(valContext, element, actualType);
    } else if (definition.getType().size() > 1) {
      // this only happens when the profile constrains the abstract children but leaves the choice open.
      if (actualType == null) {
        vi.setValid(false);
        return false; // there'll be an error elsewhere in this case, and we're going to stop.
      }
      SourcedChildDefinitions typeChildDefinitions = getActualTypeChildren(valContext, element, actualType);
      // what were going to do is merge them - the type is not allowed to constrain things that the child definitions already do (well, if it does, it'll be ignored)
      childDefinitions = mergeChildLists(childDefinitions, typeChildDefinitions, definition.getPath(), actualType);
    }

    List<ElementInfo> children = listChildren(element, stack);
    BooleanHolder bh = new BooleanHolder();
    List<String> problematicPaths = assignChildren(valContext, errors, profile, element, resource, stack, childDefinitions, children, bh);
    ok = bh.ok() && ok;
    for (ElementInfo ei : children) {
      ei.getElement().addSliceDefinition(profile, ei.getDefinition(), ei.getSlice());
    }

    ok = checkCardinalities(errors, profile, element, stack, childDefinitions, children, problematicPaths) && ok;
    // 4. check order if any slices are ordered. (todo)
        
    // 5. inspect each child for validity
    for (ElementInfo ei : children) {
      ok = checkChild(valContext, errors, profile, definition, resource, element, actualType, stack, inCodeableConcept, checkDisplayInContext, ei, extensionUrl, pct, mode) && ok;
    }

    // check type invariants (after we've sliced the children)
    ok = checkInvariants(valContext, errors, profile, definition, resource, element, stack, false) & ok;
    
    vi.setValid(ok);    

    if (!definition.getPath().contains(".") && profile.hasExtension(ExtensionDefinitions.EXT_PROFILE_STYLE) && "cda".equals(ExtensionUtilities.readStringExtension(profile, ExtensionDefinitions.EXT_PROFILE_STYLE))) {
      List<Element> templates = element.getChildren("templateId");
      for (Element t : templates) {
        String tid = t.hasChild("extension", false) ? "urn:hl7ii:"+t.getChildValue("root")+ ":"+t.getChildValue("extension") : "urn:oid:"+t.getChildValue("root");
        StructureDefinition sd = cu.fetchProfileByIdentifier(tid);
        if (sd == null) {
          hint(errors, "2023-10-20", IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), false, t.hasChild("extension", false) ? I18nConstants.CDA_UNKNOWN_TEMPLATE_EXT : I18nConstants.CDA_UNKNOWN_TEMPLATE, t.getChildValue("root"), t.getChildValue("extension"));
        } else {
          ElementDefinition ed = sd.getSnapshot().getElementFirstRep();
          if (!element.hasValidated(sd, ed)) {
            element.addMessage(signpost(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, element.line(), element.col(), stack.getLiteralPath(), I18nConstants.VALIDATION_VAL_PROFILE_SIGNPOST, sd.getVersionedUrl()));
            ok = validateElement(valContext, errors, sd, ed, null, null, resource, element, actualType, stack, inCodeableConcept, checkDisplayInContext, extensionUrl, pct, mode) && ok;
          }
        }
      }
    }      
    
    return ok;
  }

  private SourcedChildDefinitions mergeChildLists(SourcedChildDefinitions source, SourcedChildDefinitions additional, String masterPath, String typePath) {
    SourcedChildDefinitions res = new SourcedChildDefinitions(additional.getSource(), new ArrayList<>());
    res.getList().addAll(source.getList());
    for (ElementDefinition ed : additional.getList()) {
      boolean inMaster = false;
      for (ElementDefinition t : source.getList()) {
        String tp = masterPath + ed.getPath().substring(typePath.length());
        if (t.getPath().equals(tp)) {
          inMaster = true;
        }
      }
      if (!inMaster) {
        res.getList().add(ed);
      }
    }
    return res;
  }

  // todo: the element definition in context might assign a constrained profile for the type?
  public SourcedChildDefinitions getActualTypeChildren(ValidationContext valContext, Element element, String actualType) {
    SourcedChildDefinitions childDefinitions;
    StructureDefinition dt = null;
    if (isAbsolute(actualType))
      dt = this.context.fetchResource(StructureDefinition.class, actualType);
    else
      dt = this.context.fetchResource(StructureDefinition.class, "http://hl7.org/fhir/StructureDefinition/" + actualType);
    if (dt == null)
      throw new DefinitionException(context.formatMessage(I18nConstants.UNABLE_TO_RESOLVE_ACTUAL_TYPE_, actualType));
    trackUsage(dt, valContext, element);

    childDefinitions = profileUtilities.getChildMap(dt, dt.getSnapshot().getElement().get(0), false);
    return childDefinitions;
  }

  public boolean checkChild(ValidationContext valContext, List<ValidationMessage> errors, StructureDefinition profile, ElementDefinition definition,
                            Element resource, Element element, String actualType, NodeStack stack, boolean inCodeableConcept, boolean checkDisplayInContext, ElementInfo ei, String extensionUrl, ResourcePercentageLogger pct, ValidationMode mode)
    throws FHIRException, DefinitionException {
    boolean ok = true;

    if (ei.getDefinition() != null && ei.getSlice() != null) {
      log.debug(Utilities.padLeft("", ' ', stack.depth())+ "Check "+ei.getPath()+" against both "+ei.getDefinition().getId()+" and "+ei.getSlice().getId());
    }
    if (ei.getDefinition() != null) {
      log.debug(Utilities.padLeft("", ' ', stack.depth())+ "Check "+ei.getPath()+" against defn "+ei.getDefinition().getId()+" from "+profile.getVersionedUrl()+time());
      ok = checkChildByDefinition(valContext, errors, profile, definition, resource, element, actualType, stack, inCodeableConcept, checkDisplayInContext, ei, extensionUrl, ei.getDefinition(), false, pct, mode) && ok;
    }
    if (ei.getSlice() != null) {

      log.debug(Utilities.padLeft("", ' ', stack.depth())+ "Check "+ei.getPath()+" against slice "+ei.getSlice().getId()+time());

      ok = checkChildByDefinition(valContext, errors, profile, definition, resource, element, actualType, stack, inCodeableConcept, checkDisplayInContext, ei, extensionUrl, ei.getSlice(), true, pct, mode) && ok;
    }
    return ok;
  }

  private String time() {
    long t = System.currentTimeMillis();
    String s = " "+(t - start)+"ms";
    start = t;
    return s; 
  }

  public boolean checkChildByDefinition(ValidationContext valContext, List<ValidationMessage> errors, StructureDefinition profile,
                                        ElementDefinition definition, Element resource, Element element, String actualType, NodeStack stack, boolean inCodeableConcept,
                                        boolean checkDisplayInContext, ElementInfo ei, String extensionUrl, ElementDefinition checkDefn, boolean isSlice, ResourcePercentageLogger pct, ValidationMode mode) {
    boolean ok = true;
    List<String> profiles = new ArrayList<String>();
    String type = null;
    String typeName = null;
    ElementDefinition typeDefn = null;
    checkMustSupport(profile, ei);
    long s = System.currentTimeMillis();

    boolean hasType = checkDefn.getType().size() > 0;
    boolean isAbstract = hasType && Utilities.existsInList(checkDefn.getType().get(0).getWorkingCode(), "Element", "BackboneElement");
    boolean isChoice = checkDefn.getType().size() > 1 || (hasType && "*".equals(checkDefn.getType().get(0).getWorkingCode()));
    boolean isCDAChoice = profile.getUrl().startsWith(Constants.NS_CDA_ROOT) && ei.getElement().getExplicitType() != null;
    
    if (hasType && !isChoice && !isAbstract && !isCDAChoice) {
      type = checkDefn.getType().get(0).getWorkingCode();
      typeName = type;
      if (Utilities.isAbsoluteUrl(type)) {
        StructureDefinition sdt = context.fetchTypeDefinition(type);
        if (sdt != null) {
          typeName = sdt.getTypeName();
        }
      }
      String stype = ei.getElement().fhirType();
      if (stype == null || !stype.equals(type)) {
        if (checkDefn.isChoice()) {
          if (extensionUrl != null && !isAbsolute(extensionUrl)) {
            ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), ei.getPath(), false, I18nConstants.EXTENSION_PROF_TYPE, profile.getVersionedUrl(), type, stype) && ok;
          } else if (!isAbstractType(type) && !"Extension".equals(profile.getType())) {
            ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), ei.getPath(), stype.equals(typeName), I18nConstants.EXTENSION_PROF_TYPE, profile.getVersionedUrl(), type, stype) && ok;                  
         }
        } else if (!isAbstractType(type)) {
          ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), ei.getPath(), stype.equals(typeName) || 
            (Utilities.existsInList(type, "string", "id") && Utilities.existsInList(stype, "string", "id")), // work around a r4 problem with id/string
            I18nConstants.EXTENSION_PROF_TYPE, profile.getVersionedUrl(), type, stype) && ok;
        } else if (!isResource(type)) {
          type = stype;
          typeName = type;
        } else {
          // this will be sorted out in contains ...
        }
      }

      // Excluding reference is a kludge to get around versioning issues
      if (checkDefn.getType().get(0).hasProfile()) {
        for (CanonicalType p : checkDefn.getType().get(0).getProfile()) {
          profiles.add(p.getValue());
        }
      }
    } else if (checkDefn.getType().size() == 1 && "*".equals(checkDefn.getType().get(0).getWorkingCode())) {
      String prefix = tail(checkDefn.getPath());
      assert prefix.endsWith("[x]");
      type = ei.getName().substring(prefix.length() - 3);
      typeName = type;
      if (isPrimitiveType(type)) {
        type = Utilities.uncapitalize(type);
        typeName = type;
      }
      if (checkDefn.getType().get(0).hasProfile()) {
        for (CanonicalType p : checkDefn.getType().get(0).getProfile()) {
          profiles.add(p.getValue());
        }
      }
    } else if (checkDefn.getType().size() > 1 || isCDAChoice) {

      String prefix = tail(checkDefn.getPath());
      assert typesAreAllReference(checkDefn.getType()) || isCDAChoice|| checkDefn.hasRepresentation(PropertyRepresentation.TYPEATTR) || prefix.endsWith("[x]") || isResourceAndTypes(checkDefn) : "Multiple Types allowed, but name is wrong @ "+checkDefn.getPath()+": "+checkDefn.typeSummaryVB();

      if (isCDAChoice) {
        type = ei.getElement().getExplicitType();
        typeName = type;
      } else if (checkDefn.hasRepresentation(PropertyRepresentation.TYPEATTR)) {
        type = ei.getElement().getType();
        typeName = type;
      } else if (ei.getElement().isResource()) {
        type = ei.getElement().fhirType();
        typeName = type;            
      } else {
        prefix = prefix.substring(0, prefix.length() - 3);
        for (TypeRefComponent t : checkDefn.getType())
          if ((prefix + Utilities.capitalize(t.getWorkingCode())).equals(ei.getName())) {
            type = t.getWorkingCode();
            typeName = type;
            // Excluding reference is a kludge to get around versioning issues
            if (t.hasProfile() && !type.equals("Reference"))
              profiles.add(t.getProfile().get(0).getValue());
          }
      }
      if (type == null) {
        TypeRefComponent trc = checkDefn.getType().get(0);
        if (trc.getWorkingCode().equals("Reference")) {
          type = "Reference";
          typeName = type;
        } else
          ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), stack.getLiteralPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_NOTYPE, ei.getName(), describeTypes(checkDefn.getType())) && ok;
      }
    } else if (checkDefn.getContentReference() != null) {
      typeDefn = resolveNameReference(profile.getSnapshot(), checkDefn.getContentReference());
      
    } else if (checkDefn.getType().size() == 1 && ("Element".equals(checkDefn.getType().get(0).getWorkingCode()) || "BackboneElement".equals(checkDefn.getType().get(0).getWorkingCode()))) {
      if (checkDefn.getType().get(0).hasProfile()) {
        CanonicalType pu = checkDefn.getType().get(0).getProfile().get(0);
        if (pu.hasExtension(ExtensionDefinitions.EXT_PROFILE_ELEMENT))
          profiles.add(pu.getValue() + "#" + pu.getExtensionString(ExtensionDefinitions.EXT_PROFILE_ELEMENT));
        else
          profiles.add(pu.getValue());
      }
    }

    if (type != null) {
      if (type.startsWith("@")) {
        checkDefn = findElement(profile, type.substring(1));
        if (isSlice) {
          ei.setSlice(ei.getDefinition());
        } else {
          // ei.definition = ei.definition;            
        }
        type = null;
        typeName = type;
      }
    }
    NodeStack localStack = stack.push(ei.getElement(), "*".equals(ei.getDefinition().getBase().getMax()) && ei.getCount() == -1 ? 0 : ei.getCount(), checkDefn, type == null ? typeDefn : resolveType(type, checkDefn.getType()));

    log.debug("  check " + localStack.getLiteralPath()+" against "+ei.getDefinition().getId()+" in profile "+profile.getVersionedUrl()+time());

    EnumSet<ElementValidationAction> actionSet = policyAdvisor.policyForElement(this, valContext.getAppContext(), profile, ei.getDefinition(), localStack.getLiteralPath());
    
    String localStackLiteralPath = localStack.getLiteralPath();
    String eiPath = ei.getPath();
    if (!eiPath.equals(localStackLiteralPath)) {
      assert (eiPath.equals(localStackLiteralPath)) : "ei.path: " + ei.getPath() + "  -  localStack.getLiteralPath: " + localStackLiteralPath;
    }
    boolean thisIsCodeableConcept = false;
    String thisExtension = null;
    boolean checkDisplay = true;

    // SpecialElement special = ei.getElement().getSpecial();
    // this used to say
    //   if (special == SpecialElement.BUNDLE_ENTRY || special == SpecialElement.BUNDLE_OUTCOME || special == SpecialElement.BUNDLE_ISSUES || special == SpecialElement.PARAMETER) {
    //      ok = checkInvariants(valContext, errors, profile, typeDefn != null ? typeDefn : checkDefn, ei.getElement(), ei.getElement(), localStack, false) && ok;
    // but this isn't correct - when the invariant is on the element, the invariant is in the context of the resource that contains the element.
    // changed 18-Jul 2023 - see https://chat.fhir.org/#narrow/stream/179266-fhirpath/topic/FHIRPath.20.25resource.20variable
    if (actionSet.contains(ElementValidationAction.Invariants)) {
      ok = checkInvariants(valContext, errors, profile, typeDefn != null ? typeDefn : checkDefn, resource, ei.getElement(), localStack, false) && ok;
    }
    
    boolean checkBindings = actionSet.contains(ElementValidationAction.Bindings);
    ei.getElement().markValidation(profile, checkDefn);
    boolean elementValidated = false;
    if (type != null) {
      if (isPrimitiveType(type)) {
        ok = checkPrimitive(valContext, errors, ei.getPath(), type, checkDefn, ei.getElement(), profile, localStack, stack, valContext.getRootResource()) && ok;
      } else {
        if (checkDefn.hasFixed()) {
          ok = checkFixedValue(errors, ei.getPath(), ei.getElement(), checkDefn.getFixed(), profile.getVersionedUrl(), checkDefn.getSliceName(), null, false, profile.getVersionedUrl()+"#"+definition.getId()) && ok;
        }
        if (checkDefn.hasPattern()) {
          ok = checkFixedValue(errors, ei.getPath(), ei.getElement(), checkDefn.getPattern(), profile.getVersionedUrl(), checkDefn.getSliceName(), null, true, profile.getVersionedUrl()+"#"+definition.getId()) && ok;
        }
      }
      if (type.equals("Identifier")) {
        ok = checkIdentifier(errors, ei.getPath(), ei.getElement(), checkDefn) && ok;
      } else if (type.equals("Coding")) {
        ok = (checkCoding(checkBindings ? errors : new ArrayList<>(), ei.getPath(), ei.getElement(), profile, checkDefn, inCodeableConcept, checkDisplayInContext, localStack) || !checkBindings) && ok;
      } else if (type.equals("Quantity")) {
        ok = checkQuantity(errors, ei.getPath(), ei.getElement(), profile, checkDefn, localStack) && ok;
      } else if (type.equals("Attachment")) {
        ok = checkAttachment(errors, ei.getPath(), ei.getElement(), profile, checkDefn, inCodeableConcept, checkDisplayInContext, localStack) && ok;
      } else if (type.equals("CodeableConcept")) {
        BooleanHolder bh = new BooleanHolder();
        checkDisplay = checkCodeableConcept(checkBindings ? errors : new ArrayList<>(), ei.getPath(), ei.getElement(), profile, checkDefn, localStack, bh);
        ok = (bh.ok() || !checkBindings) & ok;
        thisIsCodeableConcept = true;
      } else if (type.equals("Narrative")) {
        ok = checkNarrative(valContext, errors, ei.getPath(), ei.getElement(), resource, profile, checkDefn, actualType, localStack, pct, mode) && ok;
      } else if (type.equals("Reference")) {
        ok = checkReference(valContext, errors, ei.getPath(), ei.getElement(), profile, checkDefn, actualType, localStack, pct, mode) && ok;
        // We only check extensions if we're not in a complex extension or if the element we're dealing with is not defined as part of that complex extension
      } else if (type.equals("Extension")) {
        Element eurl = ei.getElement().getNamedChild("url", false);
        if (rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.getPath(), eurl != null, I18nConstants.EXTENSION_EXT_URL_NOTFOUND)) {
          String url = eurl.primitiveValue();
          thisExtension = url;
          if (rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.getPath(), !Utilities.noString(url), I18nConstants.EXTENSION_EXT_URL_NOTFOUND)) {
            if (rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.getPath(), (extensionUrl != null) || Utilities.isAbsoluteUrl(url), I18nConstants.EXTENSION_EXT_URL_ABSOLUTE)) {
              ok = checkExtension(valContext, errors, ei.getPath(), resource, element, ei.getElement(), checkDefn, profile, localStack, stack, extensionUrl, pct, mode) && ok;
            } else {
              ok = false;
            }
          } else {
            ok = false;
          }
        } else {
          ok = false;
        }
      } else if (type.equals("Resource") || isResource(type)) {
        ok = validateContains(valContext, errors, ei.getPath(), checkDefn, definition, resource, ei.getElement(),
          localStack, idStatusForEntry(element, ei), profile, pct, mode) && ok; // if
        elementValidated = true;
        // (str.matches(".*([.,/])work\\1$"))
      } else if (Utilities.isAbsoluteUrl(type)) {
        StructureDefinition defn = context.fetchTypeDefinition(type);
        if (defn != null && defn.hasExtension(ExtensionDefinitions.EXT_BINDING_STYLE)) {
          String style = ExtensionUtilities.readStringExtension(defn, ExtensionDefinitions.EXT_BINDING_STYLE);
          if ("CDA".equals(style)) {
            if (!ei.getElement().hasChild("nullFlavor", false)) {
              if (cdaTypeIs(defn, "CS")) {
                ok = checkCDACodeSimple(valContext, errors, ei.getPath(), ei.getElement(), profile, checkDefn, localStack, defn) && ok;              
              } else if (cdaTypeIs(defn, "CV") || cdaTypeIs(defn, "PQ")) {
                ok = checkCDACoding(errors, ei.getPath(), cdaTypeIs(defn, "PQ"), ei.getElement(), profile, checkDefn, localStack, defn, inCodeableConcept, checkDisplayInContext) && ok;
              } else if (cdaTypeIs(defn, "CD") || cdaTypeIs(defn, "CE")) {
                ok = checkCDACodeableConcept(errors, ei.getPath(), ei.getElement(), profile, checkDefn, localStack, defn) && ok;
                thisIsCodeableConcept = true;
              }
            }
          }
        }
      }
    } else {
      if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), localStack.getLiteralPath(), checkDefn != null, I18nConstants.VALIDATION_VAL_CONTENT_UNKNOWN, ei.getName())) {
        ok = validateElement(valContext, errors, profile, checkDefn, null, null, resource, ei.getElement(), type, localStack, false, true, null, pct, mode) && ok;
      } else {
        ok = false;
      }
    }
    StructureDefinition p = null;
    String tail = null;
    if (profiles.isEmpty()) {
      if (type != null) {
        p = getProfileForType(type, checkDefn.getType(), profile);

        // If dealing with a primitive type, then we need to check the current child against
        // the invariants (constraints) on the current element, because otherwise it only gets
        // checked against the primary type's invariants: LLoyd
        //if (p.getKind() == StructureDefinitionKind.PRIMITIVETYPE) {
        //  checkInvariants(valContext, errors, ei.path, profile, ei.definition, null, null, resource, ei.element);
        //}

        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), ei.getPath(), p != null, I18nConstants.VALIDATION_VAL_NOTYPE, type) && ok;
      }
    } else if (profiles.size() == 1) {
      String url = profiles.get(0);
      if (url.contains("#")) {
        tail = url.substring(url.indexOf("#") + 1);
        url = url.substring(0, url.indexOf("#"));
      }
      p = this.context.fetchResource(StructureDefinition.class, url, profile);
      ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), ei.getPath(), p != null, I18nConstants.VALIDATION_VAL_UNKNOWN_PROFILE, profiles.get(0)) && ok;
    } else {
      elementValidated = true;
      HashMap<String, List<ValidationMessage>> goodProfiles = new HashMap<String, List<ValidationMessage>>();
      HashMap<String, List<ValidationMessage>> badProfiles = new HashMap<String, List<ValidationMessage>>();
      for (String typeProfile : profiles) {
        String url = typeProfile;
        tail = null;
        if (url.contains("#")) {
          tail = url.substring(url.indexOf("#") + 1);
          url = url.substring(0, url.indexOf("#"));
        }
        p = this.context.fetchResource(StructureDefinition.class, typeProfile);
        if (rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), ei.getPath(), p != null, I18nConstants.VALIDATION_VAL_UNKNOWN_PROFILE, typeProfile)) {
          List<ValidationMessage> profileErrors = new ArrayList<ValidationMessage>();
          validateElement(valContext, profileErrors, p, getElementByTail(p, tail), profile, checkDefn, resource, ei.getElement(), type, localStack, thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode); // we don't track ok here
          if (hasErrors(profileErrors))
            badProfiles.put(typeProfile, profileErrors);
          else
            goodProfiles.put(typeProfile, profileErrors);
        } else {
          ok = false;
        }
      }
      if (goodProfiles.size() == 1) {
        errors.addAll(goodProfiles.values().iterator().next());
      } else if (goodProfiles.size() == 0) {
        Collections.sort(profiles);
        ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), ei.getPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_NOMATCH, StringUtils.join("; ", profiles)) && ok;
        for (String m : badProfiles.keySet()) {
          p = this.context.fetchResource(StructureDefinition.class, m);
          for (ValidationMessage message : badProfiles.get(m)) {
            message.setMessage(message.getMessage() + " (validating against " + p.getUrl() + (p.hasVersion() ? "|" + p.getVersion() : "") + " [" + p.getName() + "])");
            errors.add(message);
          }
        }
      } else {
        warningPlural(errors, NO_RULE_DATE, IssueType.STRUCTURE, ei.line(), ei.col(), ei.getPath(), false, goodProfiles.size(), I18nConstants.VALIDATION_VAL_PROFILE_MULTIPLEMATCHES, ResourceUtilities.listStrings(goodProfiles.keySet(), true));
        for (String m : goodProfiles.keySet()) {
          p = this.context.fetchResource(StructureDefinition.class, m);
          for (ValidationMessage message : goodProfiles.get(m)) {
            message.setMessage(message.getMessage() + " (validating against " + p.getUrl() + (p.hasVersion() ? "|" + p.getVersion() : "") + " [" + p.getName() + "])");
            errors.add(message);
          }
        }
      }
    }
    if (p != null) {
      trackUsage(p, valContext, element);

      if (!elementValidated) {
        if (ei.getElement().getSpecial() == SpecialElement.BUNDLE_ENTRY || ei.getElement().getSpecial() == SpecialElement.BUNDLE_OUTCOME || ei.getElement().getSpecial() == SpecialElement.PARAMETER)
          ok = validateElement(valContext, errors, p, getElementByTail(p, tail), profile, checkDefn, ei.getElement(), ei.getElement(), type, localStack.resetIds(), thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode) && ok;
        else
          ok = validateElement(valContext, errors, p, getElementByTail(p, tail), profile, checkDefn, resource, ei.getElement(), type, localStack, thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode) && ok;
      }
      int index = profile.getSnapshot().getElement().indexOf(checkDefn);
      if (index < profile.getSnapshot().getElement().size() - 1) {
        String nextPath = profile.getSnapshot().getElement().get(index + 1).getPath();
        if (!nextPath.equals(checkDefn.getPath()) && nextPath.startsWith(checkDefn.getPath())) {
          if (ei.getElement().getSpecial() == SpecialElement.BUNDLE_ENTRY || ei.getElement().getSpecial() == SpecialElement.BUNDLE_OUTCOME || ei.getElement().getSpecial() == SpecialElement.PARAMETER) {
            ok = validateElement(valContext.forEntry(ei.getElement(), null), errors, profile, checkDefn, null, null, ei.getElement(), ei.getElement(), type, localStack, thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode) && ok;                        
          } else if (ei.getElement().getSpecial() == SpecialElement.CONTAINED) {
            ok = validateElement(valContext.forContained(ei.getElement()), errors, profile, checkDefn, null, null, ei.getElement(), ei.getElement(), type, localStack, thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode) && ok;            
          } else {
            ok = validateElement(valContext, errors, profile, checkDefn, null, null, resource, ei.getElement(), type, localStack, thisIsCodeableConcept, checkDisplay, thisExtension, pct, mode) && ok;            
          }
        }
      }
    }
    return ok;
  }

  private boolean cdaTypeIs(StructureDefinition defn, String type) {
    return ("http://hl7.org/cda/stds/core/StructureDefinition/"+type).equals(defn.getUrl());
  }

  private boolean checkCDACoding(List<ValidationMessage> errors, String path, boolean isPQ, Element element, StructureDefinition profile, ElementDefinition checkDefn, NodeStack stack, StructureDefinition defn, boolean inCodeableConcept, boolean checkDisplay) {
    boolean ok = true;
    String system = null;
    String code = element.getNamedChildValue(isPQ ? "unit" : "code", false);
    String oid = isPQ ? "2.16.840.1.113883.6.8" : element.getNamedChildValue("codeSystem", false);
    if (oid != null) {
      OIDSummary urls = context.urlsForOid(oid, "CodeSystem");
      system = "urn:oid:"+oid;
      if (urls.urlCount() == 1) {
        system = urls.getUrl();
      } else if (urls.urlCount() == 0) {
        warning(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_UNKNOWN_OID, oid);        
      } else {
        String prefUrl = urls.chooseBestUrl();
        if (prefUrl == null) {
          rule(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_OID_MULTIPLE_MATCHES, oid, urls.describe());
          ok = false;
        } else {
          system = prefUrl;
          warning(errors, "2023-10-11", IssueType.CODEINVALID, element.line(), element.col(), path, false, I18nConstants.TERMINOLOGY_TX_OID_MULTIPLE_MATCHES_CHOSEN, oid, prefUrl, urls.describe());
        }
      }
    } else {
      warning(errors, NO_RULE_DATE, IssueType.CODEINVALID, element.line(), element.col(), path, code == null, I18nConstants.TERMINOLOGY_TX_SYSTEM_NO_CODE);      
    }
    
    String version = element.getNamedChildValue("codeSystemVersion", false);
    String display = element.getNamedChildValue("displayName", false);
    return checkCodedElement(errors, path, element, profile, checkDefn, inCodeableConcept, checkDisplay, stack, code, system, version, display) && ok;
  }

  private boolean checkCDACodeSimple(ValidationContext valContext, List<ValidationMessage> errors, String path, Element element, StructureDefinition profile, ElementDefinition checkDefn, NodeStack stack, StructureDefinition defn) {
    if (element.hasChild("code", false)) {
      return checkPrimitiveBinding(valContext, errors, path, "code", checkDefn, element.getNamedChild("code", false), profile, stack);
    } else {
      return false;
    }
  }

  private boolean isAbstractType(String type) {
    StructureDefinition sd = context.fetchTypeDefinition(type);
    return sd != null && sd.getAbstract();
  }

  private boolean isResourceAndTypes(ElementDefinition ed) {
    if (!RESOURCE_X_POINTS.contains(ed.getBase().getPath())) {
      return false;
    }
    for (TypeRefComponent tr : ed.getType()) {
      if (!isResource(tr.getCode())) {
        return false;
      }
    }
    return true;
  }

  private boolean isResource(String type) {
    StructureDefinition sd = context.fetchTypeDefinition(type);
    return sd != null && sd.getKind().equals(StructureDefinitionKind.RESOURCE);
  }

  private void trackUsage(StructureDefinition profile, ValidationContext valContext, Element element) {
    if (tracker != null) {
      tracker.recordProfileUsage(profile, valContext.getAppContext(), element);
    }
  }

  private boolean hasMapping(String url, StructureDefinition defn, ElementDefinition elem) {
    String id = null;
    for (StructureDefinitionMappingComponent m : defn.getMapping()) {
      if (url.equals(m.getUri())) {
        id = m.getIdentity();
        break;
      }
    }
    if (id != null) {
      for (ElementDefinitionMappingComponent m : elem.getMapping()) {
        if (id.equals(m.getIdentity())) {
          return true;
        }
      }

    }
    return false;
  }

  private List<String> getMapping(String url, StructureDefinition defn, ElementDefinition elem) {
    List<String> res = new ArrayList<>();
    String id = null;
    for (StructureDefinitionMappingComponent m : defn.getMapping()) {
      if (url.equals(m.getUri())) {
        id = m.getIdentity();
        break;
      }
    }
    if (id != null) {
      for (ElementDefinitionMappingComponent m : elem.getMapping()) {
        if (id.equals(m.getIdentity())) {
          res.add(m.getMap());
        }
      }
    }
    return res;
  }

  public void checkMustSupport(StructureDefinition profile, ElementInfo ei) {
    String usesMustSupport = profile.getUserString(UserDataNames.keyview_usesMustSupport);
    if (usesMustSupport == null) {
      usesMustSupport = "N";
      for (ElementDefinition pe : profile.getSnapshot().getElement()) {
        if (pe.getMustSupport()) {
          usesMustSupport = "Y";
          break;
        }
      }
      profile.setUserData(UserDataNames.keyview_usesMustSupport, usesMustSupport);
    }
    String elementSupported = ei.getElement().getUserString(UserDataNames.keyview_elementSupported);
    String fixedValue = ei.getElement().getUserString(UserDataNames.keyview_hasFixed);
    if ((elementSupported == null || !elementSupported.equals("Y")) && ei.getDefinition().getMustSupport()) {
      if (ei.getDefinition().getMustSupport()) {
        ei.getElement().setUserData(UserDataNames.keyview_elementSupported, "Y");
      }
    } else if (elementSupported == null && !usesMustSupport.equals("Y"))
      ei.getElement().setUserData(UserDataNames.keyview_elementSupported, "NA");
    if (fixedValue==null  && (ei.getDefinition().hasFixed() || ei.getDefinition().hasPattern()))
      ei.getElement().setUserData(UserDataNames.keyview_hasFixed, "Y");
  }

  public boolean checkCardinalities(List<ValidationMessage> errors, StructureDefinition profile, Element element, NodeStack stack,
      SourcedChildDefinitions childDefinitions, List<ElementInfo> children, List<String> problematicPaths) throws DefinitionException {
    boolean ok = true;
    // 3. report any definitions that have a cardinality problem
    for (ElementDefinition ed : childDefinitions.getList()) {
      if (!ed.hasRepresentation(PropertyRepresentation.XHTML) && !ed.hasRepresentation(PropertyRepresentation.XMLTEXT)) { // xhtml.value is XMLText in <R3
        int count = 0;
        List<ElementDefinition> slices = null;
        if (ed.hasSlicing()) {
          slices = profileUtilities.getSliceList(profile, ed);
        }
        for (ElementInfo ei : children) {
          if (ei.getDefinition() == ed || idsMatch(ei.getDefinition(), ed)) {
            count++;
          } else if (slices != null) {
            for (ElementDefinition sed : slices) {
              if (ei.getDefinition() == sed || idsMatch(ei.getDefinition(), sed)) {
                count++;
                break;
              }
            }
          }
        }
        if (ed.getMin() > 0) {
          if (problematicPaths.contains(ed.getPath()))
            hintPlural(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, element.line(), element.col(), stack.getLiteralPath(), count >= ed.getMin(), count, I18nConstants.VALIDATION_VAL_PROFILE_NOCHECKMIN, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), Integer.toString(ed.getMin()));
          else {
            if (count < ed.getMin()) {
              if (isObservationMagicValue(profile, ed)) {                
                ok = rule(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_MINIMUM_MAGIC, ed.getSliceName(), getFixedLOINCCode(ed, profile), profile.getVersionedUrl()) && ok;
              } else if (!ed.hasSliceName()) {
                ok = rulePlural(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), false, count, I18nConstants.VALIDATION_VAL_PROFILE_MINIMUM, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), ed.getMin()) && ok;
              } else if (ed.isProfiledExtension()) {
                String url = ed.getTypeFirstRep().getProfile().get(0).getValue();
                StructureDefinition sd = context.fetchResource(StructureDefinition.class, url);
                if (sd != null) {
                  url = sd.getSnapshot().getElementByPath("Extension.url").getFixed().primitiveValue();
                }
                ok = rulePlural(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), false, ed.getMin(), I18nConstants.VALIDATION_VAL_PROFILE_MINIMUM_SLICE_EXT, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), count, url) && ok;
              } else {
                ok = rulePlural(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), false, ed.getMin(), I18nConstants.VALIDATION_VAL_PROFILE_MINIMUM_SLICE, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), count) && ok;
              }
            }
          }
        }
        if (ed.hasMax() && !ed.getMax().equals("*")) {
          if (problematicPaths.contains(ed.getPath())) {
            hintPlural(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, element.line(), element.col(), stack.getLiteralPath(), count <= Integer.parseInt(ed.getMax()), count, I18nConstants.VALIDATION_VAL_PROFILE_NOCHECKMAX, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), ed.getMax());
          } else if (count > Integer.parseInt(ed.getMax())) {
            ok = rulePlural(errors, NO_RULE_DATE, IssueType.STRUCTURE, element.line(), element.col(), stack.getLiteralPath(), false, count, I18nConstants.VALIDATION_VAL_PROFILE_MAXIMUM, profile.getVersionedUrl(), ed.getPath(), ed.getId(), ed.getSliceName(),ed.getLabel(), stack.getLiteralPath(), ed.getMax(), Integer.toString(count)) && ok;
          }
        }
      }
    }
    return ok;
  }

  private boolean idsMatch(ElementDefinition ed1, ElementDefinition ed2) {
    return ed1 != null && ed2 != null && ed1.hasId() && ed2.hasId() && ed1.getId().equals(ed2.getId());
  }

  private String getFixedLOINCCode(ElementDefinition ed, StructureDefinition profile) {
    if (ed.hasFixedCoding() && "http://loinc.org".equals(ed.getFixedCoding().getSystem())) {
      return ed.getFixedCoding().getCode();      
    }
    SourcedChildDefinitions children = profileUtilities.getChildMap(profile, ed, true);
    if (children != null) {
      for (ElementDefinition t : children.getList()) {
        if (t.getPath().endsWith(".code") && t.hasFixed()) {
          return t.getFixed().primitiveValue();
        }
      }
    }
    return "??";
  }

  private boolean isObservationMagicValue(StructureDefinition profile, ElementDefinition ed) {
    if (profile.getUrl().startsWith("http://hl7.org/fhir/StructureDefinition/") && ed.hasSliceName() && ed.getPath().equals("Observation.code.coding")) {
      return true;
    }
    return false;
  }

  public List<String> assignChildren(ValidationContext valContext, List<ValidationMessage> errors, StructureDefinition profile, Element element, Element resource,
    NodeStack stack, SourcedChildDefinitions childDefinitions, List<ElementInfo> children, BooleanHolder bh) throws DefinitionException {
    // 2. assign children to a definition
    // for each definition, for each child, check whether it belongs in the slice
    ElementDefinition slicer = null;
    List<ElementDefinition> slicerSlices = null;
    
    List<String> problematicPaths = new ArrayList<String>();
    if ("named-elements".equals(element.getProperty().getExtensionStyle())) {
      // there's no children to iterate. Instead we iterate the elements, checking that they're ok
      // there must be a child - and only one - with 
      ElementDefinition ed = null;
      for (ElementDefinition t : childDefinitions.getList()) {
        if ("named-elements".equals(t.getExtensionString(ExtensionDefinitions.EXT_EXTENSION_STYLE_NEW, ExtensionDefinitions.EXT_EXTENSION_STYLE_DEPRECATED))) {
          ed = t;
        }
      }
      if (ed == null) {
        bh.see(rule(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, stack, false, I18nConstants.VALIDATION_VAL_NAMED_EXTENSIONS_NOT_FOUND, childDefinitions.getSource().getVersionedUrl()));        
      } else {
        for (ElementInfo ei : children) {
          Set<String> contexts = new HashSet<>();
          if (!namedExtensionTypeIsOk(ei.getElement().getProperty(), ed)) {
            bh.see(rule(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, stack, false, I18nConstants.VALIDATION_VAL_NAMED_EXTENSIONS_BAD_TYPE, 
                childDefinitions.getSource().getVersionedUrl(), ed.getPath(), ed.typeSummary(), ei.getElement().getProperty().getStructure().getType()));                    
          } else if (!namedExtensionContextIsOk(errors, stack, ei.getElement().getProperty(), profile, stack.getLogicalPaths(), contexts)) {
            bh.see(rule(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, stack, false, I18nConstants.VALIDATION_VAL_NAMED_EXTENSIONS_BAD_CONTEXT, 
                ei.getElement().getProperty().getStructure().getVersionedUrl(), ed.getPath(), CommaSeparatedStringBuilder.join(",", Utilities.sorted(contexts)),
                CommaSeparatedStringBuilder.join(",", Utilities.sorted(stack.getLogicalPaths()))));                    
          } else {
            if (childDefinitions.getSource().getContext().isEmpty()) {
              warning(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, stack, false, I18nConstants.VALIDATION_VAL_NAMED_EXTENSIONS_NO_CONTEXT, ei.getElement().getProperty().getStructure().getVersionedUrl());
            }
            ei.setDefinition(ei.getElement().getProperty().getStructure(), ei.getElement().getProperty().getDefinition());
          }
        }
      }        
    } else {
      boolean unsupportedSlicing = false;
      String slicingPath = null;
      int sliceOffset = 0;
      for (int i = 0; i < childDefinitions.getList().size(); i++) {
        ElementDefinition ed = childDefinitions.getList().get(i);
        boolean childUnsupportedSlicing = false;
        boolean process = true;
        if (ed.hasSlicing() && !ed.getSlicing().getOrdered()) {
          slicingPath = ed.getPath();
        } else if (slicingPath != null && ed.getPath().equals(slicingPath)) {
          ; // nothing
        } else if (slicingPath != null && !ed.getPath().startsWith(slicingPath)) {
          slicingPath = null;
        }
        // where are we with slicing
        if (ed.hasSlicing()) {
          if (slicer != null && slicer.getPath().equals(ed.getPath())) {
            String errorContext = "profile " + profile.getVersionedUrl();
            if (resource.hasChild(ID, false) && !resource.getChildValue(ID).isEmpty()) {
              errorContext += "; instance " + resource.getChildValue("id");
            }
            throw new DefinitionException(context.formatMessage(I18nConstants.SLICE_ENCOUNTERED_MIDWAY_THROUGH_SET_PATH___ID___, slicer.getPath(), slicer.getId(), errorContext));
          }
          slicer = ed;
          process = false;
          sliceOffset = i;
          slicerSlices = new ArrayList<>();
          for (int j = i+1; j < childDefinitions.getList().size(); j++) {
            if (ed.getPath().equals(childDefinitions.getList().get(j).getPath())) {
              slicerSlices.add(childDefinitions.getList().get(j));
            }
          }
        } else if (slicer != null && !slicer.getPath().equals(ed.getPath())) {
          slicer = null;
          slicerSlices = null;
        }

        for (ElementInfo ei : children) {
          if (ei.getSliceInfo() == null) {
            ei.setSliceInfo(new ArrayList<>());
          }
          unsupportedSlicing = matchSlice(valContext, errors, ei.getSliceInfo(), profile, stack, slicer, slicerSlices, unsupportedSlicing, problematicPaths, sliceOffset, i, childDefinitions.getSource(), ed, childUnsupportedSlicing, ei, bh);
        }
      }
      int last = -1;
      ElementInfo lastei = null;
      int lastSlice = -1;
      for (ElementInfo ei : children) {
        String sliceInfo = "";
        if (slicer != null) {
          sliceInfo = " (slice: " + slicer.getPath() + ")";
        }
        if (!unsupportedSlicing) {
          if (ei.isAdditionalSlice() && ei.getDefinition() != null) {
            if (ei.getDefinition().getSlicing().getRules().equals(ElementDefinition.SlicingRules.OPEN) ||
                ei.getDefinition().getSlicing().getRules().equals(ElementDefinition.SlicingRules.OPENATEND) && true /* TODO: replace "true" with condition to check that this element is at "end" */) {
              if (!ignoreSlicingHint(ei.getDefinition(), profile)) { 
                slicingHint(errors, NO_RULE_DATE, IssueType.INFORMATIONAL, ei.line(), ei.col(), ei.getPath(), false, isProfile(slicer) || isCritical(ei.getSliceInfo()), 
                    context.formatMessage(I18nConstants.THIS_ELEMENT_DOES_NOT_MATCH_ANY_KNOWN_SLICE_,
                        profile == null ? "" : "defined in the profile " + profile.getVersionedUrl()),
                    context.formatMessage(I18nConstants.THIS_ELEMENT_DOES_NOT_MATCH_ANY_KNOWN_SLICE_, profile == null ? "" : context.formatMessage(I18nConstants.DEFINED_IN_THE_PROFILE) + " "+profile.getVersionedUrl()) + errorSummaryForSlicingAsHtml(ei.getSliceInfo()),
                    ei.getSliceInfo(), I18nConstants.THIS_ELEMENT_DOES_NOT_MATCH_ANY_KNOWN_SLICE_);
              }
            } else if (ei.getDefinition().getSlicing().getRules().equals(ElementDefinition.SlicingRules.CLOSED)) {
              bh.see(rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.line(), ei.col(), ei.getPath(), false, I18nConstants.VALIDATION_VAL_PROFILE_NOTSLICE, (profile == null ? "" : "defined in the profile " + profile.getVersionedUrl()), errorSummaryForSlicing(ei.getSliceInfo())));
            }
          } else {
            // Don't raise this if we're in an abstract profile, like Resource
            if (!childDefinitions.getSource().getAbstract()) {
              bh.see(rule(errors, NO_RULE_DATE, IssueType.NOTSUPPORTED, ei.line(), ei.col(), ei.getPath(), (ei.getDefinition() != null), I18nConstants.VALIDATION_VAL_PROFILE_NOTALLOWED, profile.getVersionedUrl()));
            }
          }
        }
        // TODO: Should get the order of elements correct when parsing elements that are XML attributes vs. elements
        boolean isXmlAttr = false;
        if (ei.getDefinition() != null) {
          for (Enumeration<PropertyRepresentation> r : ei.getDefinition().getRepresentation()) {
            if (r.getValue() == PropertyRepresentation.XMLATTR) {
              isXmlAttr = true;
              break;
            }
          }
        }

        if (!ExtensionUtilities.readBoolExtension(profile, "http://hl7.org/fhir/StructureDefinition/structuredefinition-xml-no-order", "http://hl7.org/fhir/tools/StructureDefinition/xml-no-order")) {
          boolean bok = (ei.getDefinition() == null) || (ei.getIndex() >= last) || isXmlAttr || ei.getElement().isIgnorePropertyOrder();
          bh.see(rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.line(), ei.col(), ei.getPath(), bok, I18nConstants.VALIDATION_VAL_PROFILE_OUTOFORDER, profile.getVersionedUrl(), ei.getName(), lastei == null ? "(null)" : lastei.getName()));
        }
        if (ei.getSlice() != null && ei.getIndex() == last && ei.getSlice().getSlicing().getOrdered()) {
          bh.see(rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.line(), ei.col(), ei.getPath(), (ei.getDefinition() == null) || (ei.getSliceindex() >= lastSlice) || isXmlAttr, I18nConstants.VALIDATION_VAL_PROFILE_SLICEORDER, profile.getVersionedUrl(), ei.getName()));
        }
        if (ei.getDefinition() == null || !isXmlAttr) {
          last = ei.getIndex();
          lastei = ei;
        }
        if (ei.getSlice() != null) {
          lastSlice = ei.getSliceindex();
        } else {
          lastSlice = -1;
        }
      }
    }
    return problematicPaths;
  }


  private boolean namedExtensionContextIsOk(List<ValidationMessage> errors, NodeStack stack, org.hl7.fhir.r5.elementmodel.Property property, StructureDefinition ctxtProfile, Set<String> logicalPaths, Set<String> contexts) {
    StructureDefinition sd = property.getStructure();
    if (!sd.hasContext()) {
      return true;
    }
    for (StructureDefinitionContextComponent ctxt : sd.getContext()) {
      switch (ctxt.getType()) {
      case ELEMENT:
        String exp = ctxt.getExpression();
        if (exp.contains("#")) {
          String u = exp.substring(0, exp.indexOf("#"));
          if (!u.equals(ctxtProfile.getUrl())) {
            break;
          }
          exp = exp.substring(exp.indexOf("#")+1);
        }
        contexts.add("element:"+exp);
        for (String lp : logicalPaths) {
          if (lp.equals(ctxt.getExpression())) {
            return true;
          }
        }
        break;
      case EXTENSION:
        contexts.add("extension:"+ctxt.getExpression());
        break;
      case FHIRPATH:
        contexts.add("fhirpath:"+ctxt.getExpression());
        break;
      default:
        contexts.add("?:"+ctxt.getExpression());
        break;      
      }
    }      
    return false;
  }

  private boolean namedExtensionTypeIsOk(org.hl7.fhir.r5.elementmodel.Property property, ElementDefinition ed) {
    StructureDefinition sd = property.getStructure();
    while (sd != null) {
      for (TypeRefComponent tr : ed.getType()) {
        if (typesMatch(tr, sd)) {
          return true; 
        }
      }
      sd = context.fetchResource(StructureDefinition.class, sd.getBaseDefinition());
    }   
    // special case: Base
    for (TypeRefComponent tr : ed.getType()) {
      if (Utilities.existsInList(tr.getWorkingCode(), "Base", "http://hl7.org/fhir/StructureDefinition/Base")) {
        return true; 
      }
    }
    return false;
  }

  private boolean typesMatch(TypeRefComponent tr, StructureDefinition sd) {
    return Utilities.existsInList(tr.getWorkingCode(), sd.getUrl(), sd.getVersionedUrl());
  }

  private boolean ignoreSlicingHint(ElementDefinition definition, StructureDefinition profile) {
    if (profile.getUrl().startsWith("http://hl7.org/fhir/StructureDefinition/") && "Observation.code.coding".equals(definition.getPath())) {
      return true;
    }
    return false;
  }

  public List<ElementInfo> listChildren(Element element, NodeStack stack) {
    // 1. List the children, and remember their exact path (convenience)
    List<ElementInfo> children = new ArrayList<ElementInfo>();
    ChildIterator iter = new ChildIterator(this, stack.getLiteralPath(), element);
    while (iter.next()) {
      children.add(new ElementInfo(iter.name(), iter.element(), iter.path(), iter.count()));
    }
    return children;
  }

  public boolean checkInvariants(ValidationContext valContext, List<ValidationMessage> errors, StructureDefinition profile, ElementDefinition definition, Element resource, Element element, NodeStack stack, boolean onlyNonInherited) throws FHIRException {
    return checkInvariants(valContext, errors, stack.getLiteralPath(), profile, definition, null, null, resource, element, onlyNonInherited);
  }

  public boolean matchSlice(ValidationContext valContext, List<ValidationMessage> errors, List<ValidationMessage> sliceInfo, StructureDefinition profile, NodeStack stack,
    ElementDefinition slicer, List<ElementDefinition> slicerSlices, boolean unsupportedSlicing, List<String> problematicPaths, int sliceOffset, int i, StructureDefinition sd, ElementDefinition ed,
    boolean childUnsupportedSlicing, ElementInfo ei, BooleanHolder bh) {
    boolean match = false;
    if (slicer == null || slicer == ed) {
      match = nameMatches(ei.getName(), tail(ed.getPath()));
    } else {
      if (nameMatches(ei.getName(), tail(ed.getPath())))
        try {
          match = sliceMatches(valContext, ei.getElement(), ei.getPath(), slicer, slicerSlices, ed, profile, errors, sliceInfo, stack, profile);
          if (match) {
            ei.setSlice(slicer);

            // Since a defined slice was found, this is not an additional (undefined) slice.
            ei.setAdditionalSlice(false);
          } else if (ei.getSlice() == null) {
            // if the specified slice is undefined, keep track of the fact this is an additional (undefined) slice, but only if a slice wasn't found previously
            ei.setAdditionalSlice(true);
          }
        } catch (FHIRException e) {
          rule(errors, NO_RULE_DATE, IssueType.PROCESSING, ei.line(), ei.col(), ei.getPath(), false,  I18nConstants.SLICING_CANNOT_BE_EVALUATED, e.getMessage());
          bh.fail();
          unsupportedSlicing = true;
          childUnsupportedSlicing = true;
        }
    }
    if (match) {
      boolean update = true;
      boolean isOk = ei.getDefinition() == null || ei.getDefinition() == slicer || (ei.getDefinition().getPath().endsWith("[x]") && ed.getPath().startsWith(ei.getDefinition().getPath().replace("[x]", "")));
      if (!isOk) {
        // is this a subslice? then we put it in as a replacement
        String existingName = ei.getDefinition() == null || !ei.getDefinition().hasSliceName() ? null : ei.getDefinition().getSliceName();
        String matchingName = ed.hasSliceName() ? ed.getSliceName() : null; 
        if (existingName != null && matchingName != null) {
          if (matchingName.startsWith(existingName+"/")) {
            isOk = true;
          } else if (existingName.startsWith(matchingName+"/")) {
            update = false;
            isOk = true;
          }
        }
      }
      if (rule(errors, NO_RULE_DATE, IssueType.INVALID, ei.line(), ei.col(), ei.getPath(), isOk, I18nConstants.VALIDATION_VAL_PROFILE_MATCHMULTIPLE, profile.getVersionedUrl(), (ei.getDefinition() == null || !ei.getDefinition().hasSliceName() ? "" : ei.getDefinition().getSliceName()), (ed.hasSliceName() ? ed.getSliceName() : ""))) {
        if (update) {
          ei.setDefinition(sd, ed);
          if (ei.getSlice() == null) {
            ei.setIndex(i);
          } else {
            ei.setIndex(sliceOffset);
            ei.setSliceindex(i - (sliceOffset + 1));
          }
        }
      } else {
        bh.fail();
      }
    } else if (childUnsupportedSlicing) {
      problematicPaths.add(ed.getPath());
    }
    return unsupportedSlicing;
  }

  private String slicingSummary(ElementDefinitionSlicingComponent slicing) {
    StringBuilder b = new StringBuilder();
    b.append('[');
    boolean first = true;
    for (ElementDefinitionSlicingDiscriminatorComponent t : slicing.getDiscriminator()) {
      if (first) first = false; else b.append(","); 
      b.append(t.getType().toCode());
      b.append(":");
      b.append(t.getPath());
    }
    b.append(']');
    b.append(slicing.getOrdered() ? ";ordered" : "");
    b.append(slicing.getRules().toString());
    return b.toString();
  }

  private ElementDefinition getElementByTail(StructureDefinition p, String tail) throws DefinitionException {
    if (tail == null)
      return p.getSnapshot().getElement().get(0);
    for (ElementDefinition t : p.getSnapshot().getElement()) {
      if (tail.equals(t.getId()))
        return t;
    }
    throw new DefinitionException(context.formatMessage(I18nConstants.UNABLE_TO_FIND_ELEMENT_WITH_ID_, tail));
  }

  private IdStatus idStatusForEntry(Element ep, ElementInfo ei) {
    if (ei.getDefinition().hasExtension(ExtensionDefinitions.EXT_ID_EXPECTATION)) {
      return IdStatus.fromCode(ExtensionUtilities.readStringExtension(ei.getDefinition(),ExtensionDefinitions.EXT_ID_EXPECTATION));
    } else if (isBundleEntry(ei.getPath())) {
      Element req = ep.getNamedChild("request", false);
      Element resp = ep.getNamedChild("response", false);
      Element fullUrl = ep.getNamedChild(FULL_URL, false);
      Element method = null;
      Element url = null;
      if (req != null) {
        method = req.getNamedChild("method", false);
        url = req.getNamedChild("url", false);
      }
      if (resp != null) {
        return IdStatus.OPTIONAL;
      }
      if (method == null) {
        if (fullUrl == null)
          return IdStatus.REQUIRED;
        else if (fullUrl.primitiveValue().startsWith("urn:uuid:") || fullUrl.primitiveValue().startsWith("urn:oid:"))
          return IdStatus.OPTIONAL;
        else
          return IdStatus.REQUIRED;
      } else {
        String s = method.primitiveValue();
        if (s.equals("PUT")) {
          if (url == null)
            return IdStatus.REQUIRED;
          else
            return IdStatus.OPTIONAL; // or maybe prohibited? not clear
        } else if (s.equals("POST"))
          return IdStatus.OPTIONAL; // this should be prohibited, but see task 9102
        else // actually, we should never get to here; a bundle entry with method get/delete should not have a resource
          return IdStatus.OPTIONAL;
      }
    } else if (isParametersEntry(ei.getPath()) || isBundleOutcome(ei.getPath()))
      return IdStatus.OPTIONAL;
    else
      return IdStatus.REQUIRED;
  }

  private static class ExecutedConstraintCacheKey {
    private String expression;
    private ConstraintSeverity severity;

    public ExecutedConstraintCacheKey(String fixedExpr, ConstraintSeverity severity) {
      this.expression = fixedExpr;
      this.severity = severity;
    }

    @Override
    public int hashCode() {
      return severity.hashCode() * (expression == null ? 0 : expression.hashCode());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() != o.getClass()) return false;
      ExecutedConstraintCacheKey otherKey = (ExecutedConstraintCacheKey) o;
      return StringUtils.equals(expression, otherKey.expression) && severity == otherKey.severity;
    }
  }

  private boolean checkInvariants(ValidationContext valContext, List<ValidationMessage> errors, String path, StructureDefinition profile, ElementDefinition ed, String typename, String typeProfile, Element resource, Element element, boolean onlyNonInherited) throws FHIRException, FHIRException {
    if (noInvariantChecks) {
      return true;
    }
    
    boolean ok = true;
    for (ElementDefinitionConstraintComponent inv : ed.getConstraint()) {
      if (inv.hasExpression() && (!onlyNonInherited || !inv.hasSource() || (!isInheritedProfile(profile, inv.getSource()) && !isInheritedProfile(ed.getType(), inv.getSource())) )) {
        @SuppressWarnings("unchecked")
        Map<ExecutedConstraintCacheKey, List<ValidationMessage>> invMap = executionId.equals(element.getUserString(EXECUTION_ID)) ? (Map<ExecutedConstraintCacheKey, List<ValidationMessage>>) element.getUserData(EXECUTED_CONSTRAINT_LIST) : null;
        if (invMap == null) {
          invMap = new HashMap<>();
          element.setUserData(EXECUTED_CONSTRAINT_LIST, invMap);
          element.setUserData(EXECUTION_ID, executionId);
        }
        List<ValidationMessage> invErrors = null;
        // We key based on inv.expression rather than inv.key because expressions can change in derived profiles and aren't guaranteed to be consistent across profiles.
        String fixedExpr = FHIRPathExpressionFixer.fixExpr(inv.getExpression(), inv.getKey(), context.getVersion());
        ConstraintSeverity severity = inv.getSeverity();
        ExecutedConstraintCacheKey key = new ExecutedConstraintCacheKey(fixedExpr, severity);
        if (!invMap.keySet().contains(key)) {
          invErrors = new ArrayList<ValidationMessage>();
          invMap.put(key, invErrors);
          ok = checkInvariant(valContext, invErrors, path, profile, resource, element, inv) && ok;
        } else {
          invErrors = (ArrayList<ValidationMessage>)invMap.get(key);
        }
        errors.addAll(invErrors);
      }
    }
    return ok;
  }

  private boolean isInheritedProfile(List<TypeRefComponent> types, String source) {
    for (TypeRefComponent type : types) {
      for (CanonicalType c : type.getProfile()) {
        StructureDefinition sd = context.fetchResource(StructureDefinition.class, c.asStringValue());
        if (sd != null) {
          if (sd.getUrl().equals(source)) {
            return true;
          }
          if (isInheritedProfile(sd, source)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isInheritedProfile(StructureDefinition profile, String source) {
    if (source.equals(profile.getUrl())) {
      return false;
    }
    while (profile != null) {
      profile = context.fetchResource(StructureDefinition.class, profile.getBaseDefinition(), profile);
      if (profile != null) {
        if (source.equals(profile.getUrl())) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean checkInvariant(ValidationContext valContext, List<ValidationMessage> errors, String path, StructureDefinition profile, Element resource, Element element, ElementDefinitionConstraintComponent inv) throws FHIRException {
    if (IsExemptInvariant(path, element, inv)) {
      return true;
    }
    boolean ok = true;
    // we don't allow dom-3 to execute - it takes too long (and is wrong).
    // instead, we enforce it in code 
    if ("dom-3".equals(inv.getKey())) {
      return true;
    }
    ExpressionNode n = (ExpressionNode) inv.getUserData(UserDataNames.validator_expression_cache);
    if (n == null) {
      long t = System.nanoTime();
      try {
        String expr = FHIRPathExpressionFixer.fixExpr(inv.getExpression(), inv.getKey(), context.getVersion());
        n = fpe.parse(expr);
      } catch (FHIRException e) {
        ok = rule(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, false, I18nConstants.PROBLEM_PROCESSING_EXPRESSION__IN_PROFILE__PATH__, inv.getExpression(), profile.getVersionedUrl(), path, e.getMessage()) && ok;
        return false;
      }
      timeTracker.fpe(t);
      inv.setUserData(UserDataNames.validator_expression_cache, n);
    }
    
    valContext.setProfile(profile);
    
    boolean invOK;
    String msg;
    try {
      long t = System.nanoTime();
      invOK = fpe.evaluateToBoolean(valContext, resource, valContext.getRootResource(), element, n);
      timeTracker.fpe(t);
      msg = fpe.forLog();
    } catch (Exception ex) {
      invOK = false;
      msg = /*ex.getClass().getName()+": "+*/ ex.getMessage();
      if (settings.isDebug()) {
          ex.printStackTrace();
      }
    }
    if (!invOK) {
      if (wantInvariantInMessage) {
        msg = msg + " (inv = " + n.toString() + ")";
      }
      if (!Utilities.noString(msg)) {
        msg = msg + " (log: " + msg + ")";
      }
      String msgId = null;
      if (inv.hasSource()) {
        msg = context.formatMessage(I18nConstants.INV_FAILED_SOURCE, inv.getKey() + ": '" + inv.getHuman()+"'", inv.getSource())+msg;
        msgId = inv.getSource()+"#"+inv.getKey();
      } else {
        msgId = profile.getUrl()+"#"+inv.getKey();
        msg = context.formatMessage(I18nConstants.INV_FAILED, inv.getKey() + ": '" + inv.getHuman()+"'")+msg;
      }
      String invId = (inv.hasSource() ? inv.getSource() : profile.getUrl()) + "#"+inv.getKey();
      
      if (inv.hasExtension(ExtensionDefinitions.EXT_BEST_PRACTICE) &&
        ExtensionUtilities.readBooleanExtension(inv, ExtensionDefinitions.EXT_BEST_PRACTICE)) {
        msg = msg +" (Best Practice Recommendation)";
        if (settings.getBpWarnings() == BestPracticeWarningLevel.Hint)
          hintInv(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, invOK, msg, invId, msgId);
        else if (/*bpWarnings == null || */ settings.getBpWarnings() == BestPracticeWarningLevel.Warning)
          warningInv(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, invOK, msg, invId, msgId);
        else if (settings.getBpWarnings() == BestPracticeWarningLevel.Error)
          ok = ruleInv(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, invOK, msg, invId, msgId) && ok;
      } else if (inv.getSeverity() == ConstraintSeverity.ERROR) {
        ok = ruleInv(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, invOK, msg, invId, msgId) && ok;
      } else if (inv.getSeverity() == ConstraintSeverity.WARNING) {
        warningInv(errors, NO_RULE_DATE, IssueType.INVARIANT, element.line(), element.col(), path, invOK, msg, invId, msgId);
      }
    }
    return ok;
  }
  
  private boolean IsExemptInvariant(String path, Element element, ElementDefinitionConstraintComponent inv) {
    if ("eld-24".equals(inv.getKey())) {
      String p = element.getNamedChildValue("path", false);
      return (p != null) && ((p.endsWith("xtension.url") || p.endsWith(".id")));
    }
    return false;
  }

  /*
   * The actual base entry point for internal use (re-entrant)
   */
  private boolean validateResource(ValidationContext valContext, List<ValidationMessage> errors, Element resource,
                                   Element element, StructureDefinition defn, IdStatus idstatus, NodeStack stack, ResourcePercentageLogger pct, ValidationMode mode, boolean forReference, boolean fromContained) throws FHIRException {
    boolean ok = true;    
    // check here if we call validation policy here, and then change it to the new interface
    assert stack != null;
    assert resource != null;
    boolean rok = true;
    String resourceName = element.getType(); // todo: consider namespace...?

    if (defn == null) {
      long t = System.nanoTime();
      defn = element.getProperty().getStructure();
      if (defn == null)
        defn = context.fetchResource(StructureDefinition.class, "http://hl7.org/fhir/StructureDefinition/" + resourceName);
      timeTracker.sd(t);
      //check exists
      rok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.addToLiteralPath(resourceName),
        defn != null, I18nConstants.VALIDATION_VAL_PROFILE_NODEFINITION, resourceName);
      ok = rok && ok;
    }

    // special case: we have a bundle, and the profile is not for a bundle. We'll try the first entry instead
    if (!typeMatchesDefn(resourceName, defn) && resourceName.equals(BUNDLE)) {
      NodeStack first = getFirstEntry(stack);
      if (first != null && typeMatchesDefn(first.getElement().getType(), defn)) {
        element = first.getElement();
        stack = first;
        resourceName = element.getType(); // todo: consider namespace...?
        idstatus = IdStatus.OPTIONAL; // why?
      }
      // todo: validate everything in this bundle.
    }
    if (rok) {
      // todo: not clear what we should do with regard to logical models - should they have ids? Should we check anything?
      if (element.getProperty().getStructure().getKind() != StructureDefinitionKind.LOGICAL) {
        if (idstatus == IdStatus.REQUIRED && (element.getNamedChild(ID, false) == null)) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.RESOURCE_RES_ID_MISSING) && ok;
        } else if (idstatus == IdStatus.PROHIBITED && (element.getNamedChild(ID, false) != null)) {
          ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), false, I18nConstants.RESOURCE_RES_ID_PROHIBITED) && ok;
        }
        if (element.getNamedChild(ID, false) != null) {
          Element eid = element.getNamedChild(ID, false);
          if (eid.getProperty() != null && eid.getProperty().getDefinition() != null && eid.getProperty().getDefinition().getBase().getPath().equals("Resource.id")) {
            NodeStack ns = stack.push(eid, -1, eid.getProperty().getDefinition(), null);
            if (eid.primitiveValue() != null && eid.primitiveValue().length() > 64) {
              ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, eid.line(), eid.col(), ns.getLiteralPath(), false, I18nConstants.RESOURCE_RES_ID_MALFORMED_LENGTH, eid.primitiveValue().length()) && ok;
            } else {
              ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, eid.line(), eid.col(), ns.getLiteralPath(), FormatUtilities.isValidId(eid.primitiveValue()), I18nConstants.RESOURCE_RES_ID_MALFORMED_CHARS, eid.primitiveValue()) && ok;
            }
          }
        }
      }
      boolean isMatcheType = false;
      if (element.hasExtension("http://hl7.org/fhir/tools/StructureDefinition/matchetype")) {
        Element ext = element.getExtension("http://hl7.org/fhir/tools/StructureDefinition/matchetype");
        isMatcheType = Utilities.existsInList(ext.getNamedChildValue("value"), "partial", "complete", "true");
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), matchetypeStatus != MatchetypeStatus.Disallowed, I18nConstants.RESOURCE_MATCHETYPE_DISALLOWED) && ok;        
      } else {
        ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), matchetypeStatus != MatchetypeStatus.Required, I18nConstants.RESOURCE_MATCHETYPE_REQUIRED) && ok;        
      }
      valContext.setMatchetype(isMatcheType);
      
      // validate
      if (rule(errors, NO_RULE_DATE, IssueType.INVALID, element.line(), element.col(), stack.getLiteralPath(), checkResourceName(defn, resourceName, element.getFormat()), I18nConstants.VALIDATION_VAL_PROFILE_WRONGTYPE,
          defn.getType(), resourceName, defn.getVersionedUrl())) {
        ok = start(valContext, errors, element, element, defn, stack, pct, mode, fromContained) && ok; // root is both definition and type
      } else {
        ok = false;
      }
      if (!forReference) {
        // last step: check that all contained resources are referenced or reference #
        ok = checkContainedReferences(valContext, errors, element, stack) && ok;
      }
    }
    if (testMode && ok == hasErrors(errors)) {
      throw new Error("ok is wrong. ok = "+ok+", errors = "+errorIds(stack.getLiteralPath(), ok, errors));
    }
    return ok;
  }

  private boolean checkContainedReferences(ValidationContext valContext, List<ValidationMessage> errors, Element element, NodeStack stack) {
    boolean ok = true;
    Set<String> baseRefs = (Set<String>) element.getUserData(ValidationContext.INTERNAL_REFERENCES_NAME);
    List<Element> containedList = element.getChildrenByName("contained");
    if (!containedList.isEmpty()) {
      boolean allDone = true;
      for (Element contained : containedList) {
        allDone = allDone && contained.hasUserData(ValidationContext.INTERNAL_REFERENCES_NAME);
      }
      if (allDone) {
        // We collected all the internal references in sets on the resource and the contained resources
        int i = 0;
        for (Element contained : containedList) {
          ok = checkContainedReferences(errors, stack, ok, baseRefs, containedList, i, contained);
          i++;
        }
      }
    }
    return ok;
  }

  private boolean checkContainedReferences(List<ValidationMessage> errors, NodeStack stack, boolean ok,
      Set<String> baseRefs, List<Element> containedList, int i, Element contained) {
    NodeStack n = stack.push(contained, i, null, null);
    boolean found = isReferencedFromBase(contained, baseRefs, containedList, new ArrayList<>());

    ok = rule(errors, NO_RULE_DATE, IssueType.INVALID, n, found, I18nConstants.CONTAINED_ORPHAN_DOM3, contained.getIdBase()) && ok;
    return ok;
  }

  private boolean isReferencedFromBase(Element contained, Set<String> baseRefs, List<Element> containedList, List<Element> ignoreList) {
    String id = contained.getIdBase();
    if (baseRefs.contains(id)) {
      return true;
    }
    Set<String> irefs = (Set<String>) contained.getUserData(ValidationContext.INTERNAL_REFERENCES_NAME);
    if (irefs.contains("")) {
      return true;
    }
    for (Element c : containedList) {
      if (c != contained && !ignoreList.contains(c)) { // ignore list is to prevent getting into an unterminated loop
        Set<String> refs = (Set<String>) c.getUserData(ValidationContext.INTERNAL_REFERENCES_NAME);
        List<Element> ignoreList2 = new ArrayList<Element>();
        ignoreList2.addAll(ignoreList);
        ignoreList2.add(c);
        if (refs != null && refs.contains(id) && isReferencedFromBase(c, baseRefs, containedList, ignoreList2)) {
          return true;
        }            
      }
    }
    return false;
  }

  private boolean checkResourceName(StructureDefinition defn, String resourceName, FhirFormat format) {
    if (resourceName.equals(defn.getType())) {
      return true;
    } 
    if (resourceName.equals(defn.getTypeTail())) {
      return true;
    }
    if (format == FhirFormat.XML) {
      String xn = ExtensionUtilities.readStringExtension(defn, ExtensionDefinitions.EXT_XML_NAME);
      if (resourceName.equals(xn)) {
        return true;
      }
    }
    return false;
  }

  private String errorIds(String path, boolean ok, List<ValidationMessage> errors) {
    CommaSeparatedStringBuilder b = new CommaSeparatedStringBuilder();
    for (ValidationMessage vm : errors) {
      if (vm.isError()) {
        b.append(vm.getMessageId());
      }
    }
    return b.toString();
  }

  private boolean typeMatchesDefn(String name, StructureDefinition defn) {
    if (defn.getKind() == StructureDefinitionKind.LOGICAL) {
      return name.equals(defn.getType()) || name.equals(defn.getName()) || name.equals(defn.getId());
    } else {
      return name.matches(defn.getType());
    }
  }

  private NodeStack getFirstEntry(NodeStack bundle) {
    List<Element> list = new ArrayList<Element>();
    bundle.getElement().getNamedChildren(ENTRY, list);
    if (list.isEmpty())
      return null;
    Element resource = list.get(0).getNamedChild(RESOURCE, false);
    if (resource == null)
      return null;
    else {
      NodeStack entry = bundle.push(list.get(0), 0, list.get(0).getProperty().getDefinition(), list.get(0).getProperty().getDefinition());
      return entry.push(resource, -1, resource.getProperty().getDefinition(), context.fetchTypeDefinition(resource.fhirType()).getSnapshot().getElementFirstRep());
    }
  }

  private boolean valueMatchesCriteria(Element value, ElementDefinition criteria, StructureDefinition profile) throws FHIRException {
    if (criteria.hasFixed()) {
      List<ValidationMessage> msgs = new ArrayList<ValidationMessage>();
      checkFixedValue(msgs, "{virtual}", value, criteria.getFixed(), profile.getVersionedUrl(), "value", null, false, profile.getVersionedUrl()+"#"+criteria.getId());
      return msgs.size() == 0;
    } else if (criteria.hasBinding() && criteria.getBinding().getStrength() == BindingStrength.REQUIRED && criteria.getBinding().hasValueSet()) {
      throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_RESOLVE_SLICE_MATCHING__SLICE_MATCHING_BY_VALUE_SET_NOT_DONE));
    } else {
      throw new FHIRException(context.formatMessage(I18nConstants.UNABLE_TO_RESOLVE_SLICE_MATCHING__NO_FIXED_VALUE_OR_REQUIRED_VALUE_SET));
    }
  }

  private boolean yearIsValid(String v) {
    if (v == null) {
      return false;
    }
    try {
      int i = Integer.parseInt(v.substring(0, Math.min(4, v.length())));
      return i >= 1800 && i <= thisYear() + 80;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private int thisYear() {
    return Calendar.getInstance().get(Calendar.YEAR);
  }


  public long timeNoTX() {
    return (timeTracker.getOverall() - timeTracker.getTxTime()) / 1000000;
  }
  public String reportTimes() {
    String s = String.format("Times (ms): overall = %d:4, tx = %d, sd = %d, load = %d, fpe = %d, spec = %d, ai = %d",
        timeTracker.getOverall() / 1000000, timeTracker.getTxTime() / 1000000, timeTracker.getSdTime() / 1000000, 
        timeTracker.getLoadTime() / 1000000, timeTracker.getFpeTime() / 1000000, timeTracker.getSpecTime() / 1000000, timeTracker.getAiTime() / 1000000);
    timeTracker.reset();
    return s;
  }

  public boolean isNoBindingMsgSuppressed() {
    return noBindingMsgSuppressed;
  }

  public IResourceValidator setNoBindingMsgSuppressed(boolean noBindingMsgSuppressed) {
    this.noBindingMsgSuppressed = noBindingMsgSuppressed;
    return this;
  }


  public boolean isNoTerminologyChecks() {
    return noTerminologyChecks;
  }

  public IResourceValidator setNoTerminologyChecks(boolean noTerminologyChecks) {
    this.noTerminologyChecks = noTerminologyChecks;
    return this;
  }

  public void checkAllInvariants() {
    for (StructureDefinition sd : new ContextUtilities(context).allStructures()) {
      if (sd.getDerivation() == TypeDerivationRule.SPECIALIZATION) {
        for (ElementDefinition ed : sd.getSnapshot().getElement()) {
          for (ElementDefinitionConstraintComponent inv : ed.getConstraint()) {
            if (inv.hasExpression()) {
              try {
                ExpressionNode n = (ExpressionNode) inv.getUserData(UserDataNames.validator_expression_cache);
                if (n == null) {
                  n = fpe.parse(FHIRPathExpressionFixer.fixExpr(inv.getExpression(), inv.getKey(), context.getVersion()));
                  inv.setUserData(UserDataNames.validator_expression_cache, n);
                }
                fpe.check(null, "Resource", sd.getKind() == StructureDefinitionKind.RESOURCE ? sd.getType() : "DomainResource", ed.getPath(), n);
              } catch (Exception e) {
                log.error("Error processing structure [" + sd.getId() + "] path " + ed.getPath() + ":" + inv.getKey() + " ('" + inv.getExpression() + "'): " + e.getMessage(), e);
              }
            }
          }
        }
      }
    }
  }

  private String tail(String path) {
    return path.substring(path.lastIndexOf(".") + 1);
  }
  private String tryParse(String ref) {
    String[] parts = ref.split("\\/");
    switch (parts.length) {
      case 1:
        return null;
      case 2:
        return checkResourceType(parts[0]);
      default:
        if (parts[parts.length - 2].equals("_history") && parts.length >= 4)
          return checkResourceType(parts[parts.length - 4]);
        else
          return checkResourceType(parts[parts.length - 2]);
    }
  }

  private boolean typesAreAllReference(List<TypeRefComponent> theType) {
    for (TypeRefComponent typeRefComponent : theType) {
      if (typeRefComponent.getCode().equals("Reference") == false) {
        return false;
      }
    }
    return true;
  }


  public ValidationResult checkCodeOnServer(NodeStack stack, ValueSet vs, String value, ValidationOptions options) {
    String lang = getValidationOptionsLanguage(stack);
    return checkForInactive(filterOutSpecials(stack.getLiteralPath(), vs, context.validateCode(options.withLanguage(lang), value, vs)), new CodeType(value));
  }

  static class CheckCodeOnServerException extends Exception {
    public CheckCodeOnServerException(Exception e) {
      super(e);
    }
  }

  public String getValidationOptionsLanguage(NodeStack stack) {
    if (stack.getWorkingLang() != null) {
      return stack.getWorkingLang();
    }
    if (validationLanguage != null) {
      return validationLanguage;
    }
    if (context.getLocale() != null) {
      return context.getLocale().toLanguageTag();
    }
    return Locale.US.toLanguageTag();
  }
  // no delay on this one? 
  public ValidationResult checkCodeOnServer(NodeStack stack, String code, String system, String version, String display, boolean checkDisplay) {
    String lang = getValidationOptionsLanguage(stack);
    codingObserver.seeCode(stack, system, version, code, display);
    return checkForInactive(filterOutSpecials(stack.getLiteralPath(), null, context.validateCode(settings.withLanguage(lang), system, version, code, checkDisplay ? display : null)), new Coding(system, version, code, display));
  }

  public ValidationResult checkCodeOnServer(NodeStack stack, ValueSet valueset, Coding c) {
    codingObserver.seeCode(stack, c);
    String lang = getValidationOptionsLanguage(stack);
    return checkForInactive(filterOutSpecials(stack.getLiteralPath(), valueset, context.validateCode(settings.withLanguage(lang), c, valueset)), c);
  }

  public ValidationResult checkCodeOnServer(NodeStack stack, ValueSet valueset, CodeableConcept cc) throws CheckCodeOnServerException {
    codingObserver.seeCode(stack, cc);
    try {
      String lang = getValidationOptionsLanguage(stack);
      return checkForInactive(filterOutSpecials(stack.getLiteralPath(), valueset, context.validateCode(settings.withLanguage(lang), cc, valueset)), cc);
    } catch (Exception e) {
      throw new CheckCodeOnServerException(e);
    }
  }

  private ValidationResult filterOutSpecials(String path, ValueSet vs, ValidationResult vr) {
    // this is where we hack around problems in the infrastructure that lead to technically correct errors
    // but that are wrong to the validator user
    
    // first case: the type value set is wrong for primitive special types
    for (OperationOutcomeIssueComponent iss : vr.getIssues()) {
      if (iss.hasDetails() && iss.getDetails().hasText() && iss.getDetails().getText().startsWith("Unable to resolve system - value set expansion has no matches for code 'http://hl7.org/fhirpath/System")) {
        return new ValidationResult("http://hl7.org/fhirpath/System", null, null, null);
      }
    }
    
    return vr; 
    
  }
  
  private ValidationResult checkForInactive(ValidationResult res, DataType coded) {
    if (res == null) {
      return null;
    }
    if (!res.isInactive()) {
      return res;
    }
    for (OperationOutcomeIssueComponent iss : res.getIssues()) {
      if (iss.getDetails().getText().contains("not active")) {
        return res;
      }
    }
    org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity lvl = org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.INFORMATION;
    var status = "not active";
    if (res.getStatus() != null) {
      status = res.getStatus();
    }
    String code = res.getCode();
    var op = new OperationOutcomeIssueComponent(lvl, org.hl7.fhir.r5.model.OperationOutcome.IssueType.INVALID);
    String msgId = null;
    if (code != null) {
      msgId = res.isOk() ? I18nConstants.STATUS_CODE_WARNING_CODE : I18nConstants.STATUS_CODE_HINT_CODE;
    } else {
      msgId = res.isOk() ? I18nConstants.STATUS_CODE_WARNING : I18nConstants.STATUS_CODE_HINT;
    }
    op.getDetails().setText(context.formatMessage(msgId, status, code));
    if (res.getServer() != null) {
      op.addExtension(ExtensionDefinitions.EXT_ISSUE_SERVER, new UrlType(res.getServer()));
    }
    if (coded instanceof CodeType) {
      op.addExpression(coded.fhirType());
      op.addLocation(coded.fhirType());
    } else if (coded instanceof Coding) {
      op.addExpression(coded.fhirType()+".code");
      op.addLocation(coded.fhirType()+".code");
    } else if (coded instanceof CodeableConcept) {
      CodeableConcept cc = (CodeableConcept) coded;
      if (cc.getCoding().size() == 1) {
        op.addExpression(coded.fhirType()+".coding[0].code");
        op.addLocation(coded.fhirType()+".coding[0].code");        
      } else {
        op.addExpression(coded.fhirType());
        op.addLocation(coded.fhirType());
      }
    }
    res.getIssues().add(op);
    return res;
  }

  public boolean isSecurityChecks() {
    return securityChecks;
  }

  public void setSecurityChecks(boolean securityChecks) {
    this.securityChecks = securityChecks;
  }

  @Override
  public List<BundleValidationRule> getBundleValidationRules() {
    return bundleValidationRules ;
  }

  @Override
  public boolean isValidateValueSetCodesOnTxServer() {
    return validateValueSetCodesOnTxServer;
  }

  @Override
  public void setValidateValueSetCodesOnTxServer(boolean value) {
    this.validateValueSetCodesOnTxServer = value;    
  }

  public boolean isNoCheckAggregation() {
    return noCheckAggregation;
  }

  public void setNoCheckAggregation(boolean noCheckAggregation) {
    this.noCheckAggregation = noCheckAggregation;
  }

 
  public boolean isAllowDoubleQuotesInFHIRPath() {
    return allowDoubleQuotesInFHIRPath;
  }

  public void setAllowDoubleQuotesInFHIRPath(boolean allowDoubleQuotesInFHIRPath) {
    this.allowDoubleQuotesInFHIRPath = allowDoubleQuotesInFHIRPath;
  }

  public static Element setParents(Element element) {
    if (element != null && !element.hasParentForValidator()) {
      element.setParentForValidator(null);
      setParentsInner(element);
    }
    return element;
  }
  
  public static Base setParentsBase(Base element) {
    if (element instanceof Element) {
      setParents((Element) element);
    }
    return element;
  }
  
  public static void setParentsInner(Element element) {
    for (Element child : element.getChildren()) {
      child.setParentForValidator(element);
      setParentsInner(child);
    }
  }

  public void setQuestionnaireMode(QuestionnaireMode questionnaireMode) {
    this.questionnaireMode = questionnaireMode;
  }

  public QuestionnaireMode getQuestionnaireMode() {
    return questionnaireMode;
  }

  public boolean isWantCheckSnapshotUnchanged() {
    return wantCheckSnapshotUnchanged;
  }

  public void setWantCheckSnapshotUnchanged(boolean wantCheckSnapshotUnchanged) {
    this.wantCheckSnapshotUnchanged = wantCheckSnapshotUnchanged;
  }


  public boolean isNoUnicodeBiDiControlChars() {
    return noUnicodeBiDiControlChars;
  }

  public void setNoUnicodeBiDiControlChars(boolean noUnicodeBiDiControlChars) {
    this.noUnicodeBiDiControlChars = noUnicodeBiDiControlChars;
  }



  public HtmlInMarkdownCheck getHtmlInMarkdownCheck() {
    return htmlInMarkdownCheck;
  }

  public void setHtmlInMarkdownCheck(HtmlInMarkdownCheck htmlInMarkdownCheck) {
    this.htmlInMarkdownCheck = htmlInMarkdownCheck;
  }

  public boolean isLogProgress() {
    return logProgress;
  }

  public void setLogProgress(boolean logProgress) {
    this.logProgress = logProgress;
  }


  public boolean isCheckIPSCodes() {
    return codingObserver.isCheckIPSCodes();
  }

  public void setCheckIPSCodes(boolean checkIPSCodes) {
    codingObserver.setCheckIPSCodes(checkIPSCodes);
  }

  public boolean isTestMode() {
    return testMode;
  }

  public void setTestMode(boolean testMode) {
    this.testMode = testMode;
  }
  

  public boolean isExample() {
    return example;
  }
  
  public IResourceValidator setExample(boolean example) {
    this.example = example;
    return this;
  }

  public IDigitalSignatureServices getSignatureServices() {
    return signatureServices;
  }

  public void setSignatureServices(IDigitalSignatureServices signatureServices) {
    this.signatureServices = signatureServices;
  }

  public IValidatorResourceFetcher getFetcher() {
    return this.fetcher;
  }

  public IResourceValidator setFetcher(IValidatorResourceFetcher value) {
    this.fetcher = value;
    return this;
  }

  public boolean isUnknownCodeSystemsCauseErrors() {
    return unknownCodeSystemsCauseErrors;
  }

  public void setUnknownCodeSystemsCauseErrors(boolean unknownCodeSystemsCauseErrors) {
    this.unknownCodeSystemsCauseErrors = unknownCodeSystemsCauseErrors;
  }

  public boolean isNoExperimentalContent() {
    return noExperimentalContent;
  }

  public void setNoExperimentalContent(boolean noExperimentalContent) {
    this.noExperimentalContent = noExperimentalContent;
  }

  public void resetTimes() {
    timeTracker.reset();   
  }

  public List<CodeAndTextValidationRequest> getTextsToCheck() {
    return textsToCheck;
  }

  public String getAIService() {
    return aiService;
  }

  public void setAIService(String aiService) {
    this.aiService = aiService;
  }

  public String getCacheFolder() {
    return cacheFolder;
  }

  public void setCacheFolder(String cacheFolder) {
    this.cacheFolder = cacheFolder;
  }

  public MatchetypeStatus getMatchetypeStatus() {
    return matchetypeStatus;
  }

  public void setMatchetypeStatus(MatchetypeStatus matchetypeStatus) {
    this.matchetypeStatus = matchetypeStatus;
  }


  @Override
  public BestPracticeWarningLevel getBestPracticeWarningLevel() {
    return settings.getBpWarnings();
  }

  @Override
  public IResourceValidator setBestPracticeWarningLevel(BestPracticeWarningLevel value) {
    settings.setBpWarnings(value);
    return this;
  }

  @Override
  public boolean isForPublication() {
    return settings.isForPublication();
  }

  @Override
  public IResourceValidator setForPublication(boolean forPublication) {
    settings.setForPublication(forPublication);
    return this;
  }

  @Override
  public List<UsageContext> getUsageContexts() {
    return settings.getUsageContexts();
  }

  @Override
  public boolean isWarnOnDraftOrExperimental() {
    return settings.isWarnOnDraftOrExperimental();
  }

  @Override
  public IResourceValidator setWarnOnDraftOrExperimental(boolean warnOnDraftOrExperimental) {
    settings.setWarnOnDraftOrExperimental(warnOnDraftOrExperimental);
    return this;
  }

  @Override
  public boolean isAllowExamples() {
    return settings.isAllowExamples();
  }

  @Override
  public void setAllowExamples(boolean value) {
    settings.setAllowExamples(value);
  }

  @Override
  public Coding getJurisdiction() {
    return settings.getJurisdiction(); 
  }

  @Override
  public IResourceValidator setJurisdiction(Coding jurisdiction) {
    settings.setJurisdiction(jurisdiction);
    return this;
  }
}
