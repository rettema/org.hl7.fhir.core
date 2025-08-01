package org.hl7.fhir.r4.model;

import java.math.BigDecimal;

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
import org.hl7.fhir.instance.model.api.IBaseBackboneElement;
import org.hl7.fhir.utilities.Utilities;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.api.annotation.SearchParamDefinition;

/**
 * An assessment of the likely outcome(s) for a patient or other subject as well
 * as the likelihood of each outcome.
 */
@ResourceDef(name = "RiskAssessment", profile = "http://hl7.org/fhir/StructureDefinition/RiskAssessment")
public class RiskAssessment extends DomainResource {

  public enum RiskAssessmentStatus {
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

    public static RiskAssessmentStatus fromCode(String codeString) throws FHIRException {
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
        throw new FHIRException("Unknown RiskAssessmentStatus code '" + codeString + "'");
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

  public static class RiskAssessmentStatusEnumFactory implements EnumFactory<RiskAssessmentStatus> {
    public RiskAssessmentStatus fromCode(String codeString) throws IllegalArgumentException {
      if (codeString == null || "".equals(codeString))
        if (codeString == null || "".equals(codeString))
          return null;
      if ("registered".equals(codeString))
        return RiskAssessmentStatus.REGISTERED;
      if ("preliminary".equals(codeString))
        return RiskAssessmentStatus.PRELIMINARY;
      if ("final".equals(codeString))
        return RiskAssessmentStatus.FINAL;
      if ("amended".equals(codeString))
        return RiskAssessmentStatus.AMENDED;
      if ("corrected".equals(codeString))
        return RiskAssessmentStatus.CORRECTED;
      if ("cancelled".equals(codeString))
        return RiskAssessmentStatus.CANCELLED;
      if ("entered-in-error".equals(codeString))
        return RiskAssessmentStatus.ENTEREDINERROR;
      if ("unknown".equals(codeString))
        return RiskAssessmentStatus.UNKNOWN;
      throw new IllegalArgumentException("Unknown RiskAssessmentStatus code '" + codeString + "'");
    }

    public Enumeration<RiskAssessmentStatus> fromType(PrimitiveType<?> code) throws FHIRException {
      if (code == null)
        return null;
      if (code.isEmpty())
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.NULL, code);
      String codeString = code.asStringValue();
      if (codeString == null || "".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.NULL, code);
      if ("registered".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.REGISTERED, code);
      if ("preliminary".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.PRELIMINARY, code);
      if ("final".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.FINAL, code);
      if ("amended".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.AMENDED, code);
      if ("corrected".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.CORRECTED, code);
      if ("cancelled".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.CANCELLED, code);
      if ("entered-in-error".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.ENTEREDINERROR, code);
      if ("unknown".equals(codeString))
        return new Enumeration<RiskAssessmentStatus>(this, RiskAssessmentStatus.UNKNOWN, code);
      throw new FHIRException("Unknown RiskAssessmentStatus code '" + codeString + "'");
    }

    public String toCode(RiskAssessmentStatus code) {
       if (code == RiskAssessmentStatus.NULL)
           return null;
       if (code == RiskAssessmentStatus.REGISTERED)
        return "registered";
      if (code == RiskAssessmentStatus.PRELIMINARY)
        return "preliminary";
      if (code == RiskAssessmentStatus.FINAL)
        return "final";
      if (code == RiskAssessmentStatus.AMENDED)
        return "amended";
      if (code == RiskAssessmentStatus.CORRECTED)
        return "corrected";
      if (code == RiskAssessmentStatus.CANCELLED)
        return "cancelled";
      if (code == RiskAssessmentStatus.ENTEREDINERROR)
        return "entered-in-error";
      if (code == RiskAssessmentStatus.UNKNOWN)
        return "unknown";
      return "?";
   }

    public String toSystem(RiskAssessmentStatus code) {
      return code.getSystem();
    }
  }

