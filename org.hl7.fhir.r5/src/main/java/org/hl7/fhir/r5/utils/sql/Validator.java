package org.hl7.fhir.r5.utils.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r5.context.IWorkerContext;
import org.hl7.fhir.r5.fhirpath.ExpressionNode;
import org.hl7.fhir.r5.fhirpath.FHIRPathEngine;
import org.hl7.fhir.r5.fhirpath.TypeDetails;
import org.hl7.fhir.r5.formats.JsonParser;
import org.hl7.fhir.r5.model.Base64BinaryType;
import org.hl7.fhir.r5.model.BooleanType;
import org.hl7.fhir.r5.model.CanonicalType;
import org.hl7.fhir.r5.model.CodeType;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.r5.model.DateType;
import org.hl7.fhir.r5.model.DecimalType;
import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.InstantType;
import org.hl7.fhir.r5.model.Integer64Type;
import org.hl7.fhir.r5.model.IntegerType;
import org.hl7.fhir.r5.model.OidType;
import org.hl7.fhir.r5.model.PositiveIntType;
import org.hl7.fhir.r5.model.PrimitiveType;
import org.hl7.fhir.r5.model.StringType;
import org.hl7.fhir.r5.model.TimeType;
import org.hl7.fhir.r5.model.UnsignedIntType;
import org.hl7.fhir.r5.model.UriType;
import org.hl7.fhir.r5.model.UrlType;
import org.hl7.fhir.r5.model.UuidType;
import org.hl7.fhir.r5.utils.UserDataNames;
import org.hl7.fhir.r5.utils.sql.Validator.TrueFalseOrUnknown;
import org.hl7.fhir.r5.fhirpath.ExpressionNode.CollectionStatus;
import org.hl7.fhir.r5.fhirpath.FHIRPathEngine.IssueMessage;
import org.hl7.fhir.utilities.MarkedToMoveToAdjunctPackage;
import org.hl7.fhir.utilities.Utilities;
import org.hl7.fhir.utilities.json.model.JsonArray;
import org.hl7.fhir.utilities.json.model.JsonBoolean;
import org.hl7.fhir.utilities.json.model.JsonElement;
import org.hl7.fhir.utilities.json.model.JsonNumber;
import org.hl7.fhir.utilities.json.model.JsonObject;
import org.hl7.fhir.utilities.json.model.JsonProperty;
import org.hl7.fhir.utilities.json.model.JsonString;
import org.hl7.fhir.utilities.validation.ValidationMessage;
import org.hl7.fhir.utilities.validation.ValidationMessage.IssueSeverity;
import org.hl7.fhir.utilities.validation.ValidationMessage.IssueType;
import org.hl7.fhir.utilities.validation.ValidationMessage.Source;

// see also org.hl7.fhir.validation.instance.type.ViewDefinitionValidator

@MarkedToMoveToAdjunctPackage
@Slf4j
public class Validator {

  public enum TrueFalseOrUnknown {
    TRUE, FALSE, UNKNOWN
  }

  private IWorkerContext context;
  private FHIRPathEngine fpe;
  private List<String> prohibitedNames = new ArrayList<String>();
  private List<ValidationMessage> issues = new ArrayList<ValidationMessage>();
  private TrueFalseOrUnknown supportsArrays;
  private TrueFalseOrUnknown supportsComplexTypes;
  private TrueFalseOrUnknown supportsNeedsName;

  private String resourceName;
  private String name;

  public Validator(IWorkerContext context, FHIRPathEngine fpe, List<String> prohibitedNames, @Nonnull TrueFalseOrUnknown supportsArrays, @Nonnull TrueFalseOrUnknown supportsComplexTypes, @Nonnull TrueFalseOrUnknown supportsNeedsName) {
    super();
    this.context = context;
    this.fpe = fpe;
    this.prohibitedNames = prohibitedNames;
    this.supportsArrays = supportsArrays;
    this.supportsComplexTypes = supportsComplexTypes;
    this.supportsNeedsName = supportsNeedsName;
  }

  public String getResourceName() {
    return resourceName;
  }


