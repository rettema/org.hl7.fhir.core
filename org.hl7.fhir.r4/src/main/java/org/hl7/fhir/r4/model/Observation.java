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
 * Measurements and simple assertions made about a patient, device or other
 * subject.
 */
@ResourceDef(name = "Observation", profile = "http://hl7.org/fhir/StructureDefinition/Observation")
public class Observation extends DomainResource {

  public enum ObservationStatus {
    /**
     * The existence of the observation is registered, but there is no result yet
     * available.
     */
    REGISTERED,
    /**
     * This is an initial or interim observation: data may be incomplete or
     * unverified.
     */
    PRELIMINARY,
    /**
     * The observation is complete and there are no further actions needed.
     * Additional information such "released", "signed", etc would be represented
     * using [Provenance](provenance.html) which provides not only the act but also
     * the actors and dates and other related data. These act states would be
     * associated with an observation status of `preliminary` until they are all
     * completed and then a status of `final` would be applied.
     */
    FINAL,
    /**
     * Subsequent to being Final, the observation has been modified subsequent. This
     * includes updates/new information and corrections.
     */
    AMENDED,
    /**
     * Subsequent to being Final, the observation has been modified to correct an
     * error in the test result.
     */
    CORRECTED,
    /**
     * The observation is unavailable because the measurement was not started or not
     * completed (also sometimes called "aborted").
     */
    CANCELLED,
    /**
     * The observation has been withdrawn following previous final release. This
     * electronic record should never have existed, though it is possible that
     * real-world decisions were based on it. (If real-world activity has occurred,
     * the status should be "cancelled" rather than "entered-in-error".).
     */
    ENTEREDINERROR,
    /**
     * The authoring/source system does not know which of the status values
     * currently applies for this observation. Note: This concept is not to be used
     * for "other" - one of the listed statuses is presumed to apply, but the
     * authoring/source system does not know which.
     */
    UNKNOWN,
    /**
     * added to help the parsers with the generic types
     */
    NULL;

    public static ObservationStatus fromCode(String codeString) throws FHIRException {
      if (codeString == null || "".equals(codeString))
        return null;
      if ("registered".equals(codeString))
        return REGISTERED;
      if ("preliminary".equals(codeString))
        return PRELIMINARY;
      if ("final".equals(codeString))
        return FINAL;
      if ("amended".equals(codeString))
        return AMENDED;
      if ("corrected".equals(codeString))
        return CORRECTED;
      if ("cancelled".equals(codeString))
        return CANCELLED;
      if ("entered-in-error".equals(codeString))
        return ENTEREDINERROR;
      if ("unknown".equals(codeString))
        return UNKNOWN;
      if (Configuration.isAcceptInvalidEnums())
        return null;
      else
        throw new FHIRException("Unknown ObservationStatus code '" + codeString + "'");
    }