  @Block()
  public static class RiskAssessmentPredictionComponent extends BackboneElement implements IBaseBackboneElement {
    /**
     * One of the potential outcomes for the patient (e.g. remission, death, a
     * particular condition).
     */
    @Child(name = "outcome", type = {
        CodeableConcept.class }, order = 1, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Possible outcome for the subject", formalDefinition = "One of the potential outcomes for the patient (e.g. remission, death,  a particular condition).")
    protected CodeableConcept outcome;

    /**
     * Indicates how likely the outcome is (in the specified timeframe).
     */
    @Child(name = "probability", type = { DecimalType.class,
        Range.class }, order = 2, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Likelihood of specified outcome", formalDefinition = "Indicates how likely the outcome is (in the specified timeframe).")
    protected Type probability;

    /**
     * Indicates how likely the outcome is (in the specified timeframe), expressed
     * as a qualitative value (e.g. low, medium, or high).
     */
    @Child(name = "qualitativeRisk", type = {
        CodeableConcept.class }, order = 3, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Likelihood of specified outcome as a qualitative value", formalDefinition = "Indicates how likely the outcome is (in the specified timeframe), expressed as a qualitative value (e.g. low, medium, or high).")
    @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/risk-probability")
    protected CodeableConcept qualitativeRisk;

    /**
     * Indicates the risk for this particular subject (with their specific
     * characteristics) divided by the risk of the population in general. (Numbers
     * greater than 1 = higher risk than the population, numbers less than 1 = lower
     * risk.).
     */
    @Child(name = "relativeRisk", type = {
        DecimalType.class }, order = 4, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Relative likelihood", formalDefinition = "Indicates the risk for this particular subject (with their specific characteristics) divided by the risk of the population in general.  (Numbers greater than 1 = higher risk than the population, numbers less than 1 = lower risk.).")
    protected DecimalType relativeRisk;

    /**
     * Indicates the period of time or age range of the subject to which the
     * specified probability applies.
     */
    @Child(name = "when", type = { Period.class,
        Range.class }, order = 5, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Timeframe or age range", formalDefinition = "Indicates the period of time or age range of the subject to which the specified probability applies.")
    protected Type when;

    /**
     * Additional information explaining the basis for the prediction.
     */
    @Child(name = "rationale", type = {
        StringType.class }, order = 6, min = 0, max = 1, modifier = false, summary = false)
    @Description(shortDefinition = "Explanation of prediction", formalDefinition = "Additional information explaining the basis for the prediction.")
    protected StringType rationale;

    private static final long serialVersionUID = 1283401747L;

    /**
     * Constructor
     */
    public RiskAssessmentPredictionComponent() {
      super();
    }

    /**
     * @return {@link #outcome} (One of the potential outcomes for the patient (e.g.
     *         remission, death, a particular condition).)
     */
    public CodeableConcept getOutcome() {
      if (this.outcome == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create RiskAssessmentPredictionComponent.outcome");
        else if (Configuration.doAutoCreate())
          this.outcome = new CodeableConcept(); // cc
      return this.outcome;
    }

    public boolean hasOutcome() {
      return this.outcome != null && !this.outcome.isEmpty();
    }

    /**
     * @param value {@link #outcome} (One of the potential outcomes for the patient
     *              (e.g. remission, death, a particular condition).)
     */
    public RiskAssessmentPredictionComponent setOutcome(CodeableConcept value) {
      this.outcome = value;
      return this;
    }

    /**
     * @return {@link #probability} (Indicates how likely the outcome is (in the
     *         specified timeframe).)
     */
    public Type getProbability() {
      return this.probability;
    }

    /**
     * @return {@link #probability} (Indicates how likely the outcome is (in the
     *         specified timeframe).)
     */
    public DecimalType getProbabilityDecimalType() throws FHIRException {
      if (this.probability == null)
        this.probability = new DecimalType();
      if (!(this.probability instanceof DecimalType))
        throw new FHIRException("Type mismatch: the type DecimalType was expected, but "
            + this.probability.getClass().getName() + " was encountered");
      return (DecimalType) this.probability;
    }

    public boolean hasProbabilityDecimalType() {
        return this.probability instanceof DecimalType;
    }

    /**
     * @return {@link #probability} (Indicates how likely the outcome is (in the
     *         specified timeframe).)
     */
    public Range getProbabilityRange() throws FHIRException {
      if (this.probability == null)
        this.probability = new Range();
      if (!(this.probability instanceof Range))
        throw new FHIRException("Type mismatch: the type Range was expected, but "
            + this.probability.getClass().getName() + " was encountered");
      return (Range) this.probability;
    }

    public boolean hasProbabilityRange() {
        return this.probability instanceof Range;
    }

    public boolean hasProbability() {
      return this.probability != null && !this.probability.isEmpty();
    }

    /**
     * @param value {@link #probability} (Indicates how likely the outcome is (in
     *              the specified timeframe).)
     */
    public RiskAssessmentPredictionComponent setProbability(Type value) {
      if (value != null && !(value instanceof DecimalType || value instanceof Range))
        throw new Error("Not the right type for RiskAssessment.prediction.probability[x]: " + value.fhirType());
      this.probability = value;
      return this;
    }

    /**
     * @return {@link #qualitativeRisk} (Indicates how likely the outcome is (in the
     *         specified timeframe), expressed as a qualitative value (e.g. low,
     *         medium, or high).)
     */
    public CodeableConcept getQualitativeRisk() {
      if (this.qualitativeRisk == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create RiskAssessmentPredictionComponent.qualitativeRisk");
        else if (Configuration.doAutoCreate())
          this.qualitativeRisk = new CodeableConcept(); // cc
      return this.qualitativeRisk;
    }

    public boolean hasQualitativeRisk() {
      return this.qualitativeRisk != null && !this.qualitativeRisk.isEmpty();
    }

    /**
     * @param value {@link #qualitativeRisk} (Indicates how likely the outcome is
     *              (in the specified timeframe), expressed as a qualitative value
     *              (e.g. low, medium, or high).)
     */
    public RiskAssessmentPredictionComponent setQualitativeRisk(CodeableConcept value) {
      this.qualitativeRisk = value;
      return this;
    }

    /**
     * @return {@link #relativeRisk} (Indicates the risk for this particular subject
     *         (with their specific characteristics) divided by the risk of the
     *         population in general. (Numbers greater than 1 = higher risk than the
     *         population, numbers less than 1 = lower risk.).). This is the
     *         underlying object with id, value and extensions. The accessor
     *         "getRelativeRisk" gives direct access to the value
     */
    public DecimalType getRelativeRiskElement() {
      if (this.relativeRisk == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create RiskAssessmentPredictionComponent.relativeRisk");
        else if (Configuration.doAutoCreate())
          this.relativeRisk = new DecimalType(); // bb
      return this.relativeRisk;
    }

    public boolean hasRelativeRiskElement() {
      return this.relativeRisk != null && !this.relativeRisk.isEmpty();
    }

    public boolean hasRelativeRisk() {
      return this.relativeRisk != null && !this.relativeRisk.isEmpty();
    }

    /**
     * @param value {@link #relativeRisk} (Indicates the risk for this particular
     *              subject (with their specific characteristics) divided by the
     *              risk of the population in general. (Numbers greater than 1 =
     *              higher risk than the population, numbers less than 1 = lower
     *              risk.).). This is the underlying object with id, value and
     *              extensions. The accessor "getRelativeRisk" gives direct access
     *              to the value
     */
    public RiskAssessmentPredictionComponent setRelativeRiskElement(DecimalType value) {
      this.relativeRisk = value;
      return this;
    }

    /**
     * @return Indicates the risk for this particular subject (with their specific
     *         characteristics) divided by the risk of the population in general.
     *         (Numbers greater than 1 = higher risk than the population, numbers
     *         less than 1 = lower risk.).
     */
    public BigDecimal getRelativeRisk() {
      return this.relativeRisk == null ? null : this.relativeRisk.getValue();
    }

    /**
     * @param value Indicates the risk for this particular subject (with their
     *              specific characteristics) divided by the risk of the population
     *              in general. (Numbers greater than 1 = higher risk than the
     *              population, numbers less than 1 = lower risk.).
     */
    public RiskAssessmentPredictionComponent setRelativeRisk(BigDecimal value) {
      if (value == null)
        this.relativeRisk = null;
      else {
        if (this.relativeRisk == null)
          this.relativeRisk = new DecimalType();
        this.relativeRisk.setValue(value);
      }
      return this;
    }

    /**
     * @param value Indicates the risk for this particular subject (with their
     *              specific characteristics) divided by the risk of the population
     *              in general. (Numbers greater than 1 = higher risk than the
     *              population, numbers less than 1 = lower risk.).
     */
    public RiskAssessmentPredictionComponent setRelativeRisk(long value) {
      this.relativeRisk = new DecimalType();
      this.relativeRisk.setValue(value);
      return this;
    }

    /**
     * @param value Indicates the risk for this particular subject (with their
     *              specific characteristics) divided by the risk of the population
     *              in general. (Numbers greater than 1 = higher risk than the
     *              population, numbers less than 1 = lower risk.).
     */
    public RiskAssessmentPredictionComponent setRelativeRisk(double value) {
      this.relativeRisk = new DecimalType();
      this.relativeRisk.setValue(value);
      return this;
    }

    /**
     * @return {@link #when} (Indicates the period of time or age range of the
     *         subject to which the specified probability applies.)
     */
    public Type getWhen() {
      return this.when;
    }

    /**
     * @return {@link #when} (Indicates the period of time or age range of the
     *         subject to which the specified probability applies.)
     */
    public Period getWhenPeriod() throws FHIRException {
      if (this.when == null)
        this.when = new Period();
      if (!(this.when instanceof Period))
        throw new FHIRException(
            "Type mismatch: the type Period was expected, but " + this.when.getClass().getName() + " was encountered");
      return (Period) this.when;
    }

    public boolean hasWhenPeriod() {
        return this.when instanceof Period;
    }

    /**
     * @return {@link #when} (Indicates the period of time or age range of the
     *         subject to which the specified probability applies.)
     */
    public Range getWhenRange() throws FHIRException {
      if (this.when == null)
        this.when = new Range();
      if (!(this.when instanceof Range))
        throw new FHIRException(
            "Type mismatch: the type Range was expected, but " + this.when.getClass().getName() + " was encountered");
      return (Range) this.when;
    }

    public boolean hasWhenRange() {
        return this.when instanceof Range;
    }

    public boolean hasWhen() {
      return this.when != null && !this.when.isEmpty();
    }

    /**
     * @param value {@link #when} (Indicates the period of time or age range of the
     *              subject to which the specified probability applies.)
     */
    public RiskAssessmentPredictionComponent setWhen(Type value) {
      if (value != null && !(value instanceof Period || value instanceof Range))
        throw new Error("Not the right type for RiskAssessment.prediction.when[x]: " + value.fhirType());
      this.when = value;
      return this;
    }

    /**
     * @return {@link #rationale} (Additional information explaining the basis for
     *         the prediction.). This is the underlying object with id, value and
     *         extensions. The accessor "getRationale" gives direct access to the
     *         value
     */
    public StringType getRationaleElement() {
      if (this.rationale == null)
        if (Configuration.errorOnAutoCreate())
          throw new Error("Attempt to auto-create RiskAssessmentPredictionComponent.rationale");
        else if (Configuration.doAutoCreate())
          this.rationale = new StringType(); // bb
      return this.rationale;
    }

    public boolean hasRationaleElement() {
      return this.rationale != null && !this.rationale.isEmpty();
    }

    public boolean hasRationale() {
      return this.rationale != null && !this.rationale.isEmpty();
    }

    /**
     * @param value {@link #rationale} (Additional information explaining the basis
     *              for the prediction.). This is the underlying object with id,
     *              value and extensions. The accessor "getRationale" gives direct
     *              access to the value
     */
    public RiskAssessmentPredictionComponent setRationaleElement(StringType value) {
      this.rationale = value;
      return this;
    }

    /**
     * @return Additional information explaining the basis for the prediction.
     */
    public String getRationale() {
      return this.rationale == null ? null : this.rationale.getValue();
    }

    /**
     * @param value Additional information explaining the basis for the prediction.
     */
    public RiskAssessmentPredictionComponent setRationale(String value) {
      if (Utilities.noString(value))
        this.rationale = null;
      else {
        if (this.rationale == null)
          this.rationale = new StringType();
        this.rationale.setValue(value);
      }
      return this;
    }

    protected void listChildren(List<Property> children) {
      super.listChildren(children);
      children.add(new Property("outcome", "CodeableConcept",
          "One of the potential outcomes for the patient (e.g. remission, death,  a particular condition).", 0, 1,
          outcome));
      children.add(new Property("probability[x]", "decimal|Range",
          "Indicates how likely the outcome is (in the specified timeframe).", 0, 1, probability));
      children.add(new Property("qualitativeRisk", "CodeableConcept",
          "Indicates how likely the outcome is (in the specified timeframe), expressed as a qualitative value (e.g. low, medium, or high).",
          0, 1, qualitativeRisk));
      children.add(new Property("relativeRisk", "decimal",
          "Indicates the risk for this particular subject (with their specific characteristics) divided by the risk of the population in general.  (Numbers greater than 1 = higher risk than the population, numbers less than 1 = lower risk.).",
          0, 1, relativeRisk));
      children.add(new Property("when[x]", "Period|Range",
          "Indicates the period of time or age range of the subject to which the specified probability applies.", 0, 1,
          when));
      children.add(new Property("rationale", "string",
          "Additional information explaining the basis for the prediction.", 0, 1, rationale));
    }

    @Override
    public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
      switch (_hash) {
      case -1106507950:
        /* outcome */ return new Property("outcome", "CodeableConcept",
            "One of the potential outcomes for the patient (e.g. remission, death,  a particular condition).", 0, 1,
            outcome);
      case 1430185003:
        /* probability[x] */ return new Property("probability[x]", "decimal|Range",
            "Indicates how likely the outcome is (in the specified timeframe).", 0, 1, probability);
      case -1290561483:
        /* probability */ return new Property("probability[x]", "decimal|Range",
            "Indicates how likely the outcome is (in the specified timeframe).", 0, 1, probability);
      case 888495452:
        /* probabilityDecimal */ return new Property("probability[x]", "decimal|Range",
            "Indicates how likely the outcome is (in the specified timeframe).", 0, 1, probability);
      case 9275912:
        /* probabilityRange */ return new Property("probability[x]", "decimal|Range",
            "Indicates how likely the outcome is (in the specified timeframe).", 0, 1, probability);
      case 123308730:
        /* qualitativeRisk */ return new Property("qualitativeRisk", "CodeableConcept",
            "Indicates how likely the outcome is (in the specified timeframe), expressed as a qualitative value (e.g. low, medium, or high).",
            0, 1, qualitativeRisk);
      case -70741061:
        /* relativeRisk */ return new Property("relativeRisk", "decimal",
            "Indicates the risk for this particular subject (with their specific characteristics) divided by the risk of the population in general.  (Numbers greater than 1 = higher risk than the population, numbers less than 1 = lower risk.).",
            0, 1, relativeRisk);
      case 1312831238:
        /* when[x] */ return new Property("when[x]", "Period|Range",
            "Indicates the period of time or age range of the subject to which the specified probability applies.", 0,
            1, when);
      case 3648314:
        /* when */ return new Property("when[x]", "Period|Range",
            "Indicates the period of time or age range of the subject to which the specified probability applies.", 0,
            1, when);
      case 251476379:
        /* whenPeriod */ return new Property("when[x]", "Period|Range",
            "Indicates the period of time or age range of the subject to which the specified probability applies.", 0,
            1, when);
      case -1098542557:
        /* whenRange */ return new Property("when[x]", "Period|Range",
            "Indicates the period of time or age range of the subject to which the specified probability applies.", 0,
            1, when);
      case 345689335:
        /* rationale */ return new Property("rationale", "string",
            "Additional information explaining the basis for the prediction.", 0, 1, rationale);
      default:
        return super.getNamedProperty(_hash, _name, _checkValid);
      }

    }

    @Override
    public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
      switch (hash) {
      case -1106507950:
        /* outcome */ return this.outcome == null ? new Base[0] : new Base[] { this.outcome }; // CodeableConcept
      case -1290561483:
        /* probability */ return this.probability == null ? new Base[0] : new Base[] { this.probability }; // Type
      case 123308730:
        /* qualitativeRisk */ return this.qualitativeRisk == null ? new Base[0] : new Base[] { this.qualitativeRisk }; // CodeableConcept
      case -70741061:
        /* relativeRisk */ return this.relativeRisk == null ? new Base[0] : new Base[] { this.relativeRisk }; // DecimalType
      case 3648314:
        /* when */ return this.when == null ? new Base[0] : new Base[] { this.when }; // Type
      case 345689335:
        /* rationale */ return this.rationale == null ? new Base[0] : new Base[] { this.rationale }; // StringType
      default:
        return super.getProperty(hash, name, checkValid);
      }

    }

    @Override
    public Base setProperty(int hash, String name, Base value) throws FHIRException {
      switch (hash) {
      case -1106507950: // outcome
        this.outcome = castToCodeableConcept(value); // CodeableConcept
        return value;
      case -1290561483: // probability
        this.probability = castToType(value); // Type
        return value;
      case 123308730: // qualitativeRisk
        this.qualitativeRisk = castToCodeableConcept(value); // CodeableConcept
        return value;
      case -70741061: // relativeRisk
        this.relativeRisk = castToDecimal(value); // DecimalType
        return value;
      case 3648314: // when
        this.when = castToType(value); // Type
        return value;
      case 345689335: // rationale
        this.rationale = castToString(value); // StringType
        return value;
      default:
        return super.setProperty(hash, name, value);
      }

    }

    @Override
    public Base setProperty(String name, Base value) throws FHIRException {
      if (name.equals("outcome")) {
        this.outcome = castToCodeableConcept(value); // CodeableConcept
      } else if (name.equals("probability[x]")) {
        this.probability = castToType(value); // Type
      } else if (name.equals("qualitativeRisk")) {
        this.qualitativeRisk = castToCodeableConcept(value); // CodeableConcept
      } else if (name.equals("relativeRisk")) {
        this.relativeRisk = castToDecimal(value); // DecimalType
      } else if (name.equals("when[x]")) {
        this.when = castToType(value); // Type
      } else if (name.equals("rationale")) {
        this.rationale = castToString(value); // StringType
      } else
        return super.setProperty(name, value);
      return value;
    }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
      if (name.equals("outcome")) {
        this.outcome = null;
      } else if (name.equals("probability[x]")) {
        this.probability = null;
      } else if (name.equals("qualitativeRisk")) {
        this.qualitativeRisk = null;
      } else if (name.equals("relativeRisk")) {
        this.relativeRisk = null;
      } else if (name.equals("when[x]")) {
        this.when = null;
      } else if (name.equals("rationale")) {
        this.rationale = null;
      } else
        super.removeChild(name, value);
      
    }

    @Override
    public Base makeProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case -1106507950:
        return getOutcome();
      case 1430185003:
        return getProbability();
      case -1290561483:
        return getProbability();
      case 123308730:
        return getQualitativeRisk();
      case -70741061:
        return getRelativeRiskElement();
      case 1312831238:
        return getWhen();
      case 3648314:
        return getWhen();
      case 345689335:
        return getRationaleElement();
      default:
        return super.makeProperty(hash, name);
      }

    }

    @Override
    public String[] getTypesForProperty(int hash, String name) throws FHIRException {
      switch (hash) {
      case -1106507950:
        /* outcome */ return new String[] { "CodeableConcept" };
      case -1290561483:
        /* probability */ return new String[] { "decimal", "Range" };
      case 123308730:
        /* qualitativeRisk */ return new String[] { "CodeableConcept" };
      case -70741061:
        /* relativeRisk */ return new String[] { "decimal" };
      case 3648314:
        /* when */ return new String[] { "Period", "Range" };
      case 345689335:
        /* rationale */ return new String[] { "string" };
      default:
        return super.getTypesForProperty(hash, name);
      }

    }

    @Override
    public Base addChild(String name) throws FHIRException {
      if (name.equals("outcome")) {
        this.outcome = new CodeableConcept();
        return this.outcome;
      } else if (name.equals("probabilityDecimal")) {
        this.probability = new DecimalType();
        return this.probability;
      } else if (name.equals("probabilityRange")) {
        this.probability = new Range();
        return this.probability;
      } else if (name.equals("qualitativeRisk")) {
        this.qualitativeRisk = new CodeableConcept();
        return this.qualitativeRisk;
      } else if (name.equals("relativeRisk")) {
        throw new FHIRException("Cannot call addChild on a singleton property RiskAssessment.relativeRisk");
      } else if (name.equals("whenPeriod")) {
        this.when = new Period();
        return this.when;
      } else if (name.equals("whenRange")) {
        this.when = new Range();
        return this.when;
      } else if (name.equals("rationale")) {
        throw new FHIRException("Cannot call addChild on a singleton property RiskAssessment.rationale");
      } else
        return super.addChild(name);
    }

    public RiskAssessmentPredictionComponent copy() {
      RiskAssessmentPredictionComponent dst = new RiskAssessmentPredictionComponent();
      copyValues(dst);
      return dst;
    }

    public void copyValues(RiskAssessmentPredictionComponent dst) {
      super.copyValues(dst);
      dst.outcome = outcome == null ? null : outcome.copy();
      dst.probability = probability == null ? null : probability.copy();
      dst.qualitativeRisk = qualitativeRisk == null ? null : qualitativeRisk.copy();
      dst.relativeRisk = relativeRisk == null ? null : relativeRisk.copy();
      dst.when = when == null ? null : when.copy();
      dst.rationale = rationale == null ? null : rationale.copy();
    }

    @Override
    public boolean equalsDeep(Base other_) {
      if (!super.equalsDeep(other_))
        return false;
      if (!(other_ instanceof RiskAssessmentPredictionComponent))
        return false;
      RiskAssessmentPredictionComponent o = (RiskAssessmentPredictionComponent) other_;
      return compareDeep(outcome, o.outcome, true) && compareDeep(probability, o.probability, true)
          && compareDeep(qualitativeRisk, o.qualitativeRisk, true) && compareDeep(relativeRisk, o.relativeRisk, true)
          && compareDeep(when, o.when, true) && compareDeep(rationale, o.rationale, true);
    }

    @Override
    public boolean equalsShallow(Base other_) {
      if (!super.equalsShallow(other_))
        return false;
      if (!(other_ instanceof RiskAssessmentPredictionComponent))
        return false;
      RiskAssessmentPredictionComponent o = (RiskAssessmentPredictionComponent) other_;
      return compareValues(relativeRisk, o.relativeRisk, true) && compareValues(rationale, o.rationale, true);
    }

    public boolean isEmpty() {
      return super.isEmpty()
          && ca.uhn.fhir.util.ElementUtil.isEmpty(outcome, probability, qualitativeRisk, relativeRisk, when, rationale);
    }

    public String fhirType() {
      return "RiskAssessment.prediction";

    }

  }

  /**
   * Business identifier assigned to the risk assessment.
   */
  @Child(name = "identifier", type = {
      Identifier.class }, order = 0, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = true)
  @Description(shortDefinition = "Unique identifier for the assessment", formalDefinition = "Business identifier assigned to the risk assessment.")
  protected List<Identifier> identifier;

  /**
   * A reference to the request that is fulfilled by this risk assessment.
   */
  @Child(name = "basedOn", type = { Reference.class }, order = 1, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Request fulfilled by this assessment", formalDefinition = "A reference to the request that is fulfilled by this risk assessment.")
  protected Reference basedOn;

  /**
   * The actual object that is the target of the reference (A reference to the
   * request that is fulfilled by this risk assessment.)
   */
  protected Resource basedOnTarget;

  /**
   * A reference to a resource that this risk assessment is part of, such as a
   * Procedure.
   */
  @Child(name = "parent", type = { Reference.class }, order = 2, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "Part of this occurrence", formalDefinition = "A reference to a resource that this risk assessment is part of, such as a Procedure.")
  protected Reference parent;

  /**
   * The actual object that is the target of the reference (A reference to a
   * resource that this risk assessment is part of, such as a Procedure.)
   */
  protected Resource parentTarget;

  /**
   * The status of the RiskAssessment, using the same statuses as an Observation.
   */
  @Child(name = "status", type = { CodeType.class }, order = 3, min = 1, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "registered | preliminary | final | amended +", formalDefinition = "The status of the RiskAssessment, using the same statuses as an Observation.")
  @ca.uhn.fhir.model.api.annotation.Binding(valueSet = "http://hl7.org/fhir/ValueSet/observation-status")
  protected Enumeration<RiskAssessmentStatus> status;

  /**
   * The algorithm, process or mechanism used to evaluate the risk.
   */
  @Child(name = "method", type = {
      CodeableConcept.class }, order = 4, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Evaluation mechanism", formalDefinition = "The algorithm, process or mechanism used to evaluate the risk.")
  protected CodeableConcept method;

  /**
   * The type of the risk assessment performed.
   */
  @Child(name = "code", type = { CodeableConcept.class }, order = 5, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Type of assessment", formalDefinition = "The type of the risk assessment performed.")
  protected CodeableConcept code;

  /**
   * The patient or group the risk assessment applies to.
   */
  @Child(name = "subject", type = { Patient.class,
      Group.class }, order = 6, min = 1, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Who/what does assessment apply to?", formalDefinition = "The patient or group the risk assessment applies to.")
  protected Reference subject;

  /**
   * The actual object that is the target of the reference (The patient or group
   * the risk assessment applies to.)
   */
  protected Resource subjectTarget;

  /**
   * The encounter where the assessment was performed.
   */
  @Child(name = "encounter", type = { Encounter.class }, order = 7, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Where was assessment performed?", formalDefinition = "The encounter where the assessment was performed.")
  protected Reference encounter;

  /**
   * The actual object that is the target of the reference (The encounter where
   * the assessment was performed.)
   */
  protected Encounter encounterTarget;

  /**
   * The date (and possibly time) the risk assessment was performed.
   */
  @Child(name = "occurrence", type = { DateTimeType.class,
      Period.class }, order = 8, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "When was assessment made?", formalDefinition = "The date (and possibly time) the risk assessment was performed.")
  protected Type occurrence;

  /**
   * For assessments or prognosis specific to a particular condition, indicates
   * the condition being assessed.
   */
  @Child(name = "condition", type = { Condition.class }, order = 9, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Condition assessed", formalDefinition = "For assessments or prognosis specific to a particular condition, indicates the condition being assessed.")
  protected Reference condition;

  /**
   * The actual object that is the target of the reference (For assessments or
   * prognosis specific to a particular condition, indicates the condition being
   * assessed.)
   */
  protected Condition conditionTarget;

  /**
   * The provider or software application that performed the assessment.
   */
  @Child(name = "performer", type = { Practitioner.class, PractitionerRole.class,
      Device.class }, order = 10, min = 0, max = 1, modifier = false, summary = true)
  @Description(shortDefinition = "Who did assessment?", formalDefinition = "The provider or software application that performed the assessment.")
  protected Reference performer;

  /**
   * The actual object that is the target of the reference (The provider or
   * software application that performed the assessment.)
   */
  protected Resource performerTarget;

  /**
   * The reason the risk assessment was performed.
   */
  @Child(name = "reasonCode", type = {
      CodeableConcept.class }, order = 11, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Why the assessment was necessary?", formalDefinition = "The reason the risk assessment was performed.")
  protected List<CodeableConcept> reasonCode;

  /**
   * Resources supporting the reason the risk assessment was performed.
   */
  @Child(name = "reasonReference", type = { Condition.class, Observation.class, DiagnosticReport.class,
      DocumentReference.class }, order = 12, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Why the assessment was necessary?", formalDefinition = "Resources supporting the reason the risk assessment was performed.")
  protected List<Reference> reasonReference;
  /**
   * The actual objects that are the target of the reference (Resources supporting
   * the reason the risk assessment was performed.)
   */
  protected List<Resource> reasonReferenceTarget;

  /**
   * Indicates the source data considered as part of the assessment (for example,
   * FamilyHistory, Observations, Procedures, Conditions, etc.).
   */
  @Child(name = "basis", type = {
      Reference.class }, order = 13, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Information used in assessment", formalDefinition = "Indicates the source data considered as part of the assessment (for example, FamilyHistory, Observations, Procedures, Conditions, etc.).")
  protected List<Reference> basis;
  /**
   * The actual objects that are the target of the reference (Indicates the source
   * data considered as part of the assessment (for example, FamilyHistory,
   * Observations, Procedures, Conditions, etc.).)
   */
  protected List<Resource> basisTarget;

  /**
   * Describes the expected outcome for the subject.
   */
  @Child(name = "prediction", type = {}, order = 14, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Outcome predicted", formalDefinition = "Describes the expected outcome for the subject.")
  protected List<RiskAssessmentPredictionComponent> prediction;

  /**
   * A description of the steps that might be taken to reduce the identified
   * risk(s).
   */
  @Child(name = "mitigation", type = {
      StringType.class }, order = 15, min = 0, max = 1, modifier = false, summary = false)
  @Description(shortDefinition = "How to reduce risk", formalDefinition = "A description of the steps that might be taken to reduce the identified risk(s).")
  protected StringType mitigation;

  /**
   * Additional comments about the risk assessment.
   */
  @Child(name = "note", type = {
      Annotation.class }, order = 16, min = 0, max = Child.MAX_UNLIMITED, modifier = false, summary = false)
  @Description(shortDefinition = "Comments on the risk assessment", formalDefinition = "Additional comments about the risk assessment.")
  protected List<Annotation> note;

  private static final long serialVersionUID = -2137260218L;

  /**
   * Constructor
   */
  public RiskAssessment() {
    super();
  }

  /**
   * Constructor
   */
  public RiskAssessment(Enumeration<RiskAssessmentStatus> status, Reference subject) {
    super();
    this.status = status;
    this.subject = subject;
  }

  /**
   * @return {@link #identifier} (Business identifier assigned to the risk
   *         assessment.)
   */
  public List<Identifier> getIdentifier() {
    if (this.identifier == null)
      this.identifier = new ArrayList<Identifier>();
    return this.identifier;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setIdentifier(List<Identifier> theIdentifier) {
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

  public RiskAssessment addIdentifier(Identifier t) { // 3
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
   * @return {@link #basedOn} (A reference to the request that is fulfilled by
   *         this risk assessment.)
   */
  public Reference getBasedOn() {
    if (this.basedOn == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.basedOn");
      else if (Configuration.doAutoCreate())
        this.basedOn = new Reference(); // cc
    return this.basedOn;
  }

  public boolean hasBasedOn() {
    return this.basedOn != null && !this.basedOn.isEmpty();
  }

  /**
   * @param value {@link #basedOn} (A reference to the request that is fulfilled
   *              by this risk assessment.)
   */
  public RiskAssessment setBasedOn(Reference value) {
    this.basedOn = value;
    return this;
  }

  /**
   * @return {@link #basedOn} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (A reference to the
   *         request that is fulfilled by this risk assessment.)
   */
  public Resource getBasedOnTarget() {
    return this.basedOnTarget;
  }

  /**
   * @param value {@link #basedOn} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (A reference to
   *              the request that is fulfilled by this risk assessment.)
   */
  public RiskAssessment setBasedOnTarget(Resource value) {
    this.basedOnTarget = value;
    return this;
  }

  /**
   * @return {@link #parent} (A reference to a resource that this risk assessment
   *         is part of, such as a Procedure.)
   */
  public Reference getParent() {
    if (this.parent == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.parent");
      else if (Configuration.doAutoCreate())
        this.parent = new Reference(); // cc
    return this.parent;
  }

  public boolean hasParent() {
    return this.parent != null && !this.parent.isEmpty();
  }

  /**
   * @param value {@link #parent} (A reference to a resource that this risk
   *              assessment is part of, such as a Procedure.)
   */
  public RiskAssessment setParent(Reference value) {
    this.parent = value;
    return this;
  }

  /**
   * @return {@link #parent} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (A reference to a
   *         resource that this risk assessment is part of, such as a Procedure.)
   */
  public Resource getParentTarget() {
    return this.parentTarget;
  }

  /**
   * @param value {@link #parent} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (A reference to a
   *              resource that this risk assessment is part of, such as a
   *              Procedure.)
   */
  public RiskAssessment setParentTarget(Resource value) {
    this.parentTarget = value;
    return this;
  }

  /**
   * @return {@link #status} (The status of the RiskAssessment, using the same
   *         statuses as an Observation.). This is the underlying object with id,
   *         value and extensions. The accessor "getStatus" gives direct access to
   *         the value
   */
  public Enumeration<RiskAssessmentStatus> getStatusElement() {
    if (this.status == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.status");
      else if (Configuration.doAutoCreate())
        this.status = new Enumeration<RiskAssessmentStatus>(new RiskAssessmentStatusEnumFactory()); // bb
    return this.status;
  }

  public boolean hasStatusElement() {
    return this.status != null && !this.status.isEmpty();
  }

  public boolean hasStatus() {
    return this.status != null && !this.status.isEmpty();
  }

  /**
   * @param value {@link #status} (The status of the RiskAssessment, using the
   *              same statuses as an Observation.). This is the underlying object
   *              with id, value and extensions. The accessor "getStatus" gives
   *              direct access to the value
   */
  public RiskAssessment setStatusElement(Enumeration<RiskAssessmentStatus> value) {
    this.status = value;
    return this;
  }

  /**
   * @return The status of the RiskAssessment, using the same statuses as an
   *         Observation.
   */
  public RiskAssessmentStatus getStatus() {
    return this.status == null ? null : this.status.getValue();
  }

  /**
   * @param value The status of the RiskAssessment, using the same statuses as an
   *              Observation.
   */
  public RiskAssessment setStatus(RiskAssessmentStatus value) {
    if (this.status == null)
      this.status = new Enumeration<RiskAssessmentStatus>(new RiskAssessmentStatusEnumFactory());
    this.status.setValue(value);
    return this;
  }

  /**
   * @return {@link #method} (The algorithm, process or mechanism used to evaluate
   *         the risk.)
   */
  public CodeableConcept getMethod() {
    if (this.method == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.method");
      else if (Configuration.doAutoCreate())
        this.method = new CodeableConcept(); // cc
    return this.method;
  }

  public boolean hasMethod() {
    return this.method != null && !this.method.isEmpty();
  }

  /**
   * @param value {@link #method} (The algorithm, process or mechanism used to
   *              evaluate the risk.)
   */
  public RiskAssessment setMethod(CodeableConcept value) {
    this.method = value;
    return this;
  }

  /**
   * @return {@link #code} (The type of the risk assessment performed.)
   */
  public CodeableConcept getCode() {
    if (this.code == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.code");
      else if (Configuration.doAutoCreate())
        this.code = new CodeableConcept(); // cc
    return this.code;
  }

  public boolean hasCode() {
    return this.code != null && !this.code.isEmpty();
  }

  /**
   * @param value {@link #code} (The type of the risk assessment performed.)
   */
  public RiskAssessment setCode(CodeableConcept value) {
    this.code = value;
    return this;
  }

  /**
   * @return {@link #subject} (The patient or group the risk assessment applies
   *         to.)
   */
  public Reference getSubject() {
    if (this.subject == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.subject");
      else if (Configuration.doAutoCreate())
        this.subject = new Reference(); // cc
    return this.subject;
  }

  public boolean hasSubject() {
    return this.subject != null && !this.subject.isEmpty();
  }

  /**
   * @param value {@link #subject} (The patient or group the risk assessment
   *              applies to.)
   */
  public RiskAssessment setSubject(Reference value) {
    this.subject = value;
    return this;
  }

  /**
   * @return {@link #subject} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The patient or group
   *         the risk assessment applies to.)
   */
  public Resource getSubjectTarget() {
    return this.subjectTarget;
  }

  /**
   * @param value {@link #subject} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The patient or
   *              group the risk assessment applies to.)
   */
  public RiskAssessment setSubjectTarget(Resource value) {
    this.subjectTarget = value;
    return this;
  }

  /**
   * @return {@link #encounter} (The encounter where the assessment was
   *         performed.)
   */
  public Reference getEncounter() {
    if (this.encounter == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.encounter");
      else if (Configuration.doAutoCreate())
        this.encounter = new Reference(); // cc
    return this.encounter;
  }

  public boolean hasEncounter() {
    return this.encounter != null && !this.encounter.isEmpty();
  }

  /**
   * @param value {@link #encounter} (The encounter where the assessment was
   *              performed.)
   */
  public RiskAssessment setEncounter(Reference value) {
    this.encounter = value;
    return this;
  }

  /**
   * @return {@link #encounter} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The encounter where
   *         the assessment was performed.)
   */
  public Encounter getEncounterTarget() {
    if (this.encounterTarget == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.encounter");
      else if (Configuration.doAutoCreate())
        this.encounterTarget = new Encounter(); // aa
    return this.encounterTarget;
  }

  /**
   * @param value {@link #encounter} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The encounter
   *              where the assessment was performed.)
   */
  public RiskAssessment setEncounterTarget(Encounter value) {
    this.encounterTarget = value;
    return this;
  }

  /**
   * @return {@link #occurrence} (The date (and possibly time) the risk assessment
   *         was performed.)
   */
  public Type getOccurrence() {
    return this.occurrence;
  }

  /**
   * @return {@link #occurrence} (The date (and possibly time) the risk assessment
   *         was performed.)
   */
  public DateTimeType getOccurrenceDateTimeType() throws FHIRException {
    if (this.occurrence == null)
      this.occurrence = new DateTimeType();
    if (!(this.occurrence instanceof DateTimeType))
      throw new FHIRException("Type mismatch: the type DateTimeType was expected, but "
          + this.occurrence.getClass().getName() + " was encountered");
    return (DateTimeType) this.occurrence;
  }

  public boolean hasOccurrenceDateTimeType() {
      return this.occurrence instanceof DateTimeType;
  }

  /**
   * @return {@link #occurrence} (The date (and possibly time) the risk assessment
   *         was performed.)
   */
  public Period getOccurrencePeriod() throws FHIRException {
    if (this.occurrence == null)
      this.occurrence = new Period();
    if (!(this.occurrence instanceof Period))
      throw new FHIRException("Type mismatch: the type Period was expected, but " + this.occurrence.getClass().getName()
          + " was encountered");
    return (Period) this.occurrence;
  }

  public boolean hasOccurrencePeriod() {
      return this.occurrence instanceof Period;
  }

  public boolean hasOccurrence() {
    return this.occurrence != null && !this.occurrence.isEmpty();
  }

  /**
   * @param value {@link #occurrence} (The date (and possibly time) the risk
   *              assessment was performed.)
   */
  public RiskAssessment setOccurrence(Type value) {
    if (value != null && !(value instanceof DateTimeType || value instanceof Period))
      throw new Error("Not the right type for RiskAssessment.occurrence[x]: " + value.fhirType());
    this.occurrence = value;
    return this;
  }

  /**
   * @return {@link #condition} (For assessments or prognosis specific to a
   *         particular condition, indicates the condition being assessed.)
   */
  public Reference getCondition() {
    if (this.condition == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.condition");
      else if (Configuration.doAutoCreate())
        this.condition = new Reference(); // cc
    return this.condition;
  }

  public boolean hasCondition() {
    return this.condition != null && !this.condition.isEmpty();
  }

  /**
   * @param value {@link #condition} (For assessments or prognosis specific to a
   *              particular condition, indicates the condition being assessed.)
   */
  public RiskAssessment setCondition(Reference value) {
    this.condition = value;
    return this;
  }

  /**
   * @return {@link #condition} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (For assessments or
   *         prognosis specific to a particular condition, indicates the condition
   *         being assessed.)
   */
  public Condition getConditionTarget() {
    if (this.conditionTarget == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.condition");
      else if (Configuration.doAutoCreate())
        this.conditionTarget = new Condition(); // aa
    return this.conditionTarget;
  }

  /**
   * @param value {@link #condition} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (For assessments
   *              or prognosis specific to a particular condition, indicates the
   *              condition being assessed.)
   */
  public RiskAssessment setConditionTarget(Condition value) {
    this.conditionTarget = value;
    return this;
  }

  /**
   * @return {@link #performer} (The provider or software application that
   *         performed the assessment.)
   */
  public Reference getPerformer() {
    if (this.performer == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.performer");
      else if (Configuration.doAutoCreate())
        this.performer = new Reference(); // cc
    return this.performer;
  }

  public boolean hasPerformer() {
    return this.performer != null && !this.performer.isEmpty();
  }

  /**
   * @param value {@link #performer} (The provider or software application that
   *              performed the assessment.)
   */
  public RiskAssessment setPerformer(Reference value) {
    this.performer = value;
    return this;
  }

  /**
   * @return {@link #performer} The actual object that is the target of the
   *         reference. The reference library doesn't populate this, but you can
   *         use it to hold the resource if you resolve it. (The provider or
   *         software application that performed the assessment.)
   */
  public Resource getPerformerTarget() {
    return this.performerTarget;
  }

  /**
   * @param value {@link #performer} The actual object that is the target of the
   *              reference. The reference library doesn't use these, but you can
   *              use it to hold the resource if you resolve it. (The provider or
   *              software application that performed the assessment.)
   */
  public RiskAssessment setPerformerTarget(Resource value) {
    this.performerTarget = value;
    return this;
  }

  /**
   * @return {@link #reasonCode} (The reason the risk assessment was performed.)
   */
  public List<CodeableConcept> getReasonCode() {
    if (this.reasonCode == null)
      this.reasonCode = new ArrayList<CodeableConcept>();
    return this.reasonCode;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setReasonCode(List<CodeableConcept> theReasonCode) {
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

  public RiskAssessment addReasonCode(CodeableConcept t) { // 3
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
   * @return {@link #reasonReference} (Resources supporting the reason the risk
   *         assessment was performed.)
   */
  public List<Reference> getReasonReference() {
    if (this.reasonReference == null)
      this.reasonReference = new ArrayList<Reference>();
    return this.reasonReference;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setReasonReference(List<Reference> theReasonReference) {
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

  public RiskAssessment addReasonReference(Reference t) { // 3
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
   * @return {@link #basis} (Indicates the source data considered as part of the
   *         assessment (for example, FamilyHistory, Observations, Procedures,
   *         Conditions, etc.).)
   */
  public List<Reference> getBasis() {
    if (this.basis == null)
      this.basis = new ArrayList<Reference>();
    return this.basis;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setBasis(List<Reference> theBasis) {
    this.basis = theBasis;
    return this;
  }

  public boolean hasBasis() {
    if (this.basis == null)
      return false;
    for (Reference item : this.basis)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public Reference addBasis() { // 3
    Reference t = new Reference();
    if (this.basis == null)
      this.basis = new ArrayList<Reference>();
    this.basis.add(t);
    return t;
  }

  public RiskAssessment addBasis(Reference t) { // 3
    if (t == null)
      return this;
    if (this.basis == null)
      this.basis = new ArrayList<Reference>();
    this.basis.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #basis}, creating it
   *         if it does not already exist
   */
  public Reference getBasisFirstRep() {
    if (getBasis().isEmpty()) {
      addBasis();
    }
    return getBasis().get(0);
  }

  /**
   * @return {@link #prediction} (Describes the expected outcome for the subject.)
   */
  public List<RiskAssessmentPredictionComponent> getPrediction() {
    if (this.prediction == null)
      this.prediction = new ArrayList<RiskAssessmentPredictionComponent>();
    return this.prediction;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setPrediction(List<RiskAssessmentPredictionComponent> thePrediction) {
    this.prediction = thePrediction;
    return this;
  }

  public boolean hasPrediction() {
    if (this.prediction == null)
      return false;
    for (RiskAssessmentPredictionComponent item : this.prediction)
      if (!item.isEmpty())
        return true;
    return false;
  }

  public RiskAssessmentPredictionComponent addPrediction() { // 3
    RiskAssessmentPredictionComponent t = new RiskAssessmentPredictionComponent();
    if (this.prediction == null)
      this.prediction = new ArrayList<RiskAssessmentPredictionComponent>();
    this.prediction.add(t);
    return t;
  }

  public RiskAssessment addPrediction(RiskAssessmentPredictionComponent t) { // 3
    if (t == null)
      return this;
    if (this.prediction == null)
      this.prediction = new ArrayList<RiskAssessmentPredictionComponent>();
    this.prediction.add(t);
    return this;
  }

  /**
   * @return The first repetition of repeating field {@link #prediction}, creating
   *         it if it does not already exist
   */
  public RiskAssessmentPredictionComponent getPredictionFirstRep() {
    if (getPrediction().isEmpty()) {
      addPrediction();
    }
    return getPrediction().get(0);
  }

  /**
   * @return {@link #mitigation} (A description of the steps that might be taken
   *         to reduce the identified risk(s).). This is the underlying object
   *         with id, value and extensions. The accessor "getMitigation" gives
   *         direct access to the value
   */
  public StringType getMitigationElement() {
    if (this.mitigation == null)
      if (Configuration.errorOnAutoCreate())
        throw new Error("Attempt to auto-create RiskAssessment.mitigation");
      else if (Configuration.doAutoCreate())
        this.mitigation = new StringType(); // bb
    return this.mitigation;
  }

  public boolean hasMitigationElement() {
    return this.mitigation != null && !this.mitigation.isEmpty();
  }

  public boolean hasMitigation() {
    return this.mitigation != null && !this.mitigation.isEmpty();
  }

  /**
   * @param value {@link #mitigation} (A description of the steps that might be
   *              taken to reduce the identified risk(s).). This is the underlying
   *              object with id, value and extensions. The accessor
   *              "getMitigation" gives direct access to the value
   */
  public RiskAssessment setMitigationElement(StringType value) {
    this.mitigation = value;
    return this;
  }

  /**
   * @return A description of the steps that might be taken to reduce the
   *         identified risk(s).
   */
  public String getMitigation() {
    return this.mitigation == null ? null : this.mitigation.getValue();
  }

  /**
   * @param value A description of the steps that might be taken to reduce the
   *              identified risk(s).
   */
  public RiskAssessment setMitigation(String value) {
    if (Utilities.noString(value))
      this.mitigation = null;
    else {
      if (this.mitigation == null)
        this.mitigation = new StringType();
      this.mitigation.setValue(value);
    }
    return this;
  }

  /**
   * @return {@link #note} (Additional comments about the risk assessment.)
   */
  public List<Annotation> getNote() {
    if (this.note == null)
      this.note = new ArrayList<Annotation>();
    return this.note;
  }

  /**
   * @return Returns a reference to <code>this</code> for easy method chaining
   */
  public RiskAssessment setNote(List<Annotation> theNote) {
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

  public RiskAssessment addNote(Annotation t) { // 3
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

  protected void listChildren(List<Property> children) {
    super.listChildren(children);
    children.add(new Property("identifier", "Identifier", "Business identifier assigned to the risk assessment.", 0,
        java.lang.Integer.MAX_VALUE, identifier));
    children.add(new Property("basedOn", "Reference(Any)",
        "A reference to the request that is fulfilled by this risk assessment.", 0, 1, basedOn));
    children.add(new Property("parent", "Reference(Any)",
        "A reference to a resource that this risk assessment is part of, such as a Procedure.", 0, 1, parent));
    children.add(new Property("status", "code",
        "The status of the RiskAssessment, using the same statuses as an Observation.", 0, 1, status));
    children.add(new Property("method", "CodeableConcept",
        "The algorithm, process or mechanism used to evaluate the risk.", 0, 1, method));
    children.add(new Property("code", "CodeableConcept", "The type of the risk assessment performed.", 0, 1, code));
    children.add(new Property("subject", "Reference(Patient|Group)",
        "The patient or group the risk assessment applies to.", 0, 1, subject));
    children.add(new Property("encounter", "Reference(Encounter)", "The encounter where the assessment was performed.",
        0, 1, encounter));
    children.add(new Property("occurrence[x]", "dateTime|Period",
        "The date (and possibly time) the risk assessment was performed.", 0, 1, occurrence));
    children.add(new Property("condition", "Reference(Condition)",
        "For assessments or prognosis specific to a particular condition, indicates the condition being assessed.", 0,
        1, condition));
    children.add(new Property("performer", "Reference(Practitioner|PractitionerRole|Device)",
        "The provider or software application that performed the assessment.", 0, 1, performer));
    children.add(new Property("reasonCode", "CodeableConcept", "The reason the risk assessment was performed.", 0,
        java.lang.Integer.MAX_VALUE, reasonCode));
    children.add(new Property("reasonReference", "Reference(Condition|Observation|DiagnosticReport|DocumentReference)",
        "Resources supporting the reason the risk assessment was performed.", 0, java.lang.Integer.MAX_VALUE,
        reasonReference));
    children.add(new Property("basis", "Reference(Any)",
        "Indicates the source data considered as part of the assessment (for example, FamilyHistory, Observations, Procedures, Conditions, etc.).",
        0, java.lang.Integer.MAX_VALUE, basis));
    children.add(new Property("prediction", "", "Describes the expected outcome for the subject.", 0,
        java.lang.Integer.MAX_VALUE, prediction));
    children.add(new Property("mitigation", "string",
        "A description of the steps that might be taken to reduce the identified risk(s).", 0, 1, mitigation));
    children.add(new Property("note", "Annotation", "Additional comments about the risk assessment.", 0,
        java.lang.Integer.MAX_VALUE, note));
  }

  @Override
  public Property getNamedProperty(int _hash, String _name, boolean _checkValid) throws FHIRException {
    switch (_hash) {
    case -1618432855:
      /* identifier */ return new Property("identifier", "Identifier",
          "Business identifier assigned to the risk assessment.", 0, java.lang.Integer.MAX_VALUE, identifier);
    case -332612366:
      /* basedOn */ return new Property("basedOn", "Reference(Any)",
          "A reference to the request that is fulfilled by this risk assessment.", 0, 1, basedOn);
    case -995424086:
      /* parent */ return new Property("parent", "Reference(Any)",
          "A reference to a resource that this risk assessment is part of, such as a Procedure.", 0, 1, parent);
    case -892481550:
      /* status */ return new Property("status", "code",
          "The status of the RiskAssessment, using the same statuses as an Observation.", 0, 1, status);
    case -1077554975:
      /* method */ return new Property("method", "CodeableConcept",
          "The algorithm, process or mechanism used to evaluate the risk.", 0, 1, method);
    case 3059181:
      /* code */ return new Property("code", "CodeableConcept", "The type of the risk assessment performed.", 0, 1,
          code);
    case -1867885268:
      /* subject */ return new Property("subject", "Reference(Patient|Group)",
          "The patient or group the risk assessment applies to.", 0, 1, subject);
    case 1524132147:
      /* encounter */ return new Property("encounter", "Reference(Encounter)",
          "The encounter where the assessment was performed.", 0, 1, encounter);
    case -2022646513:
      /* occurrence[x] */ return new Property("occurrence[x]", "dateTime|Period",
          "The date (and possibly time) the risk assessment was performed.", 0, 1, occurrence);
    case 1687874001:
      /* occurrence */ return new Property("occurrence[x]", "dateTime|Period",
          "The date (and possibly time) the risk assessment was performed.", 0, 1, occurrence);
    case -298443636:
      /* occurrenceDateTime */ return new Property("occurrence[x]", "dateTime|Period",
          "The date (and possibly time) the risk assessment was performed.", 0, 1, occurrence);
    case 1397156594:
      /* occurrencePeriod */ return new Property("occurrence[x]", "dateTime|Period",
          "The date (and possibly time) the risk assessment was performed.", 0, 1, occurrence);
    case -861311717:
      /* condition */ return new Property("condition", "Reference(Condition)",
          "For assessments or prognosis specific to a particular condition, indicates the condition being assessed.", 0,
          1, condition);
    case 481140686:
      /* performer */ return new Property("performer", "Reference(Practitioner|PractitionerRole|Device)",
          "The provider or software application that performed the assessment.", 0, 1, performer);
    case 722137681:
      /* reasonCode */ return new Property("reasonCode", "CodeableConcept",
          "The reason the risk assessment was performed.", 0, java.lang.Integer.MAX_VALUE, reasonCode);
    case -1146218137:
      /* reasonReference */ return new Property("reasonReference",
          "Reference(Condition|Observation|DiagnosticReport|DocumentReference)",
          "Resources supporting the reason the risk assessment was performed.", 0, java.lang.Integer.MAX_VALUE,
          reasonReference);
    case 93508670:
      /* basis */ return new Property("basis", "Reference(Any)",
          "Indicates the source data considered as part of the assessment (for example, FamilyHistory, Observations, Procedures, Conditions, etc.).",
          0, java.lang.Integer.MAX_VALUE, basis);
    case 1161234575:
      /* prediction */ return new Property("prediction", "", "Describes the expected outcome for the subject.", 0,
          java.lang.Integer.MAX_VALUE, prediction);
    case 1293793087:
      /* mitigation */ return new Property("mitigation", "string",
          "A description of the steps that might be taken to reduce the identified risk(s).", 0, 1, mitigation);
    case 3387378:
      /* note */ return new Property("note", "Annotation", "Additional comments about the risk assessment.", 0,
          java.lang.Integer.MAX_VALUE, note);
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
      /* basedOn */ return this.basedOn == null ? new Base[0] : new Base[] { this.basedOn }; // Reference
    case -995424086:
      /* parent */ return this.parent == null ? new Base[0] : new Base[] { this.parent }; // Reference
    case -892481550:
      /* status */ return this.status == null ? new Base[0] : new Base[] { this.status }; // Enumeration<RiskAssessmentStatus>
    case -1077554975:
      /* method */ return this.method == null ? new Base[0] : new Base[] { this.method }; // CodeableConcept
    case 3059181:
      /* code */ return this.code == null ? new Base[0] : new Base[] { this.code }; // CodeableConcept
    case -1867885268:
      /* subject */ return this.subject == null ? new Base[0] : new Base[] { this.subject }; // Reference
    case 1524132147:
      /* encounter */ return this.encounter == null ? new Base[0] : new Base[] { this.encounter }; // Reference
    case 1687874001:
      /* occurrence */ return this.occurrence == null ? new Base[0] : new Base[] { this.occurrence }; // Type
    case -861311717:
      /* condition */ return this.condition == null ? new Base[0] : new Base[] { this.condition }; // Reference
    case 481140686:
      /* performer */ return this.performer == null ? new Base[0] : new Base[] { this.performer }; // Reference
    case 722137681:
      /* reasonCode */ return this.reasonCode == null ? new Base[0]
          : this.reasonCode.toArray(new Base[this.reasonCode.size()]); // CodeableConcept
    case -1146218137:
      /* reasonReference */ return this.reasonReference == null ? new Base[0]
          : this.reasonReference.toArray(new Base[this.reasonReference.size()]); // Reference
    case 93508670:
      /* basis */ return this.basis == null ? new Base[0] : this.basis.toArray(new Base[this.basis.size()]); // Reference
    case 1161234575:
      /* prediction */ return this.prediction == null ? new Base[0]
          : this.prediction.toArray(new Base[this.prediction.size()]); // RiskAssessmentPredictionComponent
    case 1293793087:
      /* mitigation */ return this.mitigation == null ? new Base[0] : new Base[] { this.mitigation }; // StringType
    case 3387378:
      /* note */ return this.note == null ? new Base[0] : this.note.toArray(new Base[this.note.size()]); // Annotation
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
      this.basedOn = castToReference(value); // Reference
      return value;
    case -995424086: // parent
      this.parent = castToReference(value); // Reference
      return value;
    case -892481550: // status
      value = new RiskAssessmentStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<RiskAssessmentStatus>
      return value;
    case -1077554975: // method
      this.method = castToCodeableConcept(value); // CodeableConcept
      return value;
    case 3059181: // code
      this.code = castToCodeableConcept(value); // CodeableConcept
      return value;
    case -1867885268: // subject
      this.subject = castToReference(value); // Reference
      return value;
    case 1524132147: // encounter
      this.encounter = castToReference(value); // Reference
      return value;
    case 1687874001: // occurrence
      this.occurrence = castToType(value); // Type
      return value;
    case -861311717: // condition
      this.condition = castToReference(value); // Reference
      return value;
    case 481140686: // performer
      this.performer = castToReference(value); // Reference
      return value;
    case 722137681: // reasonCode
      this.getReasonCode().add(castToCodeableConcept(value)); // CodeableConcept
      return value;
    case -1146218137: // reasonReference
      this.getReasonReference().add(castToReference(value)); // Reference
      return value;
    case 93508670: // basis
      this.getBasis().add(castToReference(value)); // Reference
      return value;
    case 1161234575: // prediction
      this.getPrediction().add((RiskAssessmentPredictionComponent) value); // RiskAssessmentPredictionComponent
      return value;
    case 1293793087: // mitigation
      this.mitigation = castToString(value); // StringType
      return value;
    case 3387378: // note
      this.getNote().add(castToAnnotation(value)); // Annotation
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
      this.basedOn = castToReference(value); // Reference
    } else if (name.equals("parent")) {
      this.parent = castToReference(value); // Reference
    } else if (name.equals("status")) {
      value = new RiskAssessmentStatusEnumFactory().fromType(castToCode(value));
      this.status = (Enumeration) value; // Enumeration<RiskAssessmentStatus>
    } else if (name.equals("method")) {
      this.method = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("code")) {
      this.code = castToCodeableConcept(value); // CodeableConcept
    } else if (name.equals("subject")) {
      this.subject = castToReference(value); // Reference
    } else if (name.equals("encounter")) {
      this.encounter = castToReference(value); // Reference
    } else if (name.equals("occurrence[x]")) {
      this.occurrence = castToType(value); // Type
    } else if (name.equals("condition")) {
      this.condition = castToReference(value); // Reference
    } else if (name.equals("performer")) {
      this.performer = castToReference(value); // Reference
    } else if (name.equals("reasonCode")) {
      this.getReasonCode().add(castToCodeableConcept(value));
    } else if (name.equals("reasonReference")) {
      this.getReasonReference().add(castToReference(value));
    } else if (name.equals("basis")) {
      this.getBasis().add(castToReference(value));
    } else if (name.equals("prediction")) {
      this.getPrediction().add((RiskAssessmentPredictionComponent) value);
    } else if (name.equals("mitigation")) {
      this.mitigation = castToString(value); // StringType
    } else if (name.equals("note")) {
      this.getNote().add(castToAnnotation(value));
    } else
      return super.setProperty(name, value);
    return value;
  }

  @Override
  public void removeChild(String name, Base value) throws FHIRException {
    if (name.equals("identifier")) {
      this.getIdentifier().remove(castToIdentifier(value));
    } else if (name.equals("basedOn")) {
      this.basedOn = null;
    } else if (name.equals("parent")) {
      this.parent = null;
    } else if (name.equals("status")) {
      this.status = null;
    } else if (name.equals("method")) {
      this.method = null;
    } else if (name.equals("code")) {
      this.code = null;
    } else if (name.equals("subject")) {
      this.subject = null;
    } else if (name.equals("encounter")) {
      this.encounter = null;
    } else if (name.equals("occurrence[x]")) {
      this.occurrence = null;
    } else if (name.equals("condition")) {
      this.condition = null;
    } else if (name.equals("performer")) {
      this.performer = null;
    } else if (name.equals("reasonCode")) {
      this.getReasonCode().remove(castToCodeableConcept(value));
    } else if (name.equals("reasonReference")) {
      this.getReasonReference().remove(castToReference(value));
    } else if (name.equals("basis")) {
      this.getBasis().remove(castToReference(value));
    } else if (name.equals("prediction")) {
      this.getPrediction().remove((RiskAssessmentPredictionComponent) value);
    } else if (name.equals("mitigation")) {
      this.mitigation = null;
    } else if (name.equals("note")) {
      this.getNote().remove(castToAnnotation(value));
    } else
      super.removeChild(name, value);
    
  }

  @Override
  public Base makeProperty(int hash, String name) throws FHIRException {
    switch (hash) {
    case -1618432855:
      return addIdentifier();
    case -332612366:
      return getBasedOn();
    case -995424086:
      return getParent();
    case -892481550:
      return getStatusElement();
    case -1077554975:
      return getMethod();
    case 3059181:
      return getCode();
    case -1867885268:
      return getSubject();
    case 1524132147:
      return getEncounter();
    case -2022646513:
      return getOccurrence();
    case 1687874001:
      return getOccurrence();
    case -861311717:
      return getCondition();
    case 481140686:
      return getPerformer();
    case 722137681:
      return addReasonCode();
    case -1146218137:
      return addReasonReference();
    case 93508670:
      return addBasis();
    case 1161234575:
      return addPrediction();
    case 1293793087:
      return getMitigationElement();
    case 3387378:
      return addNote();
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
    case -995424086:
      /* parent */ return new String[] { "Reference" };
    case -892481550:
      /* status */ return new String[] { "code" };
    case -1077554975:
      /* method */ return new String[] { "CodeableConcept" };
    case 3059181:
      /* code */ return new String[] { "CodeableConcept" };
    case -1867885268:
      /* subject */ return new String[] { "Reference" };
    case 1524132147:
      /* encounter */ return new String[] { "Reference" };
    case 1687874001:
      /* occurrence */ return new String[] { "dateTime", "Period" };
    case -861311717:
      /* condition */ return new String[] { "Reference" };
    case 481140686:
      /* performer */ return new String[] { "Reference" };
    case 722137681:
      /* reasonCode */ return new String[] { "CodeableConcept" };
    case -1146218137:
      /* reasonReference */ return new String[] { "Reference" };
    case 93508670:
      /* basis */ return new String[] { "Reference" };
    case 1161234575:
      /* prediction */ return new String[] {};
    case 1293793087:
      /* mitigation */ return new String[] { "string" };
    case 3387378:
      /* note */ return new String[] { "Annotation" };
    default:
      return super.getTypesForProperty(hash, name);
    }

  }

  @Override
  public Base addChild(String name) throws FHIRException {
    if (name.equals("identifier")) {
      return addIdentifier();
    } else if (name.equals("basedOn")) {
      this.basedOn = new Reference();
      return this.basedOn;
    } else if (name.equals("parent")) {
      this.parent = new Reference();
      return this.parent;
    } else if (name.equals("status")) {
      throw new FHIRException("Cannot call addChild on a singleton property RiskAssessment.status");
    } else if (name.equals("method")) {
      this.method = new CodeableConcept();
      return this.method;
    } else if (name.equals("code")) {
      this.code = new CodeableConcept();
      return this.code;
    } else if (name.equals("subject")) {
      this.subject = new Reference();
      return this.subject;
    } else if (name.equals("encounter")) {
      this.encounter = new Reference();
      return this.encounter;
    } else if (name.equals("occurrenceDateTime")) {
      this.occurrence = new DateTimeType();
      return this.occurrence;
    } else if (name.equals("occurrencePeriod")) {
      this.occurrence = new Period();
      return this.occurrence;
    } else if (name.equals("condition")) {
      this.condition = new Reference();
      return this.condition;
    } else if (name.equals("performer")) {
      this.performer = new Reference();
      return this.performer;
    } else if (name.equals("reasonCode")) {
      return addReasonCode();
    } else if (name.equals("reasonReference")) {
      return addReasonReference();
    } else if (name.equals("basis")) {
      return addBasis();
    } else if (name.equals("prediction")) {
      return addPrediction();
    } else if (name.equals("mitigation")) {
      throw new FHIRException("Cannot call addChild on a singleton property RiskAssessment.mitigation");
    } else if (name.equals("note")) {
      return addNote();
    } else
      return super.addChild(name);
  }

  public String fhirType() {
    return "RiskAssessment";

  }

  public RiskAssessment copy() {
    RiskAssessment dst = new RiskAssessment();
    copyValues(dst);
    return dst;
  }

  public void copyValues(RiskAssessment dst) {
    super.copyValues(dst);
    if (identifier != null) {
      dst.identifier = new ArrayList<Identifier>();
      for (Identifier i : identifier)
        dst.identifier.add(i.copy());
    }
    ;
    dst.basedOn = basedOn == null ? null : basedOn.copy();
    dst.parent = parent == null ? null : parent.copy();
    dst.status = status == null ? null : status.copy();
    dst.method = method == null ? null : method.copy();
    dst.code = code == null ? null : code.copy();
    dst.subject = subject == null ? null : subject.copy();
    dst.encounter = encounter == null ? null : encounter.copy();
    dst.occurrence = occurrence == null ? null : occurrence.copy();
    dst.condition = condition == null ? null : condition.copy();
    dst.performer = performer == null ? null : performer.copy();
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
    if (basis != null) {
      dst.basis = new ArrayList<Reference>();
      for (Reference i : basis)
        dst.basis.add(i.copy());
    }
    ;
    if (prediction != null) {
      dst.prediction = new ArrayList<RiskAssessmentPredictionComponent>();
      for (RiskAssessmentPredictionComponent i : prediction)
        dst.prediction.add(i.copy());
    }
    ;
    dst.mitigation = mitigation == null ? null : mitigation.copy();
    if (note != null) {
      dst.note = new ArrayList<Annotation>();
      for (Annotation i : note)
        dst.note.add(i.copy());
    }
    ;
  }

  protected RiskAssessment typedCopy() {
    return copy();
  }

  @Override
  public boolean equalsDeep(Base other_) {
    if (!super.equalsDeep(other_))
      return false;
    if (!(other_ instanceof RiskAssessment))
      return false;
    RiskAssessment o = (RiskAssessment) other_;
    return compareDeep(identifier, o.identifier, true) && compareDeep(basedOn, o.basedOn, true)
        && compareDeep(parent, o.parent, true) && compareDeep(status, o.status, true)
        && compareDeep(method, o.method, true) && compareDeep(code, o.code, true)
        && compareDeep(subject, o.subject, true) && compareDeep(encounter, o.encounter, true)
        && compareDeep(occurrence, o.occurrence, true) && compareDeep(condition, o.condition, true)
        && compareDeep(performer, o.performer, true) && compareDeep(reasonCode, o.reasonCode, true)
        && compareDeep(reasonReference, o.reasonReference, true) && compareDeep(basis, o.basis, true)
        && compareDeep(prediction, o.prediction, true) && compareDeep(mitigation, o.mitigation, true)
        && compareDeep(note, o.note, true);
  }

  @Override
  public boolean equalsShallow(Base other_) {
    if (!super.equalsShallow(other_))
      return false;
    if (!(other_ instanceof RiskAssessment))
      return false;
    RiskAssessment o = (RiskAssessment) other_;
    return compareValues(status, o.status, true) && compareValues(mitigation, o.mitigation, true);
  }

  public boolean isEmpty() {
    return super.isEmpty()
        && ca.uhn.fhir.util.ElementUtil.isEmpty(identifier, basedOn, parent, status, method, code, subject, encounter,
            occurrence, condition, performer, reasonCode, reasonReference, basis, prediction, mitigation, note);
  }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.RiskAssessment;
  }

  /**
   * Search parameter: <b>date</b>
   * <p>
   * Description: <b>When was assessment made?</b><br>
   * Type: <b>date</b><br>
   * Path: <b>RiskAssessment.occurrenceDateTime</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "date", path = "(RiskAssessment.occurrence as dateTime)", description = "When was assessment made?", type = "date")
  public static final String SP_DATE = "date";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>date</b>
   * <p>
   * Description: <b>When was assessment made?</b><br>
   * Type: <b>date</b><br>
   * Path: <b>RiskAssessment.occurrenceDateTime</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.DateClientParam DATE = new ca.uhn.fhir.rest.gclient.DateClientParam(
      SP_DATE);

  /**
   * Search parameter: <b>identifier</b>
   * <p>
   * Description: <b>Unique identifier for the assessment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.identifier</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "identifier", path = "RiskAssessment.identifier", description = "Unique identifier for the assessment", type = "token")
  public static final String SP_IDENTIFIER = "identifier";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>identifier</b>
   * <p>
   * Description: <b>Unique identifier for the assessment</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.identifier</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam IDENTIFIER = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_IDENTIFIER);

  /**
   * Search parameter: <b>condition</b>
   * <p>
   * Description: <b>Condition assessed</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.condition</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "condition", path = "RiskAssessment.condition", description = "Condition assessed", type = "reference", target = {
      Condition.class })
  public static final String SP_CONDITION = "condition";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>condition</b>
   * <p>
   * Description: <b>Condition assessed</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.condition</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam CONDITION = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_CONDITION);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>RiskAssessment:condition</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_CONDITION = new ca.uhn.fhir.model.api.Include(
      "RiskAssessment:condition").toLocked();

  /**
   * Search parameter: <b>performer</b>
   * <p>
   * Description: <b>Who did assessment?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.performer</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "performer", path = "RiskAssessment.performer", description = "Who did assessment?", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Device"),
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Practitioner") }, target = { Device.class,
          Practitioner.class, PractitionerRole.class })
  public static final String SP_PERFORMER = "performer";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>performer</b>
   * <p>
   * Description: <b>Who did assessment?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.performer</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PERFORMER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PERFORMER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>RiskAssessment:performer</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PERFORMER = new ca.uhn.fhir.model.api.Include(
      "RiskAssessment:performer").toLocked();

  /**
   * Search parameter: <b>method</b>
   * <p>
   * Description: <b>Evaluation mechanism</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.method</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "method", path = "RiskAssessment.method", description = "Evaluation mechanism", type = "token")
  public static final String SP_METHOD = "method";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>method</b>
   * <p>
   * Description: <b>Evaluation mechanism</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.method</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam METHOD = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_METHOD);

  /**
   * Search parameter: <b>subject</b>
   * <p>
   * Description: <b>Who/what does assessment apply to?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.subject</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "subject", path = "RiskAssessment.subject", description = "Who/what does assessment apply to?", type = "reference", providesMembershipIn = {
      @ca.uhn.fhir.model.api.annotation.Compartment(name = "Patient") }, target = { Group.class, Patient.class })
  public static final String SP_SUBJECT = "subject";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>subject</b>
   * <p>
   * Description: <b>Who/what does assessment apply to?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.subject</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam SUBJECT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_SUBJECT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>RiskAssessment:subject</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_SUBJECT = new ca.uhn.fhir.model.api.Include(
      "RiskAssessment:subject").toLocked();

  /**
   * Search parameter: <b>patient</b>
   * <p>
   * Description: <b>Who/what does assessment apply to?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.subject</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "patient", path = "RiskAssessment.subject.where(resolve() is Patient)", description = "Who/what does assessment apply to?", type = "reference", target = {
      Patient.class })
  public static final String SP_PATIENT = "patient";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>patient</b>
   * <p>
   * Description: <b>Who/what does assessment apply to?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.subject</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam PATIENT = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_PATIENT);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>RiskAssessment:patient</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_PATIENT = new ca.uhn.fhir.model.api.Include(
      "RiskAssessment:patient").toLocked();

  /**
   * Search parameter: <b>probability</b>
   * <p>
   * Description: <b>Likelihood of specified outcome</b><br>
   * Type: <b>number</b><br>
   * Path: <b>RiskAssessment.prediction.probability[x]</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "probability", path = "RiskAssessment.prediction.probability", description = "Likelihood of specified outcome", type = "number")
  public static final String SP_PROBABILITY = "probability";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>probability</b>
   * <p>
   * Description: <b>Likelihood of specified outcome</b><br>
   * Type: <b>number</b><br>
   * Path: <b>RiskAssessment.prediction.probability[x]</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.NumberClientParam PROBABILITY = new ca.uhn.fhir.rest.gclient.NumberClientParam(
      SP_PROBABILITY);

  /**
   * Search parameter: <b>risk</b>
   * <p>
   * Description: <b>Likelihood of specified outcome as a qualitative
   * value</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.prediction.qualitativeRisk</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "risk", path = "RiskAssessment.prediction.qualitativeRisk", description = "Likelihood of specified outcome as a qualitative value", type = "token")
  public static final String SP_RISK = "risk";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>risk</b>
   * <p>
   * Description: <b>Likelihood of specified outcome as a qualitative
   * value</b><br>
   * Type: <b>token</b><br>
   * Path: <b>RiskAssessment.prediction.qualitativeRisk</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.TokenClientParam RISK = new ca.uhn.fhir.rest.gclient.TokenClientParam(
      SP_RISK);

  /**
   * Search parameter: <b>encounter</b>
   * <p>
   * Description: <b>Where was assessment performed?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.encounter</b><br>
   * </p>
   */
  @SearchParamDefinition(name = "encounter", path = "RiskAssessment.encounter", description = "Where was assessment performed?", type = "reference", target = {
      Encounter.class })
  public static final String SP_ENCOUNTER = "encounter";
  /**
   * <b>Fluent Client</b> search parameter constant for <b>encounter</b>
   * <p>
   * Description: <b>Where was assessment performed?</b><br>
   * Type: <b>reference</b><br>
   * Path: <b>RiskAssessment.encounter</b><br>
   * </p>
   */
  public static final ca.uhn.fhir.rest.gclient.ReferenceClientParam ENCOUNTER = new ca.uhn.fhir.rest.gclient.ReferenceClientParam(
      SP_ENCOUNTER);

  /**
   * Constant for fluent queries to be used to add include statements. Specifies
   * the path value of "<b>RiskAssessment:encounter</b>".
   */
  public static final ca.uhn.fhir.model.api.Include INCLUDE_ENCOUNTER = new ca.uhn.fhir.model.api.Include(
      "RiskAssessment:encounter").toLocked();

}