  public void checkViewDefinition(String path, JsonObject viewDefinition) {    
    checkProperties(viewDefinition, path, "resourceType", "url", "identifier", "name", "version", "title", "status", "experimental", "date", "publisher", "contact", "description", "useContext", "copyright", "resource", "constant", "select", "where");
    
    JsonElement nameJ = viewDefinition.get("name");
    if (nameJ == null) {
      if (supportsNeedsName == null) {
        hint(path, viewDefinition, "No name provided. A name is required in many contexts where a ViewDefinition is used");        
      } else if (supportsNeedsName == TrueFalseOrUnknown.TRUE) {
        error(path, viewDefinition, "No name provided", IssueType.REQUIRED);
      }
    } else if (!(nameJ instanceof JsonString)) {
      error(path, viewDefinition, "name must be a string", IssueType.INVALID);      
    } else {
      name = nameJ.asString();
      if (!isValidName(name)) {      
        error(path+".name", nameJ, "The name '"+name+"' is not valid", IssueType.INVARIANT);
      }
      if (prohibitedNames.contains(name)) {      
        error(path, nameJ, "The name '"+name+"' on the viewDefinition is not allowed in this context", IssueType.BUSINESSRULE);
      }
    }

    List<Column> columns = new ArrayList<>();    
    viewDefinition.setUserData(UserDataNames.db_columns, columns);
    
    JsonElement resourceNameJ = viewDefinition.get("resource");
    if (resourceNameJ == null) {
      error(path, viewDefinition, "No resource specified", IssueType.REQUIRED);      
    } else if (!(resourceNameJ instanceof JsonString)) {
      error(path, viewDefinition, "resource must be a string", IssueType.INVALID);      
    } else {
      resourceName = resourceNameJ.asString();
      if (!context.getResourceNamesAsSet().contains(resourceName)) {      
        error(path+".name", nameJ, "The name '"+resourceName+"' is not a valid resource", IssueType.BUSINESSRULE);
      } else {
        int i = 0;
        if (checkAllObjects(path, viewDefinition, "constant")) {
          for (JsonObject constant : viewDefinition.getJsonObjects("constant")) {
            checkConstant(path+".constant["+i+"]", constant);
            i++;
          }
        }
        i = 0;
        if (checkAllObjects(path, viewDefinition, "where")) {
          for (JsonObject where : viewDefinition.getJsonObjects("where")) {
            checkWhere(viewDefinition, path+".where["+i+"]", where);
            i++;
          }
        }
        TypeDetails t = new TypeDetails(CollectionStatus.SINGLETON, resourceName);

        i = 0;
        if (checkAllObjects(path, viewDefinition, "select")) {
          for (JsonObject select : viewDefinition.getJsonObjects("select")) {
            columns.addAll(checkSelect(viewDefinition, path+".select["+i+"]", select, t));
            i++;
          }
          if (i == 0) {
            error(path, viewDefinition, "No select statements found", IssueType.REQUIRED);
          }
        }
      }
    }
  }

  private List<Column> checkSelect(JsonObject vd, String path, JsonObject select, TypeDetails t) {
    List<Column> columns = new ArrayList<>();
    select.setUserData(UserDataNames.db_columns, columns);
    checkProperties(select, path, "column", "select", "forEach", "forEachOrNull", "unionAll");

    if (select.has("forEach")) {
      t = checkForEach(vd, path, select, select.get("forEach"), t);
    } else if (select.has("forEachOrNull")) {
      t = checkForEachOrNull(vd, path, select, select.get("forEachOrNull"), t);
    } 

    if (t != null) {
      
      if (select.has("column")) {
        JsonElement a = select.get("column");
        if (!(a instanceof JsonArray)) {
          error(path+".column", a, "column is not an array", IssueType.INVALID);
        } else {
          int i = 0;
          for (JsonElement e : ((JsonArray) a)) {
            if (!(e instanceof JsonObject)) {
              error(path+".column["+i+"]", a, "column["+i+"] is a "+e.type().toName()+" not an object", IssueType.INVALID);
            } else { 
              columns.add(checkColumn(vd, path+".column["+i+"]", (JsonObject) e, t));
            }
          }      
        }     
      }

      if (select.has("select")) {
        JsonElement a = select.get("select");
        if (!(a instanceof JsonArray)) {
          error(path+".select", a, "select is not an array", IssueType.INVALID);
        } else {
          int i = 0;
          for (JsonElement e : ((JsonArray) a)) {
            if (!(e instanceof JsonObject)) {
              error(path+".select["+i+"]", e, "select["+i+"] is not an object", IssueType.INVALID);
            } else { 
              columns.addAll(checkSelect(vd, path+".select["+i+"]", (JsonObject) e, t));
            }
          }      
        }     
      }

      if (select.has("unionAll")) {
        columns.addAll(checkUnion(vd, path, select, select.get("unionAll"), t));
      } 
      if (columns.isEmpty()) {
        error(path, select, "The select has no columns or selects", IssueType.REQUIRED);
      } else {
        checkColumnNamesUnique(select, path, columns);
      }
    }
    return columns;
  }


