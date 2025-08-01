package org.hl7.fhir.r5.terminologies.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.context.ContextUtilities;
import org.hl7.fhir.r5.context.IWorkerContext;
import org.hl7.fhir.r5.context.IWorkerContext.ITerminologyOperationDetails;
import org.hl7.fhir.r5.extensions.ExtensionDefinitions;
import org.hl7.fhir.r5.extensions.ExtensionUtilities;
import org.hl7.fhir.r5.model.BooleanType;
import org.hl7.fhir.r5.model.CanonicalResource;
import org.hl7.fhir.r5.model.CodeSystem;
import org.hl7.fhir.r5.model.DataType;
import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r5.model.OperationOutcome.IssueType;
import org.hl7.fhir.r5.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.hl7.fhir.r5.model.Extension;
import org.hl7.fhir.r5.model.Parameters;
import org.hl7.fhir.r5.model.Parameters.ParametersParameterComponent;
import org.hl7.fhir.r5.model.StringType;
import org.hl7.fhir.r5.model.UriType;
import org.hl7.fhir.r5.model.UrlType;
import org.hl7.fhir.r5.model.ValueSet;
import org.hl7.fhir.r5.model.ValueSet.ValueSetExpansionComponent;

import org.hl7.fhir.r5.utils.UserDataNames;
import org.hl7.fhir.utilities.MarkedToMoveToAdjunctPackage;
import org.hl7.fhir.utilities.StandardsStatus;
import org.hl7.fhir.utilities.Utilities;
import org.hl7.fhir.utilities.i18n.I18nConstants;
import org.hl7.fhir.utilities.validation.ValidationMessage.IssueSeverity;

@MarkedToMoveToAdjunctPackage
public class ValueSetProcessBase {

  public static class TerminologyOperationDetails implements ITerminologyOperationDetails {

    private List<String> supplements;

    public TerminologyOperationDetails(List<String> supplements) {
      super();
      this.supplements = supplements;
    }

    @Override
    public void seeSupplement(CodeSystem supp) {
      supplements.remove(supp.getUrl());
      supplements.remove(supp.getVersionedUrl());
    }
  }
  
  public enum OpIssueCode {
    NotInVS, ThisNotInVS, InvalidCode, Display, DisplayComment, NotFound, CodeRule, VSProcessing, InferFailed, StatusCheck, InvalidData;
    
    public String toCode() {
      switch (this) {
      case CodeRule: return "code-rule";
      case Display: return "invalid-display";
      case DisplayComment: return "display-comment";
      case InferFailed: return "cannot-infer";
      case InvalidCode: return "invalid-code";
      case NotFound: return "not-found";
      case NotInVS: return "not-in-vs";
      case InvalidData: return "invalid-data";
      case StatusCheck: return "status-check";
      case ThisNotInVS: return "this-code-not-in-vs";
      case VSProcessing: return "vs-invalid";
      default:
        return "??";      
      }
    }
  }
  protected IWorkerContext context;
  private ContextUtilities cu;
  protected TerminologyOperationContext opContext;
  protected List<String> requiredSupplements = new ArrayList<>();
  
  protected ValueSetProcessBase(IWorkerContext context, TerminologyOperationContext opContext) {
    super();
    this.context = context;
    this.opContext = opContext;
  }
  public static class AlternateCodesProcessingRules {
    private boolean all;
    private List<String> uses = new ArrayList<>();
    
    public AlternateCodesProcessingRules(boolean b) {
      all = b;
    }

    private void seeParameter(DataType value) {
      if (value != null) {
        if (value instanceof BooleanType) {
          all = ((BooleanType) value).booleanValue();
          uses.clear();
        } else if (value.isPrimitive()) {
          String s = value.primitiveValue();
          if (!Utilities.noString(s)) {
            uses.add(s);
          }
        }
      }
    }

    public void seeParameters(Parameters pp) {
      for (ParametersParameterComponent p : pp.getParameter()) {
        String name = p.getName();
        if ("includeAlternateCodes".equals(name)) {
          DataType value = p.getValue();
          seeParameter(value);
        }
      }
    }

