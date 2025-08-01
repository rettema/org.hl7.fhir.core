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
import java.util.List;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.utilities.Utilities;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.api.annotation.SearchParamDefinition;

/**
 * A container for slots of time that may be available for booking appointments.
 */
@ResourceDef(name = "Schedule", profile = "http://hl7.org/fhir/StructureDefinition/Schedule")
public class Schedule extends DomainResource {

  /**
   * External Ids for this item.
   */
  @Child(name = "identifier", type = {
      Identifier.class }, order = 0, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "External Ids for this item", formalDefinition = "External Ids for this item.")
  protected List<Identifier> identifier;

  /**
   * Whether this schedule record is in active use or should not be used (such as
   * was entered in error).
   */
  @Child(name = "active", type = { BooleanType.class }, order = 1, min = 0, max = 1, modifier = true, summary = true)
  @Description(shortDefinition = "Whether this schedule is in active use", formalDefinition = "Whether this schedule record is in active use or should not be used (such as was entered in error).")
  protected BooleanType active;

  /**
   * A broad categorization of the service that is to be performed during this
   * appointment.
   */
  @Child(name = "serviceCategory", type = {
      CodeableConcept.class }, order = 2, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "High-level category", formalDefinition = "A broad categorization of the service that is to be performed during this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/service-category")
  protected List<CodeableConcept> serviceCategory;

