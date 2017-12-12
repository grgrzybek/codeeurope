/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package grgr.ce2017.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@XmlType
@XmlRootElement(name = "celestial-body", namespace = "urn:codeeurope2017:wroclaw:karaf")
public class CelestialBody {

    @XmlElement
    private String type;
    @XmlElement
    private String name;

    @XmlTransient
    private static Random RND = new Random();

    private static Map<String, List<String>> db = new HashMap<>();

    public CelestialBody() {
    }

    public CelestialBody(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CelestialBody random() {
        if (db.isEmpty()) {
            synchronized (CelestialBody.class) {
                try {
                    JsonFactory factory = new JsonFactory();
                    ObjectMapper om = new ObjectMapper(factory);
                    ObjectNode all = (ObjectNode) om.readTree(CelestialBody.class.getResourceAsStream("names.json"));
                    all.fields().forEachRemaining(n -> {
                        List<String> values = new LinkedList<>();
                        n.getValue().forEach(v -> values.add(v.asText()));
                        db.put(n.getKey(), values);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        String type = null;
        String name = null;
        List<String> l = null;
        switch (RND.nextInt(4)) {
            case 0: // asteroid
                type = "asteroid";
                l = db.get("asteroid_prefix");
                name = l.get(RND.nextInt(l.size()));
                l = db.get("asteroid_postfix");
                name += l.get(RND.nextInt(l.size()));
                break;
            case 1: // black hole
                type = "black hole";
                l = db.get("black_hole_names");
                name = l.get(RND.nextInt(l.size()));
                break;
            case 2: // star
                type = "star";
                l = db.get("star_names");
                name = l.get(RND.nextInt(l.size()));
                break;
            case 3: // nebula
                type = "nebula";
                l = db.get("nebula_names");
                name = l.get(RND.nextInt(l.size()));
                break;
        }
        return new CelestialBody(type, name);
    }

}