    public void seeValueSet(ValueSet vs) {
      if (vs != null) {
        for (Extension ext : vs.getCompose().getExtension()) {
          if (Utilities.existsInList(ext.getUrl(), ExtensionDefinitions.EXT_VS_EXP_PARAM_NEW, ExtensionDefinitions.EXT_VS_EXP_PARAM_OLD)) {
            String name = ext.getExtensionString("name");
            Extension value = ext.getExtensionByUrl("value");
            if ("includeAlternateCodes".equals(name) && value != null && value.hasValue()) {
              seeParameter(value.getValue());
            }
          }
        }
      }
    }

    public boolean passes(List<Extension> extensions) {
      if (all) {
        return true;
      }

      for (Extension ext : extensions) {
        if (ExtensionDefinitions.EXT_CS_ALTERNATE_USE.equals(ext.getUrl())) {
          if (ext.hasValueCoding() && Utilities.existsInList(ext.getValueCoding().getCode(), uses)) {
            return true;
          }
        }
      }
      return false;
    }
  }


  protected List<OperationOutcomeIssueComponent> makeIssue(IssueSeverity level, IssueType type, String location, String message, OpIssueCode code, String server) {
    return makeIssue(level, type, location, message, code, server, null);
  }
  protected List<OperationOutcomeIssueComponent> makeIssue(IssueSeverity level, IssueType type, String location, String message, OpIssueCode code, String server, String msgId) {
    OperationOutcomeIssueComponent result = new OperationOutcomeIssueComponent();
    switch (level) {
    case ERROR:
      result.setSeverity(org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.ERROR);
      break;
    case FATAL:
      result.setSeverity(org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.FATAL);
      break;
    case INFORMATION:
      result.setSeverity(org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.INFORMATION);
      break;
    case WARNING:
      result.setSeverity(org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity.WARNING);
      break;
    }
    result.setCode(type);
    if (location != null) {
      result.addLocation(location);
      result.addExpression(location);
    }
    result.getDetails().setText(message);
    if (code != null) {
      result.getDetails().addCoding("http://hl7.org/fhir/tools/CodeSystem/tx-issue-type", code.toCode(), null);
    }
    if (server != null) {
      result.addExtension(ExtensionDefinitions.EXT_ISSUE_SERVER, new UrlType(server));
    }
    if (msgId != null) {      
      result.addExtension(ExtensionDefinitions.EXT_ISSUE_MSG_ID, new StringType(msgId));
    }
    ArrayList<OperationOutcomeIssueComponent> list = new ArrayList<>();
    list.add(result);
    return list;
  }
  
  public void checkCanonical(List<OperationOutcomeIssueComponent> issues, String path, CanonicalResource resource, CanonicalResource source) {
    if (resource != null) {
      StandardsStatus standardsStatus = ExtensionUtilities.getStandardsStatus(resource);
      if (standardsStatus == StandardsStatus.DEPRECATED) {
        addToIssues(issues, makeStatusIssue(path, "deprecated", I18nConstants.MSG_DEPRECATED, resource));
      } else if (standardsStatus == StandardsStatus.WITHDRAWN) {
        addToIssues(issues, makeStatusIssue(path, "withdrawn", I18nConstants.MSG_WITHDRAWN, resource));
      } else if (resource.getStatus() == PublicationStatus.RETIRED) {
        addToIssues(issues, makeStatusIssue(path, "retired", I18nConstants.MSG_RETIRED, resource));
      } else if (source != null) {
        if (resource.getExperimental() && !source.getExperimental()) {
          addToIssues(issues, makeStatusIssue(path, "experimental", I18nConstants.MSG_EXPERIMENTAL, resource));
        } else if ((resource.getStatus() == PublicationStatus.DRAFT || standardsStatus == StandardsStatus.DRAFT)
            && !(source.getStatus() == PublicationStatus.DRAFT || ExtensionUtilities.getStandardsStatus(source) == StandardsStatus.DRAFT)) {
          addToIssues(issues, makeStatusIssue(path, "draft", I18nConstants.MSG_DRAFT, resource));
        }
      } else {
        if (resource.getExperimental()) {
          addToIssues(issues, makeStatusIssue(path, "experimental", I18nConstants.MSG_EXPERIMENTAL, resource));
        } else if ((resource.getStatus() == PublicationStatus.DRAFT || standardsStatus == StandardsStatus.DRAFT)) {
          addToIssues(issues, makeStatusIssue(path, "draft", I18nConstants.MSG_DRAFT, resource));
        }
      }
    }
  }