  private void checkColumnNamesUnique(JsonObject select, String path, List<Column> columns) {
    Set<String> names = new HashSet<>();
    for (Column col : columns) {
      if (col != null) {
        if (!names.contains(col.getName())) {
          names.add(col.getName());       
        } else if (!col.isDuplicateReported()) {
          col.setDuplicateReported(true);
          error(path, select, "Duplicate Column Name '"+col.getName()+"'", IssueType.BUSINESSRULE);
        }
      }
    }    
  }

  private List<Column> checkUnion(JsonObject vd, String path, JsonObject focus, JsonElement expression,  TypeDetails t) {
    JsonElement a = focus.get("unionAll");
    if (!(a instanceof JsonArray)) {
      error(path+".unionAll", a, "union is not an array", IssueType.INVALID);
      return null;
    } else {  
      List<List<Column>> unionColumns = new ArrayList<>();
      int i = 0;
      for (JsonElement e : ((JsonArray) a)) {
        if (!(e instanceof JsonObject)) {
          error(path+".unionAll["+i+"]", e, "unionAll["+i+"] is not an object", IssueType.INVALID);
        } else { 
          unionColumns.add(checkSelect(vd, path+".unionAll["+i+"]", (JsonObject) e, t));
        }
        i++;
      }  
      if (i < 2) {
        warning(path+".unionAll", a, "unionAll should have more than one item");        
      }
      if (unionColumns.size() > 1) {
        List<Column> columns = unionColumns.get(0);
        for (int ic = 1; ic < unionColumns.size(); ic++) {
          String diff = columnDiffs(columns, unionColumns.get(ic));
          if (diff != null) {
            error(path+".unionAll["+i+"]", ((JsonArray) a).get(ic), "unionAll["+i+"] column definitions do not match: "+diff, IssueType.INVALID);            
          }
        }
        a.setUserData(UserDataNames.db_columns, columns);
        return columns;
      }
    }     
    return null;
  }
  
  private String columnDiffs(List<Column> list1, List<Column> list2) {
    if (list1.size() == list2.size()) {
      for (int i = 0; i < list1.size(); i++) {
        if (list1.get(i) == null || list2.get(i) == null) {
          return null; // just suppress any addition errors
        }
        String diff = list1.get(i).diff(list2.get(i));
        if (diff != null) {
          return diff+" at #"+i;
        }
      }
      return null;
    } else {
      return "Column counts differ: "+list1.size()+" vs "+list2.size();
    }
  }

