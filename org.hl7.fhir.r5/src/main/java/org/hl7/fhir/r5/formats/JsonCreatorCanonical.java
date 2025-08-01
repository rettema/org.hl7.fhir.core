package org.hl7.fhir.r5.formats;

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



import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class JsonCreatorCanonical implements JsonCreator {

  public class JsonCanValue {
    String name;
    private JsonCanValue(String name) {
      this.name = name;  
   }
  }

  private class JsonCanNumberValue extends JsonCanValue {
    private String value;
    private JsonCanNumberValue(String name, BigDecimal value) {
      super(name);
      this.value = canonicaliseDecimal(value.toPlainString());  
    }
  }

  private class JsonCanPresentedNumberValue extends JsonCanValue {
    private String value;
    private JsonCanPresentedNumberValue(String name, String value) {
      super(name);
      this.value = canonicaliseDecimal(value);  
    }
    
  }

  private String canonicaliseDecimal(String v) {
    return JsonNumberCanonicalizer.toCanonicalJson(v);
  }
  
  private class JsonCanIntegerValue extends JsonCanValue {
    private Integer value;
    private JsonCanIntegerValue(String name, Integer value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanBooleanValue extends JsonCanValue  {
    private Boolean value;
    private JsonCanBooleanValue(String name, Boolean value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanStringValue extends JsonCanValue {
    private String value;
    private JsonCanStringValue(String name, String value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanNullValue extends JsonCanValue  {
    private JsonCanNullValue(String name) {
      super(name);
    }
  }

  public class JsonCanObject extends JsonCanValue {

    boolean array;
    List<JsonCanValue> children = new ArrayList<JsonCanValue>();
    
    public JsonCanObject(String name, boolean array) {
      super(name);
      this.array = array;
    }

    public void addProp(JsonCanValue obj) {
      children.add(obj);
    }
  }

  Stack<JsonCanObject> stack;
  JsonCanObject root; 
  JsonCreatorDirect jj;
  String name;
  
  public JsonCreatorCanonical(OutputStreamWriter osw) {
    stack = new Stack<JsonCreatorCanonical.JsonCanObject>();
    jj = new JsonCreatorDirect(osw, false, false);
    name = null;
  }

  private String takeName() {
    String res = name;
    name = null;
    return res;
  }

  @Override
  public void beginObject() throws IOException {
    JsonCanObject obj = new JsonCanObject(takeName(), false);
    if (stack.isEmpty())
      root = obj;
    else
      stack.peek().addProp(obj);
    stack.push(obj);
  }

  @Override
  public void endObject() throws IOException {
    stack.pop();
  }

  @Override
  public void nullValue() throws IOException {
    stack.peek().addProp(new JsonCanNullValue(takeName()));
  }

  @Override
  public void name(String name) throws IOException {
    this.name = name;
  }

  @Override
  public void value(String value) throws IOException {
    stack.peek().addProp(new JsonCanStringValue(takeName(), value));    
  }

  @Override
  public void value(Boolean value) throws IOException {
    stack.peek().addProp(new JsonCanBooleanValue(takeName(), value));    
  }

  @Override
  public void value(BigDecimal value) throws IOException {
    stack.peek().addProp(new JsonCanNumberValue(takeName(), value));    
  }
  @Override
  public void valueNum(String value) throws IOException {
    stack.peek().addProp(new JsonCanPresentedNumberValue(takeName(), value));    
  }


  @Override
  public void value(Integer value) throws IOException {
    stack.peek().addProp(new JsonCanIntegerValue(takeName(), value));    
  }

  @Override
  public void beginArray() throws IOException {
    JsonCanObject obj = new JsonCanObject(takeName(), true);
    if (!stack.isEmpty())
      stack.peek().addProp(obj);
    stack.push(obj);
    
  }

  @Override
  public void endArray() throws IOException {
    stack.pop();    
  }

  @Override
  public void finish() throws IOException {
    writeObject(root);
  }

  private void writeObject(JsonCanObject obj) throws IOException {
    jj.beginObject();
    List<String> names = new ArrayList<String>();
    for (JsonCanValue v : obj.children) 
      names.add(v.name);
    Collections.sort(names);
    for (String n : names) {
      jj.name(n);
      JsonCanValue v = getPropForName(n, obj.children);
      if (v instanceof JsonCanNumberValue)
        jj.valueNum(((JsonCanNumberValue) v).value);
      else if (v instanceof JsonCanPresentedNumberValue)
        jj.valueNum(((JsonCanPresentedNumberValue) v).value);
      else if (v instanceof JsonCanIntegerValue)
        jj.value(((JsonCanIntegerValue) v).value);
      else if (v instanceof JsonCanBooleanValue)
        jj.value(((JsonCanBooleanValue) v).value);
      else if (v instanceof JsonCanStringValue)
        jj.value(((JsonCanStringValue) v).value);
      else if (v instanceof JsonCanNullValue)
        jj.nullValue();
      else if (v instanceof JsonCanObject) {
        JsonCanObject o = (JsonCanObject) v;
        if (o.array) 
          writeArray(o);
        else
          writeObject(o);
      } else
        throw new Error("not possible");
    }
    jj.endObject();
  }

  private JsonCanValue getPropForName(String name, List<JsonCanValue> children) {
    for (JsonCanValue child : children)
      if (child.name.equals(name))
        return child;
    return null;
  }

  private void writeArray(JsonCanObject arr) throws IOException {
    jj.beginArray();
    for (JsonCanValue v : arr.children) { 
      if (v instanceof JsonCanNumberValue)
        jj.valueNum(((JsonCanNumberValue) v).value);
      else if (v instanceof JsonCanPresentedNumberValue)
        jj.valueNum(((JsonCanPresentedNumberValue) v).value);
      else if (v instanceof JsonCanIntegerValue)
          jj.value(((JsonCanIntegerValue) v).value);
      else if (v instanceof JsonCanBooleanValue)
        jj.value(((JsonCanBooleanValue) v).value);
      else if (v instanceof JsonCanStringValue)
        jj.value(((JsonCanStringValue) v).value);
      else if (v instanceof JsonCanNullValue)
        jj.nullValue();
      else if (v instanceof JsonCanObject) {
        JsonCanObject o = (JsonCanObject) v;
        if (o.array) 
          writeArray(o);
        else
          writeObject(o);
      } else
        throw new Error("not possible");
    }
    jj.endArray();    
  }

  @Override
  public void comment(String content) {
    // canonical JSON ignores comments    
  }

  @Override
  public void link(String href) {
    // not used
  }
       
  @Override
  public void anchor(String name) {
    // not used
  }

  @Override
  public void externalLink(String string) {
    // not used
  }

  @Override
  public boolean canElide() { return false; }

  @Override
  public void elide() {
    // not used
  }

  @Override
  public boolean isCanonical() {
    return true;
  }

}