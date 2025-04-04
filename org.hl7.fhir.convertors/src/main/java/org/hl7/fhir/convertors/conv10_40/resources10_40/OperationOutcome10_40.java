package org.hl7.fhir.convertors.conv10_40.resources10_40;

import org.hl7.fhir.convertors.context.ConversionContext10_40;
import org.hl7.fhir.convertors.conv10_40.datatypes10_40.complextypes10_40.CodeableConcept10_40;
import org.hl7.fhir.convertors.conv10_40.datatypes10_40.primitivetypes10_40.String10_40;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.OperationOutcome;

public class OperationOutcome10_40 {

  static public org.hl7.fhir.r4.model.Enumeration<org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity> convertIssueSeverity(org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      Enumeration<OperationOutcome.IssueSeverity> tgt = new Enumeration<>(new OperationOutcome.IssueSeverityEnumFactory());
      ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case FATAL:
                  tgt.setValue(OperationOutcome.IssueSeverity.FATAL);
                  break;
              case ERROR:
                  tgt.setValue(OperationOutcome.IssueSeverity.ERROR);
                  break;
              case WARNING:
                  tgt.setValue(OperationOutcome.IssueSeverity.WARNING);
                  break;
              case INFORMATION:
                  tgt.setValue(OperationOutcome.IssueSeverity.INFORMATION);
                  break;
              default:
                  tgt.setValue(OperationOutcome.IssueSeverity.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity> convertIssueSeverity(org.hl7.fhir.r4.model.Enumeration<org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity> tgt = new org.hl7.fhir.dstu2.model.Enumeration<>(new org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverityEnumFactory());
      ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case FATAL:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity.FATAL);
                  break;
              case ERROR:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity.ERROR);
                  break;
              case WARNING:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity.WARNING);
                  break;
              case INFORMATION:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity.INFORMATION);
                  break;
              default:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueSeverity.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.r4.model.Enumeration<org.hl7.fhir.r4.model.OperationOutcome.IssueType> convertIssueType(org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueType> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      Enumeration<OperationOutcome.IssueType> tgt = new Enumeration<>(new OperationOutcome.IssueTypeEnumFactory());
      ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case INVALID:
                  tgt.setValue(OperationOutcome.IssueType.INVALID);
                  break;
              case STRUCTURE:
                  tgt.setValue(OperationOutcome.IssueType.STRUCTURE);
                  break;
              case REQUIRED:
                  tgt.setValue(OperationOutcome.IssueType.REQUIRED);
                  break;
              case VALUE:
                  tgt.setValue(OperationOutcome.IssueType.VALUE);
                  break;
              case INVARIANT:
                  tgt.setValue(OperationOutcome.IssueType.INVARIANT);
                  break;
              case SECURITY:
                  tgt.setValue(OperationOutcome.IssueType.SECURITY);
                  break;
              case LOGIN:
                  tgt.setValue(OperationOutcome.IssueType.LOGIN);
                  break;
              case UNKNOWN:
                  tgt.setValue(OperationOutcome.IssueType.UNKNOWN);
                  break;
              case EXPIRED:
                  tgt.setValue(OperationOutcome.IssueType.EXPIRED);
                  break;
              case FORBIDDEN:
                  tgt.setValue(OperationOutcome.IssueType.FORBIDDEN);
                  break;
              case SUPPRESSED:
                  tgt.setValue(OperationOutcome.IssueType.SUPPRESSED);
                  break;
              case PROCESSING:
                  tgt.setValue(OperationOutcome.IssueType.PROCESSING);
                  break;
              case NOTSUPPORTED:
                  tgt.setValue(OperationOutcome.IssueType.NOTSUPPORTED);
                  break;
              case DUPLICATE:
                  tgt.setValue(OperationOutcome.IssueType.DUPLICATE);
                  break;
              case NOTFOUND:
                  tgt.setValue(OperationOutcome.IssueType.NOTFOUND);
                  break;
              case TOOLONG:
                  tgt.setValue(OperationOutcome.IssueType.TOOLONG);
                  break;
              case CODEINVALID:
                  tgt.setValue(OperationOutcome.IssueType.CODEINVALID);
                  break;
              case EXTENSION:
                  tgt.setValue(OperationOutcome.IssueType.EXTENSION);
                  break;
              case TOOCOSTLY:
                  tgt.setValue(OperationOutcome.IssueType.TOOCOSTLY);
                  break;
              case BUSINESSRULE:
                  tgt.setValue(OperationOutcome.IssueType.BUSINESSRULE);
                  break;
              case CONFLICT:
                  tgt.setValue(OperationOutcome.IssueType.CONFLICT);
                  break;
              case INCOMPLETE:
                  tgt.setValue(OperationOutcome.IssueType.INCOMPLETE);
                  break;
              case TRANSIENT:
                  tgt.setValue(OperationOutcome.IssueType.TRANSIENT);
                  break;
              case LOCKERROR:
                  tgt.setValue(OperationOutcome.IssueType.LOCKERROR);
                  break;
              case NOSTORE:
                  tgt.setValue(OperationOutcome.IssueType.NOSTORE);
                  break;
              case EXCEPTION:
                  tgt.setValue(OperationOutcome.IssueType.EXCEPTION);
                  break;
              case TIMEOUT:
                  tgt.setValue(OperationOutcome.IssueType.TIMEOUT);
                  break;
              case THROTTLED:
                  tgt.setValue(OperationOutcome.IssueType.THROTTLED);
                  break;
              case INFORMATIONAL:
                  tgt.setValue(OperationOutcome.IssueType.INFORMATIONAL);
                  break;
              default:
                  tgt.setValue(OperationOutcome.IssueType.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueType> convertIssueType(org.hl7.fhir.r4.model.Enumeration<org.hl7.fhir.r4.model.OperationOutcome.IssueType> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.OperationOutcome.IssueType> tgt = new org.hl7.fhir.dstu2.model.Enumeration<>(new org.hl7.fhir.dstu2.model.OperationOutcome.IssueTypeEnumFactory());
      ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case INVALID:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.INVALID);
                  break;
              case STRUCTURE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.STRUCTURE);
                  break;
              case REQUIRED:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.REQUIRED);
                  break;
              case VALUE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.VALUE);
                  break;
              case INVARIANT:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.INVARIANT);
                  break;
              case SECURITY:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.SECURITY);
                  break;
              case LOGIN:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.LOGIN);
                  break;
              case UNKNOWN:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.UNKNOWN);
                  break;
              case EXPIRED:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.EXPIRED);
                  break;
              case FORBIDDEN:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.FORBIDDEN);
                  break;
              case SUPPRESSED:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.SUPPRESSED);
                  break;
              case PROCESSING:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.PROCESSING);
                  break;
              case NOTSUPPORTED:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.NOTSUPPORTED);
                  break;
              case DUPLICATE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.DUPLICATE);
                  break;
              case NOTFOUND:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.NOTFOUND);
                  break;
              case TOOLONG:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.TOOLONG);
                  break;
              case CODEINVALID:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.CODEINVALID);
                  break;
              case EXTENSION:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.EXTENSION);
                  break;
              case TOOCOSTLY:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.TOOCOSTLY);
                  break;
              case BUSINESSRULE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.BUSINESSRULE);
                  break;
              case CONFLICT:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.CONFLICT);
                  break;
              case INCOMPLETE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.INCOMPLETE);
                  break;
              case TRANSIENT:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.TRANSIENT);
                  break;
              case LOCKERROR:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.LOCKERROR);
                  break;
              case NOSTORE:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.NOSTORE);
                  break;
              case EXCEPTION:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.EXCEPTION);
                  break;
              case TIMEOUT:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.TIMEOUT);
                  break;
              case THROTTLED:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.THROTTLED);
                  break;
              case INFORMATIONAL:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.INFORMATIONAL);
                  break;
              default:
                  tgt.setValue(org.hl7.fhir.dstu2.model.OperationOutcome.IssueType.NULL);
                  break;
          }
      }
      return tgt;
  }

  public static org.hl7.fhir.r4.model.OperationOutcome convertOperationOutcome(org.hl7.fhir.dstu2.model.OperationOutcome src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.r4.model.OperationOutcome tgt = new org.hl7.fhir.r4.model.OperationOutcome();
    ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyDomainResource(src, tgt);
    for (org.hl7.fhir.dstu2.model.OperationOutcome.OperationOutcomeIssueComponent t : src.getIssue())
      tgt.addIssue(convertOperationOutcomeIssueComponent(t));
    return tgt;
  }

  public static org.hl7.fhir.dstu2.model.OperationOutcome convertOperationOutcome(org.hl7.fhir.r4.model.OperationOutcome src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu2.model.OperationOutcome tgt = new org.hl7.fhir.dstu2.model.OperationOutcome();
    ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyDomainResource(src, tgt);
    for (org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent t : src.getIssue())
      tgt.addIssue(convertOperationOutcomeIssueComponent(t));
    return tgt;
  }

  public static org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent convertOperationOutcomeIssueComponent(org.hl7.fhir.dstu2.model.OperationOutcome.OperationOutcomeIssueComponent src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent tgt = new org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent();
    ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyBackboneElement(src,tgt);
    if (src.hasSeverity())
      tgt.setSeverityElement(convertIssueSeverity(src.getSeverityElement()));
    if (src.hasCode())
      tgt.setCodeElement(convertIssueType(src.getCodeElement()));
    if (src.hasDetails())
      tgt.setDetails(CodeableConcept10_40.convertCodeableConcept(src.getDetails()));
    if (src.hasDiagnosticsElement())
      tgt.setDiagnosticsElement(String10_40.convertString(src.getDiagnosticsElement()));
    for (org.hl7.fhir.dstu2.model.StringType t : src.getLocation()) tgt.addLocation(t.getValue());
    return tgt;
  }

  public static org.hl7.fhir.dstu2.model.OperationOutcome.OperationOutcomeIssueComponent convertOperationOutcomeIssueComponent(org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu2.model.OperationOutcome.OperationOutcomeIssueComponent tgt = new org.hl7.fhir.dstu2.model.OperationOutcome.OperationOutcomeIssueComponent();
    ConversionContext10_40.INSTANCE.getVersionConvertor_10_40().copyBackboneElement(src,tgt);
    if (src.hasSeverity())
      tgt.setSeverityElement(convertIssueSeverity(src.getSeverityElement()));
    if (src.hasCode())
      tgt.setCodeElement(convertIssueType(src.getCodeElement()));
    if (src.hasDetails())
      tgt.setDetails(CodeableConcept10_40.convertCodeableConcept(src.getDetails()));
    if (src.hasDiagnosticsElement())
      tgt.setDiagnosticsElement(String10_40.convertString(src.getDiagnosticsElement()));
    for (org.hl7.fhir.r4.model.StringType t : src.getLocation()) tgt.addLocation(t.getValue());
    return tgt;
  }
}