  private Column checkColumn(JsonObject vd, String path, JsonObject column, TypeDetails t) {
    checkProperties(column, path, "path", "name", "description", "collection", "type", "tag");

    if (!column.has("path")) {
      error(path, column, "no path found", IssueType.INVALID);      
    } else {
      JsonElement expression = column.get("path"); 
      if (!(expression instanceof JsonString)) {
        error(path+".forEach", expression, "forEach is not a string", IssueType.INVALID);
      } else {
        String expr = expression.asString();

        List<IssueMessage> warnings = new ArrayList<>();
        TypeDetails td = null;
        ExpressionNode node = null;
        try {
          node = fpe.parse(expr);
          column.setUserData(UserDataNames.db_path, node);
          td = fpe.checkOnTypes(vd, "Resource", resourceName, t, node, warnings);
        } catch (Exception e) {
          error(path, expression, e.getMessage(), IssueType.INVALID);
        }
        if (td != null && node != null) {
          for (IssueMessage s : warnings) {
            warning(path+".path", expression, s.getMessage());
          }
          String columnName = null;
          JsonElement nameJ = column.get("name");
          if (nameJ != null) {
            if (nameJ instanceof JsonString) {
              columnName = nameJ.asString();
              if (!isValidName(columnName)) {      
                error(path+".name", nameJ, "The name '"+columnName+"' is not valid", IssueType.VALUE);
              }
            } else {
              error(path+".name", nameJ, "name must be a string", IssueType.INVALID);
            }
          }
          if (columnName == null) {
            List<String> names = node.getDistalNames();
            if (names.size() == 1 && names.get(0) != null) {
              columnName = names.get(0);
              if (!isValidName(columnName)) {      
                error(path+".path", expression, "A column name is required. The natural name to chose is '"+columnName+"' (from the path)", IssueType.INVARIANT);
              } else {
                error(path, column, "A column name is required", IssueType.REQUIRED);
              }
            } else {
              error(path, column, "A column name is required", IssueType.REQUIRED);
            }
          }
          // ok, name is sorted!
          if (columnName != null) {
            column.setUserData(UserDataNames.db_name, columnName);
            boolean isColl = false;
            if (column.has("collection")) {
              JsonElement collectionJ = column.get("collection");
              if (!(collectionJ instanceof JsonBoolean)) {
                error(path+".collection", collectionJ, "collection is not a boolean", IssueType.INVALID);
              } else {
                boolean collection = collectionJ.asJsonBoolean().asBoolean();
                if (collection) {
                  isColl = true;
                }
              }
            }
            if (isColl) {
              if (td.getCollectionStatus() == CollectionStatus.SINGLETON) {
                hint(path, column, "collection is true, but the path statement(s) ('"+expr+"') can only return single values for the column '"+columnName+"'");
              }
              if (supportsArrays == TrueFalseOrUnknown.UNKNOWN) {
                warning(path, expression, "The column '"+columnName+"' is defined as a collection, but collections are not supported in all execution contexts");
              } else if (supportsArrays == TrueFalseOrUnknown.FALSE) {
                if (td.getCollectionStatus() == CollectionStatus.SINGLETON) {
                  warning(path, expression, "The column '"+columnName+"' is defined as a collection, but this is not allowed in the current execution context. Note that the path '"+expr+"' can only return a single value");
                } else {
                  warning(path, expression, "The column '"+columnName+"' is defined as a collection, but this is not allowed in the current execution context. Note that the path '"+expr+"' can return a collection of values");                  
                }
              }
            } else {
              if (td.getCollectionStatus() != CollectionStatus.SINGLETON) {
                warning(path, column, "This column is not defined as a collection, but the path statement '"+expr+"' might return multiple values for the column '"+columnName+"' for some inputs");
              }
            }
            Set<String> types = new HashSet<>();
            if (node.isNullSet()) {
              types.add("null");
            } else {
              // ok collection is sorted
              for (String type : td.getTypes()) {
                types.add(simpleType(type));
              }

              JsonElement typeJ = column.get("type");
              if (typeJ != null) {
                if (typeJ instanceof JsonString) {
                  String type = typeJ.asString();
                  if (!td.hasType(type)) {
                    error(path+".type", typeJ, "The path expression ('"+expr+"') does not return a value of the type '"+type+"' - found "+td.describe(), IssueType.VALUE);
                  } else {
                    types.clear();
                    types.add(simpleType(type));
                  }
                } else {
                  error(path+".type", typeJ, "type must be a string", IssueType.INVALID);
                }
              }
            }
            if (types.size() != 1) {
              error(path, column, "Unable to determine a type (found "+td.describe()+")", IssueType.BUSINESSRULE);
            } else {
              String type = types.iterator().next();
              boolean ok = false;
              if (!isSimpleType(type) && !"null".equals(type)) {
                if (supportsComplexTypes == TrueFalseOrUnknown.UNKNOWN) {
                  warning(path, expression, "Column from path '"+expr+"' is a complex type ('"+type+"'). This is not supported in some Runners");
                } else if (supportsComplexTypes == TrueFalseOrUnknown.FALSE) {            
                  error(path, expression, "Column from path '"+expr+"' is a complex type ('"+type+"') but this is not allowed in this context", IssueType.BUSINESSRULE);
                } else {
                  ok = true;
                }
              } else {
                ok = true;
              }
              if (ok) {
                Column col = new Column(columnName, isColl, type, kindForType(type));
                column.setUserData(UserDataNames.db_column, col);
                return col;
              }
            }
          }
        }
      }
    }
    return null;
  }

