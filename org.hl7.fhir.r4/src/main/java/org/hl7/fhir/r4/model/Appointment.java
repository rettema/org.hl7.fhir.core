package org.hl7.fhir.r4.model;

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

// Generated on Tue, May 12, 2020 07:26+1000 for FHIR v4.0.1
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.instance.model.api.IBaseBackboneElement;
import org.hl7.fhir.utilities.Utilities;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.api.annotation.SearchParamDefinition;

/**
 * A booking of a healthcare event among patient(s), practitioner(s), related
 * person(s) and/or device(s) for a specific date/time. This may result in one
 * or more Encounter(s).
 */
@ResourceDef(name = "Appointment", profile = "http://hl7.org/fhir/StructureDefinition/Appointment")
public class Appointment extends DomainResource {

  public enum AppointmentStatus {
    /**
     * None of the participant(s) have finalized their acceptance of the appointment
     * request, and the start/end time might not be set yet.
     */
    PROPOSED,
    /**
     * Some or all of the participant(s) have not finalized their acceptance of the
     * appointment request.
     */
    PENDING,
    /**
     * All participant(s) have been considered and the appointment is confirmed to
     * go ahead at the date/times specified.
     */
    BOOKED,
    /**
     * The patient/patients has/have arrived and is/are waiting to be seen.
     */
    ARRIVED,
    /**
     * The planning stages of the appointment are now complete, the encounter
     * resource will exist and will track further status changes. Note that an
     * encounter may exist before the appointment status is fulfilled for many
     * reasons.
     */
    FULFILLED,
    /**
     * The appointment has been cancelled.
     */
    CANCELLED,
    /**
     * Some or all of the participant(s) have not/did not appear for the appointment
     * (usually the patient).
     */
    NOSHOW,
    /**
     * This instance should not have been part of this patient's medical record.
     */
    ENTEREDINERROR,
    /**
     * When checked in, all pre-encounter administrative work is complete, and the
     * encounter may begin. (where multiple patients are involved, they are all
     * present).
     */
    CHECKEDIN,
    /**
     * The appointment has been placed on a waitlist, to be scheduled/confirmed in
     * the future when a slot/service is available. A specific time might or might
     * not be pre-allocated.
     */
    WAITLIST,
    /**
     * added to help the parsers with the generic types
     */
    NULL;

    public static AppointmentStatus fromCode(String codeString) throws FHIRException {
      if (codeString == null || "".equals(codeString))
        return null;
      if ("proposed".equals(codeString))
        return PROPOSED;
      if ("pending".equals(codeString))
        return PENDING;
      if ("booked".equals(codeString))
        return BOOKED;
      if ("arrived".equals(codeString))
        return ARRIVED;
      if ("fulfilled".equals(codeString))
        return FULFILLED;
      if ("cancelled".equals(codeString))
        return CANCELLED;
      if ("noshow".equals(codeString))
        return NOSHOW;
      if ("entered-in-error".equals(codeString))
        return ENTEREDINERROR;
      if ("checked-in".equals(codeString))
        return CHECKEDIN;
      if ("waitlist".equals(codeString))
        return WAITLIST;
      if (Configuration.isAcceptInvalidEnums())
        return null;
      else
        throw new FHIRException("Unknown AppointmentStatus code '" + codeString + "'");
    }