  /**
   * The specific service that is to be performed during this appointment.
   */
  @Child(name = "serviceType", type = {
      CodeableConcept.class }, order = 3, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Specific service", formalDefinition = "The specific service that is to be performed during this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/service-type")
  protected List<CodeableConcept> serviceType;

  /**
   * The specialty of a practitioner that would be required to perform the service
   * requested in this appointment.
   */
  @Child(name = "specialty", type = {
      CodeableConcept.class }, order = 4, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Type of specialty needed", formalDefinition = "The specialty of a practitioner that would be required to perform the service requested in this appointment.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/c80-practice-codes")
  protected List<CodeableConcept> specialty;

  /**
   * Slots that reference this schedule resource provide the availability details
   * to these referenced resource(s).
   */
  @Child(name = "actor", type = { Patient.class, Practitioner.class, PractitionerRole.class, RelatedPerson.class,
      Device.class, HealthcareService.class,
      Location.class }, order = 5, min = 1, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Resource(s) that availability information is being provided for", formalDefinition = "Slots that reference this schedule resource provide the availability details to these referenced resource(s).")
  protected List<Reference> actor;
  /**
   * The actual objects that are the target of the reference (Slots that reference
   * this schedule resource provide the availability details to these referenced
   * resource(s).)
   */
  protected List<Resource> actorTarget;

  /**
   * The period of time that the slots that reference this Schedule resource cover
   * (even if none exist). These cover the amount of time that an organization's
   * planning horizon; the interval for which they are currently accepting
   * appointments. This does not define a "template" for planning outside these
   * dates.
   */
  @Child(name = "planningHorizon", type = {
      Period.class }, order = 6, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Period of time covered by schedule", formalDefinition = "The period of time that the slots that reference this Schedule resource cover (even if none exist). These  cover the amount of time that an organization's planning horizon; the interval for which they are currently accepting appointments. This does not define a \"template\" for planning outside these dates.")
  protected Period planningHorizon;

  /**
   * Comments on the availability to describe any extended information. Such as
   * custom constraints on the slots that may be associated.
   */
  @Child(name = "comment", type = { StringType.class }, order = 7, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Comments on availability", formalDefinition = "Comments on the availability to describe any extended information. Such as custom constraints on the slots that may be associated.")
  protected StringType comment;

  private static final long serialVersionUID = 203182600L;

  /**
   * Constructor
   */
  public Schedule() {
    super();
  }

  /**
   * @return {@link #identifier} (External Ids for this item.)
   */
  public List<Identifier> getIdentifier() {
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    return this.identifier;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Schedule setIdentifier(List<Identifier> theIdentifier) {
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

  public Schedule addIdentifier(Identifier t) { // 3
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
   * @return {@link #active} (Whether this schedule record is in active use or
   *         should not be used (such as was entered in error).). This is the
   *         underlying object with id, value and extensions. The accessor
   *         "getActive" gives direct access to the value
   */
  public BooleanType getActiveElement() {
    if (this.active == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Schedule.active");
      else if (Configuration.doAutoCreate())
        this.active = new BooleanType(); // bb
    return this.active;
  }

  public boolean hasActiveElement() {
    return this.active != null && !this.active.isEmpty();
  }

  public boolean hasActive() {
    return this.active != null && !this.active.isEmpty();
  }

  /**
   * @param value {@link #active} (Whether this schedule record is in active use
   *              or should not be used (such as was entered in error).). This is
   *              the underlying object with id, value and extensions. The
   *              accessor "getActive" gives direct access to the value
   */
  public Schedule setActiveElement(BooleanType value) {
    this.active = value;
    return this;
  }

  /**
   * @return Whether this schedule record is in active use or should not be used
   *         (such as was entered in error).
   */
  public boolean getActive() {
    return this.active == null || this.active.isEmpty() ? false : this.active.getValue();
  }

  /**
   * @param value Whether this schedule record is in active use or should not be
   *              used (such as was entered in error).
   */
  public Schedule setActive(boolean value) {
    if (this.active == null)
      this.active = new BooleanType();
    this.active.setValue(value);
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
  public Schedule setServiceCategory(List<CodeableConcept> theServiceCategory) {
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

  public Schedule addServiceCategory(CodeableConcept t) { // 3
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
  public Schedule setServiceType(List<CodeableConcept> theServiceType) {
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

  public Schedule addServiceType(CodeableConcept t) { // 3
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
  public Schedule setSpecialty(List<CodeableConcept> theSpecialty) {
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

  public Schedule addSpecialty(CodeableConcept t) { // 3
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
   * @return {@link #actor} (Slots that reference this schedule resource provide
   *         the availability details to these referenced resource(s).)
   */
  public List<Reference> getActor() {
    if (this.actor == null)
      this.actor = new ArrayList<Reference>();
    return this.actor;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Schedule setActor(List<Reference> theActor) {
    this.actor = theActor;
    return this;
  }

  public boolean hasActor() {
    if (this.actor == null)
      return false;
    for (Reference item : this.actor)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addActor() { // 3
    Reference t = new Reference();
    if (this.actor == null)
      this.actor = new ArrayList<Reference>();
    this.actor.add(t);
    return t;
  }

  public Schedule addActor(Reference t) { // 3
    if (t == null)
      return this;
    if (this.actor == null)
      this.actor = new ArrayList<Reference>();
    this.actor.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #actor}, creating it
   *         if it does not already exist
   */
  public Reference getActorFirstRep() {
    if (getActor().isEmpty()) {
      addActor();
    }
    return getActor().get(0);
  }

  /**
   * @return {@link #planningHorizon} (The period of time that the slots that
   *         reference this Schedule resource cover (even if none exist). These
   *         cover the amount of time that an organization's planning horizon; the
   *         interval for which they are currently accepting appointments. This
   *         does not define a "template" for planning outside these dates.)
   */
  public Period getPlanningHorizon() {
    if (this.planningHorizon == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Schedule.planningHorizon");
      else if (Configuration.doAutoCreate())
        this.planningHorizon = new Period(); // cc
    return this.planningHorizon;
  }

  public boolean hasPlanningHorizon() {
    return this.planningHorizon != null && !this.planningHorizon.isEmpty();
  }

  /**
   * @param value {@link #planningHorizon} (The period of time that the slots that
   *              reference this Schedule resource cover (even if none exist).
   *              These cover the amount of time that an organization's planning
   *              horizon; the interval for which they are currently accepting
   *              appointments. This does not define a "template" for planning
   *              outside these dates.)
   */
  public Schedule setPlanningHorizon(Period value) {
    this.planningHorizon = value;
    return this;
  }

  /**
   * @return {@link #comment} (Comments on the availability to describe any
   *         extended information. Such as custom constraints on the slots that
   *         may be associated.). This is the underlying object with id, value and
   *         extensions. The accessor "getComment" gives direct access to the
   *         value
   */
  public StringType getCommentElement() {
    if (this.comment == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Schedule.comment");
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
   * @param value {@link #comment} (Comments on the availability to describe any
   *              extended information. Such as custom constraints on the slots
   *              that may be associated.). This is the underlying object with id,
   *              value and extensions. The accessor "getComment" gives direct
   *              access to the value
   */
  public Schedule setCommentElement(StringType value) {
    this.comment = value;
    return this;
  }

  /**
   * @return Comments on the availability to describe any extended information.
   *         Such as custom constraints on the slots that may be associated.
   */
  public String getComment() {
    return this.comment == null ? null : this.comment.getValue();
  }

  /**
   * @param value Comments on the availability to describe any extended
   *              information. Such as custom constraints on the slots that may be
   *              associated.
   */
  public Schedule setComment(String value) {
    if (Utilities.noString(value))
      this.comment = null;
    else {
      if (this.comment == null)
        this.comment = new StringType();
      this.comment.setValue(value);
    }
    return this;
  }

  protected void listChildren(List<Property> children) {
    super.listChildren(children);
    children.add(new Property("identifier", "Identifier", "External Ids for this item.", 0, java.lang.Integer.MAX_VALUE,
        identifier));
    children.add(new Property("active", "boolean",
        "Whether this schedule record is in active use or should not be used (such as was entered in error).", 0, 1,
        active));
    children.add(new Property("serviceCategory", "CodeableConcept",
        "A broad categorization of the service that is to be performed during this appointment.", 0,
        java.lang.Integer.MAX_VALUE, serviceCategory));
    children.add(new Property("serviceType", "CodeableConcept",
        "The specific service that is to be performed during this appointment.", 0, java.lang.Integer.MAX_VALUE,
        serviceType));
    children.add(new Property("specialty", "CodeableConcept",
        "The specialty of a practitioner that would be required to perform the service requested in this appointment.",
        0, java.lang.Integer.MAX_VALUE, specialty));
    children.add(new Property("actor",
        "Reference(Patient|Practitioner|PractitionerRole|RelatedPerson|Device|HealthcareService|Location)",
        "Slots that reference this schedule resource provide the availability details to these referenced resource(s).",
        0, java.lang.Integer.MAX_VALUE, actor));
    children.add(new Property("planningHorizon", "Period",
        "The period of time that the slots that reference this Schedule resource cover (even if none exist). These  cover the amount of time that an organization's planning horizon; the interval for which they are currently accepting appointments. This does not define a \"template\" for planning outside these dates.",
        0, 1, planningHorizon));
    children.add(new Property("comment", "string",
        "Comments on the availability to describe any extended information. Such as custom constraints on the slots that may be associated.",
        0, 1, comment));
  }

  @Override
  public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
    switch (_hash) {
    case -1618432855:
      /* identifier */ return new Property("identifier", "Identifier", "External Ids for this item.", 0,
          java.lang.Integer.MAX_VALUE, identifier);
    case -1422950650:
      /* active */ return new Property("active", "boolean",
          "Whether this schedule record is in active use or should not be used (such as was entered in error).", 0, 1,
          active);
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
    case 92645877:
      /* actor */ return new Property("actor",
          "Reference(Patient|Practitioner|PractitionerRole|RelatedPerson|Device|HealthcareService|Location)",
          "Slots that reference this schedule resource provide the availability details to these referenced resource(s).",
          0, java.lang.Integer.MAX_VALUE, actor);
    case -1718507650:
      /* planningHorizon */ return new Property("planningHorizon", "Period",
          "The period of time that the slots that reference this Schedule resource cover (even if none exist). These  cover the amount of time that an organization's planning horizon; the interval for which they are currently accepting appointments. This does not define a \"template\" for planning outside these dates.",
          0, 1, planningHorizon);
    case 950398559:
      /* comment */ return new Property("comment", "string",
          "Comments on the availability to describe any extended information. Such as custom constraints on the slots that may be associated.",
          0, 1, comment);
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
    case -1422950650:
      /* active */ return this.active == null ? new Base[0] : new Base[] { this.active }; // BooleanType
    case 1281188563:
      /* serviceCategory */ return this.serviceCategory == null ? new Base[0]
          : this.serviceCategory.toArray(new Base[this.serviceCategory.size()]); // CodeableConcept
    case -1928370289:
      /* serviceType */ return this.serviceType == null ? new Base[0]
          : this.serviceType.toArray(new Base[this.serviceType.size()]); // CodeableConcept
    case -1694759682:
      /* specialty */ return this.specialty == null ? new Base[0]
          : this.specialty.toArray(new Base[this.specialty.size()]); // CodeableConcept
    case 92645877:
      /* actor */ return this.actor == null ? new Base[0] : this.actor.toArray(new Base[this.actor.size()]); // Reference
    case -1718507650:
      /* planningHorizon */ return this.planningHorizon == null ? new Base[0] : new Base[] { this.planningHorizon }; // Period
    case 950398559:
      /* comment */ return this.comment == null ? new Base[0] : new Base[] { this.comment }; // StringType
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
    case -1422950650: // active
      this.active = castToBoolean(value); // BooleanType
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
    case 92645877: // actor
      this.getActor().add(castToReference(value)); // Reference
      return value;
    case -1718507650: // planningHorizon
      this.planningHorizon = castToPeriod(value); // Period
      return value;
    case 950398559: // comment
      this.comment = castToString(value); // StringType
      return value;
    default:
      return super.setProperty(hash, name, value);
    }

  }

  @Override
  public Base setProperty(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().add(castToIdentifier(value));
    } else if (name.equals("active")) {
      this.active = castToBoolean(value); // BooleanType
    } else if (name.equals("serviceCategory")) {
      this.getServiceCategory().add(castToCodeableConcept(value));
    } else if (name.equals("serviceType")) {
      this.getServiceType().add(castToCodeableConcept(value));
    } else if (name.equals("specialty")) {
      this.getSpecialty().add(castToCodeableConcept(value));
    } else if (name.equals("actor")) {
      this.getActor().add(castToReference(value));
    } else if (name.equals("planningHorizon")) {
      this.planningHorizon = castToPeriod(value); // Period
    } else if (name.equals("comment")) {
      this.comment = castToString(value); // StringType
    } else
      return super.setProperty(name, value);
    return value;
  }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().remove(castToIdentifier(value));
    } else if (name.equals("active")) {
      this.active = null;
    } else if (name.equals("serviceCategory")) {
      this.getServiceCategory().remove(castToCodeableConcept(value));
    } else if (name.equals("serviceType")) {
      this.getServiceType().remove(castToCodeableConcept(value));
    } else if (name.equals("specialty")) {
      this.getSpecialty().remove(castToCodeableConcept(value));
    } else if (name.equals("actor")) {
      this.getActor().remove(castToReference(value));
    } else if (name.equals("planningHorizon")) {
      this.planningHorizon = null;
    } else if (name.equals("comment")) {
      this.comment = null;
    } else
      super.removeChild(name, value);
    
  }

  @Override
  public Base makeProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      return addIdentifier();
    case -1422950650:
      return getActiveElement();
    case 1281188563:
      return addServiceCategory();
    case -1928370289:
      return addServiceType();
    case -1694759682:
      return addSpecialty();
    case 92645877:
      return addActor();
    case -1718507650:
      return getPlanningHorizon();
    case 950398559:
      return getCommentElement();
    default:
      return super.makeProperty(hash, name);
    }

  }

  @Override
  public String[] getTypesForProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      /* identifier */ return new String[] { "Identifier" };
    case -1422950650:
      /* active */ return new String[] { "boolean" };
    case 1281188563:
      /* serviceCategory */ return new String[] { "CodeableConcept" };
    case -1928370289:
      /* serviceType */ return new String[] { "CodeableConcept" };
    case -1694759682:
      /* specialty */ return new String[] { "CodeableConcept" };
    case 92645877:
      /* actor */ return new String[] { "Reference" };
    case -1718507650:
      /* planningHorizon */ return new String[] { "Period" };
    case 950398559:
      /* comment */ return new String[] { "string" };
    default:
      return super.getTypesForProperty(hash, name);
    }

  }

  @Override
  public Base addChild(String name) throws FHIRException {
    if (name.equals("identifier")) {
      return addIdentifier();
    } else if (name.equals("active")) {
      throw new FHIRException("Cannot call addChild on a singleton property Schedule.active");
    } else if (name.equals("serviceCategory")) {
      return addServiceCategory();
    } else if (name.equals("serviceType")) {
      return addServiceType();
    } else if (name.equals("specialty")) {
      return addSpecialty();
    } else if (name.equals("actor")) {
      return addActor();
    } else if (name.equals("planningHorizon")) {
      this.planningHorizon = new Period();
      return this.planningHorizon;
    } else if (name.equals("comment")) {
      throw new FHIRException("Cannot call addChild on a singleton property Schedule.comment");
    } else
      return super.addChild(name);
  }

  public String fhirType() {
    return "Schedule";

  }

  public Schedule copy() {
    Schedule dst = new Schedule();
    copyValues(dst);
    return dst;
  }

  public void copyValues(Schedule dst) {
    super.copyValues(dst);
    if (identifier != null) {
      dst.identifier = new ArrayList<Identifier>();
      for (Identifier i : identifier)
        dst.identifier.add(i.copy());
    }
    ;
    dst.active = active == null ? null : active.copy();
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
    if (actor != null) {
      dst.actor = new ArrayList<Reference>();
      for (Reference i : actor)
        dst.actor.add(i.copy());
    }
    ;
    dst.planningHorizon = planningHorizon == null ? null : planningHorizon.copy();
    dst.comment = comment == null ? null : comment.copy();
  }

  protected Schedule typedCopy() {
    return copy();
  }

  @Override
  public boolean equalsDeep(Base other_) {
    if (!super.equalsDeep(other_))
      return false;
    if (!(other_ instanceof Schedule))
      return false;
    Schedule o = (Schedule) other_;
    return compareDeep(identifier, o.identifier, true) && compareDeep(active, o.active, true)
        && compareDeep(serviceCategory, o.serviceCategory, true) && compareDeep(serviceType, o.serviceType, true)
        && compareDeep(specialty, o.specialty, true) && compareDeep(actor, o.actor, true)
        && compareDeep(planningHorizon, o.planningHorizon, true) && compareDeep(comment, o.comment, true);
  }

  @Override
  public boolean equalsShallow(Base other_) {
    if (!super.equalsShallow(other_))
      return false;
    if (!(other_ instanceof Schedule))
      return false;
    Schedule o = (Schedule) other_;
    return compareValues(active, o.active, true) && compareValues(comment, o.comment, true);
  }

  public boolean isEmpty() {
    return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(identifier, active, serviceCategory, serviceType,
        specialty, actor, planningHorizon, comment);
  }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.Schedule;
  }

  /**
   * Search parameter: <b>actor</b>
   * <p>
   * Description: <b>The individual(HealthcareService, Practitioner, Location,
   * ...) to find a Schedule for</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Schedule.actor</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "actor", path = "Schedule.actor", description = "The individual(HealthcareService, Practitioner, Location, ...) to find a Schedule for", type = "reference", providesMembershipIn = {
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
   * Description: <b>The individual(HealthcareService, Practitioner, Location,
   * ...) to find a Schedule for</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Schedule.actor</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam ACTOR = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_ACTOR);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Schedule:actor</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_ACTOR = new ca.uhn.fhir.model.api.Include("Schedule:actor")
      .toLocked();

  /**
   * Search parameter: <b>date</b>
   * <p>
   * Description: <b>Search for Schedule resources that have a period that
   * contains this date specified</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Schedule.planningHorizon</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "date", path = "Schedule.planningHorizon", description = "Search for Schedule resources that have a period that contains this date specified", type = "date")
  public static final String SP_DATE = "date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>date</b>
   * <p>
   * Description: <b>Search for Schedule resources that have a period that
   * contains this date specified</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Schedule.planningHorizon</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.DateClientParam DATE = new ca.uhn.fhir.rest.gclient.DateClientParam(
      SP_DATE);

  /**
   * Search parameter: <b>identifier</b>
   * <p>
   * Description: <b>A Schedule Identifier</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.identifier</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "identifier", path = "Schedule.identifier", description = "A Schedule Identifier", type = "token")
  public static final String SP_IDENTIFIER = "identifier";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>identifier</b>
   * <p>
   * Description: <b>A Schedule Identifier</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.identifier</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam IDENTIFIER = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_IDENTIFIER);

  /**
   * Search parameter: <b>specialty</b>
   * <p>
   * Description: <b>Type of specialty needed</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.specialty</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "specialty", path = "Schedule.specialty", description = "Type of specialty needed", type = "token")
  public static final String SP_SPECIALTY = "specialty";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>specialty</b>
   * <p>
   * Description: <b>Type of specialty needed</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.specialty</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SPECIALTY = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SPECIALTY);

  /**
   * Search parameter: <b>service-category</b>
   * <p>
   * Description: <b>High-level category</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.serviceCategory</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "service-category", path = "Schedule.serviceCategory", description = "High-level category", type = "token")
  public static final String SP_SERVICE_CATEGORY = "service-category";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>service-category</b>
   * <p>
   * Description: <b>High-level category</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.serviceCategory</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SERVICE_CATEGORY = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SERVICE_CATEGORY);

  /**
   * Search parameter: <b>service-type</b>
   * <p>
   * Description: <b>The type of appointments that can be booked into associated
   * slot(s)</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.serviceType</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "service-type", path = "Schedule.serviceType", description = "The type of appointments that can be booked into associated slot(s)", type = "token")
  public static final String SP_SERVICE_TYPE = "service-type";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>service-type</b>
   * <p>
   * Description: <b>The type of appointments that can be booked into associated
   * slot(s)</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.serviceType</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam SERVICE_TYPE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_SERVICE_TYPE);

  /**
   * Search parameter: <b>active</b>
   * <p>
   * Description: <b>Is the schedule in active use</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.active</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "active", path = "Schedule.active", description = "Is the schedule in active use", type = "token")
  public static final String SP_ACTIVE = "active";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>active</b>
   * <p>
   * Description: <b>Is the schedule in active use</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Schedule.active</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam ACTIVE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_ACTIVE);

}