  private ColumnKind kindForType(String type) {
    switch (type) {
    case "null": return ColumnKind.Null;
    case "dateTime": return ColumnKind.DateTime;
    case "boolean": return ColumnKind.Boolean;
    case "integer": return ColumnKind.Integer;
    case "decimal": return ColumnKind.Decimal;
    case "string": return ColumnKind.String;
    case "canonical": return ColumnKind.String;
    case "url": return ColumnKind.String;
    case "uri": return ColumnKind.String;
    case "oid": return ColumnKind.String;
    case "uuid": return ColumnKind.String;
    case "id": return ColumnKind.String;
    case "code": return ColumnKind.String;
    case "base64Binary": return ColumnKind.Binary;
    case "time": return ColumnKind.Time;
    default: return ColumnKind.Complex;
    }
  }

  private boolean isSimpleType(String type) {
    return Utilities.existsInList(type, "dateTime", "boolean", "integer", "decimal", "string", "base64Binary", "id", "code", "date", "time", "canonical", "uri", "url");
  }

  private String simpleType(String type) {
    type = type.replace("http://hl7.org/fhirpath/System.", "").replace("http://hl7.org/fhir/StructureDefinition/", "");
    if (Utilities.existsInList(type, "date", "dateTime", "instant")) {
      return "dateTime";
    }
    if (Utilities.existsInList(type, "Boolean", "boolean")) {
      return "boolean";
    }
    if (Utilities.existsInList(type, "Integer", "integer", "integer64")) {
      return "integer";
    }
    if (Utilities.existsInList(type, "Decimal", "decimal")) {
      return "decimal";
    }
    if (Utilities.existsInList(type, "String", "string", "code")) {
      return "string";
    }
    if (Utilities.existsInList(type, "Time", "time")) {
      return "time";
    }
    if (Utilities.existsInList(type, "base64Binary")) {
      return "base64Binary";
    }
    return type;
  }

  private TypeDetails checkForEach(JsonObject vd, String path, JsonObject focus, JsonElement expression, TypeDetails t) {
    if (!(expression instanceof JsonString)) {
      error(path+".forEach", expression, "forEach is not a string", IssueType.INVALID);
      return null;
    } else {
      String expr = expression.asString();

      List<IssueMessage> warnings = new ArrayList<>();
      TypeDetails td = null;
      try {
        ExpressionNode n = fpe.parse(expr);
        focus.setUserData(UserDataNames.db_forEach, n);
        td = fpe.checkOnTypes(vd, "Resource", resourceName, t, n, warnings);
      } catch (Exception e) {
        error(path, expression, e.getMessage(), IssueType.INVALID);
      }
      if (td != null) {
        for (IssueMessage s : warnings) {
          warning(path+".forEach", expression, s.getMessage());
        }
      }
      return td;
    }
  }

  private TypeDetails checkForEachOrNull(JsonObject vd, String path, JsonObject focus, JsonElement expression, TypeDetails t) {
    if (!(expression instanceof JsonString)) {
      error(path+".forEachOrNull", expression, "forEachOrNull is not a string", IssueType.INVALID);
      return null;
    } else {
      String expr = expression.asString();

      List<IssueMessage> warnings = new ArrayList<>();
      TypeDetails td = null;
      try {
        ExpressionNode n = fpe.parse(expr);
        focus.setUserData(UserDataNames.db_forEachOrNull, n);
        td = fpe.checkOnTypes(vd, "Resource", resourceName, t, n, warnings);
      } catch (Exception e) {
        error(path, expression, e.getMessage(), IssueType.INVALID);
      }
      if (td != null) {
        for (IssueMessage s : warnings) {
          warning(path+".forEachOrNull", expression, s.getMessage());
        }
      }
      return td;
    }
  }

