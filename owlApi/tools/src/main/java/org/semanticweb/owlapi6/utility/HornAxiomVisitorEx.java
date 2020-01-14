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
package org.semanticweb.owlapi6.utility;

import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;

/**
 * Returns true if the visited axioms are an ontology in Horn-SHIQ form.
 */
public class HornAxiomVisitorEx implements OWLAxiomVisitorEx<Boolean> {

    private final PositiveAppearanceVisitorEx positive = new PositiveAppearanceVisitorEx();
    private final NegativeAppearanceVisitorEx negative = new NegativeAppearanceVisitorEx();

    protected boolean checkNegative(OWLClassExpression c) {
        return c.accept(negative).booleanValue();
    }

    protected boolean checkPositive(OWLClassExpression c) {
        return c.accept(positive).booleanValue();
    }

    @Override
    public Boolean doDefault(OWLObject object) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLSubClassOfAxiom axiom) {
        return Boolean
            .valueOf(checkNegative(axiom.getSubClass()) && checkNegative(axiom.getSuperClass()));
    }

    @Override
    public Boolean visit(OWLDisjointClassesAxiom axiom) {
        return Boolean.valueOf(axiom.classExpressions().allMatch(this::checkNegative));
    }

    @Override
    public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
        return Boolean.valueOf(checkPositive(axiom.getDomain()));
    }

    @Override
    public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
        return Boolean.valueOf(checkPositive(axiom.getRange()));
    }

    @Override
    public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLDisjointUnionAxiom axiom) {
        if (neitherPositiveNorNegative(axiom.getOWLClass()).booleanValue()) {
            return Boolean.FALSE;
        }
        return Boolean
            .valueOf(axiom.classExpressions().noneMatch(this::neitherPositiveNorNegative));
    }

    protected Boolean neitherPositiveNorNegative(OWLClassExpression c1) {
        return Boolean.valueOf(!checkPositive(c1) || !checkNegative(c1));
    }

    @Override
    public Boolean visit(OWLDeclarationAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLEquivalentClassesAxiom axiom) {
        return Boolean
            .valueOf(axiom.classExpressions().noneMatch(this::neitherPositiveNorNegative));
    }

    @Override
    public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
        return Boolean.TRUE;
    }

    private class PositiveAppearanceVisitorEx implements OWLClassExpressionVisitorEx<Boolean> {

        PositiveAppearanceVisitorEx() {}

        @Override
        public Boolean doDefault(OWLObject object) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf ce) {
            return Boolean.valueOf(ce.operands().noneMatch(c -> c.accept(this) == Boolean.FALSE));
        }

        @Override
        public Boolean visit(OWLObjectComplementOf ce) {
            return Boolean.valueOf(checkNegative(ce.getOperand()));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectAllValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectExactCardinality ce) {
            return Boolean.valueOf(ce.getCardinality() <= 1
                && ce.getFiller().accept(this).booleanValue() && checkNegative(ce.getFiller()));
        }

        @Override
        public Boolean visit(OWLObjectMaxCardinality ce) {
            return Boolean.valueOf(ce.getCardinality() <= 1 && checkNegative(ce.getFiller()));
        }
    }

    private class NegativeAppearanceVisitorEx implements OWLClassExpressionVisitorEx<Boolean> {

        NegativeAppearanceVisitorEx() {}

        @Override
        public Boolean doDefault(OWLObject object) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf ce) {
            return Boolean.valueOf(ce.operands().noneMatch(c -> c.accept(this) == Boolean.FALSE));
        }

        @Override
        public Boolean visit(OWLObjectUnionOf ce) {
            return Boolean.valueOf(ce.operands().noneMatch(c -> c.accept(this) == Boolean.FALSE));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality ce) {
            return Boolean
                .valueOf(ce.getCardinality() <= 1 && ce.getFiller().accept(this).booleanValue());
        }
    }
}