    public String toCode() {
      switch (this) {
      case PROPOSED:
        return "proposed";
      case PENDING:
        return "pending";
      case BOOKED:
        return "booked";
      case ARRIVED:
        return "arrived";
      case FULFILLED:
        return "fulfilled";
      case CANCELLED:
        return "cancelled";
      case NOSHOW:
        return "noshow";
      case ENTEREDINERROR:
        return "entered-in-error";
      case CHECKEDIN:
        return "checked-in";
      case WAITLIST:
        return "waitlist";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getSystem() {
      switch (this) {
      case PROPOSED:
        return "http://hl7.org/fhir/appointmentstatus";
      case PENDING:
        return "http://hl7.org/fhir/appointmentstatus";
      case BOOKED:
        return "http://hl7.org/fhir/appointmentstatus";
      case ARRIVED:
        return "http://hl7.org/fhir/appointmentstatus";
      case FULFILLED:
        return "http://hl7.org/fhir/appointmentstatus";
      case CANCELLED:
        return "http://hl7.org/fhir/appointmentstatus";
      case NOSHOW:
        return "http://hl7.org/fhir/appointmentstatus";
      case ENTEREDINERROR:
        return "http://hl7.org/fhir/appointmentstatus";
      case CHECKEDIN:
        return "http://hl7.org/fhir/appointmentstatus";
      case WAITLIST:
        return "http://hl7.org/fhir/appointmentstatus";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDefinition() {
      switch (this) {
      case PROPOSED:
        return "None of the participant(s) have finalized their acceptance of the appointment request, and the start/end time might not be set yet.";
      case PENDING:
        return "Some or all of the participant(s) have not finalized their acceptance of the appointment request.";
      case BOOKED:
        return "All participant(s) have been considered and the appointment is confirmed to go ahead at the date/times specified.";
      case ARRIVED:
        return "The patient/patients has/have arrived and is/are waiting to be seen.";
      case FULFILLED:
        return "The planning stages of the appointment are now complete, the encounter resource will exist and will track further status changes. Note that an encounter may exist before the appointment status is fulfilled for many reasons.";
      case CANCELLED:
        return "The appointment has been cancelled.";
      case NOSHOW:
        return "Some or all of the participant(s) have not/did not appear for the appointment (usually the patient).";
      case ENTEREDINERROR:
        return "This instance should not have been part of this patient's medical record.";
      case CHECKEDIN:
        return "When checked in, all pre-encounter administrative work is complete, and the encounter may begin. (where multiple patients are involved, they are all present).";
      case WAITLIST:
        return "The appointment has been placed on a waitlist, to be scheduled/confirmed in the future when a slot/service is available.\nA specific time might or might not be pre-allocated.";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDisplay() {
      switch (this) {
      case PROPOSED:
        return "Proposed";
      case PENDING:
        return "Pending";
      case BOOKED:
        return "Booked";
      case ARRIVED:
        return "Arrived";
      case FULFILLED:
        return "Fulfilled";
      case CANCELLED:
        return "Cancelled";
      case NOSHOW:
        return "No Show";
      case ENTEREDINERROR:
        return "Entered in error";
      case CHECKEDIN:
        return "Checked In";
      case WAITLIST:
        return "Waitlisted";
      case NULL:
        return null;
      default:
        return "?";
      }
    }
  }

  public static class AppointmentStatusEnumFactory implements EnumFactory<AppointmentStatus> {
    public AppointmentStatus fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
        if (codeString == null || "".equals(codeString))
          return null;
      if ("proposed".equals(codeString))
        return AppointmentStatus.PROPOSED;
      if ("pending".equals(codeString))
        return AppointmentStatus.PENDING;
      if ("booked".equals(codeString))
        return AppointmentStatus.BOOKED;
      if ("arrived".equals(codeString))
        return AppointmentStatus.ARRIVED;
      if ("fulfilled".equals(codeString))
        return AppointmentStatus.FULFILLED;
      if ("cancelled".equals(codeString))
        return AppointmentStatus.CANCELLED;
      if ("noshow".equals(codeString))
        return AppointmentStatus.NOSHOW;
      if ("entered-in-error".equals(codeString))
        return AppointmentStatus.ENTEREDINERROR;
      if ("checked-in".equals(codeString))
        return AppointmentStatus.CHECKEDIN;
      if ("waitlist".equals(codeString))
        return AppointmentStatus.WAITLIST;
      throw new IllegalArgumentException("Unknown AppointmentStatus code '" + codeString + "'");
    }

    public Enumeration<AppointmentStatus> fromType(PrimitiveType<?> code) throws FHIRException {
      if (code == null)
        return null;
      if (code.isEmpty())
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.NULL, code);
      String codeString = code.asStringValue();
      if (codeString == null || "".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.NULL, code);
      if ("proposed".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.PROPOSED, code);
      if ("pending".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.PENDING, code);
      if ("booked".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.BOOKED, code);
      if ("arrived".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.ARRIVED, code);
      if ("fulfilled".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.FULFILLED, code);
      if ("cancelled".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.CANCELLED, code);
      if ("noshow".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.NOSHOW, code);
      if ("entered-in-error".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.ENTEREDINERROR, code);
      if ("checked-in".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.CHECKEDIN, code);
      if ("waitlist".equals(codeString))
        return new Enumeration<AppointmentStatus>(this, AppointmentStatus.WAITLIST, code);
      throw new FHIRException("Unknown AppointmentStatus code '" + codeString + "'");
    }

    public String toCode(AppointmentStatus code) {
       if (code == AppointmentStatus.NULL)
           return null;
       if (code == AppointmentStatus.PROPOSED)
        return "proposed";
      if (code == AppointmentStatus.PENDING)
        return "pending";
      if (code == AppointmentStatus.BOOKED)
        return "booked";
      if (code == AppointmentStatus.ARRIVED)
        return "arrived";
      if (code == AppointmentStatus.FULFILLED)
        return "fulfilled";
      if (code == AppointmentStatus.CANCELLED)
        return "cancelled";
      if (code == AppointmentStatus.NOSHOW)
        return "noshow";
      if (code == AppointmentStatus.ENTEREDINERROR)
        return "entered-in-error";
      if (code == AppointmentStatus.CHECKEDIN)
        return "checked-in";
      if (code == AppointmentStatus.WAITLIST)
        return "waitlist";
      return "?";
   }

    public String toSystem(AppointmentStatus code) {
      return code.getSystem();
    }
  }

  public enum ParticipantRequired {
    /**
     * The participant is required to attend the appointment.
     */
    REQUIRED,
    /**
     * The participant may optionally attend the appointment.
     */
    OPTIONAL,
    /**
     * The participant is excluded from the appointment, and might not be informed
     * of the appointment taking place. (Appointment is about them, not for them -
     * such as 2 doctors discussing results about a patient's test).
     */
    INFORMATIONONLY,
    /**
     * added to help the parsers with the generic types
     */
    NULL;

    public static ParticipantRequired fromCode(String codeString) throws FHIRException {
      if (codeString == null || "".equals(codeString))
        return null;
      if ("required".equals(codeString))
        return REQUIRED;
      if ("optional".equals(codeString))
        return OPTIONAL;
      if ("information-only".equals(codeString))
        return INFORMATIONONLY;
      if (Configuration.isAcceptInvalidEnums())
        return null;
      else
        throw new FHIRException("Unknown ParticipantRequired code '" + codeString + "'");
    }

    public String toCode() {
      switch (this) {
      case REQUIRED:
        return "required";
      case OPTIONAL:
        return "optional";
      case INFORMATIONONLY:
        return "information-only";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getSystem() {
      switch (this) {
      case REQUIRED:
        return "http://hl7.org/fhir/participantrequired";
      case OPTIONAL:
        return "http://hl7.org/fhir/participantrequired";
      case INFORMATIONONLY:
        return "http://hl7.org/fhir/participantrequired";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDefinition() {
      switch (this) {
      case REQUIRED:
        return "The participant is required to attend the appointment.";
      case OPTIONAL:
        return "The participant may optionally attend the appointment.";
      case INFORMATIONONLY:
        return "The participant is excluded from the appointment, and might not be informed of the appointment taking place. (Appointment is about them, not for them - such as 2 doctors discussing results about a patient's test).";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDisplay() {
      switch (this) {
      case REQUIRED:
        return "Required";
      case OPTIONAL:
        return "Optional";
      case INFORMATIONONLY:
        return "Information Only";
      case NULL:
        return null;
      default:
        return "?";
      }
    }
  }

  public static class ParticipantRequiredEnumFactory implements EnumFactory<ParticipantRequired> {
    public ParticipantRequired fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
        if (codeString == null || "".equals(codeString))
          return null;
      if ("required".equals(codeString))
        return ParticipantRequired.REQUIRED;
      if ("optional".equals(codeString))
        return ParticipantRequired.OPTIONAL;
      if ("information-only".equals(codeString))
        return ParticipantRequired.INFORMATIONONLY;
      throw new IllegalArgumentException("Unknown ParticipantRequired code '" + codeString + "'");
    }

    public Enumeration<ParticipantRequired> fromType(PrimitiveType<?> code) throws FHIRException {
      if (code == null)
        return null;
      if (code.isEmpty())
        return new Enumeration<ParticipantRequired>(this, ParticipantRequired.NULL, code);
      String codeString = code.asStringValue();
      if (codeString == null || "".equals(codeString))
        return new Enumeration<ParticipantRequired>(this, ParticipantRequired.NULL, code);
      if ("required".equals(codeString))
        return new Enumeration<ParticipantRequired>(this, ParticipantRequired.REQUIRED, code);
      if ("optional".equals(codeString))
        return new Enumeration<ParticipantRequired>(this, ParticipantRequired.OPTIONAL, code);
      if ("information-only".equals(codeString))
        return new Enumeration<ParticipantRequired>(this, ParticipantRequired.INFORMATIONONLY, code);
      throw new FHIRException("Unknown ParticipantRequired code '" + codeString + "'");
    }

    public String toCode(ParticipantRequired code) {
       if (code == ParticipantRequired.NULL)
           return null;
       if (code == ParticipantRequired.REQUIRED)
        return "required";
      if (code == ParticipantRequired.OPTIONAL)
        return "optional";
      if (code == ParticipantRequired.INFORMATIONONLY)
        return "information-only";
      return "?";
   }

    public String toSystem(ParticipantRequired code) {
      return code.getSystem();
    }
  }

  public enum ParticipationStatus {
    /**
     * The participant has accepted the appointment.
     */
    ACCEPTED,
    /**
     * The participant has declined the appointment and will not participate in the
     * appointment.
     */
    DECLINED,
    /**
     * The participant has tentatively accepted the appointment. This could be
     * automatically created by a system and requires further processing before it
     * can be accepted. There is no commitment that attendance will occur.
     */
    TENTATIVE,
    /**
     * The participant needs to indicate if they accept the appointment by changing
     * this status to one of the other statuses.
     */
    NEEDSACTION,
    /**
     * added to help the parsers with the generic types
     */
    NULL;

    public static ParticipationStatus fromCode(String codeString) throws FHIRException {
      if (codeString == null || "".equals(codeString))
        return null;
      if ("accepted".equals(codeString))
        return ACCEPTED;
      if ("declined".equals(codeString))
        return DECLINED;
      if ("tentative".equals(codeString))
        return TENTATIVE;
      if ("needs-action".equals(codeString))
        return NEEDSACTION;
      if (Configuration.isAcceptInvalidEnums())
        return null;
      else
        throw new FHIRException("Unknown ParticipationStatus code '" + codeString + "'");
    }

    public String toCode() {
      switch (this) {
      case ACCEPTED:
        return "accepted";
      case DECLINED:
        return "declined";
      case TENTATIVE:
        return "tentative";
      case NEEDSACTION:
        return "needs-action";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getSystem() {
      switch (this) {
      case ACCEPTED:
        return "http://hl7.org/fhir/participationstatus";
      case DECLINED:
        return "http://hl7.org/fhir/participationstatus";
      case TENTATIVE:
        return "http://hl7.org/fhir/participationstatus";
      case NEEDSACTION:
        return "http://hl7.org/fhir/participationstatus";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDefinition() {
      switch (this) {
      case ACCEPTED:
        return "The participant has accepted the appointment.";
      case DECLINED:
        return "The participant has declined the appointment and will not participate in the appointment.";
      case TENTATIVE:
        return "The participant has  tentatively accepted the appointment. This could be automatically created by a system and requires further processing before it can be accepted. There is no commitment that attendance will occur.";
      case NEEDSACTION:
        return "The participant needs to indicate if they accept the appointment by changing this status to one of the other statuses.";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDisplay() {
      switch (this) {
      case ACCEPTED:
        return "Accepted";
      case DECLINED:
        return "Declined";
      case TENTATIVE:
        return "Tentative";
      case NEEDSACTION:
        return "Needs Action";
      case NULL:
        return null;
      default:
        return "?";
      }
    }
  }

  public static class ParticipationStatusEnumFactory implements EnumFactory<ParticipationStatus> {
    public ParticipationStatus fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
        if (codeString == null || "".equals(codeString))
          return null;
      if ("accepted".equals(codeString))
        return ParticipationStatus.ACCEPTED;
      if ("declined".equals(codeString))
        return ParticipationStatus.DECLINED;
      if ("tentative".equals(codeString))
        return ParticipationStatus.TENTATIVE;
      if ("needs-action".equals(codeString))
        return ParticipationStatus.NEEDSACTION;
      throw new IllegalArgumentException("Unknown ParticipationStatus code '" + codeString + "'");
    }

    public Enumeration<ParticipationStatus> fromType(PrimitiveType<?> code) throws FHIRException {
      if (code == null)
        return null;
      if (code.isEmpty())
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.NULL, code);
      String codeString = code.asStringValue();
      if (codeString == null || "".equals(codeString))
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.NULL, code);
      if ("accepted".equals(codeString))
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.ACCEPTED, code);
      if ("declined".equals(codeString))
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.DECLINED, code);
      if ("tentative".equals(codeString))
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.TENTATIVE, code);
      if ("needs-action".equals(codeString))
        return new Enumeration<ParticipationStatus>(this, ParticipationStatus.NEEDSACTION, code);
      throw new FHIRException("Unknown ParticipationStatus code '" + codeString + "'");
    }

    public String toCode(ParticipationStatus code) {
       if (code == ParticipationStatus.NULL)
           return null;
       if (code == ParticipationStatus.ACCEPTED)
        return "accepted";
      if (code == ParticipationStatus.DECLINED)
        return "declined";
      if (code == ParticipationStatus.TENTATIVE)
        return "tentative";
      if (code == ParticipationStatus.NEEDSACTION)
        return "needs-action";
      return "?";
   }

    public String toSystem(ParticipationStatus code) {
      return code.getSystem();
    }
  }

  @Block()
  public static class AppointmentParticipantComponent extends BackboneElement implements IBaseBackboneElement {
    /**
     * Role of participant in the appointment.
     */
    @Child(name = "type", type = {
        CodeableConcept.class }, order = 1, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
    @Description(shortDefinition = "Role of participant in the appointment", formalDefinition = "Role of participant in the appointment.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/encounter-participant-type")
    protected List<CodeableConcept> type;

    /**
     * A Person, Location/HealthcareService or Device that is participating in the
     * appointment.
     */
    @Child(name = "actor", type = { Patient.class, Practitioner.class, PractitionerRole.class, RelatedPerson.class,
        Device.class, HealthcareService.class,
        Location.class }, order = 2, min = 0, max = 1, modifier = false, summary = true)
    @Description(shortDefinition = "Person, Location/HealthcareService or Device", formalDefinition = "A Person, Location/HealthcareService or Device that is participating in the appointment.")
    protected Reference actor;

    /**
     * The actual object that is the target of the reference (A Person,
     * Location/HealthcareService or Device that is participating in the
     * appointment.)
     */
    protected Resource actorTarget;

    /**
     * Whether this participant is required to be present at the meeting. This
     * covers a use-case where two doctors need to meet to discuss the results for a
     * specific patient, and the patient is not required to be present.
     */
    @Child(name = "required", type = { CodeType.class }, order = 3, min = 0, max = 1, modifier = false, summary = true)
    @Description(shortDefinition = "required | optional | information-only", formalDefinition = "Whether this participant is required to be present at the meeting. This covers a use-case where two doctors need to meet to discuss the results for a specific patient, and the patient is not required to be present.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/participantrequired")
    protected Enumeration<ParticipantRequired> required;

    /**
     * Participation status of the actor.
     */
    @Child(name = "status", type = { CodeType.class }, order = 4, min = 1, max = 1, modifier = false, summary = true)
    @Description(shortDefinition = "accepted | declined | tentative | needs-action", formalDefinition = "Participation status of the actor.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/participationstatus")
    protected Enumeration<ParticipationStatus> status;

    /**
     * Participation period of the actor.
     */
    @Child(name = "period", type = { Period.class }, order = 5, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Participation period of the actor", formalDefinition = "Participation period of the actor.")
    protected Period period;

    private static final long serialVersionUID = -1939292177L;

    /**
     * Constructor
     */
    public AppointmentParticipantComponent() {
      super();
    }

    /**
     * Constructor
     */
    public AppointmentParticipantComponent(Enumeration<ParticipationStatus> status) {
      super();
      this.status = status;
    }

    /**
     * @return {@link #type} (Role of participant in the appointment.)
     */
    public List<CodeableConcept> getType() {
      if (this.type == null)
        this.type = new ArrayList<CodeableConcept>();
      return this.type;
    }

    /**
     * @return Returns a reference to <code>this</code> for easy method chaining
     */
    public AppointmentParticipantComponent setType(List<CodeableConcept> theType) {
      this.type = theType;
      return this;
    }

    public boolean hasType() {
      if (this.type == null)
        return false;
      for (CodeableConcept item : this.type)
        if (!item.isEmpty())
          return true;
      return false;
    }

    public CodeableConcept addType() { // 3
      CodeableConcept t = new CodeableConcept();
      if (this.type == null)
        this.type = new ArrayList<CodeableConcept>();
      this.type.add(t);
      return t;
    }

    public AppointmentParticipantComponent addType(CodeableConcept t) { // 3
      if (t == null)
        return this;
      if (this.type == null)
        this.type = new ArrayList<CodeableConcept>();
      this.type.add(t);
      return this;
    }

    /**
     * @return The first repetition of repeating field {@link #type}, creating it if
     *         it does not already exist
     */
    public CodeableConcept getTypeFirstRep() {
      if (getType().isEmpty()) {
        addType();
      }
      return getType().get(0);
    }

    /**
     * @return {@link #actor} (A Person, Location/HealthcareService or Device that
     *         is participating in the appointment.)
     */
    public Reference getActor() {
      if (this.actor == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create AppointmentParticipantComponent.actor");
        else if (Configuration.doAutoCreate())
          this.actor = new Reference(); // cc
      return this.actor;
    }

    public boolean hasActor() {
      return this.actor != null && !this.actor.isEmpty();
    }

    /**
     * @param value {@link #actor} (A Person, Location/HealthcareService or Device
     *              that is participating in the appointment.)
     */
    public AppointmentParticipantComponent setActor(Reference value) {
      this.actor = value;
      return this;
    }

    /**
     * @return {@link #actor} The actual object that is the target of the reference.
     *         The reference library doesn't populate this, but you can use it to
     *         hold the resource if you resolve it. (A Person,
     *         Location/HealthcareService or Device that is participating in the
     *         appointment.)
     */
    public Resource getActorTarget() {
      return this.actorTarget;
    }

    /**
     * @param value {@link #actor} The actual object that is the target of the
     *              reference. The reference library doesn't use these, but you can
     *              use it to hold the resource if you resolve it. (A Person,
     *              Location/HealthcareService or Device that is participating in
     *              the appointment.)
     */
    public AppointmentParticipantComponent setActorTarget(Resource value) {
      this.actorTarget = value;
      return this;
    }

    /**
     * @return {@link #required} (Whether this participant is required to be present
     *         at the meeting. This covers a use-case where two doctors need to meet
     *         to discuss the results for a specific patient, and the patient is not
     *         required to be present.). This is the underlying object with id,
     *         value and extensions. The accessor "getRequired" gives direct access
     *         to the value
     */
    public Enumeration<ParticipantRequired> getRequiredElement() {
      if (this.required == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create AppointmentParticipantComponent.required");
        else if (Configuration.doAutoCreate())
          this.required = new Enumeration<ParticipantRequired>(new ParticipantRequiredEnumFactory()); // bb
      return this.required;
    }

    public boolean hasRequiredElement() {
      return this.required != null && !this.required.isEmpty();
    }

    public boolean hasRequired() {
      return this.required != null && !this.required.isEmpty();
    }

    /**
     * @param value {@link #required} (Whether this participant is required to be
     *              present at the meeting. This covers a use-case where two doctors
     *              need to meet to discuss the results for a specific patient, and
     *              the patient is not required to be present.). This is the
     *              underlying object with id, value and extensions. The accessor
     *              "getRequired" gives direct access to the value
     */
    public AppointmentParticipantComponent setRequiredElement(Enumeration<ParticipantRequired> value) {
      this.required = value;
      return this;
    }

    /**
     * @return Whether this participant is required to be present at the meeting.
     *         This covers a use-case where two doctors need to meet to discuss the
     *         results for a specific patient, and the patient is not required to be
     *         present.
     */
    public ParticipantRequired getRequired() {
      return this.required == null ? null : this.required.getValue();
    }

    /**
     * @param value Whether this participant is required to be present at the
     *              meeting. This covers a use-case where two doctors need to meet
     *              to discuss the results for a specific patient, and the patient
     *              is not required to be present.
     */
    public AppointmentParticipantComponent setRequired(ParticipantRequired value) {
      if (value == null)
        this.required = null;
      else {
        if (this.required == null)
          this.required = new Enumeration<ParticipantRequired>(new ParticipantRequiredEnumFactory());
        this.required.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #status} (Participation status of the actor.). This is the
     *         underlying object with id, value and extensions. The accessor
     *         "getStatus" gives direct access to the value
     */
    public Enumeration<ParticipationStatus> getStatusElement() {
      if (this.status == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create AppointmentParticipantComponent.status");
        else if (Configuration.doAutoCreate())
          this.status = new Enumeration<ParticipationStatus>(new ParticipationStatusEnumFactory()); // bb
      return this.status;
    }

    public boolean hasStatusElement() {
      return this.status != null && !this.status.isEmpty();
    }

    public boolean hasStatus() {
      return this.status != null && !this.status.isEmpty();
    }

    /**
     * @param value {@link #status} (Participation status of the actor.). This is
     *              the underlying object with id, value and extensions. The
     *              accessor "getStatus" gives direct access to the value
     */
    public AppointmentParticipantComponent setStatusElement(Enumeration<ParticipationStatus> value) {
      this.status = value;
      return this;
    }

    /**
     * @return Participation status of the actor.
     */
    public ParticipationStatus getStatus() {
      return this.status == null ? null : this.status.getValue();
    }

    /**
     * @param value Participation status of the actor.
     */
    public AppointmentParticipantComponent setStatus(ParticipationStatus value) {
      if (this.status == null)
        this.status = new Enumeration<ParticipationStatus>(new ParticipationStatusEnumFactory());
      this.status.setValue(value);
      return this;
    }

    /**
     * @return {@link #period} (Participation period of the actor.)
     */
    public Period getPeriod() {
      if (this.period == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create AppointmentParticipantComponent.period");
        else if (Configuration.doAutoCreate())
          this.period = new Period(); // cc
      return this.period;
    }

    public boolean hasPeriod() {
      return this.period != null && !this.period.isEmpty();
    }

    /**
     * @param value {@link #period} (Participation period of the actor.)
     */
    public AppointmentParticipantComponent setPeriod(Period value) {
      this.period = value;
      return this;
    }

    protected void listChildren(List<Property> children) {
      super.listChildren(children);
      children.add(new Property("type", "CodeableConcept", "Role of participant in the appointment.", 0,
          java.lang.Integer.MAX_VALUE, type));
      children.add(new Property("actor",
          "Reference(Patient|Practitioner|PractitionerRole|RelatedPerson|Device|HealthcareService|Location)",
          "A Person, Location/HealthcareService or Device that is participating in the appointment.", 0, 1, actor));
      children.add(new Property("required", "code",
          "Whether this participant is required to be present at the meeting. This covers a use-case where two doctors need to meet to discuss the results for a specific patient, and the patient is not required to be present.",
          0, 1, required));
      children.add(new Property("status", "code", "Participation status of the actor.", 0, 1, status));
      children.add(new Property("period", "Period", "Participation period of the actor.", 0, 1, period));
    }

    @Override
    public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
      switch (_hash) {
      case 3575610:
        /* type */ return new Property("type", "CodeableConcept", "Role of participant in the appointment.", 0,
            java.lang.Integer.MAX_VALUE, type);
      case 92645877:
        /* actor */ return new Property("actor",
            "Reference(Patient|Practitioner|PractitionerRole|RelatedPerson|Device|HealthcareService|Location)",
            "A Person, Location/HealthcareService or Device that is participating in the appointment.", 0, 1, actor);
      case -393139297:
        /* required */ return new Property("required", "code",
            "Whether this participant is required to be present at the meeting. This covers a use-case where two doctors need to meet to discuss the results for a specific patient, and the patient is not required to be present.",
            0, 1, required);
      case -892481550:
        /* status */ return new Property("status", "code", "Participation status of the actor.", 0, 1, status);
      case -991726143:
        /* period */ return new Property("period", "Period", "Participation period of the actor.", 0, 1, period);
      default:
        return super.getNamedProperty(_hash, _name, _checkValid);
      }

    }

    @Override
    public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
      switch (hash) {
      case 3575610:
        /* type */ return this.type == null ? new Base[0] : this.type.toArray(new Base[this.type.size()]); // CodeableConcept
      case 92645877:
        /* actor */ return this.actor == null ? new Base[0] : new Base[] { this.actor }; // Reference
      case -393139297:
        /* required */ return this.required == null ? new Base[0] : new Base[] { this.required }; // Enumeration<ParticipantRequired>
      case -892481550:
        /* status */ return this.status == null ? new Base[0] : new Base[] { this.status }; // Enumeration<ParticipationStatus>
      case -991726143:
        /* period */ return this.period == null ? new Base[0] : new Base[] { this.period }; // Period
      default:
        return super.getProperty(hash, name, checkValid);
      }

    }

    @Override
    public Base setProperty(int hash, String name, Base value) throws FHIRException {
      switch (hash) {
      case 3575610: // type
        this.getType().add(castToCodeableConcept(value)); // CodeableConcept
        return value;
      case 92645877: // actor
        this.actor = castToReference(value); // Reference
        return value;
      case -393139297: // required
        value = new ParticipantRequiredEnumFactory().fromType(castToCode(value));
        this.required = (Enumeration) value; // Enumeration<ParticipantRequired>
        return value;
      case -892481550: // status
        value = new ParticipationStatusEnumFactory().fromType(castToCode(value));
        this.status = (Enumeration) value; // Enumeration<ParticipationStatus>
        return value;
      case -991726143: // period
        this.period = castToPeriod(value); // Period
        return value;
      default:
        return super.setProperty(hash, name, value);
      }

    }

    @Override
    public Base setProperty(String name, Base value) throws FHIRException {
      if (name.equals("type")) {
        this.getType().add(castToCodeableConcept(value));
      } else if (name.equals("actor")) {
        this.actor = castToReference(value); // Reference
      } else if (name.equals("required")) {
        value = new ParticipantRequiredEnumFactory().fromType(castToCode(value));
        this.required = (Enumeration) value; // Enumeration<ParticipantRequired>
      } else if (name.equals("status")) {
        value = new ParticipationStatusEnumFactory().fromType(castToCode(value));
        this.status = (Enumeration) value; // Enumeration<ParticipationStatus>
      } else if (name.equals("period")) {
        this.period = castToPeriod(value); // Period
      } else
        return super.setProperty(name, value);
      return value;
    }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
      if (name.equals("type")) {
        this.getType().remove(castToCodeableConcept(value));
      } else if (name.equals("actor")) {
        this.actor = null;
      } else if (name.equals("required")) {
        this.required = null;
      } else if (name.equals("status")) {
        this.status = null;
      } else if (name.equals("period")) {
        this.period = null;
      } else
        super.removeChild(name, value);
      
    }

    @Override
    public Base makeProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 3575610:
        return addType();
      case 92645877:
        return getActor();
      case -393139297:
        return getRequiredElement();
      case -892481550:
        return getStatusElement();
      case -991726143:
        return getPeriod();
      default:
        return super.makeProperty(hash, name);
      }

    }

    @Override
    public String[] getTypesForProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 3575610:
        /* type */ return new String[] { "CodeableConcept" };
      case 92645877:
        /* actor */ return new String[] { "Reference" };
      case -393139297:
        /* required */ return new String[] { "code" };
      case -892481550:
        /* status */ return new String[] { "code" };
      case -991726143:
        /* period */ return new String[] { "Period" };
      default:
        return super.getTypesForProperty(hash, name);
      }

    }

    @Override
    public Base addChild(String name) throws FHIRException {
      if (name.equals("type")) {
        return addType();
      } else if (name.equals("actor")) {
        this.actor = new Reference();
        return this.actor;
      } else if (name.equals("required")) {
        throw new FHIRException("Cannot call addChild on a singleton property Appointment.required");
      } else if (name.equals("status")) {
        throw new FHIRException("Cannot call addChild on a singleton property Appointment.status");
      } else if (name.equals("period")) {
        this.period = new Period();
        return this.period;
      } else
        return super.addChild(name);
    }

    public AppointmentParticipantComponent copy() {
      AppointmentParticipantComponent dst = new AppointmentParticipantComponent();
      copyValues(dst);
      return dst;
    }

    public void copyValues(AppointmentParticipantComponent dst) {
      super.copyValues(dst);
      if (type != null) {
        dst.type = new ArrayList<CodeableConcept>();
        for (CodeableConcept i : type)
          dst.type.add(i.copy());
      }
      ;
      dst.actor = actor == null ? null : actor.copy();
      dst.required = required == null ? null : required.copy();
      dst.status = status == null ? null : status.copy();
      dst.period = period == null ? null : period.copy();
    }

    @Override
    public boolean equalsDeep(Base other_) {
      if (!super.equalsDeep(other_))
        return false;
      if (!(other_ instanceof AppointmentParticipantComponent))
        return false;
      AppointmentParticipantComponent o = (AppointmentParticipantComponent) other_;
      return compareDeep(type, o.type, true) && compareDeep(actor, o.actor, true)
          && compareDeep(required, o.required, true) && compareDeep(status, o.status, true)
          && compareDeep(period, o.period, true);
    }

    @Override
    public boolean equalsShallow(Base other_) {
      if (!super.equalsShallow(other_))
        return false;
      if (!(other_ instanceof AppointmentParticipantComponent))
        return false;
      AppointmentParticipantComponent o = (AppointmentParticipantComponent) other_;
      return compareValues(required, o.required, true) && compareValues(status, o.status, true);
    }

    public boolean isEmpty() {
      return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(type, actor, required, status, period);
    }

    public String fhirType() {
      return "Appointment.participant";

    }

  }

  /**
   * This records identifiers associated with this appointment concern that are
   * defined by business processes and/or used to refer to it when a direct URL
   * reference to the resource itself is not appropriate (e.g. in CDA documents,
   * or in written / printed documentation).
   */
  @Child(name = "identifier", type = {
      Identifier.class }, order = 0, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "External Ids for this item", formalDefinition = "This records identifiers associated with this appointment concern that are defined by business processes and/or used to refer to it when a direct URL reference to the resource itself is not appropriate (e.g. in CDA documents, or in written / printed documentation).")
  protected List<Identifier> identifier;

  /**
   * The overall status of the Appointment. Each of the participants has their own
   * participation status which indicates their involvement in the process,
   * however this status indicates the shared status.
   */
  @Child(name = "status", type = { CodeType.class }, order = 1, min = 1, max = 1, modifier = true, summary = true)
  @Description(shortDefinition = "proposed | pending | booked | arrived | fulfilled | cancelled | noshow | entered-in-error | checked-in | waitlist", formalDefinition = "The overall status of the Appointment. Each of the participants has their own participation status which indicates their involvement in the process, however this status indicates the shared status.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/appointmentstatus")
  protected Enumeration<AppointmentStatus> status;

  /**
   * The coded reason for the appointment being cancelled. This is often used in
   * reporting/billing/futher processing to determine if further actions are
   * required, or specific fees apply.
   */
  @Child(name = "cancelationReason", type = {
      CodeableConcept.class }, order = 2, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "The coded reason for the appointment being cancelled", formalDefinition = "The coded reason for the appointment being cancelled. This is often used in reporting/billing/futher processing to determine if further actions are required, or specific fees apply.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/appointment-cancellation-reason")
  protected CodeableConcept cancelationReason;

  /**
   * A broad categorization of the service that is to be performed during this
   * appointment.
   */
  @Child(name = "serviceCategory", type = {
      CodeableConcept.class }, order = 3, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "A broad categorization of the service that is to be performed during this appointment", formalDefinition = "A broad categorization of the service that is to be performed during this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/service-category")
  protected List<CodeableConcept> serviceCategory;

  /**
   * The specific service that is to be performed during this appointment.
   */
  @Child(name = "serviceType", type = {
      CodeableConcept.class }, order = 4, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "The specific service that is to be performed during this appointment", formalDefinition = "The specific service that is to be performed during this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/service-type")
  protected List<CodeableConcept> serviceType;

  /**
   * The specialty of a practitioner that would be required to perform the service
   * requested in this appointment.
   */
  @Child(name = "specialty", type = {
      CodeableConcept.class }, order = 5, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "The specialty of a practitioner that would be required to perform the service requested in this appointment", formalDefinition = "The specialty of a practitioner that would be required to perform the service requested in this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/c80-practice-codes")
  protected List<CodeableConcept> specialty;

  /**
   * The style of appointment or patient that has been booked in the slot (not
   * service type).
   */
  @Child(name = "appointmentType", type = {
      CodeableConcept.class }, order = 6, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "The style of appointment or patient that has been booked in the slot (not service type)", formalDefinition = "The style of appointment or patient that has been booked in the slot (not service type).")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://terminology.hl7.org/ValueSet/v2-0276")
  protected CodeableConcept appointmentType;

  /**
   * The coded reason that this appointment is being scheduled. This is more
   * clinical than administrative.
   */
  @Child(name = "reasonCode", type = {
      CodeableConcept.class }, order = 7, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Coded reason this appointment is scheduled", formalDefinition = "The coded reason that this appointment is being scheduled. This is more clinical than administrative.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/encounter-reason")
  protected List<CodeableConcept> reasonCode;

  /**
   * Reason the appointment has been scheduled to take place, as specified using
   * information from another resource. When the patient arrives and the encounter
   * begins it may be used as the admission diagnosis. The indication will
   * typically be a Condition (with other resources referenced in the
   * evidence.detail), or a Procedure.
   */
  @Child(name = "reasonReference", type = { Condition.class, Procedure.class, Observation.class,
      ImmunizationRecommendation.class }, order = 8, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Reason the appointment is to take place (resource)", formalDefinition = "Reason the appointment has been scheduled to take place, as specified using information from another resource. When the patient arrives and the encounter begins it may be used as the admission diagnosis. The indication will typically be a Condition (with other resources referenced in the evidence.detail), or a Procedure.")
  protected List<Reference> reasonReference;
  /**
   * The actual objects that are the target of the reference (Reason the
   * appointment has been scheduled to take place, as specified using information
   * from another resource. When the patient arrives and the encounter begins it
   * may be used as the admission diagnosis. The indication will typically be a
   * Condition (with other resources referenced in the evidence.detail), or a
   * Procedure.)
   */
  protected List<Resource> reasonReferenceTarget;

  /**
   * The priority of the appointment. Can be used to make informed decisions if
   * needing to re-prioritize appointments. (The iCal Standard specifies 0 as
   * undefined, 1 as highest, 9 as lowest priority).
   */
  @Child(name = "priority", type = {
      UnsignedIntType.class }, order = 9, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Used to make informed decisions if needing to re-prioritize", formalDefinition = "The priority of the appointment. Can be used to make informed decisions if needing to re-prioritize appointments. (The iCal Standard specifies 0 as undefined, 1 as highest, 9 as lowest priority).")
  protected UnsignedIntType priority;

  /**
   * The brief description of the appointment as would be shown on a subject line
   * in a meeting request, or appointment list. Detailed or expanded information
   * should be put in the comment field.
   */
  @Child(name = "description", type = {
      StringType.class }, order = 10, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Shown on a subject line in a meeting request, or appointment list", formalDefinition = "The brief description of the appointment as would be shown on a subject line in a meeting request, or appointment list. Detailed or expanded information should be put in the comment field.")
  protected StringType description;

  /**
   * Additional information to support the appointment provided when making the
   * appointment.
   */
  @Child(name = "supportingInformation", type = {
      Reference.class }, order = 11, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Additional information to support the appointment", formalDefinition = "Additional information to support the appointment provided when making the appointment.")
  protected List<Reference> supportingInformation;
  /**
   * The actual objects that are the target of the reference (Additional
   * information to support the appointment provided when making the appointment.)
   */
  protected List<Resource> supportingInformationTarget;

  /**
   * Date/Time that the appointment is to take place.
   */
  @Child(name = "start", type = { InstantType.class }, order = 12, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "When appointment is to take place", formalDefinition = "Date/Time that the appointment is to take place.")
  protected InstantType start;

  /**
   * Date/Time that the appointment is to conclude.
   */
  @Child(name = "end", type = { InstantType.class }, order = 13, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "When appointment is to conclude", formalDefinition = "Date/Time that the appointment is to conclude.")
  protected InstantType end;

  /**
   * Number of minutes that the appointment is to take. This can be less than the
   * duration between the start and end times. For example, where the actual time
   * of appointment is only an estimate or if a 30 minute appointment is being
   * requested, but any time would work. Also, if there is, for example, a planned
   * 15 minute break in the middle of a long appointment, the duration may be 15
   * minutes less than the difference between the start and end.
   */
  @Child(name = "minutesDuration", type = {
      PositiveIntType.class }, order = 14, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Can be less than start/end (e.g. estimate)", formalDefinition = "Number of minutes that the appointment is to take. This can be less than the duration between the start and end times.  For example, where the actual time of appointment is only an estimate or if a 30 minute appointment is being requested, but any time would work.  Also, if there is, for example, a planned 15 minute break in the middle of a long appointment, the duration may be 15 minutes less than the difference between the start and end.")
  protected PositiveIntType minutesDuration;

  /**
   * The slots from the participants' schedules that will be filled by the
   * appointment.
   */
  @Child(name = "slot", type = {
      Slot.class }, order = 15, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "The slots that this appointment is filling", formalDefinition = "The slots from the participants' schedules that will be filled by the appointment.")
  protected List<Reference> slot;
  /**
   * The actual objects that are the target of the reference (The slots from the
   * participants' schedules that will be filled by the appointment.)
   */
  protected List<Slot> slotTarget;

  /**
   * The date that this appointment was initially created. This could be different
   * to the meta.lastModified value on the initial entry, as this could have been
   * before the resource was created on the FHIR server, and should remain
   * unchanged over the lifespan of the appointment.
   */
  @Child(name = "created", type = {
      DateTimeType.class }, order = 16, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "The date that this appointment was initially created", formalDefinition = "The date that this appointment was initially created. This could be different to the meta.lastModified value on the initial entry, as this could have been before the resource was created on the FHIR server, and should remain unchanged over the lifespan of the appointment.")
  protected DateTimeType created;

  /**
   * Additional comments about the appointment.
   */
  @Child(name = "comment", type = { StringType.class }, order = 17, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Additional comments", formalDefinition = "Additional comments about the appointment.")
  protected StringType comment;

  /**
   * While Appointment.comment contains information for internal use,
   * Appointment.patientInstructions is used to capture patient facing information
   * about the Appointment (e.g. please bring your referral or fast from 8pm night
   * before).
   */
  @Child(name = "patientInstruction", type = {
      StringType.class }, order = 18, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Detailed information and instructions for the patient", formalDefinition = "While Appointment.comment contains information for internal use, Appointment.patientInstructions is used to capture patient facing information about the Appointment (e.g. please bring your referral or fast from 8pm night before).")
  protected StringType patientInstruction;

  /**
   * The service request this appointment is allocated to assess (e.g. incoming
   * referral or procedure request).
   */
  @Child(name = "basedOn", type = {
      ServiceRequest.class }, order = 19, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "The service request this appointment is allocated to assess", formalDefinition = "The service request this appointment is allocated to assess (e.g. incoming referral or procedure request).")
  protected List<Reference> basedOn;
  /**
   * The actual objects that are the target of the reference (The service request
   * this appointment is allocated to assess (e.g. incoming referral or procedure
   * request).)
   */
  protected List<ServiceRequest> basedOnTarget;

  /**
   * List of participants involved in the appointment.
   */
  @Child(name = "participant", type = {}, order = 20, min = 1, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Participants involved in appointment", formalDefinition = "List of participants involved in the appointment.")
  protected List<AppointmentParticipantComponent> participant;

  /**
   * A set of date ranges (potentially including times) that the appointment is
   * preferred to be scheduled within.
   * 
   * The duration (usually in minutes) could also be provided to indicate the
   * length of the appointment to fill and populate the start/end times for the
   * actual allocated time. However, in other situations the duration may be
   * calculated by the scheduling system.
   */
  @Child(name = "requestedPeriod", type = {
      Period.class }, order = 21, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Potential date/time interval(s) requested to allocate the appointment within", formalDefinition = "A set of date ranges (potentially including times) that the appointment is preferred to be scheduled within.\n\nThe duration (usually in minutes) could also be provided to indicate the length of the appointment to fill and populate the start/end times for the actual allocated time. However, in other situations the duration may be calculated by the scheduling system.")
  protected List<Period> requestedPeriod;

  private static final long serialVersionUID = -1096822339L;

  /**
   * Constructor
   */
  public Appointment() {
    super();
  }

  /**
   * Constructor
   */
  public Appointment(Enumeration<AppointmentStatus> status) {
    super();
    this.status = status;
  }

  /**
   * @return {@link #identifier} (This records identifiers associated with this
   *         appointment concern that are defined by business processes and/or
   *         used to refer to it when a direct URL reference to the resource
   *         itself is not appropriate (e.g. in CDA documents, or in written /
   *         printed documentation).)
   */
  public List<Identifier> getIdentifier() {
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    return this.identifier;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setIdentifier(List<Identifier> theIdentifier) {
    this.identifier = theIdentifier;
    return this;
  }

  public boolean hasIdentifier() {
    if (this.identifier == null)
      return false;
    for (Identifier item : this.identifier)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Identifier addIdentifier() { // 3
    Identifier t = new Identifier();
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    this.identifier.add(t);
    return t;
  }

  public Appointment addIdentifier(Identifier t) { // 3
    if (t == null)
      return this;
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    this.identifier.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #identifier}, creating
   *         it if it does not already exist
   */
  public Identifier getIdentifierFirstRep() {
    if (getIdentifier().isEmpty()) {
      addIdentifier();
    }
    return getIdentifier().get(0);
  }

  /**
   * @return {@link #status} (The overall status of the Appointment. Each of the
   *         participants has their own participation status which indicates their
   *         involvement in the process, however this status indicates the shared
   *         status.). This is the underlying object with id, value and
   *         extensions. The accessor "getStatus" gives direct access to the value
   */
  public Enumeration<AppointmentStatus> getStatusElement() {
    if (this.status == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.status");
      else if (Configuration.doAutoCreate())
        this.status = new Enumeration<AppointmentStatus>(new AppointmentStatusEnumFactory()); // bb
    return this.status;
  }

  public boolean hasStatusElement() {
    return this.status != null && !this.status.isEmpty();
  }

  public boolean hasStatus() {
    return this.status != null && !this.status.isEmpty();
  }

  /**
   * @param value {@link #status} (The overall status of the Appointment. Each of
   *              the participants has their own participation status which
   *              indicates their involvement in the process, however this status
   *              indicates the shared status.). This is the underlying object
   *              with id, value and extensions. The accessor "getStatus" gives
   *              direct access to the value
   */
  public Appointment setStatusElement(Enumeration<AppointmentStatus> value) {
    this.status = value;
    return this;
  }

  /**
   * @return The overall status of the Appointment. Each of the participants has
   *         their own participation status which indicates their involvement in
   *         the process, however this status indicates the shared status.
   */
  public AppointmentStatus getStatus() {
    return this.status == null ? null : this.status.getValue();
  }

  /**
   * @param value The overall status of the Appointment. Each of the participants
   *              has their own participation status which indicates their
   *              involvement in the process, however this status indicates the
   *              shared status.
   */
  public Appointment setStatus(AppointmentStatus value) {
    if (this.status == null)
      this.status = new Enumeration<AppointmentStatus>(new AppointmentStatusEnumFactory());
    this.status.setValue(value);
    return this;
  }

  /**
   * @return {@link #cancelationReason} (The coded reason for the appointment
   *         being cancelled. This is often used in reporting/billing/futher
   *         processing to determine if further actions are required, or specific
   *         fees apply.)
   */
  public CodeableConcept getCancelationReason() {
    if (this.cancelationReason == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.cancelationReason");
      else if (Configuration.doAutoCreate())
        this.cancelationReason = new CodeableConcept(); // cc
    return this.cancelationReason;
  }

  public boolean hasCancelationReason() {
    return this.cancelationReason != null && !this.cancelationReason.isEmpty();
  }

  /**
   * @param value {@link #cancelationReason} (The coded reason for the appointment
   *              being cancelled. This is often used in reporting/billing/futher
   *              processing to determine if further actions are required, or
   *              specific fees apply.)
   */
  public Appointment setCancelationReason(CodeableConcept value) {
    this.cancelationReason = value;
    return this;
  }

  /**
   * @return {@link #serviceCategory} (A broad categorization of the service that
   *         is to be performed during this appointment.)
   */
  public List<CodeableConcept> getServiceCategory() {
    if (this.serviceCategory == null)
      this.serviceCategory = new ArrayList<CodeableConcept>();
    return this.serviceCategory;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setServiceCategory(List<CodeableConcept> theServiceCategory) {
    this.serviceCategory = theServiceCategory;
    return this;
  }

  public boolean hasServiceCategory() {
    if (this.serviceCategory == null)
      return false;
    for (CodeableConcept item : this.serviceCategory)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addServiceCategory() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.serviceCategory == null)
      this.serviceCategory = new ArrayList<CodeableConcept>();
    this.serviceCategory.add(t);
    return t;
  }

  public Appointment addServiceCategory(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.serviceCategory == null)
      this.serviceCategory = new ArrayList<CodeableConcept>();
    this.serviceCategory.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #serviceCategory},
   *         creating it if it does not already exist
   */
  public CodeableConcept getServiceCategoryFirstRep() {
    if (getServiceCategory().isEmpty()) {
      addServiceCategory();
    }
    return getServiceCategory().get(0);
  }

  /**
   * @return {@link #serviceType} (The specific service that is to be performed
   *         during this appointment.)
   */
  public List<CodeableConcept> getServiceType() {
    if (this.serviceType == null)
      this.serviceType = new ArrayList<CodeableConcept>();
    return this.serviceType;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setServiceType(List<CodeableConcept> theServiceType) {
    this.serviceType = theServiceType;
    return this;
  }

  public boolean hasServiceType() {
    if (this.serviceType == null)
      return false;
    for (CodeableConcept item : this.serviceType)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addServiceType() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.serviceType == null)
      this.serviceType = new ArrayList<CodeableConcept>();
    this.serviceType.add(t);
    return t;
  }

  public Appointment addServiceType(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.serviceType == null)
      this.serviceType = new ArrayList<CodeableConcept>();
    this.serviceType.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #serviceType},
   *         creating it if it does not already exist
   */
  public CodeableConcept getServiceTypeFirstRep() {
    if (getServiceType().isEmpty()) {
      addServiceType();
    }
    return getServiceType().get(0);
  }

  /**
   * @return {@link #specialty} (The specialty of a practitioner that would be
   *         required to perform the service requested in this appointment.)
   */
  public List<CodeableConcept> getSpecialty() {
    if (this.specialty == null)
      this.specialty = new ArrayList<CodeableConcept>();
    return this.specialty;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setSpecialty(List<CodeableConcept> theSpecialty) {
    this.specialty = theSpecialty;
    return this;
  }

  public boolean hasSpecialty() {
    if (this.specialty == null)
      return false;
    for (CodeableConcept item : this.specialty)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addSpecialty() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.specialty == null)
      this.specialty = new ArrayList<CodeableConcept>();
    this.specialty.add(t);
    return t;
  }

  public Appointment addSpecialty(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.specialty == null)
      this.specialty = new ArrayList<CodeableConcept>();
    this.specialty.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #specialty}, creating
   *         it if it does not already exist
   */
  public CodeableConcept getSpecialtyFirstRep() {
    if (getSpecialty().isEmpty()) {
      addSpecialty();
    }
    return getSpecialty().get(0);
  }

  /**
   * @return {@link #appointmentType} (The style of appointment or patient that
   *         has been booked in the slot (not service type).)
   */
  public CodeableConcept getAppointmentType() {
    if (this.appointmentType == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.appointmentType");
      else if (Configuration.doAutoCreate())
        this.appointmentType = new CodeableConcept(); // cc
    return this.appointmentType;
  }

  public boolean hasAppointmentType() {
    return this.appointmentType != null && !this.appointmentType.isEmpty();
  }

  /**
   * @param value {@link #appointmentType} (The style of appointment or patient
   *              that has been booked in the slot (not service type).)
   */
  public Appointment setAppointmentType(CodeableConcept value) {
    this.appointmentType = value;
    return this;
  }

  /**
   * @return {@link #reasonCode} (The coded reason that this appointment is being
   *         scheduled. This is more clinical than administrative.)
   */
  public List<CodeableConcept> getReasonCode() {
    if (this.reasonCode == null)
      this.reasonCode = new ArrayList<CodeableConcept>();
    return this.reasonCode;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setReasonCode(List<CodeableConcept> theReasonCode) {
    this.reasonCode = theReasonCode;
    return this;
  }

  public boolean hasReasonCode() {
    if (this.reasonCode == null)
      return false;
    for (CodeableConcept item : this.reasonCode)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addReasonCode() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.reasonCode == null)
      this.reasonCode = new ArrayList<CodeableConcept>();
    this.reasonCode.add(t);
    return t;
  }

  public Appointment addReasonCode(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.reasonCode == null)
      this.reasonCode = new ArrayList<CodeableConcept>();
    this.reasonCode.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #reasonCode}, creating
   *         it if it does not already exist
   */
  public CodeableConcept getReasonCodeFirstRep() {
    if (getReasonCode().isEmpty()) {
      addReasonCode();
    }
    return getReasonCode().get(0);
  }

  /**
   * @return {@link #reasonReference} (Reason the appointment has been scheduled
   *         to take place, as specified using information from another resource.
   *         When the patient arrives and the encounter begins it may be used as
   *         the admission diagnosis. The indication will typically be a Condition
   *         (with other resources referenced in the evidence.detail), or a
   *         Procedure.)
   */
  public List<Reference> getReasonReference() {
    if (this.reasonReference == null)
      this.reasonReference = new ArrayList<Reference>();
    return this.reasonReference;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setReasonReference(List<Reference> theReasonReference) {
    this.reasonReference = theReasonReference;
    return this;
  }

  public boolean hasReasonReference() {
    if (this.reasonReference == null)
      return false;
    for (Reference item : this.reasonReference)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addReasonReference() { // 3
    Reference t = new Reference();
    if (this.reasonReference == null)
      this.reasonReference = new ArrayList<Reference>();
    this.reasonReference.add(t);
    return t;
  }

  public Appointment addReasonReference(Reference t) { // 3
    if (t == null)
      return this;
    if (this.reasonReference == null)
      this.reasonReference = new ArrayList<Reference>();
    this.reasonReference.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #reasonReference},
   *         creating it if it does not already exist
   */
  public Reference getReasonReferenceFirstRep() {
    if (getReasonReference().isEmpty()) {
      addReasonReference();
    }
    return getReasonReference().get(0);
  }

  /**
   * @return {@link #priority} (The priority of the appointment. Can be used to
   *         make informed decisions if needing to re-prioritize appointments.
   *         (The iCal Standard specifies 0 as undefined, 1 as highest, 9 as
   *         lowest priority).). This is the underlying object with id, value and
   *         extensions. The accessor "getPriority" gives direct access to the
   *         value
   */
  public UnsignedIntType getPriorityElement() {
    if (this.priority == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.priority");
      else if (Configuration.doAutoCreate())
        this.priority = new UnsignedIntType(); // bb
    return this.priority;
  }

  public boolean hasPriorityElement() {
    return this.priority != null && !this.priority.isEmpty();
  }

  public boolean hasPriority() {
    return this.priority != null && !this.priority.isEmpty();
  }

  /**
   * @param value {@link #priority} (The priority of the appointment. Can be used
   *              to make informed decisions if needing to re-prioritize
   *              appointments. (The iCal Standard specifies 0 as undefined, 1 as
   *              highest, 9 as lowest priority).). This is the underlying object
   *              with id, value and extensions. The accessor "getPriority" gives
   *              direct access to the value
   */
  public Appointment setPriorityElement(UnsignedIntType value) {
    this.priority = value;
    return this;
  }

  /**
   * @return The priority of the appointment. Can be used to make informed
   *         decisions if needing to re-prioritize appointments. (The iCal
   *         Standard specifies 0 as undefined, 1 as highest, 9 as lowest
   *         priority).
   */
  public int getPriority() {
    return this.priority == null || this.priority.isEmpty() ? 0 : this.priority.getValue();
  }

  /**
   * @param value The priority of the appointment. Can be used to make informed
   *              decisions if needing to re-prioritize appointments. (The iCal
   *              Standard specifies 0 as undefined, 1 as highest, 9 as lowest
   *              priority).
   */
  public Appointment setPriority(int value) {
    if (this.priority == null)
      this.priority = new UnsignedIntType();
    this.priority.setValue(value);
    return this;
  }

  /**
   * @return {@link #description} (The brief description of the appointment as
   *         would be shown on a subject line in a meeting request, or appointment
   *         list. Detailed or expanded information should be put in the comment
   *         field.). This is the underlying object with id, value and extensions.
   *         The accessor "getDescription" gives direct access to the value
   */
  public StringType getDescriptionElement() {
    if (this.description == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.description");
      else if (Configuration.doAutoCreate())
        this.description = new StringType(); // bb
    return this.description;
  }

  public boolean hasDescriptionElement() {
    return this.description != null && !this.description.isEmpty();
  }

  public boolean hasDescription() {
    return this.description != null && !this.description.isEmpty();
  }

  /**
   * @param value {@link #description} (The brief description of the appointment
   *              as would be shown on a subject line in a meeting request, or
   *              appointment list. Detailed or expanded information should be put
   *              in the comment field.). This is the underlying object with id,
   *              value and extensions. The accessor "getDescription" gives direct
   *              access to the value
   */
  public Appointment setDescriptionElement(StringType value) {
    this.description = value;
    return this;
  }

  /**
   * @return The brief description of the appointment as would be shown on a
   *         subject line in a meeting request, or appointment list. Detailed or
   *         expanded information should be put in the comment field.
   */
  public String getDescription() {
    return this.description == null ? null : this.description.getValue();
  }

  /**
   * @param value The brief description of the appointment as would be shown on a
   *              subject line in a meeting request, or appointment list. Detailed
   *              or expanded information should be put in the comment field.
   */
  public Appointment setDescription(String value) {
    if (Utilities.noString(value))
      this.description = null;
    else {
      if (this.description == null)
        this.description = new StringType();
      this.description.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #supportingInformation} (Additional information to support the
   *         appointment provided when making the appointment.)
   */
  public List<Reference> getSupportingInformation() {
    if (this.supportingInformation == null)
      this.supportingInformation = new ArrayList<Reference>();
    return this.supportingInformation;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setSupportingInformation(List<Reference> theSupportingInformation) {
    this.supportingInformation = theSupportingInformation;
    return this;
  }

  public boolean hasSupportingInformation() {
    if (this.supportingInformation == null)
      return false;
    for (Reference item : this.supportingInformation)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addSupportingInformation() { // 3
    Reference t = new Reference();
    if (this.supportingInformation == null)
      this.supportingInformation = new ArrayList<Reference>();
    this.supportingInformation.add(t);
    return t;
  }

  public Appointment addSupportingInformation(Reference t) { // 3
    if (t == null)
      return this;
    if (this.supportingInformation == null)
      this.supportingInformation = new ArrayList<Reference>();
    this.supportingInformation.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field
   *         {@link #supportingInformation}, creating it if it does not already
   *         exist
   */
  public Reference getSupportingInformationFirstRep() {
    if (getSupportingInformation().isEmpty()) {
      addSupportingInformation();
    }
    return getSupportingInformation().get(0);
  }

  /**
   * @return {@link #start} (Date/Time that the appointment is to take place.).
   *         This is the underlying object with id, value and extensions. The
   *         accessor "getStart" gives direct access to the value
   */
  public InstantType getStartElement() {
    if (this.start == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.start");
      else if (Configuration.doAutoCreate())
        this.start = new InstantType(); // bb
    return this.start;
  }

  public boolean hasStartElement() {
    return this.start != null && !this.start.isEmpty();
  }

  public boolean hasStart() {
    return this.start != null && !this.start.isEmpty();
  }

  /**
   * @param value {@link #start} (Date/Time that the appointment is to take
   *              place.). This is the underlying object with id, value and
   *              extensions. The accessor "getStart" gives direct access to the
   *              value
   */
  public Appointment setStartElement(InstantType value) {
    this.start = value;
    return this;
  }

  /**
   * @return Date/Time that the appointment is to take place.
   */
  public Date getStart() {
    return this.start == null ? null : this.start.getValue();
  }

  /**
   * @param value Date/Time that the appointment is to take place.
   */
  public Appointment setStart(Date value) {
    if (value == null)
      this.start = null;
    else {
      if (this.start == null)
        this.start = new InstantType();
      this.start.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #end} (Date/Time that the appointment is to conclude.). This
   *         is the underlying object with id, value and extensions. The accessor
   *         "getEnd" gives direct access to the value
   */
  public InstantType getEndElement() {
    if (this.end == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.end");
      else if (Configuration.doAutoCreate())
        this.end = new InstantType(); // bb
    return this.end;
  }

  public boolean hasEndElement() {
    return this.end != null && !this.end.isEmpty();
  }

  public boolean hasEnd() {
    return this.end != null && !this.end.isEmpty();
  }

  /**
   * @param value {@link #end} (Date/Time that the appointment is to conclude.).
   *              This is the underlying object with id, value and extensions. The
   *              accessor "getEnd" gives direct access to the value
   */
  public Appointment setEndElement(InstantType value) {
    this.end = value;
    return this;
  }

  /**
   * @return Date/Time that the appointment is to conclude.
   */
  public Date getEnd() {
    return this.end == null ? null : this.end.getValue();
  }

  /**
   * @param value Date/Time that the appointment is to conclude.
   */
  public Appointment setEnd(Date value) {
    if (value == null)
      this.end = null;
    else {
      if (this.end == null)
        this.end = new InstantType();
      this.end.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #minutesDuration} (Number of minutes that the appointment is
   *         to take. This can be less than the duration between the start and end
   *         times. For example, where the actual time of appointment is only an
   *         estimate or if a 30 minute appointment is being requested, but any
   *         time would work. Also, if there is, for example, a planned 15 minute
   *         break in the middle of a long appointment, the duration may be 15
   *         minutes less than the difference between the start and end.). This is
   *         the underlying object with id, value and extensions. The accessor
   *         "getMinutesDuration" gives direct access to the value
   */
  public PositiveIntType getMinutesDurationElement() {
    if (this.minutesDuration == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.minutesDuration");
      else if (Configuration.doAutoCreate())
        this.minutesDuration = new PositiveIntType(); // bb
    return this.minutesDuration;
  }

  public boolean hasMinutesDurationElement() {
    return this.minutesDuration != null && !this.minutesDuration.isEmpty();
  }

  public boolean hasMinutesDuration() {
    return this.minutesDuration != null && !this.minutesDuration.isEmpty();
  }

  /**
   * @param value {@link #minutesDuration} (Number of minutes that the appointment
   *              is to take. This can be less than the duration between the start
   *              and end times. For example, where the actual time of appointment
   *              is only an estimate or if a 30 minute appointment is being
   *              requested, but any time would work. Also, if there is, for
   *              example, a planned 15 minute break in the middle of a long
   *              appointment, the duration may be 15 minutes less than the
   *              difference between the start and end.). This is the underlying
   *              object with id, value and extensions. The accessor
   *              "getMinutesDuration" gives direct access to the value
   */
  public Appointment setMinutesDurationElement(PositiveIntType value) {
    this.minutesDuration = value;
    return this;
  }

  /**
   * @return Number of minutes that the appointment is to take. This can be less
   *         than the duration between the start and end times. For example, where
   *         the actual time of appointment is only an estimate or if a 30 minute
   *         appointment is being requested, but any time would work. Also, if
   *         there is, for example, a planned 15 minute break in the middle of a
   *         long appointment, the duration may be 15 minutes less than the
   *         difference between the start and end.
   */
  public int getMinutesDuration() {
    return this.minutesDuration == null || this.minutesDuration.isEmpty() ? 0 : this.minutesDuration.getValue();
  }

  /**
   * @param value Number of minutes that the appointment is to take. This can be
   *              less than the duration between the start and end times. For
   *              example, where the actual time of appointment is only an
   *              estimate or if a 30 minute appointment is being requested, but
   *              any time would work. Also, if there is, for example, a planned
   *              15 minute break in the middle of a long appointment, the
   *              duration may be 15 minutes less than the difference between the
   *              start and end.
   */
  public Appointment setMinutesDuration(int value) {
    if (this.minutesDuration == null)
      this.minutesDuration = new PositiveIntType();
    this.minutesDuration.setValue(value);
    return this;
  }

  /**
   * @return {@link #slot} (The slots from the participants' schedules that will
   *         be filled by the appointment.)
   */
  public List<Reference> getSlot() {
    if (this.slot == null)
      this.slot = new ArrayList<Reference>();
    return this.slot;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setSlot(List<Reference> theSlot) {
    this.slot = theSlot;
    return this;
  }

  public boolean hasSlot() {
    if (this.slot == null)
      return false;
    for (Reference item : this.slot)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addSlot() { // 3
    Reference t = new Reference();
    if (this.slot == null)
      this.slot = new ArrayList<Reference>();
    this.slot.add(t);
    return t;
  }

  public Appointment addSlot(Reference t) { // 3
    if (t == null)
      return this;
    if (this.slot == null)
      this.slot = new ArrayList<Reference>();
    this.slot.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #slot}, creating it if
   *         it does not already exist
   */
  public Reference getSlotFirstRep() {
    if (getSlot().isEmpty()) {
      addSlot();
    }
    return getSlot().get(0);
  }

  /**
   * @return {@link #created} (The date that this appointment was initially
   *         created. This could be different to the meta.lastModified value on
   *         the initial entry, as this could have been before the resource was
   *         created on the FHIR server, and should remain unchanged over the
   *         lifespan of the appointment.). This is the underlying object with id,
   *         value and extensions. The accessor "getCreated" gives direct access
   *         to the value
   */
  public DateTimeType getCreatedElement() {
    if (this.created == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.created");
      else if (Configuration.doAutoCreate())
        this.created = new DateTimeType(); // bb
    return this.created;
  }

  public boolean hasCreatedElement() {
    return this.created != null && !this.created.isEmpty();
  }

  public boolean hasCreated() {
    return this.created != null && !this.created.isEmpty();
  }

  /**
   * @param value {@link #created} (The date that this appointment was initially
   *              created. This could be different to the meta.lastModified value
   *              on the initial entry, as this could have been before the
   *              resource was created on the FHIR server, and should remain
   *              unchanged over the lifespan of the appointment.). This is the
   *              underlying object with id, value and extensions. The accessor
   *              "getCreated" gives direct access to the value
   */
  public Appointment setCreatedElement(DateTimeType value) {
    this.created = value;
    return this;
  }

  /**
   * @return The date that this appointment was initially created. This could be
   *         different to the meta.lastModified value on the initial entry, as
   *         this could have been before the resource was created on the FHIR
   *         server, and should remain unchanged over the lifespan of the
   *         appointment.
   */
  public Date getCreated() {
    return this.created == null ? null : this.created.getValue();
  }

  /**
   * @param value The date that this appointment was initially created. This could
   *              be different to the meta.lastModified value on the initial
   *              entry, as this could have been before the resource was created
   *              on the FHIR server, and should remain unchanged over the
   *              lifespan of the appointment.
   */
  public Appointment setCreated(Date value) {
    if (value == null)
      this.created = null;
    else {
      if (this.created == null)
        this.created = new DateTimeType();
      this.created.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #comment} (Additional comments about the appointment.). This
   *         is the underlying object with id, value and extensions. The accessor
   *         "getComment" gives direct access to the value
   */
  public StringType getCommentElement() {
    if (this.comment == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.comment");
      else if (Configuration.doAutoCreate())
        this.comment = new StringType(); // bb
    return this.comment;
  }

  public boolean hasCommentElement() {
    return this.comment != null && !this.comment.isEmpty();
  }

  public boolean hasComment() {
    return this.comment != null && !this.comment.isEmpty();
  }

  /**
   * @param value {@link #comment} (Additional comments about the appointment.).
   *              This is the underlying object with id, value and extensions. The
   *              accessor "getComment" gives direct access to the value
   */
  public Appointment setCommentElement(StringType value) {
    this.comment = value;
    return this;
  }

  /**
   * @return Additional comments about the appointment.
   */
  public String getComment() {
    return this.comment == null ? null : this.comment.getValue();
  }

  /**
   * @param value Additional comments about the appointment.
   */
  public Appointment setComment(String value) {
    if (Utilities.noString(value))
      this.comment = null;
    else {
      if (this.comment == null)
        this.comment = new StringType();
      this.comment.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #patientInstruction} (While Appointment.comment contains
   *         information for internal use, Appointment.patientInstructions is used
   *         to capture patient facing information about the Appointment (e.g.
   *         please bring your referral or fast from 8pm night before).). This is
   *         the underlying object with id, value and extensions. The accessor
   *         "getPatientInstruction" gives direct access to the value
   */
  public StringType getPatientInstructionElement() {
    if (this.patientInstruction == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Appointment.patientInstruction");
      else if (Configuration.doAutoCreate())
        this.patientInstruction = new StringType(); // bb
    return this.patientInstruction;
  }

  public boolean hasPatientInstructionElement() {
    return this.patientInstruction != null && !this.patientInstruction.isEmpty();
  }

  public boolean hasPatientInstruction() {
    return this.patientInstruction != null && !this.patientInstruction.isEmpty();
  }

  /**
   * @param value {@link #patientInstruction} (While Appointment.comment contains
   *              information for internal use, Appointment.patientInstructions is
   *              used to capture patient facing information about the Appointment
   *              (e.g. please bring your referral or fast from 8pm night
   *              before).). This is the underlying object with id, value and
   *              extensions. The accessor "getPatientInstruction" gives direct
   *              access to the value
   */
  public Appointment setPatientInstructionElement(StringType value) {
    this.patientInstruction = value;
    return this;
  }

  /**
   * @return While Appointment.comment contains information for internal use,
   *         Appointment.patientInstructions is used to capture patient facing
   *         information about the Appointment (e.g. please bring your referral or
   *         fast from 8pm night before).
   */
  public String getPatientInstruction() {
    return this.patientInstruction == null ? null : this.patientInstruction.getValue();
  }

  /**
   * @param value While Appointment.comment contains information for internal use,
   *              Appointment.patientInstructions is used to capture patient
   *              facing information about the Appointment (e.g. please bring your
   *              referral or fast from 8pm night before).
   */
  public Appointment setPatientInstruction(String value) {
    if (Utilities.noString(value))
      this.patientInstruction = null;
    else {
      if (this.patientInstruction == null)
        this.patientInstruction = new StringType();
      this.patientInstruction.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #basedOn} (The service request this appointment is allocated
   *         to assess (e.g. incoming referral or procedure request).)
   */
  public List<Reference> getBasedOn() {
    if (this.basedOn == null)
      this.basedOn = new ArrayList<Reference>();
    return this.basedOn;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setBasedOn(List<Reference> theBasedOn) {
    this.basedOn = theBasedOn;
    return this;
  }

  public boolean hasBasedOn() {
    if (this.basedOn == null)
      return false;
    for (Reference item : this.basedOn)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addBasedOn() { // 3
    Reference t = new Reference();
    if (this.basedOn == null)
      this.basedOn = new ArrayList<Reference>();
    this.basedOn.add(t);
    return t;
  }

  public Appointment addBasedOn(Reference t) { // 3
    if (t == null)
      return this;
    if (this.basedOn == null)
      this.basedOn = new ArrayList<Reference>();
    this.basedOn.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #basedOn}, creating it
   *         if it does not already exist
   */
  public Reference getBasedOnFirstRep() {
    if (getBasedOn().isEmpty()) {
      addBasedOn();
    }
    return getBasedOn().get(0);
  }

  /**
   * @return {@link #participant} (List of participants involved in the
   *         appointment.)
   */
  public List<AppointmentParticipantComponent> getParticipant() {
    if (this.participant == null)
      this.participant = new ArrayList<AppointmentParticipantComponent>();
    return this.participant;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setParticipant(List<AppointmentParticipantComponent> theParticipant) {
    this.participant = theParticipant;
    return this;
  }

  public boolean hasParticipant() {
    if (this.participant == null)
      return false;
    for (AppointmentParticipantComponent item : this.participant)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public AppointmentParticipantComponent addParticipant() { // 3
    AppointmentParticipantComponent t = new AppointmentParticipantComponent();
    if (this.participant == null)
      this.participant = new ArrayList<AppointmentParticipantComponent>();
    this.participant.add(t);
    return t;
  }

  public Appointment addParticipant(AppointmentParticipantComponent t) { // 3
    if (t == null)
      return this;
    if (this.participant == null)
      this.participant = new ArrayList<AppointmentParticipantComponent>();
    this.participant.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #participant},
   *         creating it if it does not already exist
   */
  public AppointmentParticipantComponent getParticipantFirstRep() {
    if (getParticipant().isEmpty()) {
      addParticipant();
    }
    return getParticipant().get(0);
  }

  /**
   * @return {@link #requestedPeriod} (A set of date ranges (potentially including
   *         times) that the appointment is preferred to be scheduled within.
   * 
   *         The duration (usually in minutes) could also be provided to indicate
   *         the length of the appointment to fill and populate the start/end
   *         times for the actual allocated time. However, in other situations the
   *         duration may be calculated by the scheduling system.)
   */
  public List<Period> getRequestedPeriod() {
    if (this.requestedPeriod == null)
      this.requestedPeriod = new ArrayList<Period>();
    return this.requestedPeriod;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Appointment setRequestedPeriod(List<Period> theRequestedPeriod) {
    this.requestedPeriod = theRequestedPeriod;
    return this;
  }

  public boolean hasRequestedPeriod() {
    if (this.requestedPeriod == null)
      return false;
    for (Period item : this.requestedPeriod)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Period addRequestedPeriod() { // 3
    Period t = new Period();
    if (this.requestedPeriod == null)
      this.requestedPeriod = new ArrayList<Period>();
    this.requestedPeriod.add(t);
    return t;
  }

  public Appointment addRequestedPeriod(Period t) { // 3
    if (t == null)
      return this;
    if (this.requestedPeriod == null)
      this.requestedPeriod = new ArrayList<Period>();
    this.requestedPeriod.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #requestedPeriod},
   *         creating it if it does not already exist
   */
  public Period getRequestedPeriodFirstRep() {
    if (getRequestedPeriod().isEmpty()) {
      addRequestedPeriod();
    }
    return getRequestedPeriod().get(0);
  }

  protected void listChildren(List<Property> children) {
    super.listChildren(children);
    children.add(new Property("identifier", "Identifier",
        "This records identifiers associated with this appointment concern that are defined by business processes and/or used to refer to it when a direct URL reference to the resource itself is not appropriate (e.g. in CDA documents, or in written / printed documentation).",
        0, java.lang.Integer.MAX_VALUE, identifier));
    children.add(new Property("status", "code",
        "The overall status of the Appointment. Each of the participants has their own participation status which indicates their involvement in the process, however this status indicates the shared status.",
        0, 1, status));
    children.add(new Property("cancelationReason", "CodeableConcept",
        "The coded reason for the appointment being cancelled. This is often used in reporting/billing/futher processing to determine if further actions are required, or specific fees apply.",
        0, 1, cancelationReason));
    children.add(new Property("serviceCategory", "CodeableConcept",
        "A broad categorization of the service that is to be performed during this appointment.", 0,
        java.lang.Integer.MAX_VALUE, serviceCategory));
    children.add(new Property("serviceType", "CodeableConcept",
        "The specific service that is to be performed during this appointment.", 0, java.lang.Integer.MAX_VALUE,
        serviceType));
    children.add(new Property("specialty", "CodeableConcept",
        "The specialty of a practitioner that would be required to perform the service requested in this appointment.",
        0, java.lang.Integer.MAX_VALUE, specialty));
    children.add(new Property("appointmentType", "CodeableConcept",
        "The style of appointment or patient that has been booked in the slot (not service type).", 0, 1,
        appointmentType));
    children.add(new Property("reasonCode", "CodeableConcept",
        "The coded reason that this appointment is being scheduled. This is more clinical than administrative.", 0,
        java.lang.Integer.MAX_VALUE, reasonCode));
    children.add(new Property("reasonReference",
        "Reference(Condition|Procedure|Observation|ImmunizationRecommendation)",
        "Reason the appointment has been scheduled to take place, as specified using information from another resource. When the patient arrives and the encounter begins it may be used as the admission diagnosis. The indication will typically be a Condition (with other resources referenced in the evidence.detail), or a Procedure.",
        0, java.lang.Integer.MAX_VALUE, reasonReference));
    children.add(new Property("priority", "unsignedInt",
        "The priority of the appointment. Can be used to make informed decisions if needing to re-prioritize appointments. (The iCal Standard specifies 0 as undefined, 1 as highest, 9 as lowest priority).",
        0, 1, priority));
    children.add(new Property("description", "string",
        "The brief description of the appointment as would be shown on a subject line in a meeting request, or appointment list. Detailed or expanded information should be put in the comment field.",
        0, 1, description));
    children.add(new Property("supportingInformation", "Reference(Any)",
        "Additional information to support the appointment provided when making the appointment.", 0,
        java.lang.Integer.MAX_VALUE, supportingInformation));
    children.add(new Property("start", "instant", "Date/Time that the appointment is to take place.", 0, 1, start));
    children.add(new Property("end", "instant", "Date/Time that the appointment is to conclude.", 0, 1, end));
    children.add(new Property("minutesDuration", "positiveInt",
        "Number of minutes that the appointment is to take. This can be less than the duration between the start and end times.  For example, where the actual time of appointment is only an estimate or if a 30 minute appointment is being requested, but any time would work.  Also, if there is, for example, a planned 15 minute break in the middle of a long appointment, the duration may be 15 minutes less than the difference between the start and end.",
        0, 1, minutesDuration));
    children.add(new Property("slot", "Reference(Slot)",
        "The slots from the participants' schedules that will be filled by the appointment.", 0,
        java.lang.Integer.MAX_VALUE, slot));
    children.add(new Property("created", "dateTime",
        "The date that this appointment was initially created. This could be different to the meta.lastModified value on the initial entry, as this could have been before the resource was created on the FHIR server, and should remain unchanged over the lifespan of the appointment.",
        0, 1, created));
    children.add(new Property("comment", "string", "Additional comments about the appointment.", 0, 1, comment));
    children.add(new Property("patientInstruction", "string",
        "While Appointment.comment contains information for internal use, Appointment.patientInstructions is used to capture patient facing information about the Appointment (e.g. please bring your referral or fast from 8pm night before).",
        0, 1, patientInstruction));
    children.add(new Property("basedOn", "Reference(ServiceRequest)",
        "The service request this appointment is allocated to assess (e.g. incoming referral or procedure request).", 0,
        java.lang.Integer.MAX_VALUE, basedOn));
    children.add(new Property("participant", "", "List of participants involved in the appointment.", 0,
        java.lang.Integer.MAX_VALUE, participant));
    children.add(new Property("requestedPeriod", "Period",
        "A set of date ranges (potentially including times) that the appointment is preferred to be scheduled within.\n\nThe duration (usually in minutes) could also be provided to indicate the length of the appointment to fill and populate the start/end times for the actual allocated time. However, in other situations the duration may be calculated by the scheduling system.",
        0, java.lang.Integer.MAX_VALUE, requestedPeriod));
  }

  @Override
  public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
    switch (_hash) {
    case -1618432855:
      /* identifier */ return new Property("identifier", "Identifier",
          "This records identifiers associated with this appointment concern that are defined by business processes and/or used to refer to it when a direct URL reference to the resource itself is not appropriate (e.g. in CDA documents, or in written / printed documentation).",
          0, java.lang.Integer.MAX_VALUE, identifier);
    case -892481550:
      /* status */ return new Property("status", "code",
          "The overall status of the Appointment. Each of the participants has their own participation status which indicates their involvement in the process, however this status indicates the shared status.",
          0, 1, status);
    case 987811551:
      /* cancelationReason */ return new Property("cancelationReason", "CodeableConcept",
          "The coded reason for the appointment being cancelled. This is often used in reporting/billing/futher processing to determine if further actions are required, or specific fees apply.",
          0, 1, cancelationReason);
    case 1281188563:
      /* serviceCategory */ return new Property("serviceCategory", "CodeableConcept",
          "A broad categorization of the service that is to be performed during this appointment.", 0,
          java.lang.Integer.MAX_VALUE, serviceCategory);
    case -1928370289:
      /* serviceType */ return new Property("serviceType", "CodeableConcept",
          "The specific service that is to be performed during this appointment.", 0, java.lang.Integer.MAX_VALUE,
          serviceType);
    case -1694759682:
      /* specialty */ return new Property("specialty", "CodeableConcept",
          "The specialty of a practitioner that would be required to perform the service requested in this appointment.",
          0, java.lang.Integer.MAX_VALUE, specialty);
    case -1596426375:
      /* appointmentType */ return new Property("appointmentType", "CodeableConcept",
          "The style of appointment or patient that has been booked in the slot (not service type).", 0, 1,
          appointmentType);
    case 722137681:
      /* reasonCode */ return new Property("reasonCode", "CodeableConcept",
          "The coded reason that this appointment is being scheduled. This is more clinical than administrative.", 0,
          java.lang.Integer.MAX_VALUE, reasonCode);
    case -1146218137:
      /* reasonReference */ return new Property("reasonReference",
          "Reference(Condition|Procedure|Observation|ImmunizationRecommendation)",
          "Reason the appointment has been scheduled to take place, as specified using information from another resource. When the patient arrives and the encounter begins it may be used as the admission diagnosis. The indication will typically be a Condition (with other resources referenced in the evidence.detail), or a Procedure.",
          0, java.lang.Integer.MAX_VALUE, reasonReference);
    case -1165461084:
      /* priority */ return new Property("priority", "unsignedInt",
          "The priority of the appointment. Can be used to make informed decisions if needing to re-prioritize appointments. (The iCal Standard specifies 0 as undefined, 1 as highest, 9 as lowest priority).",
          0, 1, priority);
    case -1724546052:
      /* description */ return new Property("description", "string",
          "The brief description of the appointment as would be shown on a subject line in a meeting request, or appointment list. Detailed or expanded information should be put in the comment field.",
          0, 1, description);
    case -1248768647:
      /* supportingInformation */ return new Property("supportingInformation", "Reference(Any)",
          "Additional information to support the appointment provided when making the appointment.", 0,
          java.lang.Integer.MAX_VALUE, supportingInformation);
    case 109757538:
      /* start */ return new Property("start", "instant", "Date/Time that the appointment is to take place.", 0, 1,
          start);
    case 100571:
      /* end */ return new Property("end", "instant", "Date/Time that the appointment is to conclude.", 0, 1, end);
    case -413630573:
      /* minutesDuration */ return new Property("minutesDuration", "positiveInt",
          "Number of minutes that the appointment is to take. This can be less than the duration between the start and end times.  For example, where the actual time of appointment is only an estimate or if a 30 minute appointment is being requested, but any time would work.  Also, if there is, for example, a planned 15 minute break in the middle of a long appointment, the duration may be 15 minutes less than the difference between the start and end.",
          0, 1, minutesDuration);
    case 3533310:
      /* slot */ return new Property("slot", "Reference(Slot)",
          "The slots from the participants' schedules that will be filled by the appointment.", 0,
          java.lang.Integer.MAX_VALUE, slot);
    case 1028554472:
      /* created */ return new Property("created", "dateTime",
          "The date that this appointment was initially created. This could be different to the meta.lastModified value on the initial entry, as this could have been before the resource was created on the FHIR server, and should remain unchanged over the lifespan of the appointment.",
          0, 1, created);
    case 950398559:
      /* comment */ return new Property("comment", "string", "Additional comments about the appointment.", 0, 1,
          comment);
    case 737543241:
      /* patientInstruction */ return new Property("patientInstruction", "string",
          "While Appointment.comment contains information for internal use, Appointment.patientInstructions is used to capture patient facing information about the Appointment (e.g. please bring your referral or fast from 8pm night before).",
          0, 1, patientInstruction);
    case -332612366:
      /* basedOn */ return new Property("basedOn", "Reference(ServiceRequest)",
          "The service request this appointment is allocated to assess (e.g. incoming referral or procedure request).",
          0, java.lang.Integer.MAX_VALUE, basedOn);
    case 767422259:
      /* participant */ return new Property("participant", "", "List of participants involved in the appointment.", 0,
          java.lang.Integer.MAX_VALUE, participant);
    case -897241393:
      /* requestedPeriod */ return new Property("requestedPeriod", "Period",
          "A set of date ranges (potentially including times) that the appointment is preferred to be scheduled within.\n\nThe duration (usually in minutes) could also be provided to indicate the length of the appointment to fill and populate the start/end times for the actual allocated time. However, in other situations the duration may be calculated by the scheduling system.",
          0, java.lang.Integer.MAX_VALUE, requestedPeriod);
    default:
      return super.getNamedProperty(_hash, _name, _checkValid);
    }

  }

  @Override
  public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
    switch (hash) {
    case -1618432855:
      /* identifier */ return this.identifier == null ? new Base[0]
          : this.identifier.toArray(new Base[this.identifier.size()]); // Identifier
    case -892481550:
      /* status */ return this.status == null ? new Base[0] : new Base[] { this.status }; // Enumeration<AppointmentStatus>
    case 987811551:
      /* cancelationReason */ return this.cancelationReason == null ? new Base[0]
          : new Base[] { this.cancelationReason }; // CodeableConcept
    case 1281188563:
      /* serviceCategory */ return this.serviceCategory == null ? new Base[0]
          : this.serviceCategory.toArray(new Base[this.serviceCategory.size()]); // CodeableConcept
    case -1928370289:
      /* serviceType */ return this.serviceType == null ? new Base[0]
          : this.serviceType.toArray(new Base[this.serviceType.size()]); // CodeableConcept
    case -1694759682:
      /* specialty */ return this.specialty == null ? new Base[0]
          : this.specialty.toArray(new Base[this.specialty.size()]); // CodeableConcept
    case -1596426375:
      /* appointmentType */ return this.appointmentType == null ? new Base[0] : new Base[] { this.appointmentType }; // CodeableConcept
    case 722137681:
      /* reasonCode */ return this.reasonCode == null ? new Base[0]
          : this.reasonCode.toArray(new Base[this.reasonCode.size()]); // CodeableConcept
    case -1146218137:
      /* reasonReference */ return this.reasonReference == null ? new Base[0]
          : this.reasonReference.toArray(new Base[this.reasonReference.size()]); // Reference
    case -1165461084:
      /* priority */ return this.priority == null ? new Base[0] : new Base[] { this.priority }; // UnsignedIntType
    case -1724546052:
      /* description */ return this.description == null ? new Base[0] : new Base[] { this.description }; // StringType
    case -1248768647:
      /* supportingInformation */ return this.supportingInformation == null ? new Base[0]
          : this.supportingInformation.toArray(new Base[this.supportingInformation.size()]); // Reference
    case 109757538:
      /* start */ return this.start == null ? new Base[0] : new Base[] { this.start }; // InstantType
    case 100571:
      /* end */ return this.end == null ? new Base[0] : new Base[] { this.end }; // InstantType
    case -413630573:
      /* minutesDuration */ return this.minutesDuration == null ? new Base[0] : new Base[] { this.minutesDuration }; // PositiveIntType
    case 3533310:
      /* slot */ return this.slot == null ? new Base[0] : this.slot.toArray(new Base[this.slot.size()]); // Reference
    case 1028554472:
      /* created */ return this.created == null ? new Base[0] : new Base[] { this.created }; // DateTimeType
    case 950398559:
      /* comment */ return this.comment == null ? new Base[0] : new Base[] { this.comment }; // StringType
    case 737543241:
      /* patientInstruction */ return this.patientInstruction == null ? new Base[0]
          : new Base[] { this.patientInstruction }; // StringType
    case -332612366:
      /* basedOn */ return this.basedOn == null ? new Base[0] : this.basedOn.toArray(new Base[this.basedOn.size()]); // Reference
    case 767422259:
      /* participant */ return this.participant == null ? new Base[0]
          : this.participant.toArray(new Base[this.participant.size()]); // AppointmentParticipantComponent
    case -897241393:
      /* requestedPeriod */ return this.requestedPeriod == null ? new Base[0]
          : this.requestedPeriod.toArray(new Base[this.requestedPeriod.size()]); // Period
    default:
      return super.getProperty(hash, name, checkValid);
    }

  }

  @Override
  public Base setProperty(int hash, String name, Base value) throws FHIRException {
    switch (hash) {
    case -1618432855: // identifier
      this.getIdentifier().add(castToIdentifier(value)); // Identifier
      return value;
    case -892481550: // status
      value = new AppointmentStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<AppointmentStatus>
      return value;
    case 987811551: // cancelationReason
      this.cancelationReason = castToCodeableConcept(value); // CodeableConcept
      return value;
    case 1281188563: // serviceCategory
      this.getServiceCategory().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case -1928370289: // serviceType
      this.getServiceType().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case -1694759682: // specialty
      this.getSpecialty().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case -1596426375: // appointmentType
      this.appointmentType = castToCodeableConcept(value); // CodeableConcept
      return value;
    case 722137681: // reasonCode
      this.getReasonCode().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case -1146218137: // reasonReference
      this.getReasonReference().add(castToReference(value)); // Reference
      return value;
    case -1165461084: // priority
      this.priority = castToUnsignedInt(value); // UnsignedIntType
      return value;
    case -1724546052: // description
      this.description = castToString(value); // StringType
      return value;
    case -1248768647: // supportingInformation
      this.getSupportingInformation().add(castToReference(value)); // Reference
      return value;
    case 109757538: // start
      this.start = castToInstant(value); // InstantType
      return value;
    case 100571: // end
      this.end = castToInstant(value); // InstantType
      return value;
    case -413630573: // minutesDuration
      this.minutesDuration = castToPositiveInt(value); // PositiveIntType
      return value;
    case 3533310: // slot
      this.getSlot().add(castToReference(value)); // Reference
      return value;
    case 1028554472: // created
      this.created = castToDateTime(value); // DateTimeType
      return value;
    case 950398559: // comment
      this.comment = castToString(value); // StringType
      return value;
    case 737543241: // patientInstruction
      this.patientInstruction = castToString(value); // StringType
      return value;
    case -332612366: // basedOn
      this.getBasedOn().add(castToReference(value)); // Reference
      return value;
    case 767422259: // participant
      this.getParticipant().add((AppointmentParticipantComponent) value); // AppointmentParticipantComponent
      return value;
    case -897241393: // requestedPeriod
      this.getRequestedPeriod().add(castToPeriod(value)); // Period
      return value;
    default:
      return super.setProperty(hash, name, value);
    }

  }

  @Override
  public Base setProperty(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().add(castToIdentifier(value));
    } else if (name.equals("status")) {
      value = new AppointmentStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<AppointmentStatus>
    } else if (name.equals("cancelationReason")) {
      this.cancelationReason = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("serviceCategory")) {
      this.getServiceCategory().add(castToCodeableConcept(value));
    } else if (name.equals("serviceType")) {
      this.getServiceType().add(castToCodeableConcept(value));
    } else if (name.equals("specialty")) {
      this.getSpecialty().add(castToCodeableConcept(value));
    } else if (name.equals("appointmentType")) {
      this.appointmentType = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("reasonCode")) {
      this.getReasonCode().add(castToCodeableConcept(value));
    } else if (name.equals("reasonReference")) {
      this.getReasonReference().add(castToReference(value));
    } else if (name.equals("priority")) {
      this.priority = castToUnsignedInt(value); // UnsignedIntType
    } else if (name.equals("description")) {
      this.description = castToString(value); // StringType
    } else if (name.equals("supportingInformation")) {
      this.getSupportingInformation().add(castToReference(value));
    } else if (name.equals("start")) {
      this.start = castToInstant(value); // InstantType
    } else if (name.equals("end")) {
      this.end = castToInstant(value); // InstantType
    } else if (name.equals("minutesDuration")) {
      this.minutesDuration = castToPositiveInt(value); // PositiveIntType
    } else if (name.equals("slot")) {
      this.getSlot().add(castToReference(value));
    } else if (name.equals("created")) {
      this.created = castToDateTime(value); // DateTimeType
    } else if (name.equals("comment")) {
      this.comment = castToString(value); // StringType
    } else if (name.equals("patientInstruction")) {
      this.patientInstruction = castToString(value); // StringType
    } else if (name.equals("basedOn")) {
      this.getBasedOn().add(castToReference(value));
    } else if (name.equals("participant")) {
      this.getParticipant().add((AppointmentParticipantComponent) value);
    } else if (name.equals("requestedPeriod")) {
      this.getRequestedPeriod().add(castToPeriod(value));
    } else
      return super.setProperty(name, value);
    return value;
  }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().remove(castToIdentifier(value));
    } else if (name.equals("status")) {
      this.status = null;
    } else if (name.equals("cancelationReason")) {
      this.cancelationReason = null;
    } else if (name.equals("serviceCategory")) {
      this.getServiceCategory().remove(castToCodeableConcept(value));
    } else if (name.equals("serviceType")) {
      this.getServiceType().remove(castToCodeableConcept(value));
    } else if (name.equals("specialty")) {
      this.getSpecialty().remove(castToCodeableConcept(value));
    } else if (name.equals("appointmentType")) {
      this.appointmentType = null;
    } else if (name.equals("reasonCode")) {
      this.getReasonCode().remove(castToCodeableConcept(value));
    } else if (name.equals("reasonReference")) {
      this.getReasonReference().remove(castToReference(value));
    } else if (name.equals("priority")) {
      this.priority = null;
    } else if (name.equals("description")) {
      this.description = null;
    } else if (name.equals("supportingInformation")) {
      this.getSupportingInformation().remove(castToReference(value));
    } else if (name.equals("start")) {
      this.start = null;
    } else if (name.equals("end")) {
      this.end = null;
    } else if (name.equals("minutesDuration")) {
      this.minutesDuration = null;
    } else if (name.equals("slot")) {
      this.getSlot().remove(castToReference(value));
    } else if (name.equals("created")) {
      this.created = null;
    } else if (name.equals("comment")) {
      this.comment = null;
    } else if (name.equals("patientInstruction")) {
      this.patientInstruction = null;
    } else if (name.equals("basedOn")) {
      this.getBasedOn().remove(castToReference(value));
    } else if (name.equals("participant")) {
      this.getParticipant().remove((AppointmentParticipantComponent) value);
    } else if (name.equals("requestedPeriod")) {
      this.getRequestedPeriod().remove(castToPeriod(value));
    } else
      super.removeChild(name, value);
    
  }

  @Override
  public Base makeProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      return addIdentifier();
    case -892481550:
      return getStatusElement();
    case 987811551:
      return getCancelationReason();
    case 1281188563:
      return addServiceCategory();
    case -1928370289:
      return addServiceType();
    case -1694759682:
      return addSpecialty();
    case -1596426375:
      return getAppointmentType();
    case 722137681:
      return addReasonCode();
    case -1146218137:
      return addReasonReference();
    case -1165461084:
      return getPriorityElement();
    case -1724546052:
      return getDescriptionElement();
    case -1248768647:
      return addSupportingInformation();
    case 109757538:
      return getStartElement();
    case 100571:
      return getEndElement();
    case -413630573:
      return getMinutesDurationElement();
    case 3533310:
      return addSlot();
    case 1028554472:
      return getCreatedElement();
    case 950398559:
      return getCommentElement();
    case 737543241:
      return getPatientInstructionElement();
    case -332612366:
      return addBasedOn();
    case 767422259:
      return addParticipant();
    case -897241393:
      return addRequestedPeriod();
    default:
      return super.makeProperty(hash, name);
    }

  }

  @Override
  public String[] getTypesForProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      /* identifier */ return new String[] { "Identifier" };
    case -892481550:
      /* status */ return new String[] { "code" };
    case 987811551:
      /* cancelationReason */ return new String[] { "CodeableConcept" };
    case 1281188563:
      /* serviceCategory */ return new String[] { "CodeableConcept" };
    case -1928370289:
      /* serviceType */ return new String[] { "CodeableConcept" };
    case -1694759682:
      /* specialty */ return new String[] { "CodeableConcept" };
    case -1596426375:
      /* appointmentType */ return new String[] { "CodeableConcept" };
    case 722137681:
      /* reasonCode */ return new String[] { "CodeableConcept" };
    case -1146218137:
      /* reasonReference */ return new String[] { "Reference" };
    case -1165461084:
      /* priority */ return new String[] { "unsignedInt" };
    case -1724546052:
      /* description */ return new String[] { "string" };
    case -1248768647:
      /* supportingInformation */ return new String[] { "Reference" };
    case 109757538:
      /* start */ return new String[] { "instant" };
    case 100571:
      /* end */ return new String[] { "instant" };
    case -413630573:
      /* minutesDuration */ return new String[] { "positiveInt" };
    case 3533310:
      /* slot */ return new String[] { "Reference" };
    case 1028554472:
      /* created */ return new String[] { "dateTime" };
    case 950398559:
      /* comment */ return new String[] { "string" };
    case 737543241:
      /* patientInstruction */ return new String[] { "string" };
    case -332612366:
      /* basedOn */ return new String[] { "Reference" };
    case 767422259:
      /* participant */ return new String[] {};
    case -897241393:
      /* requestedPeriod */ return new String[] { "Period" };
    default:
      return super.getTypesForProperty(hash, name);
    }

  }

  @Override
  public Base addChild(String name) throws FHIRException {
    if (name.equals("identifier")) {
      return addIdentifier();
    } else if (name.equals("status")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.status");
    } else if (name.equals("cancelationReason")) {
      this.cancelationReason = new CodeableConcept();
      return this.cancelationReason;
    } else if (name.equals("serviceCategory")) {
      return addServiceCategory();
    } else if (name.equals("serviceType")) {
      return addServiceType();
    } else if (name.equals("specialty")) {
      return addSpecialty();
    } else if (name.equals("appointmentType")) {
      this.appointmentType = new CodeableConcept();
      return this.appointmentType;
    } else if (name.equals("reasonCode")) {
      return addReasonCode();
    } else if (name.equals("reasonReference")) {
      return addReasonReference();
    } else if (name.equals("priority")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.priority");
    } else if (name.equals("description")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.description");
    } else if (name.equals("supportingInformation")) {
      return addSupportingInformation();
    } else if (name.equals("start")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.start");
    } else if (name.equals("end")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.end");
    } else if (name.equals("minutesDuration")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.minutesDuration");
    } else if (name.equals("slot")) {
      return addSlot();
    } else if (name.equals("created")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.created");
    } else if (name.equals("comment")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.comment");
    } else if (name.equals("patientInstruction")) {
      throw new FHIRException("Cannot call addChild on a singleton property Appointment.patientInstruction");
    } else if (name.equals("basedOn")) {
      return addBasedOn();
    } else if (name.equals("participant")) {
      return addParticipant();
    } else if (name.equals("requestedPeriod")) {
      return addRequestedPeriod();
    } else
      return super.addChild(name);
  }

  public String fhirType() {
    return "Appointment";

  }

  public Appointment copy() {
    Appointment dst = new Appointment();
    copyValues(dst);
    return dst;
  }

  public void copyValues(Appointment dst) {
    super.copyValues(dst);
    if (identifier != null) {
      dst.identifier = new ArrayList<Identifier>();
      for (Identifier i : identifier)
        dst.identifier.add(i.copy());
    }
    ;
    dst.status = status == null ? null : status.copy();
    dst.cancelationReason = cancelationReason == null ? null : cancelationReason.copy();
    if (serviceCategory != null) {
      dst.serviceCategory = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : serviceCategory)
        dst.serviceCategory.add(i.copy());
    }
    ;
    if (serviceType != null) {
      dst.serviceType = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : serviceType)
        dst.serviceType.add(i.copy());
    }
    ;
    if (specialty != null) {
      dst.specialty = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : specialty)
        dst.specialty.add(i.copy());
    }
    ;
    dst.appointmentType = appointmentType == null ? null : appointmentType.copy();
    if (reasonCode != null) {
      dst.reasonCode = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : reasonCode)
        dst.reasonCode.add(i.copy());
    }
    ;
    if (reasonReference != null) {
      dst.reasonReference = new ArrayList<Reference>();
      for (Reference i : reasonReference)
        dst.reasonReference.add(i.copy());
    }
    ;
    dst.priority = priority == null ? null : priority.copy();
    dst.description = description == null ? null : description.copy();
    if (supportingInformation != null) {
      dst.supportingInformation = new ArrayList<Reference>();
      for (Reference i : supportingInformation)
        dst.supportingInformation.add(i.copy());
    }
    ;
    dst.start = start == null ? null : start.copy();
    dst.end = end == null ? null : end.copy();
    dst.minutesDuration = minutesDuration == null ? null : minutesDuration.copy();
    if (slot != null) {
      dst.slot = new ArrayList<Reference>();
      for (Reference i : slot)
        dst.slot.add(i.copy());
    }
    ;
    dst.created = created == null ? null : created.copy();
    dst.comment = comment == null ? null : comment.copy();
    dst.patientInstruction = patientInstruction == null ? null : patientInstruction.copy();
    if (basedOn != null) {
      dst.basedOn = new ArrayList<Reference>();
      for (Reference i : basedOn)
        dst.basedOn.add(i.copy());
    }
    ;
    if (participant != null) {
      dst.participant = new ArrayList<AppointmentParticipantComponent>();
      for (AppointmentParticipantComponent i : participant)
        dst.participant.add(i.copy());
    }
    ;
    if (requestedPeriod != null) {
      dst.requestedPeriod = new ArrayList<Period>();
      for (Period i : requestedPeriod)
        dst.requestedPeriod.add(i.copy());
    }
    ;
  }

  protected Appointment typedCopy() {
    return copy();
  }

  @Override
  public boolean equalsDeep(Base other_) {
    if (!super.equalsDeep(other_))
      return false;
    if (!(other_ instanceof Appointment))
      return false;
    Appointment o = (Appointment) other_;
    return compareDeep(identifier, o.identifier, true) && compareDeep(status, o.status, true)
        && compareDeep(cancelationReason, o.cancelationReason, true)
        && compareDeep(serviceCategory, o.serviceCategory, true) && compareDeep(serviceType, o.serviceType, true)
        && compareDeep(specialty, o.specialty, true) && compareDeep(appointmentType, o.appointmentType, true)
        && compareDeep(reasonCode, o.reasonCode, true) && compareDeep(reasonReference, o.reasonReference, true)
        && compareDeep(priority, o.priority, true) && compareDeep(description, o.description, true)
        && compareDeep(supportingInformation, o.supportingInformation, true) && compareDeep(start, o.start, true)
        && compareDeep(end, o.end, true) && compareDeep(minutesDuration, o.minutesDuration, true)
        && compareDeep(slot, o.slot, true) && compareDeep(created, o.created, true)
        && compareDeep(comment, o.comment, true) && compareDeep(patientInstruction, o.patientInstruction, true)
        && compareDeep(basedOn, o.basedOn, true) && compareDeep(participant, o.participant, true)
        && compareDeep(requestedPeriod, o.requestedPeriod, true);
  }

  @Override
  public boolean equalsShallow(Base other_) {
    if (!super.equalsShallow(other_))
      return false;
    if (!(other_ instanceof Appointment))
      return false;
    Appointment o = (Appointment) other_;
    return compareValues(status, o.status, true) && compareValues(priority, o.priority, true)
        && compareValues(description, o.description, true) && compareValues(start, o.start, true)
        && compareValues(end, o.end, true) && compareValues(minutesDuration, o.minutesDuration, true)
        && compareValues(created, o.created, true) && compareValues(comment, o.comment, true)
        && compareValues(patientInstruction, o.patientInstruction, true);
  }

  public boolean isEmpty() {
    return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(identifier, status, cancelationReason,
        serviceCategory, serviceType, specialty, appointmentType, reasonCode, reasonReference, priority, description,
        supportingInformation, start, end, minutesDuration, slot, created, comment, patientInstruction, basedOn,
        participant, requestedPeriod);
  }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.Appointment;
  }

  /**
   * Search parameter: <b>date</b>
   * <p>
   * Description: <b>Appointment date/time.</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Appointment.start</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "date", path = "Appointment.start", description = "Appointment date/time.", type = "date")
  public static final String SP_DATE = "date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>date</b>
   * <p>
   * Description: <b>Appointment date/time.</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Appointment.start</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.DateClientParam DATE = new ca.uhn.fhir.rest.gclient.DateClientParam(
      SP_DATE);

  /**
   * Search parameter: <b>identifier</b>
   * <p>
   * Description: <b>An Identifier of the Appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.identifier</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "identifier", path = "Appointment.identifier", description = "An Identifier of the Appointment", type = "token")
  public static final String SP_IDENTIFIER = "identifier";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>identifier</b>
   * <p>
   * Description: <b>An Identifier of the Appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.identifier</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam IDENTIFIER = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_IDENTIFIER);

  /**
   * Search parameter: <b>specialty</b>
   * <p>
   * Description: <b>The specialty of a practitioner that would be required to
   * perform the service requested in this appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.specialty</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "specialty", path = "Appointment.specialty", description = "The specialty of a practitioner that would be required to perform the service requested in this appointment", type = "token")
  public static final String SP_SPECIALTY = "specialty";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>specialty</b>
   * <p>
   * Description: <b>The specialty of a practitioner that would be required to
   * perform the service requested in this appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.specialty</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SPECIALTY = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SPECIALTY);

  /**
   * Search parameter: <b>service-category</b>
   * <p>
   * Description: <b>A broad categorization of the service that is to be performed
   * during this appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.serviceCategory</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "service-category", path = "Appointment.serviceCategory", description = "A broad categorization of the service that is to be performed during this appointment", type = "token")
  public static final String SP_SERVICE_CATEGORY = "service-category";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>service-category</b>
   * <p>
   * Description: <b>A broad categorization of the service that is to be performed
   * during this appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.serviceCategory</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SERVICE_CATEGORY = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SERVICE_CATEGORY);

  /**
   * Search parameter: <b>practitioner</b>
   * <p>
   * Description: <b>One of the individuals of the appointment is this
   * practitioner</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "practitioner", path = "Appointment.participant.actor.where(resolve() is Practitioner)", description = "One of the individuals of the appointment is this practitioner", type = "reference", target = {
      Practitioner.class })
  public static final String SP_PRACTITIONER = "practitioner";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>practitioner</b>
   * <p>
   * Description: <b>One of the individuals of the appointment is this
   * practitioner</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PRACTITIONER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PRACTITIONER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:practitioner</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PRACTITIONER = new ca.uhn.fhir.model.api.Include(
      "Appointment:practitioner").toLocked();

  /**
   * Search parameter: <b>part-status</b>
   * <p>
   * Description: <b>The Participation status of the subject, or other participant
   * on the appointment. Can be used to locate participants that have not
   * responded to meeting requests.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.participant.status</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "part-status", path = "Appointment.participant.status", description = "The Participation status of the subject, or other participant on the appointment. Can be used to locate participants that have not responded to meeting requests.", type = "token")
  public static final String SP_PART_STATUS = "part-status";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>part-status</b>
   * <p>
   * Description: <b>The Participation status of the subject, or other participant
   * on the appointment. Can be used to locate participants that have not
   * responded to meeting requests.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.participant.status</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam PART_STATUS = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_PART_STATUS);

  /**
   * Search parameter: <b>appointment-type</b>
   * <p>
   * Description: <b>The style of appointment or patient that has been booked in
   * the slot (not service type)</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.appointmentType</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "appointment-type", path = "Appointment.appointmentType", description = "The style of appointment or patient that has been booked in the slot (not service type)", type = "token")
  public static final String SP_APPOINTMENT_TYPE = "appointment-type";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>appointment-type</b>
   * <p>
   * Description: <b>The style of appointment or patient that has been booked in
   * the slot (not service type)</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.appointmentType</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam APPOINTMENT_TYPE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_APPOINTMENT_TYPE);

  /**
   * Search parameter: <b>service-type</b>
   * <p>
   * Description: <b>The specific service that is to be performed during this
   * appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.serviceType</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "service-type", path = "Appointment.serviceType", description = "The specific service that is to be performed during this appointment", type = "token")
  public static final String SP_SERVICE_TYPE = "service-type";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>service-type</b>
   * <p>
   * Description: <b>The specific service that is to be performed during this
   * appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.serviceType</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SERVICE_TYPE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SERVICE_TYPE);

  /**
   * Search parameter: <b>slot</b>
   * <p>
   * Description: <b>The slots that this appointment is filling</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.slot</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "slot", path = "Appointment.slot", description = "The slots that this appointment is filling", type = "reference", target = {
      Slot.class })
  public static final String SP_SLOT = "slot";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>slot</b>
   * <p>
   * Description: <b>The slots that this appointment is filling</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.slot</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam SLOT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_SLOT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:slot</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_SLOT = new ca.uhn.fhir.model.api.Include("Appointment:slot")
      .toLocked();

  /**
   * Search parameter: <b>reason-code</b>
   * <p>
   * Description: <b>Coded reason this appointment is scheduled</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.reasonCode</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "reason-code", path = "Appointment.reasonCode", description = "Coded reason this appointment is scheduled", type = "token")
  public static final String SP_REASON_CODE = "reason-code";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>reason-code</b>
   * <p>
   * Description: <b>Coded reason this appointment is scheduled</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.reasonCode</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam REASON_CODE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_REASON_CODE);

  /**
   * Search parameter: <b>actor</b>
   * <p>
   * Description: <b>Any one of the individuals participating in the
   * appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "actor", path = "Appointment.participant.actor", description = "Any one of the individuals participating in the appointment", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Device"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Patient"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Practitioner"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "RelatedPerson") }, target = { Device.class,
          HealthcareService.class, Location.class, Patient.class, Practitioner.class, PractitionerRole.class,
          RelatedPerson.class })
  public static final String SP_ACTOR = "actor";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>actor</b>
   * <p>
   * Description: <b>Any one of the individuals participating in the
   * appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam ACTOR = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_ACTOR);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:actor</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_ACTOR = new ca.uhn.fhir.model.api.Include(
      "Appointment:actor").toLocked();

  /**
   * Search parameter: <b>based-on</b>
   * <p>
   * Description: <b>The service request this appointment is allocated to
   * assess</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.basedOn</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "based-on", path = "Appointment.basedOn", description = "The service request this appointment is allocated to assess", type = "reference", target = {
      ServiceRequest.class })
  public static final String SP_BASED_ON = "based-on";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>based-on</b>
   * <p>
   * Description: <b>The service request this appointment is allocated to
   * assess</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.basedOn</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam BASED_ON = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_BASED_ON);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:based-on</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_BASED_ON = new ca.uhn.fhir.model.api.Include(
      "Appointment:based-on").toLocked();

  /**
   * Search parameter: <b>patient</b>
   * <p>
   * Description: <b>One of the individuals of the appointment is this
   * patient</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "patient", path = "Appointment.participant.actor.where(resolve() is Patient)", description = "One of the individuals of the appointment is this patient", type = "reference", target = {
      Patient.class })
  public static final String SP_PATIENT = "patient";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>patient</b>
   * <p>
   * Description: <b>One of the individuals of the appointment is this
   * patient</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PATIENT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PATIENT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:patient</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PATIENT = new ca.uhn.fhir.model.api.Include(
      "Appointment:patient").toLocked();

  /**
   * Search parameter: <b>reason-reference</b>
   * <p>
   * Description: <b>Reason the appointment is to take place (resource)</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.reasonReference</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "reason-reference", path = "Appointment.reasonReference", description = "Reason the appointment is to take place (resource)", type = "reference", target = {
      Condition.class, ImmunizationRecommendation.class, Observation.class, Procedure.class })
  public static final String SP_REASON_REFERENCE = "reason-reference";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>reason-reference</b>
   * <p>
   * Description: <b>Reason the appointment is to take place (resource)</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.reasonReference</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam REASON_REFERENCE = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_REASON_REFERENCE);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:reason-reference</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_REASON_REFERENCE = new ca.uhn.fhir.model.api.Include(
      "Appointment:reason-reference").toLocked();

  /**
   * Search parameter: <b>supporting-info</b>
   * <p>
   * Description: <b>Additional information to support the appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.supportingInformation</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "supporting-info", path = "Appointment.supportingInformation", description = "Additional information to support the appointment", type = "reference")
  public static final String SP_SUPPORTING_INFO = "supporting-info";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>supporting-info</b>
   * <p>
   * Description: <b>Additional information to support the appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.supportingInformation</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam SUPPORTING_INFO = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_SUPPORTING_INFO);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:supporting-info</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_SUPPORTING_INFO = new ca.uhn.fhir.model.api.Include(
      "Appointment:supporting-info").toLocked();

  /**
   * Search parameter: <b>location</b>
   * <p>
   * Description: <b>This location is listed in the participants of the
   * appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "location", path = "Appointment.participant.actor.where(resolve() is Location)", description = "This location is listed in the participants of the appointment", type = "reference", target = {
      Location.class })
  public static final String SP_LOCATION = "location";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>location</b>
   * <p>
   * Description: <b>This location is listed in the participants of the
   * appointment</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Appointment.participant.actor</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam LOCATION = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_LOCATION);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Appointment:location</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_LOCATION = new ca.uhn.fhir.model.api.Include(
      "Appointment:location").toLocked();

  /**
   * Search parameter: <b>status</b>
   * <p>
   * Description: <b>The overall status of the appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.status</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "status", path = "Appointment.status", description = "The overall status of the appointment", type = "token")
  public static final String SP_STATUS = "status";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>status</b>
   * <p>
   * Description: <b>The overall status of the appointment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Appointment.status</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam STATUS = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_STATUS);

}