  private void checkConstant(String path, JsonObject constant) {
    checkProperties(constant, path, "name", "valueBase64Binary", "valueBoolean", "valueCanonical", "valueCode", "valueDate", "valueDateTime", "valueDecimal", "valueId", "valueInstant", "valueInteger", "valueInteger64", "valueOid", "valueString", "valuePositiveInt", "valueTime", "valueUnsignedInt", "valueUri", "valueUrl", "valueUuid");
    JsonElement nameJ = constant.get("name");
    if (nameJ == null) {
      error(path, constant, "No name provided", IssueType.REQUIRED);      
    } else if (!(nameJ instanceof JsonString)) {
      error(path, constant, "Name must be a string", IssueType.INVALID);      
    } else {
      String name = constant.asString("name");
      if (!isValidName(name)) {      
        error(path+".name", nameJ, "The name '"+name+"' is not valid", IssueType.INVARIANT);
      }
    }
    if (constant.has("valueBase64Binary")) {
      checkIsString(path, constant, "valueBase64Binary", new Base64BinaryType());
    } else if (constant.has("valueBoolean")) {
      checkIsBoolean(path, constant, "valueBoolean", new BooleanType());
    } else if (constant.has("valueCanonical")) { 
      checkIsString(path, constant, "valueCanonical", new CanonicalType());
    } else if (constant.has("valueCode")) {
      checkIsString(path, constant, "valueCode", new CodeType());
    } else if (constant.has("valueDate")) {
      checkIsString(path, constant, "valueDate", new DateType());
    } else if (constant.has("valueDateTime")) {
      checkIsString(path, constant, "valueDateTime", new DateTimeType());
    } else if (constant.has("valueDecimal")) {
      checkIsNumber(path, constant, "valueDecimal", new DecimalType());
    } else if (constant.has("valueId")) {
      checkIsString(path, constant, "valueId", new IdType());
    } else if (constant.has("valueInstant")) {
      checkIsString(path, constant, "valueInstant", new InstantType());
    } else if (constant.has("valueInteger")) {
      checkIsNumber(path, constant, "valueInteger", new IntegerType());
    } else if (constant.has("valueInteger64")) {
      checkIsNumber(path, constant, "valueInteger64", new Integer64Type());
    } else if (constant.has("valueOid")) {
      checkIsString(path, constant, "valueOid", new OidType());
    } else if (constant.has("valueString")) {
      checkIsString(path, constant, "valueString", new StringType());
    } else if (constant.has("valuePositiveInt")) {
      checkIsNumber(path, constant, "valuePositiveInt", new PositiveIntType());
    } else if (constant.has("valueTime")) {
      checkIsString(path, constant, "valueTime", new TimeType());
    } else if (constant.has("valueUnsignedInt")) {
      checkIsNumber(path, constant, "valueUnsignedInt", new UnsignedIntType());
    } else if (constant.has("valueUri")) {
      checkIsString(path, constant, "valueUri", new UriType());
    } else if (constant.has("valueUrl")) {
      checkIsString(path, constant, "valueUrl", new UrlType());
    } else if (constant.has("valueUuid")) {
      checkIsString(path, constant, "valueUuid", new UuidType());
    } else {
      error(path, constant, "No value found", IssueType.REQUIRED);
    }
  }

  private void checkIsString(String path, JsonObject constant, String name, PrimitiveType<?> value) {
    JsonElement j = constant.get(name);
    if (!(j instanceof JsonString)) {
      error(path+"."+name, j, name+" must be a string", IssueType.INVALID);
    } else {
      value.setValueAsString(j.asString());
      constant.setUserData(UserDataNames.db_value, value);
    }
  }

  private void checkIsBoolean(String path, JsonObject constant, String name, PrimitiveType<?> value) {
    JsonElement j = constant.get(name);
    if (!(j instanceof JsonBoolean)) {
      error(path+"."+name, j, name+" must be a boolean", IssueType.INVALID);
    } else {
      value.setValueAsString(j.asString());
      constant.setUserData(UserDataNames.db_value, value);
    }
  }

  private void checkIsNumber(String path, JsonObject constant, String name, PrimitiveType<?> value) {
    JsonElement j = constant.get(name);
    if (!(j instanceof JsonNumber)) {
      error(path+"."+name, j, name+" must be a number", IssueType.INVALID);
    } else {
      value.setValueAsString(j.asString());
      constant.setUserData(UserDataNames.db_value, value);
    }
  }
  