    public String toCode() {
      switch (this) {
      case REGISTERED:
        return "registered";
      case PRELIMINARY:
        return "preliminary";
      case FINAL:
        return "final";
      case AMENDED:
        return "amended";
      case CORRECTED:
        return "corrected";
      case CANCELLED:
        return "cancelled";
      case ENTEREDINERROR:
        return "entered-in-error";
      case UNKNOWN:
        return "unknown";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getSystem() {
      switch (this) {
      case REGISTERED:
        return "http://hl7.org/fhir/observation-status";
      case PRELIMINARY:
        return "http://hl7.org/fhir/observation-status";
      case FINAL:
        return "http://hl7.org/fhir/observation-status";
      case AMENDED:
        return "http://hl7.org/fhir/observation-status";
      case CORRECTED:
        return "http://hl7.org/fhir/observation-status";
      case CANCELLED:
        return "http://hl7.org/fhir/observation-status";
      case ENTEREDINERROR:
        return "http://hl7.org/fhir/observation-status";
      case UNKNOWN:
        return "http://hl7.org/fhir/observation-status";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDefinition() {
      switch (this) {
      case REGISTERED:
        return "The existence of the observation is registered, but there is no result yet available.";
      case PRELIMINARY:
        return "This is an initial or interim observation: data may be incomplete or unverified.";
      case FINAL:
        return "The observation is complete and there are no further actions needed. Additional information such \"released\", \"signed\", etc would be represented using [Provenance](provenance.html) which provides not only the act but also the actors and dates and other related data. These act states would be associated with an observation status of `preliminary` until they are all completed and then a status of `final` would be applied.";
      case AMENDED:
        return "Subsequent to being Final, the observation has been modified subsequent.  This includes updates/new information and corrections.";
      case CORRECTED:
        return "Subsequent to being Final, the observation has been modified to correct an error in the test result.";
      case CANCELLED:
        return "The observation is unavailable because the measurement was not started or not completed (also sometimes called \"aborted\").";
      case ENTEREDINERROR:
        return "The observation has been withdrawn following previous final release.  This electronic record should never have existed, though it is possible that real-world decisions were based on it. (If real-world activity has occurred, the status should be \"cancelled\" rather than \"entered-in-error\".).";
      case UNKNOWN:
        return "The authoring/source system does not know which of the status values currently applies for this observation. Note: This concept is not to be used for \"other\" - one of the listed statuses is presumed to apply, but the authoring/source system does not know which.";
      case NULL:
        return null;
      default:
        return "?";
      }
    }

    public String getDisplay() {
      switch (this) {
      case REGISTERED:
        return "Registered";
      case PRELIMINARY:
        return "Preliminary";
      case FINAL:
        return "Final";
      case AMENDED:
        return "Amended";
      case CORRECTED:
        return "Corrected";
      case CANCELLED:
        return "Cancelled";
      case ENTEREDINERROR:
        return "Entered in Error";
      case UNKNOWN:
        return "Unknown";
      case NULL:
        return null;
      default:
        return "?";
      }
    }
  }

  public static class ObservationStatusEnumFactory implements EnumFactory<ObservationStatus> {
    public ObservationStatus fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
        if (codeString == null || "".equals(codeString))
          return null;
      if ("registered".equals(codeString))
        return ObservationStatus.REGISTERED;
      if ("preliminary".equals(codeString))
        return ObservationStatus.PRELIMINARY;
      if ("final".equals(codeString))
        return ObservationStatus.FINAL;
      if ("amended".equals(codeString))
        return ObservationStatus.AMENDED;
      if ("corrected".equals(codeString))
        return ObservationStatus.CORRECTED;
      if ("cancelled".equals(codeString))
        return ObservationStatus.CANCELLED;
      if ("entered-in-error".equals(codeString))
        return ObservationStatus.ENTEREDINERROR;
      if ("unknown".equals(codeString))
        return ObservationStatus.UNKNOWN;
      throw new IllegalArgumentException("Unknown ObservationStatus code '" + codeString + "'");
    }

    public Enumeration<ObservationStatus> fromType(PrimitiveType<?> code) throws FHIRException {
      if (code == null)
        return null;
      if (code.isEmpty())
        return new Enumeration<ObservationStatus>(this, ObservationStatus.NULL, code);
      String codeString = code.asStringValue();
      if (codeString == null || "".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.NULL, code);
      if ("registered".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.REGISTERED, code);
      if ("preliminary".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.PRELIMINARY, code);
      if ("final".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.FINAL, code);
      if ("amended".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.AMENDED, code);
      if ("corrected".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.CORRECTED, code);
      if ("cancelled".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.CANCELLED, code);
      if ("entered-in-error".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.ENTEREDINERROR, code);
      if ("unknown".equals(codeString))
        return new Enumeration<ObservationStatus>(this, ObservationStatus.UNKNOWN, code);
      throw new FHIRException("Unknown ObservationStatus code '" + codeString + "'");
    }

    public String toCode(ObservationStatus code) {
       if (code == ObservationStatus.NULL)
           return null;
       if (code == ObservationStatus.REGISTERED)
        return "registered";
      if (code == ObservationStatus.PRELIMINARY)
        return "preliminary";
      if (code == ObservationStatus.FINAL)
        return "final";
      if (code == ObservationStatus.AMENDED)
        return "amended";
      if (code == ObservationStatus.CORRECTED)
        return "corrected";
      if (code == ObservationStatus.CANCELLED)
        return "cancelled";
      if (code == ObservationStatus.ENTEREDINERROR)
        return "entered-in-error";
      if (code == ObservationStatus.UNKNOWN)
        return "unknown";
      return "?";
   }

    public String toSystem(ObservationStatus code) {
      return code.getSystem();
    }
  }

  @Block()
  public static class ObservationReferenceRangeComponent extends BackboneElement implements IBaseBackboneElement {
    /**
     * The value of the low bound of the reference range. The low bound of the
     * reference range endpoint is inclusive of the value (e.g. reference range is
     * >=5 - <=9). If the low bound is omitted, it is assumed to be meaningless
     * (e.g. reference range is <=2.3).
     */
    @Child(name = "low", type = { Quantity.class }, order = 1, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Low Range, if relevant", formalDefinition = "The value of the low bound of the reference range.  The low bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the low bound is omitted,  it is assumed to be meaningless (e.g. reference range is <=2.3).")
    protected Quantity low;

    /**
     * The value of the high bound of the reference range. The high bound of the
     * reference range endpoint is inclusive of the value (e.g. reference range is
     * >=5 - <=9). If the high bound is omitted, it is assumed to be meaningless
     * (e.g. reference range is >= 2.3).
     */
    @Child(name = "high", type = { Quantity.class }, order = 2, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "High Range, if relevant", formalDefinition = "The value of the high bound of the reference range.  The high bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the high bound is omitted,  it is assumed to be meaningless (e.g. reference range is >= 2.3).")
    protected Quantity high;

    /**
     * Codes to indicate the what part of the targeted reference population it
     * applies to. For example, the normal or therapeutic range.
     */
    @Child(name = "type", type = {
        CodeableConcept.class }, order = 3, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Reference range qualifier", formalDefinition = "Codes to indicate the what part of the targeted reference population it applies to. For example, the normal or therapeutic range.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/referencerange-meaning")
    protected CodeableConcept type;

    /**
     * Codes to indicate the target population this reference range applies to. For
     * example, a reference range may be based on the normal population or a
     * particular sex or race. Multiple `appliesTo` are interpreted as an "AND" of
     * the target populations. For example, to represent a target population of
     * African American females, both a code of female and a code for African
     * American would be used.
     */
    @Child(name = "appliesTo", type = {
        CodeableConcept.class }, order = 4, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
    @Description(shortDefinition = "Reference range population", formalDefinition = "Codes to indicate the target population this reference range applies to.  For example, a reference range may be based on the normal population or a particular sex or race.  Multiple `appliesTo`  are interpreted as an \"AND\" of the target populations.  For example, to represent a target population of African American females, both a code of female and a code for African American would be used.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/referencerange-appliesto")
    protected List<CodeableConcept> appliesTo;

    /**
     * The age at which this reference range is applicable. This is a neonatal age
     * (e.g. number of weeks at term) if the meaning says so.
     */
    @Child(name = "age", type = { Range.class }, order = 5, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Applicable age range, if relevant", formalDefinition = "The age at which this reference range is applicable. This is a neonatal age (e.g. number of weeks at term) if the meaning says so.")
    protected Range age;

    /**
     * Text based reference range in an observation which may be used when a
     * quantitative range is not appropriate for an observation. An example would be
     * a reference value of "Negative" or a list or table of "normals".
     */
    @Child(name = "text", type = { StringType.class }, order = 6, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Text based reference range in an observation", formalDefinition = "Text based reference range in an observation which may be used when a quantitative range is not appropriate for an observation.  An example would be a reference value of \"Negative\" or a list or table of \"normals\".")
    protected StringType text;

    private static final long serialVersionUID = -305128879L;

    /**
     * Constructor
     */
    public ObservationReferenceRangeComponent() {
      super();
    }

    /**
     * @return {@link #low} (The value of the low bound of the reference range. The
     *         low bound of the reference range endpoint is inclusive of the value
     *         (e.g. reference range is >=5 - <=9). If the low bound is omitted, it
     *         is assumed to be meaningless (e.g. reference range is <=2.3).)
     */
    public Quantity getLow() {
      if (this.low == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationReferenceRangeComponent.low");
        else if (Configuration.doAutoCreate())
          this.low = new Quantity(); // cc
      return this.low;
    }

    public boolean hasLow() {
      return this.low != null && !this.low.isEmpty();
    }

    /**
     * @param value {@link #low} (The value of the low bound of the reference range.
     *              The low bound of the reference range endpoint is inclusive of
     *              the value (e.g. reference range is >=5 - <=9). If the low bound
     *              is omitted, it is assumed to be meaningless (e.g. reference
     *              range is <=2.3).)
     */
    public ObservationReferenceRangeComponent setLow(Quantity value) {
      this.low = value;
      return this;
    }

    /**
     * @return {@link #high} (The value of the high bound of the reference range.
     *         The high bound of the reference range endpoint is inclusive of the
     *         value (e.g. reference range is >=5 - <=9). If the high bound is
     *         omitted, it is assumed to be meaningless (e.g. reference range is >=
     *         2.3).)
     */
    public Quantity getHigh() {
      if (this.high == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationReferenceRangeComponent.high");
        else if (Configuration.doAutoCreate())
          this.high = new Quantity(); // cc
      return this.high;
    }

    public boolean hasHigh() {
      return this.high != null && !this.high.isEmpty();
    }

    /**
     * @param value {@link #high} (The value of the high bound of the reference
     *              range. The high bound of the reference range endpoint is
     *              inclusive of the value (e.g. reference range is >=5 - <=9). If
     *              the high bound is omitted, it is assumed to be meaningless (e.g.
     *              reference range is >= 2.3).)
     */
    public ObservationReferenceRangeComponent setHigh(Quantity value) {
      this.high = value;
      return this;
    }

    /**
     * @return {@link #type} (Codes to indicate the what part of the targeted
     *         reference population it applies to. For example, the normal or
     *         therapeutic range.)
     */
    public CodeableConcept getType() {
      if (this.type == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationReferenceRangeComponent.type");
        else if (Configuration.doAutoCreate())
          this.type = new CodeableConcept(); // cc
      return this.type;
    }

    public boolean hasType() {
      return this.type != null && !this.type.isEmpty();
    }

    /**
     * @param value {@link #type} (Codes to indicate the what part of the targeted
     *              reference population it applies to. For example, the normal or
     *              therapeutic range.)
     */
    public ObservationReferenceRangeComponent setType(CodeableConcept value) {
      this.type = value;
      return this;
    }

    /**
     * @return {@link #appliesTo} (Codes to indicate the target population this
     *         reference range applies to. For example, a reference range may be
     *         based on the normal population or a particular sex or race. Multiple
     *         `appliesTo` are interpreted as an "AND" of the target populations.
     *         For example, to represent a target population of African American
     *         females, both a code of female and a code for African American would
     *         be used.)
     */
    public List<CodeableConcept> getAppliesTo() {
      if (this.appliesTo == null)
        this.appliesTo = new ArrayList<CodeableConcept>();
      return this.appliesTo;
    }

    /**
     * @return Returns a reference to <code>this</code> for easy method chaining
     */
    public ObservationReferenceRangeComponent setAppliesTo(List<CodeableConcept> theAppliesTo) {
      this.appliesTo = theAppliesTo;
      return this;
    }

    public boolean hasAppliesTo() {
      if (this.appliesTo == null)
        return false;
      for (CodeableConcept item : this.appliesTo)
        if (!item.isEmpty())
          return true;
      return false;
    }

    public CodeableConcept addAppliesTo() { // 3
      CodeableConcept t = new CodeableConcept();
      if (this.appliesTo == null)
        this.appliesTo = new ArrayList<CodeableConcept>();
      this.appliesTo.add(t);
      return t;
    }

    public ObservationReferenceRangeComponent addAppliesTo(CodeableConcept t) { // 3
      if (t == null)
        return this;
      if (this.appliesTo == null)
        this.appliesTo = new ArrayList<CodeableConcept>();
      this.appliesTo.add(t);
      return this;
    }

    /**
     * @return The first repetition of repeating field {@link #appliesTo}, creating
     *         it if it does not already exist
     */
    public CodeableConcept getAppliesToFirstRep() {
      if (getAppliesTo().isEmpty()) {
        addAppliesTo();
      }
      return getAppliesTo().get(0);
    }

    /**
     * @return {@link #age} (The age at which this reference range is applicable.
     *         This is a neonatal age (e.g. number of weeks at term) if the meaning
     *         says so.)
     */
    public Range getAge() {
      if (this.age == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationReferenceRangeComponent.age");
        else if (Configuration.doAutoCreate())
          this.age = new Range(); // cc
      return this.age;
    }

    public boolean hasAge() {
      return this.age != null && !this.age.isEmpty();
    }

    /**
     * @param value {@link #age} (The age at which this reference range is
     *              applicable. This is a neonatal age (e.g. number of weeks at
     *              term) if the meaning says so.)
     */
    public ObservationReferenceRangeComponent setAge(Range value) {
      this.age = value;
      return this;
    }

    /**
     * @return {@link #text} (Text based reference range in an observation which may
     *         be used when a quantitative range is not appropriate for an
     *         observation. An example would be a reference value of "Negative" or a
     *         list or table of "normals".). This is the underlying object with id,
     *         value and extensions. The accessor "getText" gives direct access to
     *         the value
     */
    public StringType getTextElement() {
      if (this.text == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationReferenceRangeComponent.text");
        else if (Configuration.doAutoCreate())
          this.text = new StringType(); // bb
      return this.text;
    }

    public boolean hasTextElement() {
      return this.text != null && !this.text.isEmpty();
    }

    public boolean hasText() {
      return this.text != null && !this.text.isEmpty();
    }

    /**
     * @param value {@link #text} (Text based reference range in an observation
     *              which may be used when a quantitative range is not appropriate
     *              for an observation. An example would be a reference value of
     *              "Negative" or a list or table of "normals".). This is the
     *              underlying object with id, value and extensions. The accessor
     *              "getText" gives direct access to the value
     */
    public ObservationReferenceRangeComponent setTextElement(StringType value) {
      this.text = value;
      return this;
    }

    /**
     * @return Text based reference range in an observation which may be used when a
     *         quantitative range is not appropriate for an observation. An example
     *         would be a reference value of "Negative" or a list or table of
     *         "normals".
     */
    public String getText() {
      return this.text == null ? null : this.text.getValue();
    }

    /**
     * @param value Text based reference range in an observation which may be used
     *              when a quantitative range is not appropriate for an observation.
     *              An example would be a reference value of "Negative" or a list or
     *              table of "normals".
     */
    public ObservationReferenceRangeComponent setText(String value) {
      if (Utilities.noString(value))
        this.text = null;
      else {
        if (this.text == null)
          this.text = new StringType();
        this.text.setValue(value);
      }
      return this;
    }

    protected void listChildren(List<Property> children) {
      super.listChildren(children);
      children.add(new Property("low", "SimpleQuantity",
          "The value of the low bound of the reference range.  The low bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the low bound is omitted,  it is assumed to be meaningless (e.g. reference range is <=2.3).",
          0, 1, low));
      children.add(new Property("high", "SimpleQuantity",
          "The value of the high bound of the reference range.  The high bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the high bound is omitted,  it is assumed to be meaningless (e.g. reference range is >= 2.3).",
          0, 1, high));
      children.add(new Property("type", "CodeableConcept",
          "Codes to indicate the what part of the targeted reference population it applies to. For example, the normal or therapeutic range.",
          0, 1, type));
      children.add(new Property("appliesTo", "CodeableConcept",
          "Codes to indicate the target population this reference range applies to.  For example, a reference range may be based on the normal population or a particular sex or race.  Multiple `appliesTo`  are interpreted as an \"AND\" of the target populations.  For example, to represent a target population of African American females, both a code of female and a code for African American would be used.",
          0, java.lang.Integer.MAX_VALUE, appliesTo));
      children.add(new Property("age", "Range",
          "The age at which this reference range is applicable. This is a neonatal age (e.g. number of weeks at term) if the meaning says so.",
          0, 1, age));
      children.add(new Property("text", "string",
          "Text based reference range in an observation which may be used when a quantitative range is not appropriate for an observation.  An example would be a reference value of \"Negative\" or a list or table of \"normals\".",
          0, 1, text));
    }

    @Override
    public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
      switch (_hash) {
      case 107348:
        /* low */ return new Property("low", "SimpleQuantity",
            "The value of the low bound of the reference range.  The low bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the low bound is omitted,  it is assumed to be meaningless (e.g. reference range is <=2.3).",
            0, 1, low);
      case 3202466:
        /* high */ return new Property("high", "SimpleQuantity",
            "The value of the high bound of the reference range.  The high bound of the reference range endpoint is inclusive of the value (e.g.  reference range is >=5 - <=9). If the high bound is omitted,  it is assumed to be meaningless (e.g. reference range is >= 2.3).",
            0, 1, high);
      case 3575610:
        /* type */ return new Property("type", "CodeableConcept",
            "Codes to indicate the what part of the targeted reference population it applies to. For example, the normal or therapeutic range.",
            0, 1, type);
      case -2089924569:
        /* appliesTo */ return new Property("appliesTo", "CodeableConcept",
            "Codes to indicate the target population this reference range applies to.  For example, a reference range may be based on the normal population or a particular sex or race.  Multiple `appliesTo`  are interpreted as an \"AND\" of the target populations.  For example, to represent a target population of African American females, both a code of female and a code for African American would be used.",
            0, java.lang.Integer.MAX_VALUE, appliesTo);
      case 96511:
        /* age */ return new Property("age", "Range",
            "The age at which this reference range is applicable. This is a neonatal age (e.g. number of weeks at term) if the meaning says so.",
            0, 1, age);
      case 3556653:
        /* text */ return new Property("text", "string",
            "Text based reference range in an observation which may be used when a quantitative range is not appropriate for an observation.  An example would be a reference value of \"Negative\" or a list or table of \"normals\".",
            0, 1, text);
      default:
        return super.getNamedProperty(_hash, _name, _checkValid);
      }

    }

    @Override
    public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
      switch (hash) {
      case 107348:
        /* low */ return this.low == null ? new Base[0] : new Base[] { this.low }; // Quantity
      case 3202466:
        /* high */ return this.high == null ? new Base[0] : new Base[] { this.high }; // Quantity
      case 3575610:
        /* type */ return this.type == null ? new Base[0] : new Base[] { this.type }; // CodeableConcept
      case -2089924569:
        /* appliesTo */ return this.appliesTo == null ? new Base[0]
            : this.appliesTo.toArray(new Base[this.appliesTo.size()]); // CodeableConcept
      case 96511:
        /* age */ return this.age == null ? new Base[0] : new Base[] { this.age }; // Range
      case 3556653:
        /* text */ return this.text == null ? new Base[0] : new Base[] { this.text }; // StringType
      default:
        return super.getProperty(hash, name, checkValid);
      }

    }

    @Override
    public Base setProperty(int hash, String name, Base value) throws FHIRException {
      switch (hash) {
      case 107348: // low
        this.low = castToQuantity(value); // Quantity
        return value;
      case 3202466: // high
        this.high = castToQuantity(value); // Quantity
        return value;
      case 3575610: // type
        this.type = castToCodeableConcept(value); // CodeableConcept
        return value;
      case -2089924569: // appliesTo
        this.getAppliesTo().add(castToCodeableConcept(value)); // CodeableConcept
        return value;
      case 96511: // age
        this.age = castToRange(value); // Range
        return value;
      case 3556653: // text
        this.text = castToString(value); // StringType
        return value;
      default:
        return super.setProperty(hash, name, value);
      }

    }

    @Override
    public Base setProperty(String name, Base value) throws FHIRException {
      if (name.equals("low")) {
        this.low = castToQuantity(value); // Quantity
      } else if (name.equals("high")) {
        this.high = castToQuantity(value); // Quantity
      } else if (name.equals("type")) {
        this.type = castToCodeableConcept(value); // CodeableConcept
      } else if (name.equals("appliesTo")) {
        this.getAppliesTo().add(castToCodeableConcept(value));
      } else if (name.equals("age")) {
        this.age = castToRange(value); // Range
      } else if (name.equals("text")) {
        this.text = castToString(value); // StringType
      } else
        return super.setProperty(name, value);
      return value;
    }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
      if (name.equals("low")) {
        this.low = null;
      } else if (name.equals("high")) {
        this.high = null;
      } else if (name.equals("type")) {
        this.type = null;
      } else if (name.equals("appliesTo")) {
        this.getAppliesTo().remove(castToCodeableConcept(value));
      } else if (name.equals("age")) {
        this.age = null;
      } else if (name.equals("text")) {
        this.text = null;
      } else
        super.removeChild(name, value);
      
    }

    @Override
    public Base makeProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 107348:
        return getLow();
      case 3202466:
        return getHigh();
      case 3575610:
        return getType();
      case -2089924569:
        return addAppliesTo();
      case 96511:
        return getAge();
      case 3556653:
        return getTextElement();
      default:
        return super.makeProperty(hash, name);
      }

    }

    @Override
    public String[] getTypesForProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 107348:
        /* low */ return new String[] { "SimpleQuantity" };
      case 3202466:
        /* high */ return new String[] { "SimpleQuantity" };
      case 3575610:
        /* type */ return new String[] { "CodeableConcept" };
      case -2089924569:
        /* appliesTo */ return new String[] { "CodeableConcept" };
      case 96511:
        /* age */ return new String[] { "Range" };
      case 3556653:
        /* text */ return new String[] { "string" };
      default:
        return super.getTypesForProperty(hash, name);
      }

    }

    @Override
    public Base addChild(String name) throws FHIRException {
      if (name.equals("low")) {
        this.low = new Quantity();
        return this.low;
      } else if (name.equals("high")) {
        this.high = new Quantity();
        return this.high;
      } else if (name.equals("type")) {
        this.type = new CodeableConcept();
        return this.type;
      } else if (name.equals("appliesTo")) {
        return addAppliesTo();
      } else if (name.equals("age")) {
        this.age = new Range();
        return this.age;
      } else if (name.equals("text")) {
        throw new FHIRException("Cannot call addChild on a singleton property Observation.text");
      } else
        return super.addChild(name);
    }

    public ObservationReferenceRangeComponent copy() {
      ObservationReferenceRangeComponent dst = new ObservationReferenceRangeComponent();
      copyValues(dst);
      return dst;
    }

    public void copyValues(ObservationReferenceRangeComponent dst) {
      super.copyValues(dst);
      dst.low = low == null ? null : low.copy();
      dst.high = high == null ? null : high.copy();
      dst.type = type == null ? null : type.copy();
      if (appliesTo != null) {
        dst.appliesTo = new ArrayList<CodeableConcept>();
        for (CodeableConcept i : appliesTo)
          dst.appliesTo.add(i.copy());
      }
      ;
      dst.age = age == null ? null : age.copy();
      dst.text = text == null ? null : text.copy();
    }

    @Override
    public boolean equalsDeep(Base other_) {
      if (!super.equalsDeep(other_))
        return false;
      if (!(other_ instanceof ObservationReferenceRangeComponent))
        return false;
      ObservationReferenceRangeComponent o = (ObservationReferenceRangeComponent) other_;
      return compareDeep(low, o.low, true) && compareDeep(high, o.high, true) && compareDeep(type, o.type, true)
          && compareDeep(appliesTo, o.appliesTo, true) && compareDeep(age, o.age, true)
          && compareDeep(text, o.text, true);
    }

    @Override
    public boolean equalsShallow(Base other_) {
      if (!super.equalsShallow(other_))
        return false;
      if (!(other_ instanceof ObservationReferenceRangeComponent))
        return false;
      ObservationReferenceRangeComponent o = (ObservationReferenceRangeComponent) other_;
      return compareValues(text, o.text, true);
    }

    public boolean isEmpty() {
      return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(low, high, type, appliesTo, age, text);
    }

    public String fhirType() {
      return "Observation.referenceRange";

    }

  }

  @Block()
  public static class ObservationComponentComponent extends BackboneElement implements IBaseBackboneElement {
    /**
     * Describes what was observed. Sometimes this is called the observation "code".
     */
    @Child(name = "code", type = {
        CodeableConcept.class }, order = 1, min = 1, max = 1, modifier = false, summary = true)
    @Description(shortDefinition = "Type of component observation (code / type)", formalDefinition = "Describes what was observed. Sometimes this is called the observation \"code\".")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-codes")
    protected CodeableConcept code;

    /**
     * The information determined as a result of making the observation, if the
     * information has a simple value.
     */
    @Child(name = "value", type = { Quantity.class, CodeableConcept.class, StringType.class, BooleanType.class,
        IntegerType.class, Range.class, Ratio.class, SampledData.class, TimeType.class, DateTimeType.class,
        Period.class }, order = 2, min = 0, max = 1, modifier = false, summary = true)
    @Description(shortDefinition = "Actual component result", formalDefinition = "The information determined as a result of making the observation, if the information has a simple value.")
    protected Type value;

    /**
     * Provides a reason why the expected value in the element
     * Observation.component.value[x] is missing.
     */
    @Child(name = "dataAbsentReason", type = {
        CodeableConcept.class }, order = 3, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Why the component result is missing", formalDefinition = "Provides a reason why the expected value in the element Observation.component.value[x] is missing.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/data-absent-reason")
    protected CodeableConcept dataAbsentReason;

    /**
     * A categorical assessment of an observation value. For example, high, low,
     * normal.
     */
    @Child(name = "interpretation", type = {
        CodeableConcept.class }, order = 4, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
    @Description(shortDefinition = "High, low, normal, etc.", formalDefinition = "A categorical assessment of an observation value.  For example, high, low, normal.")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-interpretation")
    protected List<CodeableConcept> interpretation;

    /**
     * Guidance on how to interpret the value by comparison to a normal or
     * recommended range.
     */
    @Child(name = "referenceRange", type = {
        ObservationReferenceRangeComponent.class }, order = 5, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
    @Description(shortDefinition = "Provides guide for interpretation of component result", formalDefinition = "Guidance on how to interpret the value by comparison to a normal or recommended range.")
    protected List<ObservationReferenceRangeComponent> referenceRange;

    private static final long serialVersionUID = 576590931L;

    /**
     * Constructor
     */
    public ObservationComponentComponent() {
      super();
    }

    /**
     * Constructor
     */
    public ObservationComponentComponent(CodeableConcept code) {
      super();
      this.code = code;
    }

    /**
     * @return {@link #code} (Describes what was observed. Sometimes this is called
     *         the observation "code".)
     */
    public CodeableConcept getCode() {
      if (this.code == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationComponentComponent.code");
        else if (Configuration.doAutoCreate())
          this.code = new CodeableConcept(); // cc
      return this.code;
    }

    public boolean hasCode() {
      return this.code != null && !this.code.isEmpty();
    }

    /**
     * @param value {@link #code} (Describes what was observed. Sometimes this is
     *              called the observation "code".)
     */
    public ObservationComponentComponent setCode(CodeableConcept value) {
      this.code = value;
      return this;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public Type getValue() {
      return this.value;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public Quantity getValueQuantity() throws FHIRException {
      if (this.value == null)
        this.value = new Quantity();
      if (!(this.value instanceof Quantity))
        throw new FHIRException("Type mismatch: the type Quantity was expected, but " + this.value.getClass().getName()
            + " was encountered");
      return (Quantity) this.value;
    }

    public boolean hasValueQuantity() {
        return this.value instanceof Quantity;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public CodeableConcept getValueCodeableConcept() throws FHIRException {
      if (this.value == null)
        this.value = new CodeableConcept();
      if (!(this.value instanceof CodeableConcept))
        throw new FHIRException("Type mismatch: the type CodeableConcept was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (CodeableConcept) this.value;
    }

    public boolean hasValueCodeableConcept() {
        return this.value instanceof CodeableConcept;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public StringType getValueStringType() throws FHIRException {
      if (this.value == null)
        this.value = new StringType();
      if (!(this.value instanceof StringType))
        throw new FHIRException("Type mismatch: the type StringType was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (StringType) this.value;
    }

    public boolean hasValueStringType() {
        return this.value instanceof StringType;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public BooleanType getValueBooleanType() throws FHIRException {
      if (this.value == null)
        this.value = new BooleanType();
      if (!(this.value instanceof BooleanType))
        throw new FHIRException("Type mismatch: the type BooleanType was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (BooleanType) this.value;
    }

    public boolean hasValueBooleanType() {
        return this.value instanceof BooleanType;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public IntegerType getValueIntegerType() throws FHIRException {
      if (this.value == null)
        this.value = new IntegerType();
      if (!(this.value instanceof IntegerType))
        throw new FHIRException("Type mismatch: the type IntegerType was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (IntegerType) this.value;
    }

    public boolean hasValueIntegerType() {
        return this.value instanceof IntegerType;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public Range getValueRange() throws FHIRException {
      if (this.value == null)
        this.value = new Range();
      if (!(this.value instanceof Range))
        throw new FHIRException(
            "Type mismatch: the type Range was expected, but " + this.value.getClass().getName() + " was encountered");
      return (Range) this.value;
    }

    public boolean hasValueRange() {
        return this.value instanceof Range;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public Ratio getValueRatio() throws FHIRException {
      if (this.value == null)
        this.value = new Ratio();
      if (!(this.value instanceof Ratio))
        throw new FHIRException(
            "Type mismatch: the type Ratio was expected, but " + this.value.getClass().getName() + " was encountered");
      return (Ratio) this.value;
    }

    public boolean hasValueRatio() {
        return this.value instanceof Ratio;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public SampledData getValueSampledData() throws FHIRException {
      if (this.value == null)
        this.value = new SampledData();
      if (!(this.value instanceof SampledData))
        throw new FHIRException("Type mismatch: the type SampledData was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (SampledData) this.value;
    }

    public boolean hasValueSampledData() {
        return this.value instanceof SampledData;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public TimeType getValueTimeType() throws FHIRException {
      if (this.value == null)
        this.value = new TimeType();
      if (!(this.value instanceof TimeType))
        throw new FHIRException("Type mismatch: the type TimeType was expected, but " + this.value.getClass().getName()
            + " was encountered");
      return (TimeType) this.value;
    }

    public boolean hasValueTimeType() {
        return this.value instanceof TimeType;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public DateTimeType getValueDateTimeType() throws FHIRException {
      if (this.value == null)
        this.value = new DateTimeType();
      if (!(this.value instanceof DateTimeType))
        throw new FHIRException("Type mismatch: the type DateTimeType was expected, but "
            + this.value.getClass().getName() + " was encountered");
      return (DateTimeType) this.value;
    }

    public boolean hasValueDateTimeType() {
        return this.value instanceof DateTimeType;
    }

    /**
     * @return {@link #value} (The information determined as a result of making the
     *         observation, if the information has a simple value.)
     */
    public Period getValuePeriod() throws FHIRException {
      if (this.value == null)
        this.value = new Period();
      if (!(this.value instanceof Period))
        throw new FHIRException(
            "Type mismatch: the type Period was expected, but " + this.value.getClass().getName() + " was encountered");
      return (Period) this.value;
    }

    public boolean hasValuePeriod() {
        return this.value instanceof Period;
    }

    public boolean hasValue() {
      return this.value != null && !this.value.isEmpty();
    }

    /**
     * @param value {@link #value} (The information determined as a result of making
     *              the observation, if the information has a simple value.)
     */
    public ObservationComponentComponent setValue(Type value) {
      if (value != null && !(value instanceof Quantity || value instanceof CodeableConcept
          || value instanceof StringType || value instanceof BooleanType || value instanceof IntegerType
          || value instanceof Range || value instanceof Ratio || value instanceof SampledData
          || value instanceof TimeType || value instanceof DateTimeType || value instanceof Period))
        throw new Error("Not the right type for Observation.component.value[x]: " + value.fhirType());
      this.value = value;
      return this;
    }

    /**
     * @return {@link #dataAbsentReason} (Provides a reason why the expected value
     *         in the element Observation.component.value[x] is missing.)
     */
    public CodeableConcept getDataAbsentReason() {
      if (this.dataAbsentReason == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create ObservationComponentComponent.dataAbsentReason");
        else if (Configuration.doAutoCreate())
          this.dataAbsentReason = new CodeableConcept(); // cc
      return this.dataAbsentReason;
    }

    public boolean hasDataAbsentReason() {
      return this.dataAbsentReason != null && !this.dataAbsentReason.isEmpty();
    }

    /**
     * @param value {@link #dataAbsentReason} (Provides a reason why the expected
     *              value in the element Observation.component.value[x] is missing.)
     */
    public ObservationComponentComponent setDataAbsentReason(CodeableConcept value) {
      this.dataAbsentReason = value;
      return this;
    }

    /**
     * @return {@link #interpretation} (A categorical assessment of an observation
     *         value. For example, high, low, normal.)
     */
    public List<CodeableConcept> getInterpretation() {
      if (this.interpretation == null)
        this.interpretation = new ArrayList<CodeableConcept>();
      return this.interpretation;
    }

    /**
     * @return Returns a reference to <code>this</code> for easy method chaining
     */
    public ObservationComponentComponent setInterpretation(List<CodeableConcept> theInterpretation) {
      this.interpretation = theInterpretation;
      return this;
    }

    public boolean hasInterpretation() {
      if (this.interpretation == null)
        return false;
      for (CodeableConcept item : this.interpretation)
        if (!item.isEmpty())
          return true;
      return false;
    }

    public CodeableConcept addInterpretation() { // 3
      CodeableConcept t = new CodeableConcept();
      if (this.interpretation == null)
        this.interpretation = new ArrayList<CodeableConcept>();
      this.interpretation.add(t);
      return t;
    }

    public ObservationComponentComponent addInterpretation(CodeableConcept t) { // 3
      if (t == null)
        return this;
      if (this.interpretation == null)
        this.interpretation = new ArrayList<CodeableConcept>();
      this.interpretation.add(t);
      return this;
    }

    /**
     * @return The first repetition of repeating field {@link #interpretation},
     *         creating it if it does not already exist
     */
    public CodeableConcept getInterpretationFirstRep() {
      if (getInterpretation().isEmpty()) {
        addInterpretation();
      }
      return getInterpretation().get(0);
    }

    /**
     * @return {@link #referenceRange} (Guidance on how to interpret the value by
     *         comparison to a normal or recommended range.)
     */
    public List<ObservationReferenceRangeComponent> getReferenceRange() {
      if (this.referenceRange == null)
        this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
      return this.referenceRange;
    }

    /**
     * @return Returns a reference to <code>this</code> for easy method chaining
     */
    public ObservationComponentComponent setReferenceRange(List<ObservationReferenceRangeComponent> theReferenceRange) {
      this.referenceRange = theReferenceRange;
      return this;
    }

    public boolean hasReferenceRange() {
      if (this.referenceRange == null)
        return false;
      for (ObservationReferenceRangeComponent item : this.referenceRange)
        if (!item.isEmpty())
          return true;
      return false;
    }

    public ObservationReferenceRangeComponent addReferenceRange() { // 3
      ObservationReferenceRangeComponent t = new ObservationReferenceRangeComponent();
      if (this.referenceRange == null)
        this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
      this.referenceRange.add(t);
      return t;
    }

    public ObservationComponentComponent addReferenceRange(ObservationReferenceRangeComponent t) { // 3
      if (t == null)
        return this;
      if (this.referenceRange == null)
        this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
      this.referenceRange.add(t);
      return this;
    }

    /**
     * @return The first repetition of repeating field {@link #referenceRange},
     *         creating it if it does not already exist
     */
    public ObservationReferenceRangeComponent getReferenceRangeFirstRep() {
      if (getReferenceRange().isEmpty()) {
        addReferenceRange();
      }
      return getReferenceRange().get(0);
    }

    protected void listChildren(List<Property> children) {
      super.listChildren(children);
      children.add(new Property("code", "CodeableConcept",
          "Describes what was observed. Sometimes this is called the observation \"code\".", 0, 1, code));
      children.add(new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value));
      children.add(new Property("dataAbsentReason", "CodeableConcept",
          "Provides a reason why the expected value in the element Observation.component.value[x] is missing.", 0, 1,
          dataAbsentReason));
      children.add(new Property("interpretation", "CodeableConcept",
          "A categorical assessment of an observation value.  For example, high, low, normal.", 0,
          java.lang.Integer.MAX_VALUE, interpretation));
      children.add(new Property("referenceRange", "@Observation.referenceRange",
          "Guidance on how to interpret the value by comparison to a normal or recommended range.", 0,
          java.lang.Integer.MAX_VALUE, referenceRange));
    }

    @Override
    public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
      switch (_hash) {
      case 3059181:
        /* code */ return new Property("code", "CodeableConcept",
            "Describes what was observed. Sometimes this is called the observation \"code\".", 0, 1, code);
      case -1410166417:
        /* value[x] */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 111972721:
        /* value */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -2029823716:
        /* valueQuantity */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 924902896:
        /* valueCodeableConcept */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -1424603934:
        /* valueString */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 733421943:
        /* valueBoolean */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -1668204915:
        /* valueInteger */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 2030761548:
        /* valueRange */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 2030767386:
        /* valueRatio */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -962229101:
        /* valueSampledData */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -765708322:
        /* valueTime */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 1047929900:
        /* valueDateTime */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case -1524344174:
        /* valuePeriod */ return new Property("value[x]",
            "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
            "The information determined as a result of making the observation, if the information has a simple value.",
            0, 1, value);
      case 1034315687:
        /* dataAbsentReason */ return new Property("dataAbsentReason", "CodeableConcept",
            "Provides a reason why the expected value in the element Observation.component.value[x] is missing.", 0, 1,
            dataAbsentReason);
      case -297950712:
        /* interpretation */ return new Property("interpretation", "CodeableConcept",
            "A categorical assessment of an observation value.  For example, high, low, normal.", 0,
            java.lang.Integer.MAX_VALUE, interpretation);
      case -1912545102:
        /* referenceRange */ return new Property("referenceRange", "@Observation.referenceRange",
            "Guidance on how to interpret the value by comparison to a normal or recommended range.", 0,
            java.lang.Integer.MAX_VALUE, referenceRange);
      default:
        return super.getNamedProperty(_hash, _name, _checkValid);
      }

    }

    @Override
    public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
      switch (hash) {
      case 3059181:
        /* code */ return this.code == null ? new Base[0] : new Base[] { this.code }; // CodeableConcept
      case 111972721:
        /* value */ return this.value == null ? new Base[0] : new Base[] { this.value }; // Type
      case 1034315687:
        /* dataAbsentReason */ return this.dataAbsentReason == null ? new Base[0]
            : new Base[] { this.dataAbsentReason }; // CodeableConcept
      case -297950712:
        /* interpretation */ return this.interpretation == null ? new Base[0]
            : this.interpretation.toArray(new Base[this.interpretation.size()]); // CodeableConcept
      case -1912545102:
        /* referenceRange */ return this.referenceRange == null ? new Base[0]
            : this.referenceRange.toArray(new Base[this.referenceRange.size()]); // ObservationReferenceRangeComponent
      default:
        return super.getProperty(hash, name, checkValid);
      }

    }

    @Override
    public Base setProperty(int hash, String name, Base value) throws FHIRException {
      switch (hash) {
      case 3059181: // code
        this.code = castToCodeableConcept(value); // CodeableConcept
        return value;
      case 111972721: // value
        this.value = castToType(value); // Type
        return value;
      case 1034315687: // dataAbsentReason
        this.dataAbsentReason = castToCodeableConcept(value); // CodeableConcept
        return value;
      case -297950712: // interpretation
        this.getInterpretation().add(castToCodeableConcept(value)); // CodeableConcept
        return value;
      case -1912545102: // referenceRange
        this.getReferenceRange().add((ObservationReferenceRangeComponent) value); // ObservationReferenceRangeComponent
        return value;
      default:
        return super.setProperty(hash, name, value);
      }

    }

    @Override
    public Base setProperty(String name, Base value) throws FHIRException {
      if (name.equals("code")) {
        this.code = castToCodeableConcept(value); // CodeableConcept
      } else if (name.equals("value[x]")) {
        this.value = castToType(value); // Type
      } else if (name.equals("dataAbsentReason")) {
        this.dataAbsentReason = castToCodeableConcept(value); // CodeableConcept
      } else if (name.equals("interpretation")) {
        this.getInterpretation().add(castToCodeableConcept(value));
      } else if (name.equals("referenceRange")) {
        this.getReferenceRange().add((ObservationReferenceRangeComponent) value);
      } else
        return super.setProperty(name, value);
      return value;
    }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
      if (name.equals("code")) {
        this.code = null;
      } else if (name.equals("value[x]")) {
        this.value = null;
      } else if (name.equals("dataAbsentReason")) {
        this.dataAbsentReason = null;
      } else if (name.equals("interpretation")) {
        this.getInterpretation().remove(castToCodeableConcept(value));
      } else if (name.equals("referenceRange")) {
        this.getReferenceRange().remove((ObservationReferenceRangeComponent) value);
      } else
        super.removeChild(name, value);
      
    }

    @Override
    public Base makeProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 3059181:
        return getCode();
      case -1410166417:
        return getValue();
      case 111972721:
        return getValue();
      case 1034315687:
        return getDataAbsentReason();
      case -297950712:
        return addInterpretation();
      case -1912545102:
        return addReferenceRange();
      default:
        return super.makeProperty(hash, name);
      }

    }

    @Override
    public String[] getTypesForProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case 3059181:
        /* code */ return new String[] { "CodeableConcept" };
      case 111972721:
        /* value */ return new String[] { "Quantity", "CodeableConcept", "string", "boolean", "integer", "Range",
            "Ratio", "SampledData", "time", "dateTime", "Period" };
      case 1034315687:
        /* dataAbsentReason */ return new String[] { "CodeableConcept" };
      case -297950712:
        /* interpretation */ return new String[] { "CodeableConcept" };
      case -1912545102:
        /* referenceRange */ return new String[] { "@Observation.referenceRange" };
      default:
        return super.getTypesForProperty(hash, name);
      }

    }

    @Override
    public Base addChild(String name) throws FHIRException {
      if (name.equals("code")) {
        this.code = new CodeableConcept();
        return this.code;
      } else if (name.equals("valueQuantity")) {
        this.value = new Quantity();
        return this.value;
      } else if (name.equals("valueCodeableConcept")) {
        this.value = new CodeableConcept();
        return this.value;
      } else if (name.equals("valueString")) {
        this.value = new StringType();
        return this.value;
      } else if (name.equals("valueBoolean")) {
        this.value = new BooleanType();
        return this.value;
      } else if (name.equals("valueInteger")) {
        this.value = new IntegerType();
        return this.value;
      } else if (name.equals("valueRange")) {
        this.value = new Range();
        return this.value;
      } else if (name.equals("valueRatio")) {
        this.value = new Ratio();
        return this.value;
      } else if (name.equals("valueSampledData")) {
        this.value = new SampledData();
        return this.value;
      } else if (name.equals("valueTime")) {
        this.value = new TimeType();
        return this.value;
      } else if (name.equals("valueDateTime")) {
        this.value = new DateTimeType();
        return this.value;
      } else if (name.equals("valuePeriod")) {
        this.value = new Period();
        return this.value;
      } else if (name.equals("dataAbsentReason")) {
        this.dataAbsentReason = new CodeableConcept();
        return this.dataAbsentReason;
      } else if (name.equals("interpretation")) {
        return addInterpretation();
      } else if (name.equals("referenceRange")) {
        return addReferenceRange();
      } else
        return super.addChild(name);
    }

    public ObservationComponentComponent copy() {
      ObservationComponentComponent dst = new ObservationComponentComponent();
      copyValues(dst);
      return dst;
    }

    public void copyValues(ObservationComponentComponent dst) {
      super.copyValues(dst);
      dst.code = code == null ? null : code.copy();
      dst.value = value == null ? null : value.copy();
      dst.dataAbsentReason = dataAbsentReason == null ? null : dataAbsentReason.copy();
      if (interpretation != null) {
        dst.interpretation = new ArrayList<CodeableConcept>();
        for (CodeableConcept i : interpretation)
          dst.interpretation.add(i.copy());
      }
      ;
      if (referenceRange != null) {
        dst.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
        for (ObservationReferenceRangeComponent i : referenceRange)
          dst.referenceRange.add(i.copy());
      }
      ;
    }

    @Override
    public boolean equalsDeep(Base other_) {
      if (!super.equalsDeep(other_))
        return false;
      if (!(other_ instanceof ObservationComponentComponent))
        return false;
      ObservationComponentComponent o = (ObservationComponentComponent) other_;
      return compareDeep(code, o.code, true) && compareDeep(value, o.value, true)
          && compareDeep(dataAbsentReason, o.dataAbsentReason, true)
          && compareDeep(interpretation, o.interpretation, true) && compareDeep(referenceRange, o.referenceRange, true);
    }

    @Override
    public boolean equalsShallow(Base other_) {
      if (!super.equalsShallow(other_))
        return false;
      if (!(other_ instanceof ObservationComponentComponent))
        return false;
      ObservationComponentComponent o = (ObservationComponentComponent) other_;
      return true;
    }

    public boolean isEmpty() {
      return super.isEmpty()
          && ca.uhn.fhir.util.ElementUtil.isEmpty(code, value, dataAbsentReason, interpretation, referenceRange);
    }

    public String fhirType() {
      return "Observation.component";

    }

  }

  /**
   * A unique identifier assigned to this observation.
   */
  @Child(name = "identifier", type = {
      Identifier.class }, order = 0, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Business Identifier for observation", formalDefinition = "A unique identifier assigned to this observation.")
  protected List<Identifier> identifier;

  /**
   * A plan, proposal or order that is fulfilled in whole or in part by this
   * event. For example, a MedicationRequest may require a patient to have
   * laboratory test performed before it is dispensed.
   */
  @Child(name = "basedOn", type = { CarePlan.class, DeviceRequest.class, ImmunizationRecommendation.class,
      MedicationRequest.class, NutritionOrder.class,
      ServiceRequest.class }, order = 1, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Fulfills plan, proposal or order", formalDefinition = "A plan, proposal or order that is fulfilled in whole or in part by this event.  For example, a MedicationRequest may require a patient to have laboratory test performed before  it is dispensed.")
  protected List<Reference> basedOn;
  /**
   * The actual objects that are the target of the reference (A plan, proposal or
   * order that is fulfilled in whole or in part by this event. For example, a
   * MedicationRequest may require a patient to have laboratory test performed
   * before it is dispensed.)
   */
  protected List<Resource> basedOnTarget;

  /**
   * A larger event of which this particular Observation is a component or step.
   * For example, an observation as part of a procedure.
   */
  @Child(name = "partOf", type = { MedicationAdministration.class, MedicationDispense.class, MedicationStatement.class,
      Procedure.class, Immunization.class,
      ImagingStudy.class }, order = 2, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Part of referenced event", formalDefinition = "A larger event of which this particular Observation is a component or step.  For example,  an observation as part of a procedure.")
  protected List<Reference> partOf;
  /**
   * The actual objects that are the target of the reference (A larger event of
   * which this particular Observation is a component or step. For example, an
   * observation as part of a procedure.)
   */
  protected List<Resource> partOfTarget;

  /**
   * The status of the result value.
   */
  @Child(name = "status", type = { CodeType.class }, order = 3, min = 1, max = 1, modifier = true, summary = true)
  @Description(shortDefinition = "registered | preliminary | final | amended +", formalDefinition = "The status of the result value.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-status")
  protected Enumeration<ObservationStatus> status;

  /**
   * A code that classifies the general type of observation being made.
   */
  @Child(name = "category", type = {
      CodeableConcept.class }, order = 4, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Classification of  type of observation", formalDefinition = "A code that classifies the general type of observation being made.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-category")
  protected List<CodeableConcept> category;

  /**
   * Describes what was observed. Sometimes this is called the observation "name".
   */
  @Child(name = "code", type = { CodeableConcept.class }, order = 5, min = 1, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Type of observation (code / type)", formalDefinition = "Describes what was observed. Sometimes this is called the observation \"name\".")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-codes")
  protected CodeableConcept code;

  /**
   * The patient, or group of patients, location, or device this observation is
   * about and into whose record the observation is placed. If the actual focus of
   * the observation is different from the subject (or a sample of, part, or
   * region of the subject), the `focus` element or the `code` itself specifies
   * the actual focus of the observation.
   */
  @Child(name = "subject", type = { Patient.class, Group.class, Device.class,
      Location.class }, order = 6, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Who and/or what the observation is about", formalDefinition = "The patient, or group of patients, location, or device this observation is about and into whose record the observation is placed. If the actual focus of the observation is different from the subject (or a sample of, part, or region of the subject), the `focus` element or the `code` itself specifies the actual focus of the observation.")
  protected Reference subject;

  /**
   * The actual object that is the target of the reference (The patient, or group
   * of patients, location, or device this observation is about and into whose
   * record the observation is placed. If the actual focus of the observation is
   * different from the subject (or a sample of, part, or region of the subject),
   * the `focus` element or the `code` itself specifies the actual focus of the
   * observation.)
   */
  protected Resource subjectTarget;

  /**
   * The actual focus of an observation when it is not the patient of record
   * representing something or someone associated with the patient such as a
   * spouse, parent, fetus, or donor. For example, fetus observations in a
   * mother's record. The focus of an observation could also be an existing
   * condition, an intervention, the subject's diet, another observation of the
   * subject, or a body structure such as tumor or implanted device. An example
   * use case would be using the Observation resource to capture whether the
   * mother is trained to change her child's tracheostomy tube. In this example,
   * the child is the patient of record and the mother is the focus.
   */
  @Child(name = "focus", type = {
      Reference.class }, order = 7, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "What the observation is about, when it is not about the subject of record", formalDefinition = "The actual focus of an observation when it is not the patient of record representing something or someone associated with the patient such as a spouse, parent, fetus, or donor. For example, fetus observations in a mother's record.  The focus of an observation could also be an existing condition,  an intervention, the subject's diet,  another observation of the subject,  or a body structure such as tumor or implanted device.   An example use case would be using the Observation resource to capture whether the mother is trained to change her child's tracheostomy tube. In this example, the child is the patient of record and the mother is the focus.")
  protected List<Reference> focus;
  /**
   * The actual objects that are the target of the reference (The actual focus of
   * an observation when it is not the patient of record representing something or
   * someone associated with the patient such as a spouse, parent, fetus, or
   * donor. For example, fetus observations in a mother's record. The focus of an
   * observation could also be an existing condition, an intervention, the
   * subject's diet, another observation of the subject, or a body structure such
   * as tumor or implanted device. An example use case would be using the
   * Observation resource to capture whether the mother is trained to change her
   * child's tracheostomy tube. In this example, the child is the patient of
   * record and the mother is the focus.)
   */
  protected List<Resource> focusTarget;

  /**
   * The healthcare event (e.g. a patient and healthcare provider interaction)
   * during which this observation is made.
   */
  @Child(name = "encounter", type = { Encounter.class }, order = 8, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Healthcare event during which this observation is made", formalDefinition = "The healthcare event  (e.g. a patient and healthcare provider interaction) during which this observation is made.")
  protected Reference encounter;

  /**
   * The actual object that is the target of the reference (The healthcare event
   * (e.g. a patient and healthcare provider interaction) during which this
   * observation is made.)
   */
  protected Encounter encounterTarget;

  /**
   * The time or time-period the observed value is asserted as being true. For
   * biological subjects - e.g. human patients - this is usually called the
   * "physiologically relevant time". This is usually either the time of the
   * procedure or of specimen collection, but very often the source of the
   * date/time is not known, only the date/time itself.
   */
  @Child(name = "effective", type = { DateTimeType.class, Period.class, Timing.class,
      InstantType.class }, order = 9, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Clinically relevant time/time-period for observation", formalDefinition = "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.")
  protected Type effective;

  /**
   * The date and time this version of the observation was made available to
   * providers, typically after the results have been reviewed and verified.
   */
  @Child(name = "issued", type = { InstantType.class }, order = 10, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Date/Time this version was made available", formalDefinition = "The date and time this version of the observation was made available to providers, typically after the results have been reviewed and verified.")
  protected InstantType issued;

  /**
   * Who was responsible for asserting the observed value as "true".
   */
  @Child(name = "performer", type = { Practitioner.class, PractitionerRole.class, Organization.class, CareTeam.class,
      Patient.class,
      RelatedPerson.class }, order = 11, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Who is responsible for the observation", formalDefinition = "Who was responsible for asserting the observed value as \"true\".")
  protected List<Reference> performer;
  /**
   * The actual objects that are the target of the reference (Who was responsible
   * for asserting the observed value as "true".)
   */
  protected List<Resource> performerTarget;

  /**
   * The information determined as a result of making the observation, if the
   * information has a simple value.
   */
  @Child(name = "value", type = { Quantity.class, CodeableConcept.class, StringType.class, BooleanType.class,
      IntegerType.class, Range.class, Ratio.class, SampledData.class, TimeType.class, DateTimeType.class,
      Period.class }, order = 12, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Actual result", formalDefinition = "The information determined as a result of making the observation, if the information has a simple value.")
  protected Type value;

  /**
   * Provides a reason why the expected value in the element Observation.value[x]
   * is missing.
   */
  @Child(name = "dataAbsentReason", type = {
      CodeableConcept.class }, order = 13, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Why the result is missing", formalDefinition = "Provides a reason why the expected value in the element Observation.value[x] is missing.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/data-absent-reason")
  protected CodeableConcept dataAbsentReason;

  /**
   * A categorical assessment of an observation value. For example, high, low,
   * normal.
   */
  @Child(name = "interpretation", type = {
      CodeableConcept.class }, order = 14, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "High, low, normal, etc.", formalDefinition = "A categorical assessment of an observation value.  For example, high, low, normal.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-interpretation")
  protected List<CodeableConcept> interpretation;

  /**
   * Comments about the observation or the results.
   */
  @Child(name = "note", type = {
      Annotation.class }, order = 15, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Comments about the observation", formalDefinition = "Comments about the observation or the results.")
  protected List<Annotation> note;

  /**
   * Indicates the site on the subject's body where the observation was made (i.e.
   * the target site).
   */
  @Child(name = "bodySite", type = {
      CodeableConcept.class }, order = 16, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Observed body part", formalDefinition = "Indicates the site on the subject's body where the observation was made (i.e. the target site).")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/body-site")
  protected CodeableConcept bodySite;

  /**
   * Indicates the mechanism used to perform the observation.
   */
  @Child(name = "method", type = {
      CodeableConcept.class }, order = 17, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "How it was done", formalDefinition = "Indicates the mechanism used to perform the observation.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-methods")
  protected CodeableConcept method;

  /**
   * The specimen that was used when this observation was made.
   */
  @Child(name = "specimen", type = { Specimen.class }, order = 18, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Specimen used for this observation", formalDefinition = "The specimen that was used when this observation was made.")
  protected Reference specimen;

  /**
   * The actual object that is the target of the reference (The specimen that was
   * used when this observation was made.)
   */
  protected Specimen specimenTarget;

  /**
   * The device used to generate the observation data.
   */
  @Child(name = "device", type = { Device.class,
      DeviceMetric.class }, order = 19, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "(Measurement) Device", formalDefinition = "The device used to generate the observation data.")
  protected Reference device;

  /**
   * The actual object that is the target of the reference (The device used to
   * generate the observation data.)
   */
  protected Resource deviceTarget;

  /**
   * Guidance on how to interpret the value by comparison to a normal or
   * recommended range. Multiple reference ranges are interpreted as an "OR". In
   * other words, to represent two distinct target populations, two
   * `referenceRange` elements would be used.
   */
  @Child(name = "referenceRange", type = {}, order = 20, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Provides guide for interpretation", formalDefinition = "Guidance on how to interpret the value by comparison to a normal or recommended range.  Multiple reference ranges are interpreted as an \"OR\".   In other words, to represent two distinct target populations, two `referenceRange` elements would be used.")
  protected List<ObservationReferenceRangeComponent> referenceRange;

  /**
   * This observation is a group observation (e.g. a battery, a panel of tests, a
   * set of vital sign measurements) that includes the target as a member of the
   * group.
   */
  @Child(name = "hasMember", type = { Observation.class, QuestionnaireResponse.class,
      MolecularSequence.class }, order = 21, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Related resource that belongs to the Observation group", formalDefinition = "This observation is a group observation (e.g. a battery, a panel of tests, a set of vital sign measurements) that includes the target as a member of the group.")
  protected List<Reference> hasMember;
  /**
   * The actual objects that are the target of the reference (This observation is
   * a group observation (e.g. a battery, a panel of tests, a set of vital sign
   * measurements) that includes the target as a member of the group.)
   */
  protected List<Resource> hasMemberTarget;

  /**
   * The target resource that represents a measurement from which this observation
   * value is derived. For example, a calculated anion gap or a fetal measurement
   * based on an ultrasound image.
   */
  @Child(name = "derivedFrom", type = { DocumentReference.class, ImagingStudy.class, Media.class,
      QuestionnaireResponse.class, Observation.class,
      MolecularSequence.class }, order = 22, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Related measurements the observation is made from", formalDefinition = "The target resource that represents a measurement from which this observation value is derived. For example, a calculated anion gap or a fetal measurement based on an ultrasound image.")
  protected List<Reference> derivedFrom;
  /**
   * The actual objects that are the target of the reference (The target resource
   * that represents a measurement from which this observation value is derived.
   * For example, a calculated anion gap or a fetal measurement based on an
   * ultrasound image.)
   */
  protected List<Resource> derivedFromTarget;

  /**
   * Some observations have multiple component observations. These component
   * observations are expressed as separate code value pairs that share the same
   * attributes. Examples include systolic and diastolic component observations
   * for blood pressure measurement and multiple component observations for
   * genetics observations.
   */
  @Child(name = "component", type = {}, order = 23, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Component results", formalDefinition = "Some observations have multiple component observations.  These component observations are expressed as separate code value pairs that share the same attributes.  Examples include systolic and diastolic component observations for blood pressure measurement and multiple component observations for genetics observations.")
  protected List<ObservationComponentComponent> component;

  private static final long serialVersionUID = -2036786355L;

  /**
   * Constructor
   */
  public Observation() {
    super();
  }

  /**
   * Constructor
   */
  public Observation(Enumeration<ObservationStatus> status, CodeableConcept code) {
    super();
    this.status = status;
    this.code = code;
  }

  /**
   * @return {@link #identifier} (A unique identifier assigned to this
   *         observation.)
   */
  public List<Identifier> getIdentifier() {
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    return this.identifier;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setIdentifier(List<Identifier> theIdentifier) {
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

  public Observation addIdentifier(Identifier t) { // 3
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
   * @return {@link #basedOn} (A plan, proposal or order that is fulfilled in
   *         whole or in part by this event. For example, a MedicationRequest may
   *         require a patient to have laboratory test performed before it is
   *         dispensed.)
   */
  public List<Reference> getBasedOn() {
    if (this.basedOn == null)
      this.basedOn = new ArrayList<Reference>();
    return this.basedOn;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setBasedOn(List<Reference> theBasedOn) {
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

  public Observation addBasedOn(Reference t) { // 3
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
   * @return {@link #partOf} (A larger event of which this particular Observation
   *         is a component or step. For example, an observation as part of a
   *         procedure.)
   */
  public List<Reference> getPartOf() {
    if (this.partOf == null)
      this.partOf = new ArrayList<Reference>();
    return this.partOf;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setPartOf(List<Reference> thePartOf) {
    this.partOf = thePartOf;
    return this;
  }

  public boolean hasPartOf() {
    if (this.partOf == null)
      return false;
    for (Reference item : this.partOf)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addPartOf() { // 3
    Reference t = new Reference();
    if (this.partOf == null)
      this.partOf = new ArrayList<Reference>();
    this.partOf.add(t);
    return t;
  }

  public Observation addPartOf(Reference t) { // 3
    if (t == null)
      return this;
    if (this.partOf == null)
      this.partOf = new ArrayList<Reference>();
    this.partOf.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #partOf}, creating it
   *         if it does not already exist
   */
  public Reference getPartOfFirstRep() {
    if (getPartOf().isEmpty()) {
      addPartOf();
    }
    return getPartOf().get(0);
  }

  /**
   * @return {@link #status} (The status of the result value.). This is the
   *         underlying object with id, value and extensions. The accessor
   *         "getStatus" gives direct access to the value
   */
  public Enumeration<ObservationStatus> getStatusElement() {
    if (this.status == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.status");
      else if (Configuration.doAutoCreate())
        this.status = new Enumeration<ObservationStatus>(new ObservationStatusEnumFactory()); // bb
    return this.status;
  }

  public boolean hasStatusElement() {
    return this.status != null && !this.status.isEmpty();
  }

  public boolean hasStatus() {
    return this.status != null && !this.status.isEmpty();
  }

  /**
   * @param value {@link #status} (The status of the result value.). This is the
   *              underlying object with id, value and extensions. The accessor
   *              "getStatus" gives direct access to the value
   */
  public Observation setStatusElement(Enumeration<ObservationStatus> value) {
    this.status = value;
    return this;
  }

  /**
   * @return The status of the result value.
   */
  public ObservationStatus getStatus() {
    return this.status == null ? null : this.status.getValue();
  }

  /**
   * @param value The status of the result value.
   */
  public Observation setStatus(ObservationStatus value) {
    if (this.status == null)
      this.status = new Enumeration<ObservationStatus>(new ObservationStatusEnumFactory());
    this.status.setValue(value);
    return this;
  }

  /**
   * @return {@link #category} (A code that classifies the general type of
   *         observation being made.)
   */
  public List<CodeableConcept> getCategory() {
    if (this.category == null)
      this.category = new ArrayList<CodeableConcept>();
    return this.category;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setCategory(List<CodeableConcept> theCategory) {
    this.category = theCategory;
    return this;
  }

  public boolean hasCategory() {
    if (this.category == null)
      return false;
    for (CodeableConcept item : this.category)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addCategory() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.category == null)
      this.category = new ArrayList<CodeableConcept>();
    this.category.add(t);
    return t;
  }

  public Observation addCategory(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.category == null)
      this.category = new ArrayList<CodeableConcept>();
    this.category.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #category}, creating
   *         it if it does not already exist
   */
  public CodeableConcept getCategoryFirstRep() {
    if (getCategory().isEmpty()) {
      addCategory();
    }
    return getCategory().get(0);
  }

  /**
   * @return {@link #code} (Describes what was observed. Sometimes this is called
   *         the observation "name".)
   */
  public CodeableConcept getCode() {
    if (this.code == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.code");
      else if (Configuration.doAutoCreate())
        this.code = new CodeableConcept(); // cc
    return this.code;
  }

  public boolean hasCode() {
    return this.code != null && !this.code.isEmpty();
  }

  /**
   * @param value {@link #code} (Describes what was observed. Sometimes this is
   *              called the observation "name".)
   */
  public Observation setCode(CodeableConcept value) {
    this.code = value;
    return this;
  }

  /**
   * @return {@link #subject} (The patient, or group of patients, location, or
   *         device this observation is about and into whose record the
   *         observation is placed. If the actual focus of the observation is
   *         different from the subject (or a sample of, part, or region of the
   *         subject), the `focus` element or the `code` itself specifies the
   *         actual focus of the observation.)
   */
  public Reference getSubject() {
    if (this.subject == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.subject");
      else if (Configuration.doAutoCreate())
        this.subject = new Reference(); // cc
    return this.subject;
  }

  public boolean hasSubject() {
    return this.subject != null && !this.subject.isEmpty();
  }

  /**
   * @param value {@link #subject} (The patient, or group of patients, location,
   *              or device this observation is about and into whose record the
   *              observation is placed. If the actual focus of the observation is
   *              different from the subject (or a sample of, part, or region of
   *              the subject), the `focus` element or the `code` itself specifies
   *              the actual focus of the observation.)
   */
  public Observation setSubject(Reference value) {
    this.subject = value;
    return this;
  }

  /**
   * @return {@link #subject} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The patient, or group
   *         of patients, location, or device this observation is about and into
   *         whose record the observation is placed. If the actual focus of the
   *         observation is different from the subject (or a sample of, part, or
   *         region of the subject), the `focus` element or the `code` itself
   *         specifies the actual focus of the observation.)
   */
  public Resource getSubjectTarget() {
    return this.subjectTarget;
  }

  /**
   * @param value {@link #subject} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The patient, or
   *              group of patients, location, or device this observation is about
   *              and into whose record the observation is placed. If the actual
   *              focus of the observation is different from the subject (or a
   *              sample of, part, or region of the subject), the `focus` element
   *              or the `code` itself specifies the actual focus of the
   *              observation.)
   */
  public Observation setSubjectTarget(Resource value) {
    this.subjectTarget = value;
    return this;
  }

  /**
   * @return {@link #focus} (The actual focus of an observation when it is not the
   *         patient of record representing something or someone associated with
   *         the patient such as a spouse, parent, fetus, or donor. For example,
   *         fetus observations in a mother's record. The focus of an observation
   *         could also be an existing condition, an intervention, the subject's
   *         diet, another observation of the subject, or a body structure such as
   *         tumor or implanted device. An example use case would be using the
   *         Observation resource to capture whether the mother is trained to
   *         change her child's tracheostomy tube. In this example, the child is
   *         the patient of record and the mother is the focus.)
   */
  public List<Reference> getFocus() {
    if (this.focus == null)
      this.focus = new ArrayList<Reference>();
    return this.focus;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setFocus(List<Reference> theFocus) {
    this.focus = theFocus;
    return this;
  }

  public boolean hasFocus() {
    if (this.focus == null)
      return false;
    for (Reference item : this.focus)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addFocus() { // 3
    Reference t = new Reference();
    if (this.focus == null)
      this.focus = new ArrayList<Reference>();
    this.focus.add(t);
    return t;
  }

  public Observation addFocus(Reference t) { // 3
    if (t == null)
      return this;
    if (this.focus == null)
      this.focus = new ArrayList<Reference>();
    this.focus.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #focus}, creating it
   *         if it does not already exist
   */
  public Reference getFocusFirstRep() {
    if (getFocus().isEmpty()) {
      addFocus();
    }
    return getFocus().get(0);
  }

  /**
   * @return {@link #encounter} (The healthcare event (e.g. a patient and
   *         healthcare provider interaction) during which this observation is
   *         made.)
   */
  public Reference getEncounter() {
    if (this.encounter == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.encounter");
      else if (Configuration.doAutoCreate())
        this.encounter = new Reference(); // cc
    return this.encounter;
  }

  public boolean hasEncounter() {
    return this.encounter != null && !this.encounter.isEmpty();
  }

  /**
   * @param value {@link #encounter} (The healthcare event (e.g. a patient and
   *              healthcare provider interaction) during which this observation
   *              is made.)
   */
  public Observation setEncounter(Reference value) {
    this.encounter = value;
    return this;
  }

  /**
   * @return {@link #encounter} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The healthcare event
   *         (e.g. a patient and healthcare provider interaction) during which
   *         this observation is made.)
   */
  public Encounter getEncounterTarget() {
    if (this.encounterTarget == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.encounter");
      else if (Configuration.doAutoCreate())
        this.encounterTarget = new Encounter(); // aa
    return this.encounterTarget;
  }

  /**
   * @param value {@link #encounter} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The healthcare
   *              event (e.g. a patient and healthcare provider interaction)
   *              during which this observation is made.)
   */
  public Observation setEncounterTarget(Encounter value) {
    this.encounterTarget = value;
    return this;
  }

  /**
   * @return {@link #effective} (The time or time-period the observed value is
   *         asserted as being true. For biological subjects - e.g. human patients
   *         - this is usually called the "physiologically relevant time". This is
   *         usually either the time of the procedure or of specimen collection,
   *         but very often the source of the date/time is not known, only the
   *         date/time itself.)
   */
  public Type getEffective() {
    return this.effective;
  }

  /**
   * @return {@link #effective} (The time or time-period the observed value is
   *         asserted as being true. For biological subjects - e.g. human patients
   *         - this is usually called the "physiologically relevant time". This is
   *         usually either the time of the procedure or of specimen collection,
   *         but very often the source of the date/time is not known, only the
   *         date/time itself.)
   */
  public DateTimeType getEffectiveDateTimeType() throws FHIRException {
    if (this.effective == null)
      this.effective = new DateTimeType();
    if (!(this.effective instanceof DateTimeType))
      throw new FHIRException("Type mismatch: the type DateTimeType was expected, but "
          + this.effective.getClass().getName() + " was encountered");
    return (DateTimeType) this.effective;
  }

  public boolean hasEffectiveDateTimeType() {
      return this.effective instanceof DateTimeType;
  }

  /**
   * @return {@link #effective} (The time or time-period the observed value is
   *         asserted as being true. For biological subjects - e.g. human patients
   *         - this is usually called the "physiologically relevant time". This is
   *         usually either the time of the procedure or of specimen collection,
   *         but very often the source of the date/time is not known, only the
   *         date/time itself.)
   */
  public Period getEffectivePeriod() throws FHIRException {
    if (this.effective == null)
      this.effective = new Period();
    if (!(this.effective instanceof Period))
      throw new FHIRException("Type mismatch: the type Period was expected, but " + this.effective.getClass().getName()
          + " was encountered");
    return (Period) this.effective;
  }

  public boolean hasEffectivePeriod() {
      return this.effective instanceof Period;
  }

  /**
   * @return {@link #effective} (The time or time-period the observed value is
   *         asserted as being true. For biological subjects - e.g. human patients
   *         - this is usually called the "physiologically relevant time". This is
   *         usually either the time of the procedure or of specimen collection,
   *         but very often the source of the date/time is not known, only the
   *         date/time itself.)
   */
  public Timing getEffectiveTiming() throws FHIRException {
    if (this.effective == null)
      this.effective = new Timing();
    if (!(this.effective instanceof Timing))
      throw new FHIRException("Type mismatch: the type Timing was expected, but " + this.effective.getClass().getName()
          + " was encountered");
    return (Timing) this.effective;
  }

  public boolean hasEffectiveTiming() {
      return this.effective instanceof Timing;
  }

  /**
   * @return {@link #effective} (The time or time-period the observed value is
   *         asserted as being true. For biological subjects - e.g. human patients
   *         - this is usually called the "physiologically relevant time". This is
   *         usually either the time of the procedure or of specimen collection,
   *         but very often the source of the date/time is not known, only the
   *         date/time itself.)
   */
  public InstantType getEffectiveInstantType() throws FHIRException {
    if (this.effective == null)
      this.effective = new InstantType();
    if (!(this.effective instanceof InstantType))
      throw new FHIRException("Type mismatch: the type InstantType was expected, but "
          + this.effective.getClass().getName() + " was encountered");
    return (InstantType) this.effective;
  }

  public boolean hasEffectiveInstantType() {
      return this.effective instanceof InstantType;
  }

  public boolean hasEffective() {
    return this.effective != null && !this.effective.isEmpty();
  }

  /**
   * @param value {@link #effective} (The time or time-period the observed value
   *              is asserted as being true. For biological subjects - e.g. human
   *              patients - this is usually called the "physiologically relevant
   *              time". This is usually either the time of the procedure or of
   *              specimen collection, but very often the source of the date/time
   *              is not known, only the date/time itself.)
   */
  public Observation setEffective(Type value) {
    if (value != null && !(value instanceof DateTimeType || value instanceof Period || value instanceof Timing
        || value instanceof InstantType))
      throw new Error("Not the right type for Observation.effective[x]: " + value.fhirType());
    this.effective = value;
    return this;
  }

  /**
   * @return {@link #issued} (The date and time this version of the observation
   *         was made available to providers, typically after the results have
   *         been reviewed and verified.). This is the underlying object with id,
   *         value and extensions. The accessor "getIssued" gives direct access to
   *         the value
   */
  public InstantType getIssuedElement() {
    if (this.issued == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.issued");
      else if (Configuration.doAutoCreate())
        this.issued = new InstantType(); // bb
    return this.issued;
  }

  public boolean hasIssuedElement() {
    return this.issued != null && !this.issued.isEmpty();
  }

  public boolean hasIssued() {
    return this.issued != null && !this.issued.isEmpty();
  }

  /**
   * @param value {@link #issued} (The date and time this version of the
   *              observation was made available to providers, typically after the
   *              results have been reviewed and verified.). This is the
   *              underlying object with id, value and extensions. The accessor
   *              "getIssued" gives direct access to the value
   */
  public Observation setIssuedElement(InstantType value) {
    this.issued = value;
    return this;
  }

  /**
   * @return The date and time this version of the observation was made available
   *         to providers, typically after the results have been reviewed and
   *         verified.
   */
  public Date getIssued() {
    return this.issued == null ? null : this.issued.getValue();
  }

  /**
   * @param value The date and time this version of the observation was made
   *              available to providers, typically after the results have been
   *              reviewed and verified.
   */
  public Observation setIssued(Date value) {
    if (value == null)
      this.issued = null;
    else {
      if (this.issued == null)
        this.issued = new InstantType();
      this.issued.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #performer} (Who was responsible for asserting the observed
   *         value as "true".)
   */
  public List<Reference> getPerformer() {
    if (this.performer == null)
      this.performer = new ArrayList<Reference>();
    return this.performer;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setPerformer(List<Reference> thePerformer) {
    this.performer = thePerformer;
    return this;
  }

  public boolean hasPerformer() {
    if (this.performer == null)
      return false;
    for (Reference item : this.performer)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addPerformer() { // 3
    Reference t = new Reference();
    if (this.performer == null)
      this.performer = new ArrayList<Reference>();
    this.performer.add(t);
    return t;
  }

  public Observation addPerformer(Reference t) { // 3
    if (t == null)
      return this;
    if (this.performer == null)
      this.performer = new ArrayList<Reference>();
    this.performer.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #performer}, creating
   *         it if it does not already exist
   */
  public Reference getPerformerFirstRep() {
    if (getPerformer().isEmpty()) {
      addPerformer();
    }
    return getPerformer().get(0);
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public Type getValue() {
    return this.value;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public Quantity getValueQuantity() throws FHIRException {
    if (this.value == null)
      this.value = new Quantity();
    if (!(this.value instanceof Quantity))
      throw new FHIRException(
          "Type mismatch: the type Quantity was expected, but " + this.value.getClass().getName() + " was encountered");
    return (Quantity) this.value;
  }

  public boolean hasValueQuantity() {
      return this.value instanceof Quantity;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public CodeableConcept getValueCodeableConcept() throws FHIRException {
    if (this.value == null)
      this.value = new CodeableConcept();
    if (!(this.value instanceof CodeableConcept))
      throw new FHIRException("Type mismatch: the type CodeableConcept was expected, but "
          + this.value.getClass().getName() + " was encountered");
    return (CodeableConcept) this.value;
  }

  public boolean hasValueCodeableConcept() {
      return this.value instanceof CodeableConcept;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public StringType getValueStringType() throws FHIRException {
    if (this.value == null)
      this.value = new StringType();
    if (!(this.value instanceof StringType))
      throw new FHIRException("Type mismatch: the type StringType was expected, but " + this.value.getClass().getName()
          + " was encountered");
    return (StringType) this.value;
  }

  public boolean hasValueStringType() {
      return this.value instanceof StringType;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public BooleanType getValueBooleanType() throws FHIRException {
    if (this.value == null)
      this.value = new BooleanType();
    if (!(this.value instanceof BooleanType))
      throw new FHIRException("Type mismatch: the type BooleanType was expected, but " + this.value.getClass().getName()
          + " was encountered");
    return (BooleanType) this.value;
  }

  public boolean hasValueBooleanType() {
      return this.value instanceof BooleanType;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public IntegerType getValueIntegerType() throws FHIRException {
    if (this.value == null)
      this.value = new IntegerType();
    if (!(this.value instanceof IntegerType))
      throw new FHIRException("Type mismatch: the type IntegerType was expected, but " + this.value.getClass().getName()
          + " was encountered");
    return (IntegerType) this.value;
  }

  public boolean hasValueIntegerType() {
      return this.value instanceof IntegerType;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public Range getValueRange() throws FHIRException {
    if (this.value == null)
      this.value = new Range();
    if (!(this.value instanceof Range))
      throw new FHIRException(
          "Type mismatch: the type Range was expected, but " + this.value.getClass().getName() + " was encountered");
    return (Range) this.value;
  }

  public boolean hasValueRange() {
      return this.value instanceof Range;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public Ratio getValueRatio() throws FHIRException {
    if (this.value == null)
      this.value = new Ratio();
    if (!(this.value instanceof Ratio))
      throw new FHIRException(
          "Type mismatch: the type Ratio was expected, but " + this.value.getClass().getName() + " was encountered");
    return (Ratio) this.value;
  }

  public boolean hasValueRatio() {
      return this.value instanceof Ratio;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public SampledData getValueSampledData() throws FHIRException {
    if (this.value == null)
      this.value = new SampledData();
    if (!(this.value instanceof SampledData))
      throw new FHIRException("Type mismatch: the type SampledData was expected, but " + this.value.getClass().getName()
          + " was encountered");
    return (SampledData) this.value;
  }

  public boolean hasValueSampledData() {
      return this.value instanceof SampledData;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public TimeType getValueTimeType() throws FHIRException {
    if (this.value == null)
      this.value = new TimeType();
    if (!(this.value instanceof TimeType))
      throw new FHIRException(
          "Type mismatch: the type TimeType was expected, but " + this.value.getClass().getName() + " was encountered");
    return (TimeType) this.value;
  }

  public boolean hasValueTimeType() {
      return this.value instanceof TimeType;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public DateTimeType getValueDateTimeType() throws FHIRException {
    if (this.value == null)
      this.value = new DateTimeType();
    if (!(this.value instanceof DateTimeType))
      throw new FHIRException("Type mismatch: the type DateTimeType was expected, but "
          + this.value.getClass().getName() + " was encountered");
    return (DateTimeType) this.value;
  }

  public boolean hasValueDateTimeType() {
      return this.value instanceof DateTimeType;
  }

  /**
   * @return {@link #value} (The information determined as a result of making the
   *         observation, if the information has a simple value.)
   */
  public Period getValuePeriod() throws FHIRException {
    if (this.value == null)
      this.value = new Period();
    if (!(this.value instanceof Period))
      throw new FHIRException(
          "Type mismatch: the type Period was expected, but " + this.value.getClass().getName() + " was encountered");
    return (Period) this.value;
  }

  public boolean hasValuePeriod() {
      return this.value instanceof Period;
  }

  public boolean hasValue() {
    return this.value != null && !this.value.isEmpty();
  }

  /**
   * @param value {@link #value} (The information determined as a result of making
   *              the observation, if the information has a simple value.)
   */
  public Observation setValue(Type value) {
    if (value != null && !(value instanceof Quantity || value instanceof CodeableConcept || value instanceof StringType
        || value instanceof BooleanType || value instanceof IntegerType || value instanceof Range
        || value instanceof Ratio || value instanceof SampledData || value instanceof TimeType
        || value instanceof DateTimeType || value instanceof Period))
      throw new Error("Not the right type for Observation.value[x]: " + value.fhirType());
    this.value = value;
    return this;
  }

  /**
   * @return {@link #dataAbsentReason} (Provides a reason why the expected value
   *         in the element Observation.value[x] is missing.)
   */
  public CodeableConcept getDataAbsentReason() {
    if (this.dataAbsentReason == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.dataAbsentReason");
      else if (Configuration.doAutoCreate())
        this.dataAbsentReason = new CodeableConcept(); // cc
    return this.dataAbsentReason;
  }

  public boolean hasDataAbsentReason() {
    return this.dataAbsentReason != null && !this.dataAbsentReason.isEmpty();
  }

  /**
   * @param value {@link #dataAbsentReason} (Provides a reason why the expected
   *              value in the element Observation.value[x] is missing.)
   */
  public Observation setDataAbsentReason(CodeableConcept value) {
    this.dataAbsentReason = value;
    return this;
  }

  /**
   * @return {@link #interpretation} (A categorical assessment of an observation
   *         value. For example, high, low, normal.)
   */
  public List<CodeableConcept> getInterpretation() {
    if (this.interpretation == null)
      this.interpretation = new ArrayList<CodeableConcept>();
    return this.interpretation;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setInterpretation(List<CodeableConcept> theInterpretation) {
    this.interpretation = theInterpretation;
    return this;
  }

  public boolean hasInterpretation() {
    if (this.interpretation == null)
      return false;
    for (CodeableConcept item : this.interpretation)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public CodeableConcept addInterpretation() { // 3
    CodeableConcept t = new CodeableConcept();
    if (this.interpretation == null)
      this.interpretation = new ArrayList<CodeableConcept>();
    this.interpretation.add(t);
    return t;
  }

  public Observation addInterpretation(CodeableConcept t) { // 3
    if (t == null)
      return this;
    if (this.interpretation == null)
      this.interpretation = new ArrayList<CodeableConcept>();
    this.interpretation.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #interpretation},
   *         creating it if it does not already exist
   */
  public CodeableConcept getInterpretationFirstRep() {
    if (getInterpretation().isEmpty()) {
      addInterpretation();
    }
    return getInterpretation().get(0);
  }

  /**
   * @return {@link #note} (Comments about the observation or the results.)
   */
  public List<Annotation> getNote() {
    if (this.note == null)
      this.note = new ArrayList<Annotation>();
    return this.note;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setNote(List<Annotation> theNote) {
    this.note = theNote;
    return this;
  }

  public boolean hasNote() {
    if (this.note == null)
      return false;
    for (Annotation item : this.note)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Annotation addNote() { // 3
    Annotation t = new Annotation();
    if (this.note == null)
      this.note = new ArrayList<Annotation>();
    this.note.add(t);
    return t;
  }

  public Observation addNote(Annotation t) { // 3
    if (t == null)
      return this;
    if (this.note == null)
      this.note = new ArrayList<Annotation>();
    this.note.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #note}, creating it if
   *         it does not already exist
   */
  public Annotation getNoteFirstRep() {
    if (getNote().isEmpty()) {
      addNote();
    }
    return getNote().get(0);
  }

  /**
   * @return {@link #bodySite} (Indicates the site on the subject's body where the
   *         observation was made (i.e. the target site).)
   */
  public CodeableConcept getBodySite() {
    if (this.bodySite == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.bodySite");
      else if (Configuration.doAutoCreate())
        this.bodySite = new CodeableConcept(); // cc
    return this.bodySite;
  }

  public boolean hasBodySite() {
    return this.bodySite != null && !this.bodySite.isEmpty();
  }

  /**
   * @param value {@link #bodySite} (Indicates the site on the subject's body
   *              where the observation was made (i.e. the target site).)
   */
  public Observation setBodySite(CodeableConcept value) {
    this.bodySite = value;
    return this;
  }

  /**
   * @return {@link #method} (Indicates the mechanism used to perform the
   *         observation.)
   */
  public CodeableConcept getMethod() {
    if (this.method == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.method");
      else if (Configuration.doAutoCreate())
        this.method = new CodeableConcept(); // cc
    return this.method;
  }

  public boolean hasMethod() {
    return this.method != null && !this.method.isEmpty();
  }

  /**
   * @param value {@link #method} (Indicates the mechanism used to perform the
   *              observation.)
   */
  public Observation setMethod(CodeableConcept value) {
    this.method = value;
    return this;
  }

  /**
   * @return {@link #specimen} (The specimen that was used when this observation
   *         was made.)
   */
  public Reference getSpecimen() {
    if (this.specimen == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.specimen");
      else if (Configuration.doAutoCreate())
        this.specimen = new Reference(); // cc
    return this.specimen;
  }

  public boolean hasSpecimen() {
    return this.specimen != null && !this.specimen.isEmpty();
  }

  /**
   * @param value {@link #specimen} (The specimen that was used when this
   *              observation was made.)
   */
  public Observation setSpecimen(Reference value) {
    this.specimen = value;
    return this;
  }

  /**
   * @return {@link #specimen} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The specimen that was
   *         used when this observation was made.)
   */
  public Specimen getSpecimenTarget() {
    if (this.specimenTarget == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.specimen");
      else if (Configuration.doAutoCreate())
        this.specimenTarget = new Specimen(); // aa
    return this.specimenTarget;
  }

  /**
   * @param value {@link #specimen} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The specimen
   *              that was used when this observation was made.)
   */
  public Observation setSpecimenTarget(Specimen value) {
    this.specimenTarget = value;
    return this;
  }

  /**
   * @return {@link #device} (The device used to generate the observation data.)
   */
  public Reference getDevice() {
    if (this.device == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create Observation.device");
      else if (Configuration.doAutoCreate())
        this.device = new Reference(); // cc
    return this.device;
  }

  public boolean hasDevice() {
    return this.device != null && !this.device.isEmpty();
  }

  /**
   * @param value {@link #device} (The device used to generate the observation
   *              data.)
   */
  public Observation setDevice(Reference value) {
    this.device = value;
    return this;
  }

  /**
   * @return {@link #device} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The device used to
   *         generate the observation data.)
   */
  public Resource getDeviceTarget() {
    return this.deviceTarget;
  }

  /**
   * @param value {@link #device} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The device used
   *              to generate the observation data.)
   */
  public Observation setDeviceTarget(Resource value) {
    this.deviceTarget = value;
    return this;
  }

  /**
   * @return {@link #referenceRange} (Guidance on how to interpret the value by
   *         comparison to a normal or recommended range. Multiple reference
   *         ranges are interpreted as an "OR". In other words, to represent two
   *         distinct target populations, two `referenceRange` elements would be
   *         used.)
   */
  public List<ObservationReferenceRangeComponent> getReferenceRange() {
    if (this.referenceRange == null)
      this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
    return this.referenceRange;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setReferenceRange(List<ObservationReferenceRangeComponent> theReferenceRange) {
    this.referenceRange = theReferenceRange;
    return this;
  }

  public boolean hasReferenceRange() {
    if (this.referenceRange == null)
      return false;
    for (ObservationReferenceRangeComponent item : this.referenceRange)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public ObservationReferenceRangeComponent addReferenceRange() { // 3
    ObservationReferenceRangeComponent t = new ObservationReferenceRangeComponent();
    if (this.referenceRange == null)
      this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
    this.referenceRange.add(t);
    return t;
  }

  public Observation addReferenceRange(ObservationReferenceRangeComponent t) { // 3
    if (t == null)
      return this;
    if (this.referenceRange == null)
      this.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
    this.referenceRange.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #referenceRange},
   *         creating it if it does not already exist
   */
  public ObservationReferenceRangeComponent getReferenceRangeFirstRep() {
    if (getReferenceRange().isEmpty()) {
      addReferenceRange();
    }
    return getReferenceRange().get(0);
  }

  /**
   * @return {@link #hasMember} (This observation is a group observation (e.g. a
   *         battery, a panel of tests, a set of vital sign measurements) that
   *         includes the target as a member of the group.)
   */
  public List<Reference> getHasMember() {
    if (this.hasMember == null)
      this.hasMember = new ArrayList<Reference>();
    return this.hasMember;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setHasMember(List<Reference> theHasMember) {
    this.hasMember = theHasMember;
    return this;
  }

  public boolean hasHasMember() {
    if (this.hasMember == null)
      return false;
    for (Reference item : this.hasMember)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addHasMember() { // 3
    Reference t = new Reference();
    if (this.hasMember == null)
      this.hasMember = new ArrayList<Reference>();
    this.hasMember.add(t);
    return t;
  }

  public Observation addHasMember(Reference t) { // 3
    if (t == null)
      return this;
    if (this.hasMember == null)
      this.hasMember = new ArrayList<Reference>();
    this.hasMember.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #hasMember}, creating
   *         it if it does not already exist
   */
  public Reference getHasMemberFirstRep() {
    if (getHasMember().isEmpty()) {
      addHasMember();
    }
    return getHasMember().get(0);
  }

  /**
   * @return {@link #derivedFrom} (The target resource that represents a
   *         measurement from which this observation value is derived. For
   *         example, a calculated anion gap or a fetal measurement based on an
   *         ultrasound image.)
   */
  public List<Reference> getDerivedFrom() {
    if (this.derivedFrom == null)
      this.derivedFrom = new ArrayList<Reference>();
    return this.derivedFrom;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setDerivedFrom(List<Reference> theDerivedFrom) {
    this.derivedFrom = theDerivedFrom;
    return this;
  }

  public boolean hasDerivedFrom() {
    if (this.derivedFrom == null)
      return false;
    for (Reference item : this.derivedFrom)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addDerivedFrom() { // 3
    Reference t = new Reference();
    if (this.derivedFrom == null)
      this.derivedFrom = new ArrayList<Reference>();
    this.derivedFrom.add(t);
    return t;
  }

  public Observation addDerivedFrom(Reference t) { // 3
    if (t == null)
      return this;
    if (this.derivedFrom == null)
      this.derivedFrom = new ArrayList<Reference>();
    this.derivedFrom.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #derivedFrom},
   *         creating it if it does not already exist
   */
  public Reference getDerivedFromFirstRep() {
    if (getDerivedFrom().isEmpty()) {
      addDerivedFrom();
    }
    return getDerivedFrom().get(0);
  }

  /**
   * @return {@link #component} (Some observations have multiple component
   *         observations. These component observations are expressed as separate
   *         code value pairs that share the same attributes. Examples include
   *         systolic and diastolic component observations for blood pressure
   *         measurement and multiple component observations for genetics
   *         observations.)
   */
  public List<ObservationComponentComponent> getComponent() {
    if (this.component == null)
      this.component = new ArrayList<ObservationComponentComponent>();
    return this.component;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public Observation setComponent(List<ObservationComponentComponent> theComponent) {
    this.component = theComponent;
    return this;
  }

  public boolean hasComponent() {
    if (this.component == null)
      return false;
    for (ObservationComponentComponent item : this.component)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public ObservationComponentComponent addComponent() { // 3
    ObservationComponentComponent t = new ObservationComponentComponent();
    if (this.component == null)
      this.component = new ArrayList<ObservationComponentComponent>();
    this.component.add(t);
    return t;
  }

  public Observation addComponent(ObservationComponentComponent t) { // 3
    if (t == null)
      return this;
    if (this.component == null)
      this.component = new ArrayList<ObservationComponentComponent>();
    this.component.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #component}, creating
   *         it if it does not already exist
   */
  public ObservationComponentComponent getComponentFirstRep() {
    if (getComponent().isEmpty()) {
      addComponent();
    }
    return getComponent().get(0);
  }

  protected void listChildren(List<Property> children) {
    super.listChildren(children);
    children.add(new Property("identifier", "Identifier", "A unique identifier assigned to this observation.", 0,
        java.lang.Integer.MAX_VALUE, identifier));
    children.add(new Property("basedOn",
        "Reference(CarePlan|DeviceRequest|ImmunizationRecommendation|MedicationRequest|NutritionOrder|ServiceRequest)",
        "A plan, proposal or order that is fulfilled in whole or in part by this event.  For example, a MedicationRequest may require a patient to have laboratory test performed before  it is dispensed.",
        0, java.lang.Integer.MAX_VALUE, basedOn));
    children.add(new Property("partOf",
        "Reference(MedicationAdministration|MedicationDispense|MedicationStatement|Procedure|Immunization|ImagingStudy)",
        "A larger event of which this particular Observation is a component or step.  For example,  an observation as part of a procedure.",
        0, java.lang.Integer.MAX_VALUE, partOf));
    children.add(new Property("status", "code", "The status of the result value.", 0, 1, status));
    children.add(new Property("category", "CodeableConcept",
        "A code that classifies the general type of observation being made.", 0, java.lang.Integer.MAX_VALUE,
        category));
    children.add(new Property("code", "CodeableConcept",
        "Describes what was observed. Sometimes this is called the observation \"name\".", 0, 1, code));
    children.add(new Property("subject", "Reference(Patient|Group|Device|Location)",
        "The patient, or group of patients, location, or device this observation is about and into whose record the observation is placed. If the actual focus of the observation is different from the subject (or a sample of, part, or region of the subject), the `focus` element or the `code` itself specifies the actual focus of the observation.",
        0, 1, subject));
    children.add(new Property("focus", "Reference(Any)",
        "The actual focus of an observation when it is not the patient of record representing something or someone associated with the patient such as a spouse, parent, fetus, or donor. For example, fetus observations in a mother's record.  The focus of an observation could also be an existing condition,  an intervention, the subject's diet,  another observation of the subject,  or a body structure such as tumor or implanted device.   An example use case would be using the Observation resource to capture whether the mother is trained to change her child's tracheostomy tube. In this example, the child is the patient of record and the mother is the focus.",
        0, java.lang.Integer.MAX_VALUE, focus));
    children.add(new Property("encounter", "Reference(Encounter)",
        "The healthcare event  (e.g. a patient and healthcare provider interaction) during which this observation is made.",
        0, 1, encounter));
    children.add(new Property("effective[x]", "dateTime|Period|Timing|instant",
        "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
        0, 1, effective));
    children.add(new Property("issued", "instant",
        "The date and time this version of the observation was made available to providers, typically after the results have been reviewed and verified.",
        0, 1, issued));
    children.add(new Property("performer",
        "Reference(Practitioner|PractitionerRole|Organization|CareTeam|Patient|RelatedPerson)",
        "Who was responsible for asserting the observed value as \"true\".", 0, java.lang.Integer.MAX_VALUE,
        performer));
    children.add(new Property("value[x]",
        "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
        "The information determined as a result of making the observation, if the information has a simple value.", 0,
        1, value));
    children.add(new Property("dataAbsentReason", "CodeableConcept",
        "Provides a reason why the expected value in the element Observation.value[x] is missing.", 0, 1,
        dataAbsentReason));
    children.add(new Property("interpretation", "CodeableConcept",
        "A categorical assessment of an observation value.  For example, high, low, normal.", 0,
        java.lang.Integer.MAX_VALUE, interpretation));
    children.add(new Property("note", "Annotation", "Comments about the observation or the results.", 0,
        java.lang.Integer.MAX_VALUE, note));
    children.add(new Property("bodySite", "CodeableConcept",
        "Indicates the site on the subject's body where the observation was made (i.e. the target site).", 0, 1,
        bodySite));
    children.add(new Property("method", "CodeableConcept", "Indicates the mechanism used to perform the observation.",
        0, 1, method));
    children.add(new Property("specimen", "Reference(Specimen)",
        "The specimen that was used when this observation was made.", 0, 1, specimen));
    children.add(new Property("device", "Reference(Device|DeviceMetric)",
        "The device used to generate the observation data.", 0, 1, device));
    children.add(new Property("referenceRange", "",
        "Guidance on how to interpret the value by comparison to a normal or recommended range.  Multiple reference ranges are interpreted as an \"OR\".   In other words, to represent two distinct target populations, two `referenceRange` elements would be used.",
        0, java.lang.Integer.MAX_VALUE, referenceRange));
    children.add(new Property("hasMember", "Reference(Observation|QuestionnaireResponse|MolecularSequence)",
        "This observation is a group observation (e.g. a battery, a panel of tests, a set of vital sign measurements) that includes the target as a member of the group.",
        0, java.lang.Integer.MAX_VALUE, hasMember));
    children.add(new Property("derivedFrom",
        "Reference(DocumentReference|ImagingStudy|Media|QuestionnaireResponse|Observation|MolecularSequence)",
        "The target resource that represents a measurement from which this observation value is derived. For example, a calculated anion gap or a fetal measurement based on an ultrasound image.",
        0, java.lang.Integer.MAX_VALUE, derivedFrom));
    children.add(new Property("component", "",
        "Some observations have multiple component observations.  These component observations are expressed as separate code value pairs that share the same attributes.  Examples include systolic and diastolic component observations for blood pressure measurement and multiple component observations for genetics observations.",
        0, java.lang.Integer.MAX_VALUE, component));
  }

  @Override
  public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
    switch (_hash) {
    case -1618432855:
      /* identifier */ return new Property("identifier", "Identifier",
          "A unique identifier assigned to this observation.", 0, java.lang.Integer.MAX_VALUE, identifier);
    case -332612366:
      /* basedOn */ return new Property("basedOn",
          "Reference(CarePlan|DeviceRequest|ImmunizationRecommendation|MedicationRequest|NutritionOrder|ServiceRequest)",
          "A plan, proposal or order that is fulfilled in whole or in part by this event.  For example, a MedicationRequest may require a patient to have laboratory test performed before  it is dispensed.",
          0, java.lang.Integer.MAX_VALUE, basedOn);
    case -995410646:
      /* partOf */ return new Property("partOf",
          "Reference(MedicationAdministration|MedicationDispense|MedicationStatement|Procedure|Immunization|ImagingStudy)",
          "A larger event of which this particular Observation is a component or step.  For example,  an observation as part of a procedure.",
          0, java.lang.Integer.MAX_VALUE, partOf);
    case -892481550:
      /* status */ return new Property("status", "code", "The status of the result value.", 0, 1, status);
    case 50511102:
      /* category */ return new Property("category", "CodeableConcept",
          "A code that classifies the general type of observation being made.", 0, java.lang.Integer.MAX_VALUE,
          category);
    case 3059181:
      /* code */ return new Property("code", "CodeableConcept",
          "Describes what was observed. Sometimes this is called the observation \"name\".", 0, 1, code);
    case -1867885268:
      /* subject */ return new Property("subject", "Reference(Patient|Group|Device|Location)",
          "The patient, or group of patients, location, or device this observation is about and into whose record the observation is placed. If the actual focus of the observation is different from the subject (or a sample of, part, or region of the subject), the `focus` element or the `code` itself specifies the actual focus of the observation.",
          0, 1, subject);
    case 97604824:
      /* focus */ return new Property("focus", "Reference(Any)",
          "The actual focus of an observation when it is not the patient of record representing something or someone associated with the patient such as a spouse, parent, fetus, or donor. For example, fetus observations in a mother's record.  The focus of an observation could also be an existing condition,  an intervention, the subject's diet,  another observation of the subject,  or a body structure such as tumor or implanted device.   An example use case would be using the Observation resource to capture whether the mother is trained to change her child's tracheostomy tube. In this example, the child is the patient of record and the mother is the focus.",
          0, java.lang.Integer.MAX_VALUE, focus);
    case 1524132147:
      /* encounter */ return new Property("encounter", "Reference(Encounter)",
          "The healthcare event  (e.g. a patient and healthcare provider interaction) during which this observation is made.",
          0, 1, encounter);
    case 247104889:
      /* effective[x] */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -1468651097:
      /* effective */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -275306910:
      /* effectiveDateTime */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -403934648:
      /* effectivePeriod */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -285872943:
      /* effectiveTiming */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -1295730118:
      /* effectiveInstant */ return new Property("effective[x]", "dateTime|Period|Timing|instant",
          "The time or time-period the observed value is asserted as being true. For biological subjects - e.g. human patients - this is usually called the \"physiologically relevant time\". This is usually either the time of the procedure or of specimen collection, but very often the source of the date/time is not known, only the date/time itself.",
          0, 1, effective);
    case -1179159893:
      /* issued */ return new Property("issued", "instant",
          "The date and time this version of the observation was made available to providers, typically after the results have been reviewed and verified.",
          0, 1, issued);
    case 481140686:
      /* performer */ return new Property("performer",
          "Reference(Practitioner|PractitionerRole|Organization|CareTeam|Patient|RelatedPerson)",
          "Who was responsible for asserting the observed value as \"true\".", 0, java.lang.Integer.MAX_VALUE,
          performer);
    case -1410166417:
      /* value[x] */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 111972721:
      /* value */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -2029823716:
      /* valueQuantity */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 924902896:
      /* valueCodeableConcept */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -1424603934:
      /* valueString */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 733421943:
      /* valueBoolean */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -1668204915:
      /* valueInteger */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 2030761548:
      /* valueRange */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 2030767386:
      /* valueRatio */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -962229101:
      /* valueSampledData */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -765708322:
      /* valueTime */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 1047929900:
      /* valueDateTime */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case -1524344174:
      /* valuePeriod */ return new Property("value[x]",
          "Quantity|CodeableConcept|string|boolean|integer|Range|Ratio|SampledData|time|dateTime|Period",
          "The information determined as a result of making the observation, if the information has a simple value.", 0,
          1, value);
    case 1034315687:
      /* dataAbsentReason */ return new Property("dataAbsentReason", "CodeableConcept",
          "Provides a reason why the expected value in the element Observation.value[x] is missing.", 0, 1,
          dataAbsentReason);
    case -297950712:
      /* interpretation */ return new Property("interpretation", "CodeableConcept",
          "A categorical assessment of an observation value.  For example, high, low, normal.", 0,
          java.lang.Integer.MAX_VALUE, interpretation);
    case 3387378:
      /* note */ return new Property("note", "Annotation", "Comments about the observation or the results.", 0,
          java.lang.Integer.MAX_VALUE, note);
    case 1702620169:
      /* bodySite */ return new Property("bodySite", "CodeableConcept",
          "Indicates the site on the subject's body where the observation was made (i.e. the target site).", 0, 1,
          bodySite);
    case -1077554975:
      /* method */ return new Property("method", "CodeableConcept",
          "Indicates the mechanism used to perform the observation.", 0, 1, method);
    case -2132868344:
      /* specimen */ return new Property("specimen", "Reference(Specimen)",
          "The specimen that was used when this observation was made.", 0, 1, specimen);
    case -1335157162:
      /* device */ return new Property("device", "Reference(Device|DeviceMetric)",
          "The device used to generate the observation data.", 0, 1, device);
    case -1912545102:
      /* referenceRange */ return new Property("referenceRange", "",
          "Guidance on how to interpret the value by comparison to a normal or recommended range.  Multiple reference ranges are interpreted as an \"OR\".   In other words, to represent two distinct target populations, two `referenceRange` elements would be used.",
          0, java.lang.Integer.MAX_VALUE, referenceRange);
    case -458019372:
      /* hasMember */ return new Property("hasMember", "Reference(Observation|QuestionnaireResponse|MolecularSequence)",
          "This observation is a group observation (e.g. a battery, a panel of tests, a set of vital sign measurements) that includes the target as a member of the group.",
          0, java.lang.Integer.MAX_VALUE, hasMember);
    case 1077922663:
      /* derivedFrom */ return new Property("derivedFrom",
          "Reference(DocumentReference|ImagingStudy|Media|QuestionnaireResponse|Observation|MolecularSequence)",
          "The target resource that represents a measurement from which this observation value is derived. For example, a calculated anion gap or a fetal measurement based on an ultrasound image.",
          0, java.lang.Integer.MAX_VALUE, derivedFrom);
    case -1399907075:
      /* component */ return new Property("component", "",
          "Some observations have multiple component observations.  These component observations are expressed as separate code value pairs that share the same attributes.  Examples include systolic and diastolic component observations for blood pressure measurement and multiple component observations for genetics observations.",
          0, java.lang.Integer.MAX_VALUE, component);
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
    case -332612366:
      /* basedOn */ return this.basedOn == null ? new Base[0] : this.basedOn.toArray(new Base[this.basedOn.size()]); // Reference
    case -995410646:
      /* partOf */ return this.partOf == null ? new Base[0] : this.partOf.toArray(new Base[this.partOf.size()]); // Reference
    case -892481550:
      /* status */ return this.status == null ? new Base[0] : new Base[] { this.status }; // Enumeration<ObservationStatus>
    case 50511102:
      /* category */ return this.category == null ? new Base[0] : this.category.toArray(new Base[this.category.size()]); // CodeableConcept
    case 3059181:
      /* code */ return this.code == null ? new Base[0] : new Base[] { this.code }; // CodeableConcept
    case -1867885268:
      /* subject */ return this.subject == null ? new Base[0] : new Base[] { this.subject }; // Reference
    case 97604824:
      /* focus */ return this.focus == null ? new Base[0] : this.focus.toArray(new Base[this.focus.size()]); // Reference
    case 1524132147:
      /* encounter */ return this.encounter == null ? new Base[0] : new Base[] { this.encounter }; // Reference
    case -1468651097:
      /* effective */ return this.effective == null ? new Base[0] : new Base[] { this.effective }; // Type
    case -1179159893:
      /* issued */ return this.issued == null ? new Base[0] : new Base[] { this.issued }; // InstantType
    case 481140686:
      /* performer */ return this.performer == null ? new Base[0]
          : this.performer.toArray(new Base[this.performer.size()]); // Reference
    case 111972721:
      /* value */ return this.value == null ? new Base[0] : new Base[] { this.value }; // Type
    case 1034315687:
      /* dataAbsentReason */ return this.dataAbsentReason == null ? new Base[0] : new Base[] { this.dataAbsentReason }; // CodeableConcept
    case -297950712:
      /* interpretation */ return this.interpretation == null ? new Base[0]
          : this.interpretation.toArray(new Base[this.interpretation.size()]); // CodeableConcept
    case 3387378:
      /* note */ return this.note == null ? new Base[0] : this.note.toArray(new Base[this.note.size()]); // Annotation
    case 1702620169:
      /* bodySite */ return this.bodySite == null ? new Base[0] : new Base[] { this.bodySite }; // CodeableConcept
    case -1077554975:
      /* method */ return this.method == null ? new Base[0] : new Base[] { this.method }; // CodeableConcept
    case -2132868344:
      /* specimen */ return this.specimen == null ? new Base[0] : new Base[] { this.specimen }; // Reference
    case -1335157162:
      /* device */ return this.device == null ? new Base[0] : new Base[] { this.device }; // Reference
    case -1912545102:
      /* referenceRange */ return this.referenceRange == null ? new Base[0]
          : this.referenceRange.toArray(new Base[this.referenceRange.size()]); // ObservationReferenceRangeComponent
    case -458019372:
      /* hasMember */ return this.hasMember == null ? new Base[0]
          : this.hasMember.toArray(new Base[this.hasMember.size()]); // Reference
    case 1077922663:
      /* derivedFrom */ return this.derivedFrom == null ? new Base[0]
          : this.derivedFrom.toArray(new Base[this.derivedFrom.size()]); // Reference
    case -1399907075:
      /* component */ return this.component == null ? new Base[0]
          : this.component.toArray(new Base[this.component.size()]); // ObservationComponentComponent
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
    case -332612366: // basedOn
      this.getBasedOn().add(castToReference(value)); // Reference
      return value;
    case -995410646: // partOf
      this.getPartOf().add(castToReference(value)); // Reference
      return value;
    case -892481550: // status
      value = new ObservationStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<ObservationStatus>
      return value;
    case 50511102: // category
      this.getCategory().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case 3059181: // code
      this.code = castToCodeableConcept(value); // CodeableConcept
      return value;
    case -1867885268: // subject
      this.subject = castToReference(value); // Reference
      return value;
    case 97604824: // focus
      this.getFocus().add(castToReference(value)); // Reference
      return value;
    case 1524132147: // encounter
      this.encounter = castToReference(value); // Reference
      return value;
    case -1468651097: // effective
      this.effective = castToType(value); // Type
      return value;
    case -1179159893: // issued
      this.issued = castToInstant(value); // InstantType
      return value;
    case 481140686: // performer
      this.getPerformer().add(castToReference(value)); // Reference
      return value;
    case 111972721: // value
      this.value = castToType(value); // Type
      return value;
    case 1034315687: // dataAbsentReason
      this.dataAbsentReason = castToCodeableConcept(value); // CodeableConcept
      return value;
    case -297950712: // interpretation
      this.getInterpretation().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case 3387378: // note
      this.getNote().add(castToAnnotation(value)); // Annotation
      return value;
    case 1702620169: // bodySite
      this.bodySite = castToCodeableConcept(value); // CodeableConcept
      return value;
    case -1077554975: // method
      this.method = castToCodeableConcept(value); // CodeableConcept
      return value;
    case -2132868344: // specimen
      this.specimen = castToReference(value); // Reference
      return value;
    case -1335157162: // device
      this.device = castToReference(value); // Reference
      return value;
    case -1912545102: // referenceRange
      this.getReferenceRange().add((ObservationReferenceRangeComponent) value); // ObservationReferenceRangeComponent
      return value;
    case -458019372: // hasMember
      this.getHasMember().add(castToReference(value)); // Reference
      return value;
    case 1077922663: // derivedFrom
      this.getDerivedFrom().add(castToReference(value)); // Reference
      return value;
    case -1399907075: // component
      this.getComponent().add((ObservationComponentComponent) value); // ObservationComponentComponent
      return value;
    default:
      return super.setProperty(hash, name, value);
    }

  }

  @Override
  public Base setProperty(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().add(castToIdentifier(value));
    } else if (name.equals("basedOn")) {
      this.getBasedOn().add(castToReference(value));
    } else if (name.equals("partOf")) {
      this.getPartOf().add(castToReference(value));
    } else if (name.equals("status")) {
      value = new ObservationStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<ObservationStatus>
    } else if (name.equals("category")) {
      this.getCategory().add(castToCodeableConcept(value));
    } else if (name.equals("code")) {
      this.code = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("subject")) {
      this.subject = castToReference(value); // Reference
    } else if (name.equals("focus")) {
      this.getFocus().add(castToReference(value));
    } else if (name.equals("encounter")) {
      this.encounter = castToReference(value); // Reference
    } else if (name.equals("effective[x]")) {
      this.effective = castToType(value); // Type
    } else if (name.equals("issued")) {
      this.issued = castToInstant(value); // InstantType
    } else if (name.equals("performer")) {
      this.getPerformer().add(castToReference(value));
    } else if (name.equals("value[x]")) {
      this.value = castToType(value); // Type
    } else if (name.equals("dataAbsentReason")) {
      this.dataAbsentReason = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("interpretation")) {
      this.getInterpretation().add(castToCodeableConcept(value));
    } else if (name.equals("note")) {
      this.getNote().add(castToAnnotation(value));
    } else if (name.equals("bodySite")) {
      this.bodySite = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("method")) {
      this.method = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("specimen")) {
      this.specimen = castToReference(value); // Reference
    } else if (name.equals("device")) {
      this.device = castToReference(value); // Reference
    } else if (name.equals("referenceRange")) {
      this.getReferenceRange().add((ObservationReferenceRangeComponent) value);
    } else if (name.equals("hasMember")) {
      this.getHasMember().add(castToReference(value));
    } else if (name.equals("derivedFrom")) {
      this.getDerivedFrom().add(castToReference(value));
    } else if (name.equals("component")) {
      this.getComponent().add((ObservationComponentComponent) value);
    } else
      return super.setProperty(name, value);
    return value;
  }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().remove(castToIdentifier(value));
    } else if (name.equals("basedOn")) {
      this.getBasedOn().remove(castToReference(value));
    } else if (name.equals("partOf")) {
      this.getPartOf().remove(castToReference(value));
    } else if (name.equals("status")) {
      this.status = null;
    } else if (name.equals("category")) {
      this.getCategory().remove(castToCodeableConcept(value));
    } else if (name.equals("code")) {
      this.code = null;
    } else if (name.equals("subject")) {
      this.subject = null;
    } else if (name.equals("focus")) {
      this.getFocus().remove(castToReference(value));
    } else if (name.equals("encounter")) {
      this.encounter = null;
    } else if (name.equals("effective[x]")) {
      this.effective = null;
    } else if (name.equals("issued")) {
      this.issued = null;
    } else if (name.equals("performer")) {
      this.getPerformer().remove(castToReference(value));
    } else if (name.equals("value[x]")) {
      this.value = null;
    } else if (name.equals("dataAbsentReason")) {
      this.dataAbsentReason = null;
    } else if (name.equals("interpretation")) {
      this.getInterpretation().remove(castToCodeableConcept(value));
    } else if (name.equals("note")) {
      this.getNote().remove(castToAnnotation(value));
    } else if (name.equals("bodySite")) {
      this.bodySite = null;
    } else if (name.equals("method")) {
      this.method = null;
    } else if (name.equals("specimen")) {
      this.specimen = null;
    } else if (name.equals("device")) {
      this.device = null;
    } else if (name.equals("referenceRange")) {
      this.getReferenceRange().remove((ObservationReferenceRangeComponent) value);
    } else if (name.equals("hasMember")) {
      this.getHasMember().remove(castToReference(value));
    } else if (name.equals("derivedFrom")) {
      this.getDerivedFrom().remove(castToReference(value));
    } else if (name.equals("component")) {
      this.getComponent().remove((ObservationComponentComponent) value);
    } else
      super.removeChild(name, value);
    
  }

  @Override
  public Base makeProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      return addIdentifier();
    case -332612366:
      return addBasedOn();
    case -995410646:
      return addPartOf();
    case -892481550:
      return getStatusElement();
    case 50511102:
      return addCategory();
    case 3059181:
      return getCode();
    case -1867885268:
      return getSubject();
    case 97604824:
      return addFocus();
    case 1524132147:
      return getEncounter();
    case 247104889:
      return getEffective();
    case -1468651097:
      return getEffective();
    case -1179159893:
      return getIssuedElement();
    case 481140686:
      return addPerformer();
    case -1410166417:
      return getValue();
    case 111972721:
      return getValue();
    case 1034315687:
      return getDataAbsentReason();
    case -297950712:
      return addInterpretation();
    case 3387378:
      return addNote();
    case 1702620169:
      return getBodySite();
    case -1077554975:
      return getMethod();
    case -2132868344:
      return getSpecimen();
    case -1335157162:
      return getDevice();
    case -1912545102:
      return addReferenceRange();
    case -458019372:
      return addHasMember();
    case 1077922663:
      return addDerivedFrom();
    case -1399907075:
      return addComponent();
    default:
      return super.makeProperty(hash, name);
    }

  }

  @Override
  public String[] getTypesForProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      /* identifier */ return new String[] { "Identifier" };
    case -332612366:
      /* basedOn */ return new String[] { "Reference" };
    case -995410646:
      /* partOf */ return new String[] { "Reference" };
    case -892481550:
      /* status */ return new String[] { "code" };
    case 50511102:
      /* category */ return new String[] { "CodeableConcept" };
    case 3059181:
      /* code */ return new String[] { "CodeableConcept" };
    case -1867885268:
      /* subject */ return new String[] { "Reference" };
    case 97604824:
      /* focus */ return new String[] { "Reference" };
    case 1524132147:
      /* encounter */ return new String[] { "Reference" };
    case -1468651097:
      /* effective */ return new String[] { "dateTime", "Period", "Timing", "instant" };
    case -1179159893:
      /* issued */ return new String[] { "instant" };
    case 481140686:
      /* performer */ return new String[] { "Reference" };
    case 111972721:
      /* value */ return new String[] { "Quantity", "CodeableConcept", "string", "boolean", "integer", "Range", "Ratio",
          "SampledData", "time", "dateTime", "Period" };
    case 1034315687:
      /* dataAbsentReason */ return new String[] { "CodeableConcept" };
    case -297950712:
      /* interpretation */ return new String[] { "CodeableConcept" };
    case 3387378:
      /* note */ return new String[] { "Annotation" };
    case 1702620169:
      /* bodySite */ return new String[] { "CodeableConcept" };
    case -1077554975:
      /* method */ return new String[] { "CodeableConcept" };
    case -2132868344:
      /* specimen */ return new String[] { "Reference" };
    case -1335157162:
      /* device */ return new String[] { "Reference" };
    case -1912545102:
      /* referenceRange */ return new String[] {};
    case -458019372:
      /* hasMember */ return new String[] { "Reference" };
    case 1077922663:
      /* derivedFrom */ return new String[] { "Reference" };
    case -1399907075:
      /* component */ return new String[] {};
    default:
      return super.getTypesForProperty(hash, name);
    }

  }

  @Override
  public Base addChild(String name) throws FHIRException {
    if (name.equals("identifier")) {
      return addIdentifier();
    } else if (name.equals("basedOn")) {
      return addBasedOn();
    } else if (name.equals("partOf")) {
      return addPartOf();
    } else if (name.equals("status")) {
      throw new FHIRException("Cannot call addChild on a singleton property Observation.status");
    } else if (name.equals("category")) {
      return addCategory();
    } else if (name.equals("code")) {
      this.code = new CodeableConcept();
      return this.code;
    } else if (name.equals("subject")) {
      this.subject = new Reference();
      return this.subject;
    } else if (name.equals("focus")) {
      return addFocus();
    } else if (name.equals("encounter")) {
      this.encounter = new Reference();
      return this.encounter;
    } else if (name.equals("effectiveDateTime")) {
      this.effective = new DateTimeType();
      return this.effective;
    } else if (name.equals("effectivePeriod")) {
      this.effective = new Period();
      return this.effective;
    } else if (name.equals("effectiveTiming")) {
      this.effective = new Timing();
      return this.effective;
    } else if (name.equals("effectiveInstant")) {
      this.effective = new InstantType();
      return this.effective;
    } else if (name.equals("issued")) {
      throw new FHIRException("Cannot call addChild on a singleton property Observation.issued");
    } else if (name.equals("performer")) {
      return addPerformer();
    } else if (name.equals("valueQuantity")) {
      this.value = new Quantity();
      return this.value;
    } else if (name.equals("valueCodeableConcept")) {
      this.value = new CodeableConcept();
      return this.value;
    } else if (name.equals("valueString")) {
      this.value = new StringType();
      return this.value;
    } else if (name.equals("valueBoolean")) {
      this.value = new BooleanType();
      return this.value;
    } else if (name.equals("valueInteger")) {
      this.value = new IntegerType();
      return this.value;
    } else if (name.equals("valueRange")) {
      this.value = new Range();
      return this.value;
    } else if (name.equals("valueRatio")) {
      this.value = new Ratio();
      return this.value;
    } else if (name.equals("valueSampledData")) {
      this.value = new SampledData();
      return this.value;
    } else if (name.equals("valueTime")) {
      this.value = new TimeType();
      return this.value;
    } else if (name.equals("valueDateTime")) {
      this.value = new DateTimeType();
      return this.value;
    } else if (name.equals("valuePeriod")) {
      this.value = new Period();
      return this.value;
    } else if (name.equals("dataAbsentReason")) {
      this.dataAbsentReason = new CodeableConcept();
      return this.dataAbsentReason;
    } else if (name.equals("interpretation")) {
      return addInterpretation();
    } else if (name.equals("note")) {
      return addNote();
    } else if (name.equals("bodySite")) {
      this.bodySite = new CodeableConcept();
      return this.bodySite;
    } else if (name.equals("method")) {
      this.method = new CodeableConcept();
      return this.method;
    } else if (name.equals("specimen")) {
      this.specimen = new Reference();
      return this.specimen;
    } else if (name.equals("device")) {
      this.device = new Reference();
      return this.device;
    } else if (name.equals("referenceRange")) {
      return addReferenceRange();
    } else if (name.equals("hasMember")) {
      return addHasMember();
    } else if (name.equals("derivedFrom")) {
      return addDerivedFrom();
    } else if (name.equals("component")) {
      return addComponent();
    } else
      return super.addChild(name);
  }

  public String fhirType() {
    return "Observation";

  }

  public Observation copy() {
    Observation dst = new Observation();
    copyValues(dst);
    return dst;
  }

  public void copyValues(Observation dst) {
    super.copyValues(dst);
    if (identifier != null) {
      dst.identifier = new ArrayList<Identifier>();
      for (Identifier i : identifier)
        dst.identifier.add(i.copy());
    }
    ;
    if (basedOn != null) {
      dst.basedOn = new ArrayList<Reference>();
      for (Reference i : basedOn)
        dst.basedOn.add(i.copy());
    }
    ;
    if (partOf != null) {
      dst.partOf = new ArrayList<Reference>();
      for (Reference i : partOf)
        dst.partOf.add(i.copy());
    }
    ;
    dst.status = status == null ? null : status.copy();
    if (category != null) {
      dst.category = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : category)
        dst.category.add(i.copy());
    }
    ;
    dst.code = code == null ? null : code.copy();
    dst.subject = subject == null ? null : subject.copy();
    if (focus != null) {
      dst.focus = new ArrayList<Reference>();
      for (Reference i : focus)
        dst.focus.add(i.copy());
    }
    ;
    dst.encounter = encounter == null ? null : encounter.copy();
    dst.effective = effective == null ? null : effective.copy();
    dst.issued = issued == null ? null : issued.copy();
    if (performer != null) {
      dst.performer = new ArrayList<Reference>();
      for (Reference i : performer)
        dst.performer.add(i.copy());
    }
    ;
    dst.value = value == null ? null : value.copy();
    dst.dataAbsentReason = dataAbsentReason == null ? null : dataAbsentReason.copy();
    if (interpretation != null) {
      dst.interpretation = new ArrayList<CodeableConcept>();
      for (CodeableConcept i : interpretation)
        dst.interpretation.add(i.copy());
    }
    ;
    if (note != null) {
      dst.note = new ArrayList<Annotation>();
      for (Annotation i : note)
        dst.note.add(i.copy());
    }
    ;
    dst.bodySite = bodySite == null ? null : bodySite.copy();
    dst.method = method == null ? null : method.copy();
    dst.specimen = specimen == null ? null : specimen.copy();
    dst.device = device == null ? null : device.copy();
    if (referenceRange != null) {
      dst.referenceRange = new ArrayList<ObservationReferenceRangeComponent>();
      for (ObservationReferenceRangeComponent i : referenceRange)
        dst.referenceRange.add(i.copy());
    }
    ;
    if (hasMember != null) {
      dst.hasMember = new ArrayList<Reference>();
      for (Reference i : hasMember)
        dst.hasMember.add(i.copy());
    }
    ;
    if (derivedFrom != null) {
      dst.derivedFrom = new ArrayList<Reference>();
      for (Reference i : derivedFrom)
        dst.derivedFrom.add(i.copy());
    }
    ;
    if (component != null) {
      dst.component = new ArrayList<ObservationComponentComponent>();
      for (ObservationComponentComponent i : component)
        dst.component.add(i.copy());
    }
    ;
  }

  protected Observation typedCopy() {
    return copy();
  }

  @Override
  public boolean equalsDeep(Base other_) {
    if (!super.equalsDeep(other_))
      return false;
    if (!(other_ instanceof Observation))
      return false;
    Observation o = (Observation) other_;
    return compareDeep(identifier, o.identifier, true) && compareDeep(basedOn, o.basedOn, true)
        && compareDeep(partOf, o.partOf, true) && compareDeep(status, o.status, true)
        && compareDeep(category, o.category, true) && compareDeep(code, o.code, true)
        && compareDeep(subject, o.subject, true) && compareDeep(focus, o.focus, true)
        && compareDeep(encounter, o.encounter, true) && compareDeep(effective, o.effective, true)
        && compareDeep(issued, o.issued, true) && compareDeep(performer, o.performer, true)
        && compareDeep(value, o.value, true) && compareDeep(dataAbsentReason, o.dataAbsentReason, true)
        && compareDeep(interpretation, o.interpretation, true) && compareDeep(note, o.note, true)
        && compareDeep(bodySite, o.bodySite, true) && compareDeep(method, o.method, true)
        && compareDeep(specimen, o.specimen, true) && compareDeep(device, o.device, true)
        && compareDeep(referenceRange, o.referenceRange, true) && compareDeep(hasMember, o.hasMember, true)
        && compareDeep(derivedFrom, o.derivedFrom, true) && compareDeep(component, o.component, true);
  }

  @Override
  public boolean equalsShallow(Base other_) {
    if (!super.equalsShallow(other_))
      return false;
    if (!(other_ instanceof Observation))
      return false;
    Observation o = (Observation) other_;
    return compareValues(status, o.status, true) && compareValues(issued, o.issued, true);
  }

  public boolean isEmpty() {
    return super.isEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(identifier, basedOn, partOf, status, category, code,
        subject, focus, encounter, effective, issued, performer, value, dataAbsentReason, interpretation, note,
        bodySite, method, specimen, device, referenceRange, hasMember, derivedFrom, component);
  }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.Observation;
  }

  /**
   * Search parameter: <b>date</b>
   * <p>
   * Description: <b>Obtained date/time. If the obtained element is a period, a
   * date that falls in the period</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Observation.effective[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "date", path = "Observation.effective", description = "Obtained date/time. If the obtained element is a period, a date that falls in the period", type = "date")
  public static final String SP_DATE = "date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>date</b>
   * <p>
   * Description: <b>Obtained date/time. If the obtained element is a period, a
   * date that falls in the period</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Observation.effective[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.DateClientParam DATE = new ca.uhn.fhir.rest.gclient.DateClientParam(
      SP_DATE);

  /**
   * Search parameter: <b>combo-data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.value[x] or Observation.component.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.dataAbsentReason,
   * Observation.component.dataAbsentReason</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-data-absent-reason", path = "Observation.dataAbsentReason | Observation.component.dataAbsentReason", description = "The reason why the expected value in the element Observation.value[x] or Observation.component.value[x] is missing.", type = "token")
  public static final String SP_COMBO_DATA_ABSENT_REASON = "combo-data-absent-reason";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>combo-data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.value[x] or Observation.component.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.dataAbsentReason,
   * Observation.component.dataAbsentReason</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMBO_DATA_ABSENT_REASON = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMBO_DATA_ABSENT_REASON);

  /**
   * Search parameter: <b>code</b>
   * <p>
   * Description: <b>The code of the observation type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.code</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "code", path = "Observation.code", description = "The code of the observation type", type = "token")
  public static final String SP_CODE = "code";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>code</b>
   * <p>
   * Description: <b>The code of the observation type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.code</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam CODE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_CODE);

  /**
   * Search parameter: <b>combo-code-value-quantity</b>
   * <p>
   * Description: <b>Code and quantity value parameter pair, including in
   * components</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-code-value-quantity", path = "Observation | Observation.component", description = "Code and quantity value parameter pair, including in components", type = "composite", compositeOf = {
      "combo-code", "combo-value-quantity" })
  public static final String SP_COMBO_CODE_VALUE_QUANTITY = "combo-code-value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>combo-code-value-quantity</b>
   * <p>
   * Description: <b>Code and quantity value parameter pair, including in
   * components</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam> COMBO_CODE_VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam>(
      SP_COMBO_CODE_VALUE_QUANTITY);

  /**
   * Search parameter: <b>subject</b>
   * <p>
   * Description: <b>The subject that the observation is about</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.subject</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "subject", path = "Observation.subject", description = "The subject that the observation is about", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Device"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Patient") }, target = { Device.class, Group.class,
          Location.class, Patient.class })
  public static final String SP_SUBJECT = "subject";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>subject</b>
   * <p>
   * Description: <b>The subject that the observation is about</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.subject</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam SUBJECT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_SUBJECT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:subject</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_SUBJECT = new ca.uhn.fhir.model.api.Include(
      "Observation:subject").toLocked();

  /**
   * Search parameter: <b>component-data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.component.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.dataAbsentReason</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-data-absent-reason", path = "Observation.component.dataAbsentReason", description = "The reason why the expected value in the element Observation.component.value[x] is missing.", type = "token")
  public static final String SP_COMPONENT_DATA_ABSENT_REASON = "component-data-absent-reason";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>component-data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.component.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.dataAbsentReason</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMPONENT_DATA_ABSENT_REASON = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMPONENT_DATA_ABSENT_REASON);

  /**
   * Search parameter: <b>value-concept</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a
   * CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.valueCodeableConcept</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "value-concept", path = "(Observation.value as CodeableConcept)", description = "The value of the observation, if the value is a CodeableConcept", type = "token")
  public static final String SP_VALUE_CONCEPT = "value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>value-concept</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a
   * CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.valueCodeableConcept</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_VALUE_CONCEPT);

  /**
   * Search parameter: <b>value-date</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a date or
   * period of time</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Observation.valueDateTime, Observation.valuePeriod</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "value-date", path = "(Observation.value as dateTime) | (Observation.value as Period)", description = "The value of the observation, if the value is a date or period of time", type = "date")
  public static final String SP_VALUE_DATE = "value-date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>value-date</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a date or
   * period of time</b><br>
   * Type: <b>date</b><br>
   * Path: <b>Observation.valueDateTime, Observation.valuePeriod</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.DateClientParam VALUE_DATE = new ca.uhn.fhir.rest.gclient.DateClientParam(
      SP_VALUE_DATE);

  /**
   * Search parameter: <b>focus</b>
   * <p>
   * Description: <b>The focus of an observation when the focus is not the patient
   * of record.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.focus</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "focus", path = "Observation.focus", description = "The focus of an observation when the focus is not the patient of record.", type = "reference")
  public static final String SP_FOCUS = "focus";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>focus</b>
   * <p>
   * Description: <b>The focus of an observation when the focus is not the patient
   * of record.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.focus</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam FOCUS = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_FOCUS);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:focus</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_FOCUS = new ca.uhn.fhir.model.api.Include(
      "Observation:focus").toLocked();

  /**
   * Search parameter: <b>derived-from</b>
   * <p>
   * Description: <b>Related measurements the observation is made from</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.derivedFrom</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "derived-from", path = "Observation.derivedFrom", description = "Related measurements the observation is made from", type = "reference", target = {
      DocumentReference.class, ImagingStudy.class, Media.class, MolecularSequence.class, Observation.class,
      QuestionnaireResponse.class })
  public static final String SP_DERIVED_FROM = "derived-from";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>derived-from</b>
   * <p>
   * Description: <b>Related measurements the observation is made from</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.derivedFrom</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam DERIVED_FROM = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_DERIVED_FROM);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:derived-from</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_DERIVED_FROM = new ca.uhn.fhir.model.api.Include(
      "Observation:derived-from").toLocked();

  /**
   * Search parameter: <b>part-of</b>
   * <p>
   * Description: <b>Part of referenced event</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.partOf</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "part-of", path = "Observation.partOf", description = "Part of referenced event", type = "reference", target = {
      ImagingStudy.class, Immunization.class, MedicationAdministration.class, MedicationDispense.class,
      MedicationStatement.class, Procedure.class })
  public static final String SP_PART_OF = "part-of";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>part-of</b>
   * <p>
   * Description: <b>Part of referenced event</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.partOf</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PART_OF = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PART_OF);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:part-of</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PART_OF = new ca.uhn.fhir.model.api.Include(
      "Observation:part-of").toLocked();

  /**
   * Search parameter: <b>has-member</b>
   * <p>
   * Description: <b>Related resource that belongs to the Observation
   * group</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.hasMember</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "has-member", path = "Observation.hasMember", description = "Related resource that belongs to the Observation group", type = "reference", target = {
      MolecularSequence.class, Observation.class, QuestionnaireResponse.class })
  public static final String SP_HAS_MEMBER = "has-member";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>has-member</b>
   * <p>
   * Description: <b>Related resource that belongs to the Observation
   * group</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.hasMember</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam HAS_MEMBER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_HAS_MEMBER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:has-member</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_HAS_MEMBER = new ca.uhn.fhir.model.api.Include(
      "Observation:has-member").toLocked();

  /**
   * Search parameter: <b>code-value-string</b>
   * <p>
   * Description: <b>Code and string value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "code-value-string", path = "Observation", description = "Code and string value parameter pair", type = "composite", compositeOf = {
      "code", "value-string" })
  public static final String SP_CODE_VALUE_STRING = "code-value-string";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>code-value-string</b>
   * <p>
   * Description: <b>Code and string value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.StringClientParam> CODE_VALUE_STRING = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.StringClientParam>(
      SP_CODE_VALUE_STRING);

  /**
   * Search parameter: <b>component-code-value-quantity</b>
   * <p>
   * Description: <b>Component code and component quantity value parameter
   * pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-code-value-quantity", path = "Observation.component", description = "Component code and component quantity value parameter pair", type = "composite", compositeOf = {
      "component-code", "component-value-quantity" })
  public static final String SP_COMPONENT_CODE_VALUE_QUANTITY = "component-code-value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>component-code-value-quantity</b>
   * <p>
   * Description: <b>Component code and component quantity value parameter
   * pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam> COMPONENT_CODE_VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam>(
      SP_COMPONENT_CODE_VALUE_QUANTITY);

  /**
   * Search parameter: <b>based-on</b>
   * <p>
   * Description: <b>Reference to the service request.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.basedOn</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "based-on", path = "Observation.basedOn", description = "Reference to the service request.", type = "reference", target = {
      CarePlan.class, DeviceRequest.class, ImmunizationRecommendation.class, MedicationRequest.class,
      NutritionOrder.class, ServiceRequest.class })
  public static final String SP_BASED_ON = "based-on";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>based-on</b>
   * <p>
   * Description: <b>Reference to the service request.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.basedOn</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam BASED_ON = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_BASED_ON);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:based-on</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_BASED_ON = new ca.uhn.fhir.model.api.Include(
      "Observation:based-on").toLocked();

  /**
   * Search parameter: <b>code-value-date</b>
   * <p>
   * Description: <b>Code and date/time value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "code-value-date", path = "Observation", description = "Code and date/time value parameter pair", type = "composite", compositeOf = {
      "code", "value-date" })
  public static final String SP_CODE_VALUE_DATE = "code-value-date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>code-value-date</b>
   * <p>
   * Description: <b>Code and date/time value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.DateClientParam> CODE_VALUE_DATE = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.DateClientParam>(
      SP_CODE_VALUE_DATE);

  /**
   * Search parameter: <b>patient</b>
   * <p>
   * Description: <b>The subject that the observation is about (if
   * patient)</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.subject</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "patient", path = "Observation.subject.where(resolve() is Patient)", description = "The subject that the observation is about (if patient)", type = "reference", target = {
      Patient.class })
  public static final String SP_PATIENT = "patient";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>patient</b>
   * <p>
   * Description: <b>The subject that the observation is about (if
   * patient)</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.subject</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PATIENT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PATIENT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:patient</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PATIENT = new ca.uhn.fhir.model.api.Include(
      "Observation:patient").toLocked();

  /**
   * Search parameter: <b>specimen</b>
   * <p>
   * Description: <b>Specimen used for this observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.specimen</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "specimen", path = "Observation.specimen", description = "Specimen used for this observation", type = "reference", target = {
      Specimen.class })
  public static final String SP_SPECIMEN = "specimen";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>specimen</b>
   * <p>
   * Description: <b>Specimen used for this observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.specimen</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam SPECIMEN = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_SPECIMEN);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:specimen</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_SPECIMEN = new ca.uhn.fhir.model.api.Include(
      "Observation:specimen").toLocked();

  /**
   * Search parameter: <b>component-code</b>
   * <p>
   * Description: <b>The component code of the observation type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.code</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-code", path = "Observation.component.code", description = "The component code of the observation type", type = "token")
  public static final String SP_COMPONENT_CODE = "component-code";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>component-code</b>
   * <p>
   * Description: <b>The component code of the observation type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.code</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMPONENT_CODE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMPONENT_CODE);

  /**
   * Search parameter: <b>code-value-quantity</b>
   * <p>
   * Description: <b>Code and quantity value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "code-value-quantity", path = "Observation", description = "Code and quantity value parameter pair", type = "composite", compositeOf = {
      "code", "value-quantity" })
  public static final String SP_CODE_VALUE_QUANTITY = "code-value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>code-value-quantity</b>
   * <p>
   * Description: <b>Code and quantity value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam> CODE_VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.QuantityClientParam>(
      SP_CODE_VALUE_QUANTITY);

  /**
   * Search parameter: <b>combo-code-value-concept</b>
   * <p>
   * Description: <b>Code and coded value parameter pair, including in
   * components</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-code-value-concept", path = "Observation | Observation.component", description = "Code and coded value parameter pair, including in components", type = "composite", compositeOf = {
      "combo-code", "combo-value-concept" })
  public static final String SP_COMBO_CODE_VALUE_CONCEPT = "combo-code-value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>combo-code-value-concept</b>
   * <p>
   * Description: <b>Code and coded value parameter pair, including in
   * components</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam> COMBO_CODE_VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam>(
      SP_COMBO_CODE_VALUE_CONCEPT);

  /**
   * Search parameter: <b>value-string</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a string, and
   * also searches in CodeableConcept.text</b><br>
   * Type: <b>string</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "value-string", path = "(Observation.value as string) | (Observation.value as CodeableConcept).text", description = "The value of the observation, if the value is a string, and also searches in CodeableConcept.text", type = "string")
  public static final String SP_VALUE_STRING = "value-string";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>value-string</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a string, and
   * also searches in CodeableConcept.text</b><br>
   * Type: <b>string</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.StringClientParam VALUE_STRING = new ca.uhn.fhir.rest.gclient.StringClientParam(
      SP_VALUE_STRING);

  /**
   * Search parameter: <b>identifier</b>
   * <p>
   * Description: <b>The unique id for a particular observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.identifier</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "identifier", path = "Observation.identifier", description = "The unique id for a particular observation", type = "token")
  public static final String SP_IDENTIFIER = "identifier";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>identifier</b>
   * <p>
   * Description: <b>The unique id for a particular observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.identifier</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam IDENTIFIER = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_IDENTIFIER);

  /**
   * Search parameter: <b>performer</b>
   * <p>
   * Description: <b>Who performed the observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.performer</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "performer", path = "Observation.performer", description = "Who performed the observation", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Patient"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Practitioner"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "RelatedPerson") }, target = { CareTeam.class,
          Organization.class, Patient.class, Practitioner.class, PractitionerRole.class, RelatedPerson.class })
  public static final String SP_PERFORMER = "performer";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>performer</b>
   * <p>
   * Description: <b>Who performed the observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.performer</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PERFORMER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PERFORMER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:performer</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PERFORMER = new ca.uhn.fhir.model.api.Include(
      "Observation:performer").toLocked();

  /**
   * Search parameter: <b>combo-code</b>
   * <p>
   * Description: <b>The code of the observation type or component type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.code, Observation.component.code</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-code", path = "Observation.code | Observation.component.code", description = "The code of the observation type or component type", type = "token")
  public static final String SP_COMBO_CODE = "combo-code";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>combo-code</b>
   * <p>
   * Description: <b>The code of the observation type or component type</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.code, Observation.component.code</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMBO_CODE = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMBO_CODE);

  /**
   * Search parameter: <b>method</b>
   * <p>
   * Description: <b>The method used for the observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.method</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "method", path = "Observation.method", description = "The method used for the observation", type = "token")
  public static final String SP_METHOD = "method";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>method</b>
   * <p>
   * Description: <b>The method used for the observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.method</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam METHOD = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_METHOD);

  /**
   * Search parameter: <b>value-quantity</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a Quantity, or
   * a SampledData (just search on the bounds of the values in sampled
   * data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "value-quantity", path = "(Observation.value as Quantity) | (Observation.value as SampledData)", description = "The value of the observation, if the value is a Quantity, or a SampledData (just search on the bounds of the values in sampled data)", type = "quantity")
  public static final String SP_VALUE_QUANTITY = "value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>value-quantity</b>
   * <p>
   * Description: <b>The value of the observation, if the value is a Quantity, or
   * a SampledData (just search on the bounds of the values in sampled
   * data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.QuantityClientParam VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.QuantityClientParam(
      SP_VALUE_QUANTITY);

  /**
   * Search parameter: <b>component-value-quantity</b>
   * <p>
   * Description: <b>The value of the component observation, if the value is a
   * Quantity, or a SampledData (just search on the bounds of the values in
   * sampled data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.component.value[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-value-quantity", path = "(Observation.component.value as Quantity) | (Observation.component.value as SampledData)", description = "The value of the component observation, if the value is a Quantity, or a SampledData (just search on the bounds of the values in sampled data)", type = "quantity")
  public static final String SP_COMPONENT_VALUE_QUANTITY = "component-value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>component-value-quantity</b>
   * <p>
   * Description: <b>The value of the component observation, if the value is a
   * Quantity, or a SampledData (just search on the bounds of the values in
   * sampled data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.component.value[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.QuantityClientParam COMPONENT_VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.QuantityClientParam(
      SP_COMPONENT_VALUE_QUANTITY);

  /**
   * Search parameter: <b>data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.dataAbsentReason</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "data-absent-reason", path = "Observation.dataAbsentReason", description = "The reason why the expected value in the element Observation.value[x] is missing.", type = "token")
  public static final String SP_DATA_ABSENT_REASON = "data-absent-reason";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>data-absent-reason</b>
   * <p>
   * Description: <b>The reason why the expected value in the element
   * Observation.value[x] is missing.</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.dataAbsentReason</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam DATA_ABSENT_REASON = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_DATA_ABSENT_REASON);

  /**
   * Search parameter: <b>combo-value-quantity</b>
   * <p>
   * Description: <b>The value or component value of the observation, if the value
   * is a Quantity, or a SampledData (just search on the bounds of the values in
   * sampled data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-value-quantity", path = "(Observation.value as Quantity) | (Observation.value as SampledData) | (Observation.component.value as Quantity) | (Observation.component.value as SampledData)", description = "The value or component value of the observation, if the value is a Quantity, or a SampledData (just search on the bounds of the values in sampled data)", type = "quantity")
  public static final String SP_COMBO_VALUE_QUANTITY = "combo-value-quantity";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>combo-value-quantity</b>
   * <p>
   * Description: <b>The value or component value of the observation, if the value
   * is a Quantity, or a SampledData (just search on the bounds of the values in
   * sampled data)</b><br>
   * Type: <b>quantity</b><br>
   * Path: <b>Observation.value[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.QuantityClientParam COMBO_VALUE_QUANTITY = new ca.uhn.fhir.rest.gclient.QuantityClientParam(
      SP_COMBO_VALUE_QUANTITY);

  /**
   * Search parameter: <b>encounter</b>
   * <p>
   * Description: <b>Encounter related to the observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.encounter</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "encounter", path = "Observation.encounter", description = "Encounter related to the observation", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Encounter") }, target = { Encounter.class })
  public static final String SP_ENCOUNTER = "encounter";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>encounter</b>
   * <p>
   * Description: <b>Encounter related to the observation</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.encounter</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam ENCOUNTER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_ENCOUNTER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:encounter</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_ENCOUNTER = new ca.uhn.fhir.model.api.Include(
      "Observation:encounter").toLocked();

  /**
   * Search parameter: <b>code-value-concept</b>
   * <p>
   * Description: <b>Code and coded value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "code-value-concept", path = "Observation", description = "Code and coded value parameter pair", type = "composite", compositeOf = {
      "code", "value-concept" })
  public static final String SP_CODE_VALUE_CONCEPT = "code-value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>code-value-concept</b>
   * <p>
   * Description: <b>Code and coded value parameter pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam> CODE_VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam>(
      SP_CODE_VALUE_CONCEPT);

  /**
   * Search parameter: <b>component-code-value-concept</b>
   * <p>
   * Description: <b>Component code and component coded value parameter
   * pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-code-value-concept", path = "Observation.component", description = "Component code and component coded value parameter pair", type = "composite", compositeOf = {
      "component-code", "component-value-concept" })
  public static final String SP_COMPONENT_CODE_VALUE_CONCEPT = "component-code-value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>component-code-value-concept</b>
   * <p>
   * Description: <b>Component code and component coded value parameter
   * pair</b><br>
   * Type: <b>composite</b><br>
   * Path: <b></b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam> COMPONENT_CODE_VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.CompositeClientParam<ca.uhn.fhir.rest.gclient.TokenClientParam, ca.uhn.fhir.rest.gclient.TokenClientParam>(
      SP_COMPONENT_CODE_VALUE_CONCEPT);

  /**
   * Search parameter: <b>component-value-concept</b>
   * <p>
   * Description: <b>The value of the component observation, if the value is a
   * CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.valueCodeableConcept</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "component-value-concept", path = "(Observation.component.value as CodeableConcept)", description = "The value of the component observation, if the value is a CodeableConcept", type = "token")
  public static final String SP_COMPONENT_VALUE_CONCEPT = "component-value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for
   * <b>component-value-concept</b>
   * <p>
   * Description: <b>The value of the component observation, if the value is a
   * CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.component.valueCodeableConcept</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMPONENT_VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMPONENT_VALUE_CONCEPT);

  /**
   * Search parameter: <b>category</b>
   * <p>
   * Description: <b>The classification of the type of observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.category</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "category", path = "Observation.category", description = "The classification of the type of observation", type = "token")
  public static final String SP_CATEGORY = "category";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>category</b>
   * <p>
   * Description: <b>The classification of the type of observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.category</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam CATEGORY = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_CATEGORY);

  /**
   * Search parameter: <b>device</b>
   * <p>
   * Description: <b>The Device that generated the observation data.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.device</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "device", path = "Observation.device", description = "The Device that generated the observation data.", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Device") }, target = { Device.class, DeviceMetric.class })
  public static final String SP_DEVICE = "device";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>device</b>
   * <p>
   * Description: <b>The Device that generated the observation data.</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>Observation.device</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam DEVICE = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_DEVICE);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>Observation:device</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_DEVICE = new ca.uhn.fhir.model.api.Include(
      "Observation:device").toLocked();

  /**
   * Search parameter: <b>combo-value-concept</b>
   * <p>
   * Description: <b>The value or component value of the observation, if the value
   * is a CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.valueCodeableConcept,
   * Observation.component.valueCodeableConcept</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "combo-value-concept", path = "(Observation.value as CodeableConcept) | (Observation.component.value as CodeableConcept)", description = "The value or component value of the observation, if the value is a CodeableConcept", type = "token")
  public static final String SP_COMBO_VALUE_CONCEPT = "combo-value-concept";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>combo-value-concept</b>
   * <p>
   * Description: <b>The value or component value of the observation, if the value
   * is a CodeableConcept</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.valueCodeableConcept,
   * Observation.component.valueCodeableConcept</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam COMBO_VALUE_CONCEPT = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_COMBO_VALUE_CONCEPT);

  /**
   * Search parameter: <b>status</b>
   * <p>
   * Description: <b>The status of the observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.status</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "status", path = "Observation.status", description = "The status of the observation", type = "token")
  public static final String SP_STATUS = "status";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>status</b>
   * <p>
   * Description: <b>The status of the observation</b><br>
   * Type: <b>token</b><br>
   * Path: <b>Observation.status</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam STATUS = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_STATUS);

}
