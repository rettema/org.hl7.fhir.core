package org.hl7.fhir.r5.model;


/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, \
  are permitted provided that the following conditions are met:
  
   * Redistributions of source code must retain the above copyright notice, this \
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, \
     this list of conditions and the following disclaimer in the documentation \
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND \
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED \
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. \
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, \
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT \
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR \
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, \
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) \
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE \
  POSSIBILITY OF SUCH DAMAGE.
  */

// Generated on Thu, Mar 23, 2023 19:59+1100 for FHIR v5.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.r5.extensions.ExtensionDefinitions;
import org.hl7.fhir.utilities.Utilities;
import org.hl7.fhir.r5.model.Enumerations.*;

import org.hl7.fhir.instance.model.api.IBaseBackboneElement;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.instance.model.api.ICompositeType;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.api.annotation.SearchParamDefinition;
import org.hl7.fhir.instance.model.api.IBaseBackboneElement;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.ChildOrder;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Block;

import org.hl7.fhir.instance.model.api.IBaseOperationOutcome;
/**
 * A collection of error, warning, or information messages that result from a system action.
 */
@ResourceDef(name="OperationOutcome", profile="http://hl7.org/fhir/StructureDefinition/OperationOutcome")
public class OperationOutcome extends DomainResource implements IBaseOperationOutcome {