  private void checkWhere(JsonObject vd, String path, JsonObject where) {
    checkProperties(where, path, "path", "description");

    String expr = where.asString("path");
    if (expr == null) {
      error(path, where, "No path provided", IssueType.REQUIRED);
    }
    List<String> types = new ArrayList<>();
    List<IssueMessage> warnings = new ArrayList<>();
    types.add(resourceName);
    TypeDetails td = null;
    try {
      ExpressionNode n = fpe.parse(expr);
      where.setUserData(UserDataNames.db_path, n);
      td = fpe.checkOnTypes(vd, "Resource", resourceName, types, n, warnings);
    } catch (Exception e) {
      error(path, where.get("path"), e.getMessage(), IssueType.INVALID);
    }
    if (td != null) {
      if (td.getCollectionStatus() != CollectionStatus.SINGLETON || td.getTypes().size() != 1 || !td.hasType("boolean")) {
        error(path+".path", where.get("path"), "A where path must return a boolean, but the expression "+expr+" returns a "+td.describe(), IssueType.BUSINESSRULE);
      } else {
        for (IssueMessage s : warnings) {
          warning(path+".path", where.get("path"), s.getMessage());
        }
      }
    }
  }

  private void checkProperties(JsonObject obj, String path, String... names) {
    for (JsonProperty p : obj.getProperties()) {
      boolean nameOk = "extension".equals(p.getName());
      for (String name : names) {
        nameOk = nameOk || name.equals(p.getName());
      }
      if (!nameOk) {
        error(path+"."+p.getName(), p.getValue(), "Unknown JSON property "+p.getName(), IssueType.UNKNOWN);
      }
    }
    
  }

  private boolean isValidName(String name) {
    boolean first = true;
    for (char c : name.toCharArray()) {
      if (!(Character.isAlphabetic(c) || Character.isDigit(c) || (!first && c == '_'))) {
        return false;
      }
      first = false;
    }
    return true;
  }


  private boolean checkAllObjects(String path, JsonObject focus, String name) {
    if (!focus.has(name)) {
      return true;
    } else if (!(focus.get(name) instanceof JsonArray)) {
      error(path+"."+name, focus.get(name), name+" must be an array", IssueType.INVALID);
      return false;
    } else {
      JsonArray arr = focus.getJsonArray(name);
      int i = 0;
      boolean ok = true;
      for (JsonElement e : arr) {
        if (!(e instanceof JsonObject)) {
          error(path+"."+name+"["+i+"]", e, name+"["+i+"] must be an object", IssueType.INVALID);
          ok = false;
        }
      }
      return ok;
    }
  }

  private void error(String path, JsonElement e, String issue, IssueType type) {
    ValidationMessage vm = new ValidationMessage(Source.InstanceValidator, type, e.getStart().getLine(), e.getStart().getCol(), path, issue, IssueSeverity.ERROR);
    issues.add(vm);

  }

  private void warning(String path, JsonElement e, String issue) {
    ValidationMessage vm = new ValidationMessage(Source.InstanceValidator, IssueType.BUSINESSRULE, e.getStart().getLine(), e.getStart().getCol(), path, issue, IssueSeverity.WARNING);
    issues.add(vm);
  }

  private void hint(String path, JsonElement e, String issue) {
    ValidationMessage vm = new ValidationMessage(Source.InstanceValidator, IssueType.BUSINESSRULE, e.getStart().getLine(), e.getStart().getCol(), path, issue, IssueSeverity.INFORMATION);
    issues.add(vm);
  }

  public void dump() {
    for (ValidationMessage vm : issues) {
      log.info(vm.summary());
    }

  }

  public void check() {    
    if (!isOk()) {
      throw new FHIRException("View Definition is not valid");
    }

  }

  public String getName() {
    return name;
  }

  public List<ValidationMessage> getIssues() {
    return issues;
  }

  public boolean isOk() {
    boolean ok = true;
    for (ValidationMessage vm : issues) {
      if (vm.isError()) {
        ok = false;
      }
    }
    return ok;
  }
}
