/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi6.model.parameters;

import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * Search options.
 *
 * @author ignazio
 * @since 4.0.0
 */
public enum AxiomAnnotations {
    /**
     * Search for axioms ignoring annotations.
     */
    IGNORE_AXIOM_ANNOTATIONS {
        @Override
        public boolean contains(OWLOntology o, OWLAxiom ax) {
            return o.containsAxiomIgnoreAnnotations(ax);
        }
    },
    /**
     * Search for axioms taking annotations into account.
     */
    CONSIDER_AXIOM_ANNOTATIONS {
        @Override
        public boolean contains(OWLOntology o, OWLAxiom ax) {
            return o.containsAxiom(ax);
        }
    };

    /**
     * @param o ontology to check
     * @param ax axiom to check
     * @return true if the ontology contains the axiom, considering or disregarding annotations
     * depending on the axiom annotation value.
     */
    public abstract boolean contains(OWLOntology o, OWLAxiom ax);
}