    public enum IssueSeverity {
        /**
         * The issue caused the action to fail and no further checking could be performed.
         */
        FATAL, 
        /**
         * The issue is sufficiently important to cause the action to fail.
         */
        ERROR, 
        /**
         * The issue is not important enough to cause the action to fail but may cause it to be performed suboptimally or in a way that is not as desired.
         */
        WARNING, 
        /**
         * The issue has no relation to the degree of success of the action.
         */
        INFORMATION, 
        /**
         * The operation completed successfully.
         */
        SUCCESS, 
        /**
         * added to help the parsers with the generic types
         */
        NULL;
        public static IssueSeverity fromCode(String codeString) throws FHIRException {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("fatal".equals(codeString))
          return FATAL;
        if ("error".equals(codeString))
          return ERROR;
        if ("warning".equals(codeString))
          return WARNING;
        if ("information".equals(codeString))
          return INFORMATION;
        if ("success".equals(codeString))
          return SUCCESS;
        if (Configuration.isAcceptInvalidEnums())
          return null;
        else
          throw new FHIRException("Unknown IssueSeverity code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case FATAL: return "fatal";
            case ERROR: return "error";
            case WARNING: return "warning";
            case INFORMATION: return "information";
            case SUCCESS: return "success";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getSystem() {
          switch (this) {
            case FATAL: return "http://hl7.org/fhir/issue-severity";
            case ERROR: return "http://hl7.org/fhir/issue-severity";
            case WARNING: return "http://hl7.org/fhir/issue-severity";
            case INFORMATION: return "http://hl7.org/fhir/issue-severity";
            case SUCCESS: return "http://hl7.org/fhir/issue-severity";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getDefinition() {
          switch (this) {
            case FATAL: return "The issue caused the action to fail and no further checking could be performed.";
            case ERROR: return "The issue is sufficiently important to cause the action to fail.";
            case WARNING: return "The issue is not important enough to cause the action to fail but may cause it to be performed suboptimally or in a way that is not as desired.";
            case INFORMATION: return "The issue has no relation to the degree of success of the action.";
            case SUCCESS: return "The operation completed successfully.";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getDisplay() {
          switch (this) {
            case FATAL: return "Fatal";
            case ERROR: return "Error";
            case WARNING: return "Warning";
            case INFORMATION: return "Information";
            case SUCCESS: return "Operation Successful";
            case NULL: return null;
            default: return "?";
          }
        }
        public boolean isHigherThan(IssueSeverity other) {
          return this.ordinal() < other.ordinal();
        }
    }

  public static class IssueSeverityEnumFactory implements EnumFactory<IssueSeverity> {
    public IssueSeverity fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("fatal".equals(codeString))
          return IssueSeverity.FATAL;
        if ("error".equals(codeString))
          return IssueSeverity.ERROR;
        if ("warning".equals(codeString))
          return IssueSeverity.WARNING;
        if ("information".equals(codeString))
          return IssueSeverity.INFORMATION;
        if ("success".equals(codeString))
          return IssueSeverity.SUCCESS;
        throw new IllegalArgumentException("Unknown IssueSeverity code '"+codeString+"'");
        }
        public Enumeration<IssueSeverity> fromType(PrimitiveType<?> code) throws FHIRException {
          if (code == null)
            return null;
          if (code.isEmpty())
            return new Enumeration<IssueSeverity>(this, IssueSeverity.NULL, code);
          String codeString = ((PrimitiveType) code).asStringValue();
          if (codeString == null || "".equals(codeString))
            return new Enumeration<IssueSeverity>(this, IssueSeverity.NULL, code);
        if ("fatal".equals(codeString))
          return new Enumeration<IssueSeverity>(this, IssueSeverity.FATAL, code);
        if ("error".equals(codeString))
          return new Enumeration<IssueSeverity>(this, IssueSeverity.ERROR, code);
        if ("warning".equals(codeString))
          return new Enumeration<IssueSeverity>(this, IssueSeverity.WARNING, code);
        if ("information".equals(codeString))
          return new Enumeration<IssueSeverity>(this, IssueSeverity.INFORMATION, code);
        if ("success".equals(codeString))
          return new Enumeration<IssueSeverity>(this, IssueSeverity.SUCCESS, code);
        throw new FHIRException("Unknown IssueSeverity code '"+codeString+"'");
        }
    public String toCode(IssueSeverity code) {
       if (code == IssueSeverity.NULL)
           return null;
       if (code == IssueSeverity.FATAL)
        return "fatal";
      if (code == IssueSeverity.ERROR)
        return "error";
      if (code == IssueSeverity.WARNING)
        return "warning";
      if (code == IssueSeverity.INFORMATION)
        return "information";
      if (code == IssueSeverity.SUCCESS)
        return "success";
      return "?";
   }
    public String toSystem(IssueSeverity code) {
      return code.getSystem();
      }
    }

    public enum IssueType {
        /**
         * Content invalid against the specification or a profile.
         */
        INVALID, 
        /**
         * A structural issue in the content such as wrong namespace, unable to parse the content completely, invalid syntax, etc.
         */
        STRUCTURE, 
        /**
         * A required element is missing.
         */
        REQUIRED, 
        /**
         * An element or header value is invalid.
         */
        VALUE, 
        /**
         * A content validation rule failed - e.g. a schematron rule.
         */
        INVARIANT, 
        /**
         * An authentication/authorization/permissions issue of some kind.
         */
        SECURITY, 
        /**
         * The client needs to initiate an authentication process.
         */
        LOGIN, 
        /**
         * The user or system was not able to be authenticated (either there is no process, or the proferred token is unacceptable).
         */
        UNKNOWN, 
        /**
         * User session expired; a login may be required.
         */
        EXPIRED, 
        /**
         * The user does not have the rights to perform this action.
         */
        FORBIDDEN, 
        /**
         * Some information was not or might not have been returned due to business rules, consent or privacy rules, or access permission constraints.  This information may be accessible through alternate processes.
         */
        SUPPRESSED, 
        /**
         * Processing issues. These are expected to be final e.g. there is no point resubmitting the same content unchanged.
         */
        PROCESSING, 
        /**
         * The interaction, operation, resource or profile is not supported.
         */
        NOTSUPPORTED, 
        /**
         * An attempt was made to create a duplicate record.
         */
        DUPLICATE, 
        /**
         * Multiple matching records were found when the operation required only one match.
         */
        MULTIPLEMATCHES, 
        /**
         * The reference provided was not found. In a pure RESTful environment, this would be an HTTP 404 error, but this code may be used where the content is not found further into the application architecture.
         */
        NOTFOUND, 
        /**
         * The reference pointed to content (usually a resource) that has been deleted.
         */
        DELETED, 
        /**
         * Provided content is too long (typically, this is a denial of service protection type of error).
         */
        TOOLONG, 
        /**
         * The code or system could not be understood, or it was not valid in the context of a particular ValueSet.code.
         */
        CODEINVALID, 
        /**
         * An extension was found that was not acceptable, could not be resolved, or a modifierExtension was not recognized.
         */
        EXTENSION, 
        /**
         * The operation was stopped to protect server resources; e.g. a request for a value set expansion on all of SNOMED CT.
         */
        TOOCOSTLY, 
        /**
         * The content/operation failed to pass some business rule and so could not proceed.
         */
        BUSINESSRULE, 
        /**
         * Content could not be accepted because of an edit conflict (i.e. version aware updates). (In a pure RESTful environment, this would be an HTTP 409 error, but this code may be used where the conflict is discovered further into the application architecture.).
         */
        CONFLICT, 
        /**
         * Some search filters might not have applied on all results.  Data may have been included that does not meet all of the filters listed in the `self` `Bundle.link`.
         */
        LIMITEDFILTER, 
        /**
         * Transient processing issues. The system receiving the message may be able to resubmit the same content once an underlying issue is resolved.
         */
        TRANSIENT, 
        /**
         * A resource/record locking failure (usually in an underlying database).
         */
        LOCKERROR, 
        /**
         * The persistent store is unavailable; e.g. the database is down for maintenance or similar action, and the interaction or operation cannot be processed.
         */
        NOSTORE, 
        /**
         * An unexpected internal error has occurred.
         */
        EXCEPTION, 
        /**
         * An internal timeout has occurred.
         */
        TIMEOUT, 
        /**
         * Not all data sources typically accessed could be reached or responded in time, so the returned information might not be complete (applies to search interactions and some operations).
         */
        INCOMPLETE, 
        /**
         * The system is not prepared to handle this request due to load management.
         */
        THROTTLED, 
        /**
         * A message unrelated to the processing success of the completed operation (examples of the latter include things like reminders of password expiry, system maintenance times, etc.).
         */
        INFORMATIONAL, 
        /**
         * The operation completed successfully.
         */
        SUCCESS, 
        /**
         * added to help the parsers with the generic types
         */
        NULL;
        public static IssueType fromCode(String codeString) throws FHIRException {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("invalid".equals(codeString))
          return INVALID;
        if ("structure".equals(codeString))
          return STRUCTURE;
        if ("required".equals(codeString))
          return REQUIRED;
        if ("value".equals(codeString))
          return VALUE;
        if ("invariant".equals(codeString))
          return INVARIANT;
        if ("security".equals(codeString))
          return SECURITY;
        if ("login".equals(codeString))
          return LOGIN;
        if ("unknown".equals(codeString))
          return UNKNOWN;
        if ("expired".equals(codeString))
          return EXPIRED;
        if ("forbidden".equals(codeString))
          return FORBIDDEN;
        if ("suppressed".equals(codeString))
          return SUPPRESSED;
        if ("processing".equals(codeString))
          return PROCESSING;
        if ("not-supported".equals(codeString))
          return NOTSUPPORTED;
        if ("duplicate".equals(codeString))
          return DUPLICATE;
        if ("multiple-matches".equals(codeString))
          return MULTIPLEMATCHES;
        if ("not-found".equals(codeString))
          return NOTFOUND;
        if ("deleted".equals(codeString))
          return DELETED;
        if ("too-long".equals(codeString))
          return TOOLONG;
        if ("code-invalid".equals(codeString))
          return CODEINVALID;
        if ("extension".equals(codeString))
          return EXTENSION;
        if ("too-costly".equals(codeString))
          return TOOCOSTLY;
        if ("business-rule".equals(codeString))
          return BUSINESSRULE;
        if ("conflict".equals(codeString))
          return CONFLICT;
        if ("limited-filter".equals(codeString))
          return LIMITEDFILTER;
        if ("transient".equals(codeString))
          return TRANSIENT;
        if ("lock-error".equals(codeString))
          return LOCKERROR;
        if ("no-store".equals(codeString))
          return NOSTORE;
        if ("exception".equals(codeString))
          return EXCEPTION;
        if ("timeout".equals(codeString))
          return TIMEOUT;
        if ("incomplete".equals(codeString))
          return INCOMPLETE;
        if ("throttled".equals(codeString))
          return THROTTLED;
        if ("informational".equals(codeString))
          return INFORMATIONAL;
        if ("success".equals(codeString))
          return SUCCESS;
        if (Configuration.isAcceptInvalidEnums())
          return null;
        else
          throw new FHIRException("Unknown IssueType code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case INVALID: return "invalid";
            case STRUCTURE: return "structure";
            case REQUIRED: return "required";
            case VALUE: return "value";
            case INVARIANT: return "invariant";
            case SECURITY: return "security";
            case LOGIN: return "login";
            case UNKNOWN: return "unknown";
            case EXPIRED: return "expired";
            case FORBIDDEN: return "forbidden";
            case SUPPRESSED: return "suppressed";
            case PROCESSING: return "processing";
            case NOTSUPPORTED: return "not-supported";
            case DUPLICATE: return "duplicate";
            case MULTIPLEMATCHES: return "multiple-matches";
            case NOTFOUND: return "not-found";
            case DELETED: return "deleted";
            case TOOLONG: return "too-long";
            case CODEINVALID: return "code-invalid";
            case EXTENSION: return "extension";
            case TOOCOSTLY: return "too-costly";
            case BUSINESSRULE: return "business-rule";
            case CONFLICT: return "conflict";
            case LIMITEDFILTER: return "limited-filter";
            case TRANSIENT: return "transient";
            case LOCKERROR: return "lock-error";
            case NOSTORE: return "no-store";
            case EXCEPTION: return "exception";
            case TIMEOUT: return "timeout";
            case INCOMPLETE: return "incomplete";
            case THROTTLED: return "throttled";
            case INFORMATIONAL: return "informational";
            case SUCCESS: return "success";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getSystem() {
          switch (this) {
            case INVALID: return "http://hl7.org/fhir/issue-type";
            case STRUCTURE: return "http://hl7.org/fhir/issue-type";
            case REQUIRED: return "http://hl7.org/fhir/issue-type";
            case VALUE: return "http://hl7.org/fhir/issue-type";
            case INVARIANT: return "http://hl7.org/fhir/issue-type";
            case SECURITY: return "http://hl7.org/fhir/issue-type";
            case LOGIN: return "http://hl7.org/fhir/issue-type";
            case UNKNOWN: return "http://hl7.org/fhir/issue-type";
            case EXPIRED: return "http://hl7.org/fhir/issue-type";
            case FORBIDDEN: return "http://hl7.org/fhir/issue-type";
            case SUPPRESSED: return "http://hl7.org/fhir/issue-type";
            case PROCESSING: return "http://hl7.org/fhir/issue-type";
            case NOTSUPPORTED: return "http://hl7.org/fhir/issue-type";
            case DUPLICATE: return "http://hl7.org/fhir/issue-type";
            case MULTIPLEMATCHES: return "http://hl7.org/fhir/issue-type";
            case NOTFOUND: return "http://hl7.org/fhir/issue-type";
            case DELETED: return "http://hl7.org/fhir/issue-type";
            case TOOLONG: return "http://hl7.org/fhir/issue-type";
            case CODEINVALID: return "http://hl7.org/fhir/issue-type";
            case EXTENSION: return "http://hl7.org/fhir/issue-type";
            case TOOCOSTLY: return "http://hl7.org/fhir/issue-type";
            case BUSINESSRULE: return "http://hl7.org/fhir/issue-type";
            case CONFLICT: return "http://hl7.org/fhir/issue-type";
            case LIMITEDFILTER: return "http://hl7.org/fhir/issue-type";
            case TRANSIENT: return "http://hl7.org/fhir/issue-type";
            case LOCKERROR: return "http://hl7.org/fhir/issue-type";
            case NOSTORE: return "http://hl7.org/fhir/issue-type";
            case EXCEPTION: return "http://hl7.org/fhir/issue-type";
            case TIMEOUT: return "http://hl7.org/fhir/issue-type";
            case INCOMPLETE: return "http://hl7.org/fhir/issue-type";
            case THROTTLED: return "http://hl7.org/fhir/issue-type";
            case INFORMATIONAL: return "http://hl7.org/fhir/issue-type";
            case SUCCESS: return "http://hl7.org/fhir/issue-type";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getDefinition() {
          switch (this) {
            case INVALID: return "Content invalid against the specification or a profile.";
            case STRUCTURE: return "A structural issue in the content such as wrong namespace, unable to parse the content completely, invalid syntax, etc.";
            case REQUIRED: return "A required element is missing.";
            case VALUE: return "An element or header value is invalid.";
            case INVARIANT: return "A content validation rule failed - e.g. a schematron rule.";
            case SECURITY: return "An authentication/authorization/permissions issue of some kind.";
            case LOGIN: return "The client needs to initiate an authentication process.";
            case UNKNOWN: return "The user or system was not able to be authenticated (either there is no process, or the proferred token is unacceptable).";
            case EXPIRED: return "User session expired; a login may be required.";
            case FORBIDDEN: return "The user does not have the rights to perform this action.";
            case SUPPRESSED: return "Some information was not or might not have been returned due to business rules, consent or privacy rules, or access permission constraints.  This information may be accessible through alternate processes.";
            case PROCESSING: return "Processing issues. These are expected to be final e.g. there is no point resubmitting the same content unchanged.";
            case NOTSUPPORTED: return "The interaction, operation, resource or profile is not supported.";
            case DUPLICATE: return "An attempt was made to create a duplicate record.";
            case MULTIPLEMATCHES: return "Multiple matching records were found when the operation required only one match.";
            case NOTFOUND: return "The reference provided was not found. In a pure RESTful environment, this would be an HTTP 404 error, but this code may be used where the content is not found further into the application architecture.";
            case DELETED: return "The reference pointed to content (usually a resource) that has been deleted.";
            case TOOLONG: return "Provided content is too long (typically, this is a denial of service protection type of error).";
            case CODEINVALID: return "The code or system could not be understood, or it was not valid in the context of a particular ValueSet.code.";
            case EXTENSION: return "An extension was found that was not acceptable, could not be resolved, or a modifierExtension was not recognized.";
            case TOOCOSTLY: return "The operation was stopped to protect server resources; e.g. a request for a value set expansion on all of SNOMED CT.";
            case BUSINESSRULE: return "The content/operation failed to pass some business rule and so could not proceed.";
            case CONFLICT: return "Content could not be accepted because of an edit conflict (i.e. version aware updates). (In a pure RESTful environment, this would be an HTTP 409 error, but this code may be used where the conflict is discovered further into the application architecture.).";
            case LIMITEDFILTER: return "Some search filters might not have applied on all results.  Data may have been included that does not meet all of the filters listed in the `self` `Bundle.link`.";
            case TRANSIENT: return "Transient processing issues. The system receiving the message may be able to resubmit the same content once an underlying issue is resolved.";
            case LOCKERROR: return "A resource/record locking failure (usually in an underlying database).";
            case NOSTORE: return "The persistent store is unavailable; e.g. the database is down for maintenance or similar action, and the interaction or operation cannot be processed.";
            case EXCEPTION: return "An unexpected internal error has occurred.";
            case TIMEOUT: return "An internal timeout has occurred.";
            case INCOMPLETE: return "Not all data sources typically accessed could be reached or responded in time, so the returned information might not be complete (applies to search interactions and some operations).";
            case THROTTLED: return "The system is not prepared to handle this request due to load management.";
            case INFORMATIONAL: return "A message unrelated to the processing success of the completed operation (examples of the latter include things like reminders of password expiry, system maintenance times, etc.).";
            case SUCCESS: return "The operation completed successfully.";
            case NULL: return null;
            default: return "?";
          }
        }
        public String getDisplay() {
          switch (this) {
            case INVALID: return "Invalid Content";
            case STRUCTURE: return "Structural Issue";
            case REQUIRED: return "Required element missing";
            case VALUE: return "Element value invalid";
            case INVARIANT: return "Validation rule failed";
            case SECURITY: return "Security Problem";
            case LOGIN: return "Login Required";
            case UNKNOWN: return "Unknown User";
            case EXPIRED: return "Session Expired";
            case FORBIDDEN: return "Forbidden";
            case SUPPRESSED: return "Information  Suppressed";
            case PROCESSING: return "Processing Failure";
            case NOTSUPPORTED: return "Content not supported";
            case DUPLICATE: return "Duplicate";
            case MULTIPLEMATCHES: return "Multiple Matches";
            case NOTFOUND: return "Not Found";
            case DELETED: return "Deleted";
            case TOOLONG: return "Content Too Long";
            case CODEINVALID: return "Invalid Code";
            case EXTENSION: return "Unacceptable Extension";
            case TOOCOSTLY: return "Operation Too Costly";
            case BUSINESSRULE: return "Business Rule Violation";
            case CONFLICT: return "Edit Version Conflict";
            case LIMITEDFILTER: return "Limited Filter Application";
            case TRANSIENT: return "Transient Issue";
            case LOCKERROR: return "Lock Error";
            case NOSTORE: return "No Store Available";
            case EXCEPTION: return "Exception";
            case TIMEOUT: return "Timeout";
            case INCOMPLETE: return "Incomplete Results";
            case THROTTLED: return "Throttled";
            case INFORMATIONAL: return "Informational Note";
            case SUCCESS: return "Operation Successful";
            case NULL: return null;
            default: return "?";
          }
        }
    }

  public static class IssueTypeEnumFactory implements EnumFactory<IssueType> {
    public IssueType fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("invalid".equals(codeString))
          return IssueType.INVALID;
        if ("structure".equals(codeString))
          return IssueType.STRUCTURE;
        if ("required".equals(codeString))
          return IssueType.REQUIRED;
        if ("value".equals(codeString))
          return IssueType.VALUE;
        if ("invariant".equals(codeString))
          return IssueType.INVARIANT;
        if ("security".equals(codeString))
          return IssueType.SECURITY;
        if ("login".equals(codeString))
          return IssueType.LOGIN;
        if ("unknown".equals(codeString))
          return IssueType.UNKNOWN;
        if ("expired".equals(codeString))
          return IssueType.EXPIRED;
        if ("forbidden".equals(codeString))
          return IssueType.FORBIDDEN;
        if ("suppressed".equals(codeString))
          return IssueType.SUPPRESSED;
        if ("processing".equals(codeString))
          return IssueType.PROCESSING;
        if ("not-supported".equals(codeString))
          return IssueType.NOTSUPPORTED;
        if ("duplicate".equals(codeString))
          return IssueType.DUPLICATE;
        if ("multiple-matches".equals(codeString))
          return IssueType.MULTIPLEMATCHES;
        if ("not-found".equals(codeString))
          return IssueType.NOTFOUND;
        if ("deleted".equals(codeString))
          return IssueType.DELETED;
        if ("too-long".equals(codeString))
          return IssueType.TOOLONG;
        if ("code-invalid".equals(codeString))
          return IssueType.CODEINVALID;
        if ("extension".equals(codeString))
          return IssueType.EXTENSION;
        if ("too-costly".equals(codeString))
          return IssueType.TOOCOSTLY;
        if ("business-rule".equals(codeString))
          return IssueType.BUSINESSRULE;
        if ("conflict".equals(codeString))
          return IssueType.CONFLICT;
        if ("limited-filter".equals(codeString))
          return IssueType.LIMITEDFILTER;
        if ("transient".equals(codeString))
          return IssueType.TRANSIENT;
        if ("lock-error".equals(codeString))
          return IssueType.LOCKERROR;
        if ("no-store".equals(codeString))
          return IssueType.NOSTORE;
        if ("exception".equals(codeString))
          return IssueType.EXCEPTION;
        if ("timeout".equals(codeString))
          return IssueType.TIMEOUT;
        if ("incomplete".equals(codeString))
          return IssueType.INCOMPLETE;
        if ("throttled".equals(codeString))
          return IssueType.THROTTLED;
        if ("informational".equals(codeString))
          return IssueType.INFORMATIONAL;
        if ("success".equals(codeString))
          return IssueType.SUCCESS;
        throw new IllegalArgumentException("Unknown IssueType code '"+codeString+"'");
        }
        public Enumeration<IssueType> fromType(PrimitiveType<?> code) throws FHIRException {
          if (code == null)
            return null;
          if (code.isEmpty())
            return new Enumeration<IssueType>(this, IssueType.NULL, code);
          String codeString = ((PrimitiveType) code).asStringValue();
          if (codeString == null || "".equals(codeString))
            return new Enumeration<IssueType>(this, IssueType.NULL, code);
        if ("invalid".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.INVALID, code);
        if ("structure".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.STRUCTURE, code);
        if ("required".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.REQUIRED, code);
        if ("value".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.VALUE, code);
        if ("invariant".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.INVARIANT, code);
        if ("security".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.SECURITY, code);
        if ("login".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.LOGIN, code);
        if ("unknown".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.UNKNOWN, code);
        if ("expired".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.EXPIRED, code);
        if ("forbidden".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.FORBIDDEN, code);
        if ("suppressed".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.SUPPRESSED, code);
        if ("processing".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.PROCESSING, code);
        if ("not-supported".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.NOTSUPPORTED, code);
        if ("duplicate".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.DUPLICATE, code);
        if ("multiple-matches".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.MULTIPLEMATCHES, code);
        if ("not-found".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.NOTFOUND, code);
        if ("deleted".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.DELETED, code);
        if ("too-long".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.TOOLONG, code);
        if ("code-invalid".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.CODEINVALID, code);
        if ("extension".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.EXTENSION, code);
        if ("too-costly".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.TOOCOSTLY, code);
        if ("business-rule".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.BUSINESSRULE, code);
        if ("conflict".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.CONFLICT, code);
        if ("limited-filter".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.LIMITEDFILTER, code);
        if ("transient".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.TRANSIENT, code);
        if ("lock-error".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.LOCKERROR, code);
        if ("no-store".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.NOSTORE, code);
        if ("exception".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.EXCEPTION, code);
        if ("timeout".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.TIMEOUT, code);
        if ("incomplete".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.INCOMPLETE, code);
        if ("throttled".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.THROTTLED, code);
        if ("informational".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.INFORMATIONAL, code);
        if ("success".equals(codeString))
          return new Enumeration<IssueType>(this, IssueType.SUCCESS, code);
        throw new FHIRException("Unknown IssueType code '"+codeString+"'");
        }
    public String toCode(IssueType code) {
       if (code == IssueType.NULL)
           return null;
       if (code == IssueType.INVALID)
        return "invalid";
      if (code == IssueType.STRUCTURE)
        return "structure";
      if (code == IssueType.REQUIRED)
        return "required";
      if (code == IssueType.VALUE)
        return "value";
      if (code == IssueType.INVARIANT)
        return "invariant";
      if (code == IssueType.SECURITY)
        return "security";
      if (code == IssueType.LOGIN)
        return "login";
      if (code == IssueType.UNKNOWN)
        return "unknown";
      if (code == IssueType.EXPIRED)
        return "expired";
      if (code == IssueType.FORBIDDEN)
        return "forbidden";
      if (code == IssueType.SUPPRESSED)
        return "suppressed";
      if (code == IssueType.PROCESSING)
        return "processing";
      if (code == IssueType.NOTSUPPORTED)
        return "not-supported";
      if (code == IssueType.DUPLICATE)
        return "duplicate";
      if (code == IssueType.MULTIPLEMATCHES)
        return "multiple-matches";
      if (code == IssueType.NOTFOUND)
        return "not-found";
      if (code == IssueType.DELETED)
        return "deleted";
      if (code == IssueType.TOOLONG)
        return "too-long";
      if (code == IssueType.CODEINVALID)
        return "code-invalid";
      if (code == IssueType.EXTENSION)
        return "extension";
      if (code == IssueType.TOOCOSTLY)
        return "too-costly";
      if (code == IssueType.BUSINESSRULE)
        return "business-rule";
      if (code == IssueType.CONFLICT)
        return "conflict";
      if (code == IssueType.LIMITEDFILTER)
        return "limited-filter";
      if (code == IssueType.TRANSIENT)
        return "transient";
      if (code == IssueType.LOCKERROR)
        return "lock-error";
      if (code == IssueType.NOSTORE)
        return "no-store";
      if (code == IssueType.EXCEPTION)
        return "exception";
      if (code == IssueType.TIMEOUT)
        return "timeout";
      if (code == IssueType.INCOMPLETE)
        return "incomplete";
      if (code == IssueType.THROTTLED)
        return "throttled";
      if (code == IssueType.INFORMATIONAL)
        return "informational";
      if (code == IssueType.SUCCESS)
        return "success";
      return "?";
   }
    public String toSystem(IssueType code) {
      return code.getSystem();
      }
    }

    @Block()
    public static class OperationOutcomeIssueComponent extends BackboneElement implements IBaseBackboneElement {
        /**
         * Indicates whether the issue indicates a variation from successful processing.
         */
        @Child(name = "severity", type = {CodeType.class}, order=1, min=1, max=1, modifier=false, summary=true)
        @Description(shortDefinition="fatal | error | warning | information | success", formalDefinition="Indicates whether the issue indicates a variation from successful processing." )
        @ca.uhn.fhir.model.api.annotation.Binding(valueSet="http://hl7.org/fhir/ValueSet/issue-severity")
        protected Enumeration<IssueSeverity> severity;

        /**
         * Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.
         */
        @Child(name = "code", type = {CodeType.class}, order=2, min=1, max=1, modifier=false, summary=true)
        @Description(shortDefinition="Error or warning code", formalDefinition="Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element." )
        @ca.uhn.fhir.model.api.annotation.Binding(valueSet="http://hl7.org/fhir/ValueSet/issue-type")
        protected Enumeration<IssueType> code;

        /**
         * Additional details about the error. This may be a text description of the error or a system code that identifies the error.
         */
        @Child(name = "details", type = {CodeableConcept.class}, order=3, min=0, max=1, modifier=false, summary=true)
        @Description(shortDefinition="Additional details about the error", formalDefinition="Additional details about the error. This may be a text description of the error or a system code that identifies the error." )
        @ca.uhn.fhir.model.api.annotation.Binding(valueSet="http://hl7.org/fhir/ValueSet/operation-outcome")
        protected CodeableConcept details;

        /**
         * Additional diagnostic information about the issue.
         */
        @Child(name = "diagnostics", type = {StringType.class}, order=4, min=0, max=1, modifier=false, summary=true)
        @Description(shortDefinition="Additional diagnostic information about the issue", formalDefinition="Additional diagnostic information about the issue." )
        protected StringType diagnostics;

        /**
         * This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. 

For resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be "http." + the parameter name.
         */
        @Child(name = "location", type = {StringType.class}, order=5, min=0, max=Child.MAX_UNLIMITED, modifier=false, summary=true)
        @Description(shortDefinition="Deprecated: Path of element(s) related to issue", formalDefinition="This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. \n\nFor resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be \"http.\" + the parameter name." )
        protected List<StringType> location;

        /**
         * A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.
         */
        @Child(name = "expression", type = {StringType.class}, order=6, min=0, max=Child.MAX_UNLIMITED, modifier=false, summary=true)
        @Description(shortDefinition="FHIRPath of element(s) related to issue", formalDefinition="A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised." )
        protected List<StringType> expression;

        private static final long serialVersionUID = -1681095438L;

    /**
     * Constructor
     */
      public OperationOutcomeIssueComponent() {
        super();
      }

    /**
     * Constructor
     */
      public OperationOutcomeIssueComponent(IssueSeverity severity, IssueType code) {
        super();
        this.setSeverity(severity);
        this.setCode(code);
      }

        /**
         * @return {@link #severity} (Indicates whether the issue indicates a variation from successful processing.). This is the underlying object with id, value and extensions. The accessor "getSeverity" gives direct access to the value
         */
        public Enumeration<IssueSeverity> getSeverityElement() { 
          if (this.severity == null)
            if (Configuration.errorOnAutoCreate())
              throw new Error("Attempt to auto-create OperationOutcomeIssueComponent.severity");
            else if (Configuration.doAutoCreate())
              this.severity = new Enumeration<IssueSeverity>(new IssueSeverityEnumFactory()); // bb
          return this.severity;
        }

        public boolean hasSeverityElement() { 
          return this.severity != null && !this.severity.isEmpty();
        }

        public boolean hasSeverity() { 
          return this.severity != null && !this.severity.isEmpty();
        }

        /**
         * @param value {@link #severity} (Indicates whether the issue indicates a variation from successful processing.). This is the underlying object with id, value and extensions. The accessor "getSeverity" gives direct access to the value
         */
        public OperationOutcomeIssueComponent setSeverityElement(Enumeration<IssueSeverity> value) { 
          this.severity = value;
          return this;
        }

        /**
         * @return Indicates whether the issue indicates a variation from successful processing.
         */
        public IssueSeverity getSeverity() { 
          return this.severity == null ? null : this.severity.getValue();
        }

        /**
         * @param value Indicates whether the issue indicates a variation from successful processing.
         */
        public OperationOutcomeIssueComponent setSeverity(IssueSeverity value) { 
            if (this.severity == null)
              this.severity = new Enumeration<IssueSeverity>(new IssueSeverityEnumFactory());
            this.severity.setValue(value);
          return this;
        }

        /**
         * @return {@link #code} (Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.). This is the underlying object with id, value and extensions. The accessor "getCode" gives direct access to the value
         */
        public Enumeration<IssueType> getCodeElement() { 
          if (this.code == null)
            if (Configuration.errorOnAutoCreate())
              throw new Error("Attempt to auto-create OperationOutcomeIssueComponent.code");
            else if (Configuration.doAutoCreate())
              this.code = new Enumeration<IssueType>(new IssueTypeEnumFactory()); // bb
          return this.code;
        }

        public boolean hasCodeElement() { 
          return this.code != null && !this.code.isEmpty();
        }

        public boolean hasCode() { 
          return this.code != null && !this.code.isEmpty();
        }

        /**
         * @param value {@link #code} (Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.). This is the underlying object with id, value and extensions. The accessor "getCode" gives direct access to the value
         */
        public OperationOutcomeIssueComponent setCodeElement(Enumeration<IssueType> value) { 
          this.code = value;
          return this;
        }

        /**
         * @return Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.
         */
        public IssueType getCode() { 
          return this.code == null ? null : this.code.getValue();
        }

        /**
         * @param value Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.
         */
        public OperationOutcomeIssueComponent setCode(IssueType value) { 
            if (this.code == null)
              this.code = new Enumeration<IssueType>(new IssueTypeEnumFactory());
            this.code.setValue(value);
          return this;
        }

        /**
         * @return {@link #details} (Additional details about the error. This may be a text description of the error or a system code that identifies the error.)
         */
        public CodeableConcept getDetails() { 
          if (this.details == null)
            if (Configuration.errorOnAutoCreate())
              throw new Error("Attempt to auto-create OperationOutcomeIssueComponent.details");
            else if (Configuration.doAutoCreate())
              this.details = new CodeableConcept(); // cc
          return this.details;
        }

        public boolean hasDetails() { 
          return this.details != null && !this.details.isEmpty();
        }

        /**
         * @param value {@link #details} (Additional details about the error. This may be a text description of the error or a system code that identifies the error.)
         */
        public OperationOutcomeIssueComponent setDetails(CodeableConcept value) { 
          this.details = value;
          return this;
        }

        /**
         * @return {@link #diagnostics} (Additional diagnostic information about the issue.). This is the underlying object with id, value and extensions. The accessor "getDiagnostics" gives direct access to the value
         */
        public StringType getDiagnosticsElement() { 
          if (this.diagnostics == null)
            if (Configuration.errorOnAutoCreate())
              throw new Error("Attempt to auto-create OperationOutcomeIssueComponent.diagnostics");
            else if (Configuration.doAutoCreate())
              this.diagnostics = new StringType(); // bb
          return this.diagnostics;
        }

        public boolean hasDiagnosticsElement() { 
          return this.diagnostics != null && !this.diagnostics.isEmpty();
        }

        public boolean hasDiagnostics() { 
          return this.diagnostics != null && !this.diagnostics.isEmpty();
        }

        /**
         * @param value {@link #diagnostics} (Additional diagnostic information about the issue.). This is the underlying object with id, value and extensions. The accessor "getDiagnostics" gives direct access to the value
         */
        public OperationOutcomeIssueComponent setDiagnosticsElement(StringType value) { 
          this.diagnostics = value;
          return this;
        }

        /**
         * @return Additional diagnostic information about the issue.
         */
        public String getDiagnostics() { 
          return this.diagnostics == null ? null : this.diagnostics.getValue();
        }

        /**
         * @param value Additional diagnostic information about the issue.
         */
        public OperationOutcomeIssueComponent setDiagnostics(String value) { 
          if (Utilities.noString(value))
            this.diagnostics = null;
          else {
            if (this.diagnostics == null)
              this.diagnostics = new StringType();
            this.diagnostics.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #location} (This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. 

For resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be "http." + the parameter name.)
         */
        public List<StringType> getLocation() { 
          if (this.location == null)
            this.location = new ArrayList<StringType>();
          return this.location;
        }

        /**
         * @return Returns a reference to <code>this</code> for easy method chaining
         */
        public OperationOutcomeIssueComponent setLocation(List<StringType> theLocation) { 
          this.location = theLocation;
          return this;
        }

        public boolean hasLocation() { 
          if (this.location == null)
            return false;
          for (StringType item : this.location)
            if (!item.isEmpty())
              return true;
          return false;
        }

        /**
         * @return {@link #location} (This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. 

For resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be "http." + the parameter name.)
         */
        public StringType addLocationElement() {//2 
          StringType t = new StringType();
          if (this.location == null)
            this.location = new ArrayList<StringType>();
          this.location.add(t);
          return t;
        }

        /**
         * @param value {@link #location} (This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. 

For resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be "http." + the parameter name.)
         */
        public OperationOutcomeIssueComponent addLocation(String value) { //1
          StringType t = new StringType();
          t.setValue(value);
          if (this.location == null)
            this.location = new ArrayList<StringType>();
          this.location.add(t);
          return this;
        }

        /**
         * @param value {@link #location} (This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. 

For resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be "http." + the parameter name.)
         */
        public boolean hasLocation(String value) { 
          if (this.location == null)
            return false;
          for (StringType v : this.location)
            if (v.getValue().equals(value)) // string
              return true;
          return false;
        }

        /**
         * @return {@link #expression} (A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.)
         */
        public List<StringType> getExpression() { 
          if (this.expression == null)
            this.expression = new ArrayList<StringType>();
          return this.expression;
        }

        /**
         * @return Returns a reference to <code>this</code> for easy method chaining
         */
        public OperationOutcomeIssueComponent setExpression(List<StringType> theExpression) { 
          this.expression = theExpression;
          return this;
        }

        public boolean hasExpression() { 
          if (this.expression == null)
            return false;
          for (StringType item : this.expression)
            if (!item.isEmpty())
              return true;
          return false;
        }

        /**
         * @return {@link #expression} (A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.)
         */
        public StringType addExpressionElement() {//2 
          StringType t = new StringType();
          if (this.expression == null)
            this.expression = new ArrayList<StringType>();
          this.expression.add(t);
          return t;
        }

        /**
         * @param value {@link #expression} (A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.)
         */
        public OperationOutcomeIssueComponent addExpression(String value) { //1
          StringType t = new StringType();
          t.setValue(value);
          if (this.expression == null)
            this.expression = new ArrayList<StringType>();
          this.expression.add(t);
          return this;
        }

        /**
         * @param value {@link #expression} (A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.)
         */
        public boolean hasExpression(String value) { 
          if (this.expression == null)
            return false;
          for (StringType v : this.expression)
            if (v.getValue().equals(value)) // string
              return true;
          return false;
        }

        protected void listChildren(List<Property> children) {
          super.listChildren(children);
          children.add(new Property("severity", "code", "Indicates whether the issue indicates a variation from successful processing.", 0, 1, severity));
          children.add(new Property("code", "code", "Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.", 0, 1, code));
          children.add(new Property("details", "CodeableConcept", "Additional details about the error. This may be a text description of the error or a system code that identifies the error.", 0, 1, details));
          children.add(new Property("diagnostics", "string", "Additional diagnostic information about the issue.", 0, 1, diagnostics));
          children.add(new Property("location", "string", "This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. \n\nFor resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be \"http.\" + the parameter name.", 0, java.lang.Integer.MAX_VALUE, location));
          children.add(new Property("expression", "string", "A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.", 0, java.lang.Integer.MAX_VALUE, expression));
        }

        @Override
        public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
          switch (_hash) {
          case 1478300413: /*severity*/  return new Property("severity", "code", "Indicates whether the issue indicates a variation from successful processing.", 0, 1, severity);
          case 3059181: /*code*/  return new Property("code", "code", "Describes the type of the issue. The system that creates an OperationOutcome SHALL choose the most applicable code from the IssueType value set, and may additional provide its own code for the error in the details element.", 0, 1, code);
          case 1557721666: /*details*/  return new Property("details", "CodeableConcept", "Additional details about the error. This may be a text description of the error or a system code that identifies the error.", 0, 1, details);
          case -740386388: /*diagnostics*/  return new Property("diagnostics", "string", "Additional diagnostic information about the issue.", 0, 1, diagnostics);
          case 1901043637: /*location*/  return new Property("location", "string", "This element is deprecated because it is XML specific. It is replaced by issue.expression, which is format independent, and simpler to parse. \n\nFor resource issues, this will be a simple XPath limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.  For HTTP errors, will be \"http.\" + the parameter name.", 0, java.lang.Integer.MAX_VALUE, location);
          case -1795452264: /*expression*/  return new Property("expression", "string", "A [simple subset of FHIRPath](fhirpath.html#simple) limited to element names, repetition indicators and the default child accessor that identifies one of the elements in the resource that caused this issue to be raised.", 0, java.lang.Integer.MAX_VALUE, expression);
          default: return super.getNamedProperty(_hash, _name, _checkValid);
          }

        }

      @Override
      public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
        switch (hash) {
        case 1478300413: /*severity*/ return this.severity == null ? new Base[0] : new Base[] {this.severity}; // Enumeration<IssueSeverity>
        case 3059181: /*code*/ return this.code == null ? new Base[0] : new Base[] {this.code}; // Enumeration<IssueType>
        case 1557721666: /*details*/ return this.details == null ? new Base[0] : new Base[] {this.details}; // CodeableConcept
        case -740386388: /*diagnostics*/ return this.diagnostics == null ? new Base[0] : new Base[] {this.diagnostics}; // StringType
        case 1901043637: /*location*/ return this.location == null ? new Base[0] : this.location.toArray(new Base[this.location.size()]); // StringType
        case -1795452264: /*expression*/ return this.expression == null ? new Base[0] : this.expression.toArray(new Base[this.expression.size()]); // StringType
        default: return super.getProperty(hash, name, checkValid);
        }

      }

      @Override
      public Base setProperty(int hash, String name, Base value) throws FHIRException {
        switch (hash) {
        case 1478300413: // severity
          value = new IssueSeverityEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.severity = (Enumeration) value; // Enumeration<IssueSeverity>
          return value;
        case 3059181: // code
          value = new IssueTypeEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.code = (Enumeration) value; // Enumeration<IssueType>
          return value;
        case 1557721666: // details
          this.details = TypeConvertor.castToCodeableConcept(value); // CodeableConcept
          return value;
        case -740386388: // diagnostics
          this.diagnostics = TypeConvertor.castToString(value); // StringType
          return value;
        case 1901043637: // location
          this.getLocation().add(TypeConvertor.castToString(value)); // StringType
          return value;
        case -1795452264: // expression
          this.getExpression().add(TypeConvertor.castToString(value)); // StringType
          return value;
        default: return super.setProperty(hash, name, value);
        }

      }

      @Override
      public Base setProperty(String name, Base value) throws FHIRException {
        if (name.equals("severity")) {
          value = new IssueSeverityEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.severity = (Enumeration) value; // Enumeration<IssueSeverity>
        } else if (name.equals("code")) {
          value = new IssueTypeEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.code = (Enumeration) value; // Enumeration<IssueType>
        } else if (name.equals("details")) {
          this.details = TypeConvertor.castToCodeableConcept(value); // CodeableConcept
        } else if (name.equals("diagnostics")) {
          this.diagnostics = TypeConvertor.castToString(value); // StringType
        } else if (name.equals("location")) {
          this.getLocation().add(TypeConvertor.castToString(value));
        } else if (name.equals("expression")) {
          this.getExpression().add(TypeConvertor.castToString(value));
        } else
          return super.setProperty(name, value);
        return value;
      }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
        if (name.equals("severity")) {
          value = new IssueSeverityEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.severity = (Enumeration) value; // Enumeration<IssueSeverity>
        } else if (name.equals("code")) {
          value = new IssueTypeEnumFactory().fromType(TypeConvertor.castToCode(value));
          this.code = (Enumeration) value; // Enumeration<IssueType>
        } else if (name.equals("details")) {
          this.details = null;
        } else if (name.equals("diagnostics")) {
          this.diagnostics = null;
        } else if (name.equals("location")) {
          this.getLocation().remove(value);
        } else if (name.equals("expression")) {
          this.getExpression().remove(value);
        } else
          super.removeChild(name, value);
        
      }

      @Override
      public Base makeProperty(int hash, String name) throws FHIRException {
        switch (hash) {
        case 1478300413:  return getSeverityElement();
        case 3059181:  return getCodeElement();
        case 1557721666:  return getDetails();
        case -740386388:  return getDiagnosticsElement();
        case 1901043637:  return addLocationElement();
        case -1795452264:  return addExpressionElement();
        default: return super.makeProperty(hash, name);
        }

      }

      @Override
      public String[] getTypesForProperty(int hash, String name) throws FHIRException {
        switch (hash) {
        case 1478300413: /*severity*/ return new String[] {"code"};
        case 3059181: /*code*/ return new String[] {"code"};
        case 1557721666: /*details*/ return new String[] {"CodeableConcept"};
        case -740386388: /*diagnostics*/ return new String[] {"string"};
        case 1901043637: /*location*/ return new String[] {"string"};
        case -1795452264: /*expression*/ return new String[] {"string"};
        default: return super.getTypesForProperty(hash, name);
        }

      }

      @Override
      public Base addChild(String name) throws FHIRException {
        if (name.equals("severity")) {
          throw new FHIRException("Cannot call addChild on a singleton property OperationOutcome.issue.severity");
        }
        else if (name.equals("code")) {
          throw new FHIRException("Cannot call addChild on a singleton property OperationOutcome.issue.code");
        }
        else if (name.equals("details")) {
          this.details = new CodeableConcept();
          return this.details;
        }
        else if (name.equals("diagnostics")) {
          throw new FHIRException("Cannot call addChild on a singleton property OperationOutcome.issue.diagnostics");
        }
        else if (name.equals("location")) {
          throw new FHIRException("Cannot call addChild on a singleton property OperationOutcome.issue.location");
        }
        else if (name.equals("expression")) {
          throw new FHIRException("Cannot call addChild on a singleton property OperationOutcome.issue.expression");
        }
        else
          return super.addChild(name);
      }

      public OperationOutcomeIssueComponent copy() {
        OperationOutcomeIssueComponent dst = new OperationOutcomeIssueComponent();
        copyValues(dst);
        return dst;
      }

      public void copyValues(OperationOutcomeIssueComponent dst) {
        super.copyValues(dst);
        dst.severity = severity == null ? null : severity.copy();
        dst.code = code == null ? null : code.copy();
        dst.details = details == null ? null : details.copy();
        dst.diagnostics = diagnostics == null ? null : diagnostics.copy();
        if (location != null) {
          dst.location = new ArrayList<StringType>();
          for (StringType i : location)
            dst.location.add(i.copy());
        };
        if (expression != null) {
          dst.expression = new ArrayList<StringType>();
          for (StringType i : expression)
            dst.expression.add(i.copy());
        };
      }

      @Override
      public boolean equalsDeep(Base other_) {
        if (!super.equalsDeep(other_))
          return false;
        if (!(other_ instanceof OperationOutcomeIssueComponent))
          return false;
        OperationOutcomeIssueComponent o = (OperationOutcomeIssueComponent) other_;
        return compareDeep(severity, o.severity, true) && compareDeep(code, o.code, true) && compareDeep(details, o.details, true)
           && compareDeep(diagnostics, o.diagnostics, true) && compareDeep(location, o.location, true) && compareDeep(expression, o.expression, true)
          ;
      }

      @Override
      public boolean equalsShallow(Base other_) {
        if (!super.equalsShallow(other_))
          return false;
        if (!(other_ instanceof OperationOutcomeIssueComponent))
          return false;
        OperationOutcomeIssueComponent o = (OperationOutcomeIssueComponent) other_;
        return compareValues(severity, o.severity, true) && compareValues(code, o.code, true) && compareValues(diagnostics, o.diagnostics, true)
           && compareValues(location, o.location, true) && compareValues(expression, o.expression, true);
      }

      public boolean isEmpty() {
        return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(severity, code, details
          , diagnostics, location, expression);
      }

      public String fhirType() {
        return "OperationOutcome.issue";

      }

      // added from java-adornments.txt:
      @Override 
      public String toString() { 
        String srvr = hasExtension(ExtensionDefinitions.EXT_ISSUE_SERVER) ? " (from "+getExtensionString(ExtensionDefinitions.EXT_ISSUE_SERVER)+")" : "";
        if (getExpression().size() == 1) { 
          return getSeverity().toCode()+"/"+getCode().toCode()+" @ "+getExpression().get(0)+(hasDiagnostics() ? " "+getDiagnostics() : "")+": "+getDetails().getText()+srvr; 
        } else { 
          return getSeverity().toCode()+"/"+getCode().toCode()+" @ "+getExpression()+(hasDiagnostics() ? " "+getDiagnostics() : "")+": "+getDetails().getText()+srvr; 
        } 
      } 

      public boolean isWarningOrMore() {
        switch (getSeverity()) {
        case FATAL: return true;
        case ERROR: return true;
        case WARNING: return true;
        case INFORMATION: return false;
        case SUCCESS: return false;
        case NULL: return false;
        default: return false;
        }
      }
      public  boolean isInformationorLess() {
        switch (getSeverity()) {
        case FATAL: return false;
        case ERROR: return true;
        case WARNING: return false;
        case INFORMATION: return true;
        case SUCCESS: return true;
        case NULL: return true;
        default: return false;
        }
      }

      public List<StringType> getExpressionOrLocation() {
        return hasExpression() ? getExpression() : getLocation();
      }

      public boolean hasExpressionOrLocation() {
        return hasExpression() || hasLocation();
      }

      public void resetPath(String root, String newRoot) {
        for (StringType st : getLocation()) {
          if (st.hasValue() && st.getValue().startsWith(root+".")) {
            st.setValue(newRoot+st.getValue().substring(root.length()));
          }
        }
        for (StringType st : getExpression()) {
          if (st.hasValue() && st.getValue().startsWith(root+".")) {
            st.setValue(newRoot+st.getValue().substring(root.length()));
          }
        }
      }  

      public String getText() {
        if (getDetails().hasText()) {
          return getDetails().getText();
        }
        if (hasDiagnostics()) {
          return getDiagnostics();
        }
        return null;
      }
      // end addition
    }

    /**
     * An error, warning, or information message that results from a system action.
     */
    @Child(name = "issue", type = {}, order=0, min=1, max=Child.MAX_UNLIMITED, modifier=false, summary=true)
    @Description(shortDefinition="A single issue associated with the action", formalDefinition="An error, warning, or information message that results from a system action." )
    protected List<OperationOutcomeIssueComponent> issue;

    private static final long serialVersionUID = -152150052L;

  /**
   * Constructor
   */
    public OperationOutcome() {
      super();
    }

  /**
   * Constructor
   */
    public OperationOutcome(OperationOutcomeIssueComponent issue) {
      super();
      this.addIssue(issue);
    }

    /**
     * @return {@link #issue} (An error, warning, or information message that results from a system action.)
     */
    public List<OperationOutcomeIssueComponent> getIssue() { 
      if (this.issue == null)
        this.issue = new ArrayList<OperationOutcomeIssueComponent>();
      return this.issue;
    }

    /**
     * @return Returns a reference to <code>this</code> for easy method chaining
     */
    public OperationOutcome setIssue(List<OperationOutcomeIssueComponent> theIssue) { 
      this.issue = theIssue;
      return this;
    }

    public boolean hasIssue() { 
      if (this.issue == null)
        return false;
      for (OperationOutcomeIssueComponent item : this.issue)
        if (!item.isEmpty())
          return true;
      return false;
    }

    public OperationOutcomeIssueComponent addIssue() { //3
      OperationOutcomeIssueComponent t = new OperationOutcomeIssueComponent();
      if (this.issue == null)
        this.issue = new ArrayList<OperationOutcomeIssueComponent>();
      this.issue.add(t);
      return t;
    }

    public OperationOutcome addIssue(OperationOutcomeIssueComponent t) { //3
      if (t == null)
        return this;
      if (this.issue == null)
        this.issue = new ArrayList<OperationOutcomeIssueComponent>();
      this.issue.add(t);
      return this;
    }

    /**
     * @return The first repetition of repeating field {@link #issue}, creating it if it does not already exist {3}
     */
    public OperationOutcomeIssueComponent getIssueFirstRep() { 
      if (getIssue().isEmpty()) {
        addIssue();
      }
      return getIssue().get(0);
    }

      protected void listChildren(List<Property> children) {
        super.listChildren(children);
        children.add(new Property("issue", "", "An error, warning, or information message that results from a system action.", 0, java.lang.Integer.MAX_VALUE, issue));
      }

      @Override
      public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
        switch (_hash) {
        case 100509913: /*issue*/  return new Property("issue", "", "An error, warning, or information message that results from a system action.", 0, java.lang.Integer.MAX_VALUE, issue);
        default: return super.getNamedProperty(_hash, _name, _checkValid);
        }

      }

      @Override
      public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
        switch (hash) {
        case 100509913: /*issue*/ return this.issue == null ? new Base[0] : this.issue.toArray(new Base[this.issue.size()]); // OperationOutcomeIssueComponent
        default: return super.getProperty(hash, name, checkValid);
        }

      }

      @Override
      public Base setProperty(int hash, String name, Base value) throws FHIRException {
        switch (hash) {
        case 100509913: // issue
          this.getIssue().add((OperationOutcomeIssueComponent) value); // OperationOutcomeIssueComponent
          return value;
        default: return super.setProperty(hash, name, value);
        }

      }

      @Override
      public Base setProperty(String name, Base value) throws FHIRException {
        if (name.equals("issue")) {
          this.getIssue().add((OperationOutcomeIssueComponent) value);
        } else
          return super.setProperty(name, value);
        return value;
      }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
        if (name.equals("issue")) {
          this.getIssue().remove((OperationOutcomeIssueComponent) value);
        } else
          super.removeChild(name, value);
        
      }

      @Override
      public Base makeProperty(int hash, String name) throws FHIRException {
        switch (hash) {
        case 100509913:  return addIssue(); 
        default: return super.makeProperty(hash, name);
        }

      }

      @Override
      public String[] getTypesForProperty(int hash, String name) throws FHIRException {
        switch (hash) {
        case 100509913: /*issue*/ return new String[] {};
        default: return super.getTypesForProperty(hash, name);
        }

      }

      @Override
      public Base addChild(String name) throws FHIRException {
        if (name.equals("issue")) {
          return addIssue();
        }
        else
          return super.addChild(name);
      }

  public String fhirType() {
    return "OperationOutcome";

  }

      public OperationOutcome copy() {
        OperationOutcome dst = new OperationOutcome();
        copyValues(dst);
        return dst;
      }

      public void copyValues(OperationOutcome dst) {
        super.copyValues(dst);
        if (issue != null) {
          dst.issue = new ArrayList<OperationOutcomeIssueComponent>();
          for (OperationOutcomeIssueComponent i : issue)
            dst.issue.add(i.copy());
        };
      }

      protected OperationOutcome typedCopy() {
        return copy();
      }

      @Override
      public boolean equalsDeep(Base other_) {
        if (!super.equalsDeep(other_))
          return false;
        if (!(other_ instanceof OperationOutcome))
          return false;
        OperationOutcome o = (OperationOutcome) other_;
        return compareDeep(issue, o.issue, true);
      }

      @Override
      public boolean equalsShallow(Base other_) {
        if (!super.equalsShallow(other_))
          return false;
        if (!(other_ instanceof OperationOutcome))
          return false;
        OperationOutcome o = (OperationOutcome) other_;
        return true;
      }

      public boolean isEmpty() {
        return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(issue);
      }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.OperationOutcome;
   }

// Manual code (from Configuration.txt):
  public boolean supportsCopyright() {
    return true;
  }

  
  public boolean isSuccess() {
    for (OperationOutcomeIssueComponent iss : getIssue()) {
      if (iss.isWarningOrMore() || iss.getCode() != IssueType.INFORMATIONAL) {
        return false;
      }
      if (iss.isInformationorLess() || iss.getCode() != IssueType.INFORMATIONAL) {
        return true;
      }
    }
    return false;
  }

// end addition

}