  private List<OperationOutcomeIssueComponent> makeStatusIssue(String path, String id, String msg, CanonicalResource resource) {
    List<OperationOutcomeIssueComponent> iss = makeIssue(IssueSeverity.INFORMATION, IssueType.BUSINESSRULE, null, context.formatMessage(msg, resource.getVersionedUrl(), null, resource.fhirType()), OpIssueCode.StatusCheck, null);

    // this is a testing hack - see TerminologyServiceTests
    iss.get(0).setUserData(UserDataNames.tx_status_msg_name, "warning-"+id);
    iss.get(0).setUserData(UserDataNames.tx_status_msg_value, new UriType(resource.getVersionedUrl()));
    ExtensionUtilities.setStringExtension(iss.get(0), ExtensionDefinitions.EXT_ISSUE_MSG_ID, msg);
    
    return iss;
  }
  
  private void addToIssues(List<OperationOutcomeIssueComponent> issues, List<OperationOutcomeIssueComponent> toAdd) {
    for (OperationOutcomeIssueComponent t : toAdd) {
      boolean found = false;
      for (OperationOutcomeIssueComponent i : issues) {
        if (i.getSeverity() == t.getSeverity() && i.getCode() == t.getCode() && i.getDetails().getText().equals(t.getDetails().getText())) { // ignore location
          found = true;
        }
      }
      if (!found) {
        issues.add(t);
      }
    }    
  }

  public void checkCanonical(ValueSetExpansionComponent params, CanonicalResource resource, ValueSet source) {
    if (resource != null) {
      StandardsStatus standardsStatus = ExtensionUtilities.getStandardsStatus(resource);
      if (standardsStatus == StandardsStatus.DEPRECATED) {
        if (!params.hasParameterValue("warning-deprecated", resource.getVersionedUrl())) {
          params.addParameter("warning-deprecated", new UriType(resource.getVersionedUrl()));
        } 
      } else if (standardsStatus == StandardsStatus.WITHDRAWN) {
        if (!params.hasParameterValue("warning-withdrawn", resource.getVersionedUrl())) {
          params.addParameter("warning-withdrawn", new UriType(resource.getVersionedUrl()));
        } 
      } else if (resource.getStatus() == PublicationStatus.RETIRED) {
        if (!params.hasParameterValue("warning-retired", resource.getVersionedUrl())) {
          params.addParameter("warning-retired", new UriType(resource.getVersionedUrl()));
        } 
      } else if (resource.getExperimental() && !source.getExperimental()) {
        if (!params.hasParameterValue("warning-experimental", resource.getVersionedUrl())) {
          params.addParameter("warning-experimental", new UriType(resource.getVersionedUrl()));
        }         
      } else if ((resource.getStatus() == PublicationStatus.DRAFT || standardsStatus == StandardsStatus.DRAFT)
          && !(source.getStatus() == PublicationStatus.DRAFT || ExtensionUtilities.getStandardsStatus(source) == StandardsStatus.DRAFT)) {
        if (!params.hasParameterValue("warning-draft", resource.getVersionedUrl())) {
          params.addParameter("warning-draft", new UriType(resource.getVersionedUrl()));
        }         
      }
    }
  }

  public TerminologyOperationContext getOpContext() {
    return opContext;
  }

                         
  public ContextUtilities getCu() {
    if (cu == null) {
      cu = new ContextUtilities(context);
    }
    return cu;
  }


  public String removeSupplement(String s) {
    requiredSupplements.remove(s);
    if (s.contains("|")) {
      s = s.substring(0, s.indexOf("|"));
      requiredSupplements.remove(s);
    }
    return s;
  }
  
  protected AlternateCodesProcessingRules altCodeParams = new AlternateCodesProcessingRules(false);
  protected AlternateCodesProcessingRules allAltCodes = new AlternateCodesProcessingRules(true);
}
