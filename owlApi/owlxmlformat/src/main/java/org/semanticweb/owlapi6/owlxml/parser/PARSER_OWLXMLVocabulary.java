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
package org.semanticweb.owlapi6.owlxml.parser;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_URI;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANONYMOUS_INDIVIDUAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.BODY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CARDINALITY_ATTRIBUTE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.COMMENT;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_DEFINITION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_FACET;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_IRI;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_RESTRICTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_ALL_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_COMPLEMENT_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_EXACT_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_HAS_VALUE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_INTERSECTION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_MAX_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_MIN_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_ONE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_SOME_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_UNION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DECLARATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DESCRIPTION_GRAPH_RULE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_CLASSES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_UNION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DL_SAFE_RULE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DOCUMENTATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ENTITY_ANNOTATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.FACET_RESTRICTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.HAS_KEY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.HEAD;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IMPORT;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IRI_ATTRIBUTE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IRI_ELEMENT;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.LABEL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.LITERAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NAME_ATTRIBUTE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NODE_ID;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_COMPLEMENT_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_HAS_SELF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_HAS_VALUE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_INTERSECTION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_INVERSE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_MAX_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_MIN_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_ONE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_UNION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ONTOLOGY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.PREFIX;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_CLASS_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.UNION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.VARIABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.builders.BaseBuilder;
import org.semanticweb.owlapi6.builders.Builder;
import org.semanticweb.owlapi6.builders.BuilderAnnotation;
import org.semanticweb.owlapi6.builders.BuilderAnnotationAssertion;
import org.semanticweb.owlapi6.builders.BuilderAnnotationProperty;
import org.semanticweb.owlapi6.builders.BuilderAnnotationPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderAnnotationPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderAnonymousIndividual;
import org.semanticweb.owlapi6.builders.BuilderAsymmetricObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderClass;
import org.semanticweb.owlapi6.builders.BuilderClassAssertion;
import org.semanticweb.owlapi6.builders.BuilderComplementOf;
import org.semanticweb.owlapi6.builders.BuilderDataAllValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderDataComplementOf;
import org.semanticweb.owlapi6.builders.BuilderDataExactCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataHasValue;
import org.semanticweb.owlapi6.builders.BuilderDataIntersectionOf;
import org.semanticweb.owlapi6.builders.BuilderDataMaxCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataMinCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataOneOf;
import org.semanticweb.owlapi6.builders.BuilderDataProperty;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderDataSomeValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderDataUnionOf;
import org.semanticweb.owlapi6.builders.BuilderDatatype;
import org.semanticweb.owlapi6.builders.BuilderDatatypeDefinition;
import org.semanticweb.owlapi6.builders.BuilderDatatypeRestriction;
import org.semanticweb.owlapi6.builders.BuilderDeclaration;
import org.semanticweb.owlapi6.builders.BuilderDifferentIndividuals;
import org.semanticweb.owlapi6.builders.BuilderDisjointClasses;
import org.semanticweb.owlapi6.builders.BuilderDisjointDataProperties;
import org.semanticweb.owlapi6.builders.BuilderDisjointObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderDisjointUnion;
import org.semanticweb.owlapi6.builders.BuilderEquivalentClasses;
import org.semanticweb.owlapi6.builders.BuilderEquivalentDataProperties;
import org.semanticweb.owlapi6.builders.BuilderEquivalentObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderFacetRestriction;
import org.semanticweb.owlapi6.builders.BuilderFunctionalDataProperty;
import org.semanticweb.owlapi6.builders.BuilderFunctionalObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderHasKey;
import org.semanticweb.owlapi6.builders.BuilderInverseFunctionalObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderInverseObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderIrreflexiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderLiteral;
import org.semanticweb.owlapi6.builders.BuilderNamedIndividual;
import org.semanticweb.owlapi6.builders.BuilderNegativeDataPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderNegativeObjectPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderObjectAllValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderObjectExactCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectHasSelf;
import org.semanticweb.owlapi6.builders.BuilderObjectHasValue;
import org.semanticweb.owlapi6.builders.BuilderObjectIntersectionOf;
import org.semanticweb.owlapi6.builders.BuilderObjectInverseOf;
import org.semanticweb.owlapi6.builders.BuilderObjectMaxCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectMinCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderObjectSomeValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderOneOf;
import org.semanticweb.owlapi6.builders.BuilderPropertyChain;
import org.semanticweb.owlapi6.builders.BuilderReflexiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderSWRLBuiltInAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLClassAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDataPropertyAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDataRangeAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLIndividualsAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLRule;
import org.semanticweb.owlapi6.builders.BuilderSWRLSameIndividualAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLVariable;
import org.semanticweb.owlapi6.builders.BuilderSameIndividual;
import org.semanticweb.owlapi6.builders.BuilderSubAnnotationPropertyOf;
import org.semanticweb.owlapi6.builders.BuilderSubClass;
import org.semanticweb.owlapi6.builders.BuilderSubDataProperty;
import org.semanticweb.owlapi6.builders.BuilderSubObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderSymmetricObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderTransitiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderUnionOf;
import org.semanticweb.owlapi6.builders.SettableCardinality;
import org.semanticweb.owlapi6.builders.SettableProperty;
import org.semanticweb.owlapi6.builders.SettableRange;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataRange;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLBinaryAtom;
import org.semanticweb.owlapi6.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi6.model.SWRLClassAtom;
import org.semanticweb.owlapi6.model.SWRLDArgument;
import org.semanticweb.owlapi6.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi6.model.SWRLIArgument;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.model.SetOntologyID;
import org.semanticweb.owlapi6.utilities.XMLUtils;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;
import org.semanticweb.owlapi6.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
enum PARSER_OWLXMLVocabulary {
//@formatter:off
    /** CLASS.                              */  PARSER_CLASS                               (CLASS                               , OWLClassEH::new),
    /** DATA_PROPERTY.                      */  PARSER_DATA_PROPERTY                       (DATA_PROPERTY                       , DataPropertyEH::new),
    /** OBJECT_PROPERTY.                    */  PARSER_OBJECT_PROPERTY                     (OBJECT_PROPERTY                     , OWLObjectPropertyEH::new),
    /** NAMED_INDIVIDUAL.                   */  PARSER_NAMED_INDIVIDUAL                    (NAMED_INDIVIDUAL                    , IndividualEH::new),
    /** ENTITY_ANNOTATION.                  */  PARSER_ENTITY_ANNOTATION                   (ENTITY_ANNOTATION                   , LegacyEntityAnnEH::new),
    /** ANNOTATION_PROPERTY.                */  PARSER_ANNOTATION_PROPERTY                 (ANNOTATION_PROPERTY                 , AnnotationPropEH::new),
    /** DATATYPE.                           */  PARSER_DATATYPE                            (DATATYPE                            , DatatypeEH::new),
    /** ANNOTATION.                         */  PARSER_ANNOTATION                          (ANNOTATION                          , AnnEH::new),
    /** ANONYMOUS_INDIVIDUAL.               */  PARSER_ANONYMOUS_INDIVIDUAL                (ANONYMOUS_INDIVIDUAL                , AnonEH::new),
    /** IMPORT.                             */  PARSER_IMPORT                              (IMPORT                              , ImportsEH::new),
    /** ONTOLOGY.                           */  PARSER_ONTOLOGY                            (ONTOLOGY                            , OntologyEH::new),
    /** LITERAL.                            */  PARSER_LITERAL                             (LITERAL                             , LiteralEH::new),
    /** OBJECT_INVERSE_OF.                  */  PARSER_OBJECT_INVERSE_OF                   (OBJECT_INVERSE_OF                   , InverseObjectEH::new),
    /** DATA_COMPLEMENT_OF.                 */  PARSER_DATA_COMPLEMENT_OF                  (DATA_COMPLEMENT_OF                  , DataComplEH::new),
    /** DATA_ONE_OF.                        */  PARSER_DATA_ONE_OF                         (DATA_ONE_OF                         , DataOneOfEH::new),
    /** DATATYPE_RESTRICTION.               */  PARSER_DATATYPE_RESTRICTION                (DATATYPE_RESTRICTION                , DatatypeRestrictionEH::new),
    /** FACET_RESTRICTION.                  */  PARSER_FACET_RESTRICTION                   (FACET_RESTRICTION                   , DatatypeFacetEH::new),
    /** DATA_UNION_OF.                      */  PARSER_DATA_UNION_OF                       (DATA_UNION_OF                       , DataUnionOfEH::new),
    /** DATA_INTERSECTION_OF.               */  PARSER_DATA_INTERSECTION_OF                (DATA_INTERSECTION_OF                , DataIntersectionOfEH::new),
    /** OBJECT_INTERSECTION_OF.             */  PARSER_OBJECT_INTERSECTION_OF              (OBJECT_INTERSECTION_OF              , IntersectionOfEH::new),
    /** OBJECT_UNION_OF.                    */  PARSER_OBJECT_UNION_OF                     (OBJECT_UNION_OF                     , ObjectUnionOfEH::new),
    /** OBJECT_COMPLEMENT_OF.               */  PARSER_OBJECT_COMPLEMENT_OF                (OBJECT_COMPLEMENT_OF                , ComplementOfEH::new),
    /** OBJECT_ONE_OF.                      */  PARSER_OBJECT_ONE_OF                       (OBJECT_ONE_OF                       , OneOfEH::new),
    /** OBJECT_SOME_VALUES_FROM.            */  PARSER_OBJECT_SOME_VALUES_FROM             (OBJECT_SOME_VALUES_FROM             , ()->new ObjectREH<>(BuilderObjectSomeValuesFrom::new)),
    /** OBJECT_ALL_VALUES_FROM.             */  PARSER_OBJECT_ALL_VALUES_FROM              (OBJECT_ALL_VALUES_FROM              , ()->new ObjectREH<>(BuilderObjectAllValuesFrom::new)),
    /** OBJECT_HAS_SELF.                    */  PARSER_OBJECT_HAS_SELF                     (OBJECT_HAS_SELF                     , ()->new ClassEH<>(BuilderObjectHasSelf::new)),
    /** OBJECT_HAS_VALUE.                   */  PARSER_OBJECT_HAS_VALUE                    (OBJECT_HAS_VALUE                    , HasValueEH::new),
    /** OBJECT_MIN_CARDINALITY.             */  PARSER_OBJECT_MIN_CARDINALITY              (OBJECT_MIN_CARDINALITY              , ()->new ObjectCardEH<>(BuilderObjectMinCardinality::new)),
    /** OBJECT_EXACT_CARDINALITY.           */  PARSER_OBJECT_EXACT_CARDINALITY            (OBJECT_EXACT_CARDINALITY            , ()->new ObjectCardEH<>(BuilderObjectExactCardinality::new)),
    /** OBJECT_MAX_CARDINALITY.             */  PARSER_OBJECT_MAX_CARDINALITY              (OBJECT_MAX_CARDINALITY              , ()->new ObjectCardEH<>(BuilderObjectMaxCardinality::new)),
    /** DATA_SOME_VALUES_FROM.              */  PARSER_DATA_SOME_VALUES_FROM               (DATA_SOME_VALUES_FROM               , ()->new DataREH<>(BuilderDataSomeValuesFrom::new)),
    /** DATA_ALL_VALUES_FROM.               */  PARSER_DATA_ALL_VALUES_FROM                (DATA_ALL_VALUES_FROM                , ()->new DataREH<>(BuilderDataAllValuesFrom::new)),
    /** DATA_HAS_VALUE.                     */  PARSER_DATA_HAS_VALUE                      (DATA_HAS_VALUE                      , DataHasValueEH::new),
    /** DATA_MIN_CARDINALITY.               */  PARSER_DATA_MIN_CARDINALITY                (DATA_MIN_CARDINALITY                , ()->new DataCardEH<>(BuilderDataMinCardinality::new)),
    /** DATA_EXACT_CARDINALITY.             */  PARSER_DATA_EXACT_CARDINALITY              (DATA_EXACT_CARDINALITY              , ()->new DataCardEH<>(BuilderDataExactCardinality::new)),
    /** DATA_MAX_CARDINALITY.               */  PARSER_DATA_MAX_CARDINALITY                (DATA_MAX_CARDINALITY                , ()->new DataCardEH<>(BuilderDataMaxCardinality::new)),
    /** SUB_CLASS_OF.                       */  PARSER_SUB_CLASS_OF                        (SUB_CLASS_OF                        , SubClassEH::new),
    /** EQUIVALENT_CLASSES.                 */  PARSER_EQUIVALENT_CLASSES                  (EQUIVALENT_CLASSES                  , EquivalentClassesEH::new),
    /** DISJOINT_CLASSES.                   */  PARSER_DISJOINT_CLASSES                    (DISJOINT_CLASSES                    , DisjointClassesEH::new),
    /** DISJOINT_UNION.                     */  PARSER_DISJOINT_UNION                      (DISJOINT_UNION                      , DisjointUnionEH::new),
    /** UNION_OF.                           */  PARSER_UNION_OF                            (UNION_OF                            , UnionOfEH::new),
    /** SUB_OBJECT_PROPERTY_OF.             */  PARSER_SUB_OBJECT_PROPERTY_OF              (SUB_OBJECT_PROPERTY_OF              , SubObjectPropertyOfEH::new),
    /** OBJECT_PROPERTY_CHAIN.              */  PARSER_OBJECT_PROPERTY_CHAIN               (OBJECT_PROPERTY_CHAIN               , ChainEH::new),
    /** EQUIVALENT_OBJECT_PROPERTIES.       */  PARSER_EQUIVALENT_OBJECT_PROPERTIES        (EQUIVALENT_OBJECT_PROPERTIES        , EqObjectPropertiesEH::new),
    /** DISJOINT_OBJECT_PROPERTIES.         */  PARSER_DISJOINT_OBJECT_PROPERTIES          (DISJOINT_OBJECT_PROPERTIES          , DisjointObjectPropertiesEH::new),
    /** OBJECT_PROPERTY_DOMAIN.             */  PARSER_OBJECT_PROPERTY_DOMAIN              (OBJECT_PROPERTY_DOMAIN              , ObjectPropertyDomainEH::new),
    /** OBJECT_PROPERTY_RANGE.              */  PARSER_OBJECT_PROPERTY_RANGE               (OBJECT_PROPERTY_RANGE               , ObjectPropertyRangeEH::new),
    /** INVERSE_OBJECT_PROPERTIES.          */  PARSER_INVERSE_OBJECT_PROPERTIES           (INVERSE_OBJECT_PROPERTIES           , InverseObjectAxiomEH::new),
    /** FUNCTIONAL_OBJECT_PROPERTY.         */  PARSER_FUNCTIONAL_OBJECT_PROPERTY          (FUNCTIONAL_OBJECT_PROPERTY          , ()->new AxiomEH<>(BuilderFunctionalObjectProperty::new)),
    /** INVERSE_FUNCTIONAL_OBJECT_PROPERTY. */  PARSER_INVERSE_FUNCTIONAL_OBJECT_PROPERTY  (INVERSE_FUNCTIONAL_OBJECT_PROPERTY  , ()->new AxiomEH<>(BuilderInverseFunctionalObjectProperty::new)),
    /** SYMMETRIC_OBJECT_PROPERTY.          */  PARSER_SYMMETRIC_OBJECT_PROPERTY           (SYMMETRIC_OBJECT_PROPERTY           , ()->new AxiomEH<>(BuilderSymmetricObjectProperty::new)),
    /** ASYMMETRIC_OBJECT_PROPERTY.         */  PARSER_ASYMMETRIC_OBJECT_PROPERTY          (ASYMMETRIC_OBJECT_PROPERTY          , ()->new AxiomEH<>(BuilderAsymmetricObjectProperty::new)),
    /** REFLEXIVE_OBJECT_PROPERTY.          */  PARSER_REFLEXIVE_OBJECT_PROPERTY           (REFLEXIVE_OBJECT_PROPERTY           , ()->new AxiomEH<>(BuilderReflexiveObjectProperty::new)),
    /** IRREFLEXIVE_OBJECT_PROPERTY.        */  PARSER_IRREFLEXIVE_OBJECT_PROPERTY         (IRREFLEXIVE_OBJECT_PROPERTY         , ()->new AxiomEH<>(BuilderIrreflexiveObjectProperty::new)),
    /** TRANSITIVE_OBJECT_PROPERTY.         */  PARSER_TRANSITIVE_OBJECT_PROPERTY          (TRANSITIVE_OBJECT_PROPERTY          , ()->new AxiomEH<>(BuilderTransitiveObjectProperty::new)),
    /** SUB_DATA_PROPERTY_OF.               */  PARSER_SUB_DATA_PROPERTY_OF                (SUB_DATA_PROPERTY_OF                , SubDataPropertyOfEH::new),
    /** EQUIVALENT_DATA_PROPERTIES.         */  PARSER_EQUIVALENT_DATA_PROPERTIES          (EQUIVALENT_DATA_PROPERTIES          , EqDataPropertiesEH::new),
    /** DISJOINT_DATA_PROPERTIES.           */  PARSER_DISJOINT_DATA_PROPERTIES            (DISJOINT_DATA_PROPERTIES            , DisjointDataPropertiesEH::new),
    /** DATA_PROPERTY_DOMAIN.               */  PARSER_DATA_PROPERTY_DOMAIN                (DATA_PROPERTY_DOMAIN                , DataPropertyDomainEH::new),
    /** DATA_PROPERTY_RANGE.                */  PARSER_DATA_PROPERTY_RANGE                 (DATA_PROPERTY_RANGE                 , DataPropertyRangeEH::new),
    /** FUNCTIONAL_DATA_PROPERTY.           */  PARSER_FUNCTIONAL_DATA_PROPERTY            (FUNCTIONAL_DATA_PROPERTY            , ()->new AxiomEH<>(BuilderFunctionalDataProperty::new)),
    /** SAME_INDIVIDUAL.                    */  PARSER_SAME_INDIVIDUAL                     (SAME_INDIVIDUAL                     , SameIndividualsEH::new),
    /** DIFFERENT_INDIVIDUALS.              */  PARSER_DIFFERENT_INDIVIDUALS               (DIFFERENT_INDIVIDUALS               , DifferentIndividualsEH::new),
    /** CLASS_ASSERTION.                    */  PARSER_CLASS_ASSERTION                     (CLASS_ASSERTION                     , ClassAssertAxiomEH::new),
    /** OBJECT_PROPERTY_ASSERTION.          */  PARSER_OBJECT_PROPERTY_ASSERTION           (OBJECT_PROPERTY_ASSERTION           , ObjectPropertyAxiomEH::new),
    /** DATA_PROPERTY_ASSERTION.            */  PARSER_DATA_PROPERTY_ASSERTION             (DATA_PROPERTY_ASSERTION             , DataPropertyAxiomEH::new),
    /** NEGATIVE_OBJECT_PROPERTY_ASSERTION. */  PARSER_NEGATIVE_OBJECT_PROPERTY_ASSERTION  (NEGATIVE_OBJECT_PROPERTY_ASSERTION  , NegObjectPropertyAxiomEH::new),
    /** NEGATIVE_DATA_PROPERTY_ASSERTION.   */  PARSER_NEGATIVE_DATA_PROPERTY_ASSERTION    (NEGATIVE_DATA_PROPERTY_ASSERTION    , NegDataPropertyAxiomEH::new),
    /** HAS_KEY.                            */  PARSER_HAS_KEY                             (HAS_KEY                             , HasKeyEH::new),
    /** DECLARATION.                        */  PARSER_DECLARATION                         (DECLARATION                         , DeclarationEH::new),
    /** ANNOTATION_ASSERTION.               */  PARSER_ANNOTATION_ASSERTION                (ANNOTATION_ASSERTION                , AnnotationAxiomEH::new),
    /** ANNOTATION_PROPERTY_DOMAIN.         */  PARSER_ANNOTATION_PROPERTY_DOMAIN          (ANNOTATION_PROPERTY_DOMAIN          , AnnDomainEH::new),
    /** ANNOTATION_PROPERTY_RANGE.          */  PARSER_ANNOTATION_PROPERTY_RANGE           (ANNOTATION_PROPERTY_RANGE           , AnnotationRangeEH::new),
    /** SUB_ANNOTATION_PROPERTY_OF.         */  PARSER_SUB_ANNOTATION_PROPERTY_OF          (SUB_ANNOTATION_PROPERTY_OF          , SubAnnPropertyOfEH::new),
    /** DATATYPE_DEFINITION.                */  PARSER_DATATYPE_DEFINITION                 (DATATYPE_DEFINITION                 , DatatypeDefinitionEH::new),
    /** IRI_ELEMENT.                        */  PARSER_IRI_ELEMENT                         (IRI_ELEMENT                         , ()->new IRIEH(false)),
    /** ABBREVIATED_IRI_ELEMENT.            */  PARSER_ABBREVIATED_IRI_ELEMENT             (ABBREVIATED_IRI_ELEMENT             , ()->new IRIEH(true)),
    /** NODE_ID.                            */  PARSER_NODE_ID                             (NODE_ID                             ),
    /** ANNOTATION_URI.                     */  PARSER_ANNOTATION_URI                      (ANNOTATION_URI                      ),
    /** LABEL.                              */  PARSER_LABEL                               (LABEL                               ),
    /** COMMENT.                            */  PARSER_COMMENT                             (COMMENT                             ),
    /** DOCUMENTATION.                      */  PARSER_DOCUMENTATION                       (DOCUMENTATION                       ),
    /** DATATYPE_FACET.                     */  PARSER_DATATYPE_FACET                      (DATATYPE_FACET                      ),
    /** DATATYPE_IRI.                       */  PARSER_DATATYPE_IRI                        (DATATYPE_IRI                        ),
    /** DATA_RANGE.                         */  PARSER_DATA_RANGE                          (DATA_RANGE                          ),
    /** PREFIX.                             */  PARSER_PREFIX                              (PREFIX                              ),
    /** NAME_ATTRIBUTE.                     */  PARSER_NAME_ATTRIBUTE                      (NAME_ATTRIBUTE                      ),
    /** IRI_ATTRIBUTE.                      */  PARSER_IRI_ATTRIBUTE                       (IRI_ATTRIBUTE                       ),
    /** ABBREVIATED_IRI_ATTRIBUTE.          */  PARSER_ABBREVIATED_IRI_ATTRIBUTE           (ABBREVIATED_IRI_ATTRIBUTE           ),
    /** CARDINALITY_ATTRIBUTE.              */  PARSER_CARDINALITY_ATTRIBUTE               (CARDINALITY_ATTRIBUTE               ),
    
    // Rules Extensions
    /** DL_SAFE_RULE.                       */  PARSER_DL_SAFE_RULE                        (DL_SAFE_RULE                        , SWRLRuleEH::new),
    /** BODY.                               */  PARSER_BODY                                (BODY                                , AtomListEH::new),
    /** HEAD.                               */  PARSER_HEAD                                (HEAD                                , AtomListEH::new),
    /** CLASS_ATOM.                         */  PARSER_CLASS_ATOM                          (CLASS_ATOM                          , ClassAtomEH::new),
    /** DATA_RANGE_ATOM.                    */  PARSER_DATA_RANGE_ATOM                     (DATA_RANGE_ATOM                     , DataRangeAtomEH::new),
    /** OBJECT_PROPERTY_ATOM.               */  PARSER_OBJECT_PROPERTY_ATOM                (OBJECT_PROPERTY_ATOM                , ObjectPropertyAtomEH::new),
    /** DATA_PROPERTY_ATOM.                 */  PARSER_DATA_PROPERTY_ATOM                  (DATA_PROPERTY_ATOM                  , DataPropertyAtomEH::new),
    /** BUILT_IN_ATOM.                      */  PARSER_BUILT_IN_ATOM                       (BUILT_IN_ATOM                       , BuiltInAtomEH::new),
    /** SAME_INDIVIDUAL_ATOM.               */  PARSER_SAME_INDIVIDUAL_ATOM                (SAME_INDIVIDUAL_ATOM                , ()->new IndividualsAtomEH<>(BuilderSWRLSameIndividualAtom::new)),
    /** DIFFERENT_INDIVIDUALS_ATOM.         */  PARSER_DIFFERENT_INDIVIDUALS_ATOM          (DIFFERENT_INDIVIDUALS_ATOM          , ()->new IndividualsAtomEH<>(BuilderSWRLDifferentIndividualsAtom::new)),
    /** VARIABLE.                           */  PARSER_VARIABLE                            (VARIABLE                            , VariableEH::new),
    /** DESCRIPTION_GRAPH_RULE.             */  PARSER_DESCRIPTION_GRAPH_RULE              (DESCRIPTION_GRAPH_RULE              );
//@formatter:on

    private final String shortName;
    private final Supplier<OWLEH<?, ?>> create;

    PARSER_OWLXMLVocabulary(OWLXMLVocabulary name) {
        this(name, () -> {
            throw new OWLRuntimeException(
                name.getShortForm() + " vocabulary element does not have a handler");
        });
    }

    PARSER_OWLXMLVocabulary(OWLXMLVocabulary name, Supplier<OWLEH<?, ?>> create) {
        shortName = name.getShortForm();
        this.create = create;
    }

    /**
     * @return short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param handler owlxml handler
     * @return element handler
     */
    OWLEH<?, ?> createHandler(OWLXMLPH handler) {
        OWLEH<?, ?> owleh = create.get();
        owleh.setHandler(handler);
        return owleh;
    }
}


interface ObjectPropertyEH {

    <T> T getOWLObject();

    <T> T getOWLObject(Class<T> witness);
}


@SuppressWarnings({"unused"})
abstract class OWLEH<O, B extends Builder<O>> {

    final StringBuilder sb = new StringBuilder();
    OWLXMLPH handler;
    OWLEH<?, ?> parentHandler;
    String elementName;
    OWLDataFactory df;
    Function<OWLDataFactory, B> provider;
    B builder;
    HandleChild child;

    void setHandler(OWLXMLPH handler) {
        this.handler = handler;
        this.df = handler.getDataFactory();
        if (provider != null) {
            this.builder = provider.apply(df);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getOWLObject() {
        return (T) builder.buildObject();
    }

    public <T> T getOWLObject(Class<T> witness) {
        return witness.cast(getOWLObject());
    }

    IRI getIRIFromAttribute(String localName, String value) {
        if (localName.equals(IRI_ATTRIBUTE.getShortForm())) {
            return handler.getIRI(value);
        } else if (localName.equals(ABBREVIATED_IRI_ATTRIBUTE.getShortForm())) {
            return handler.getAbbreviatedIRI(value);
        } else if ("URI".equals(localName)) {
            // Legacy
            return handler.getIRI(value);
        }
        ensureAttributeNotNull(null, IRI_ATTRIBUTE.getShortForm());
        return df.getIRI("");
    }

    IRI getIRIFromElement(String elementLocalName, String textContent) {
        if (elementLocalName.equals(IRI_ELEMENT.getShortForm())) {
            return handler.getIRI(textContent.trim());
        } else if (elementLocalName.equals(ABBREVIATED_IRI_ELEMENT.getShortForm())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(handler, elementLocalName + " is not an IRI element");
    }

    OWLEH<?, ?> getParentHandler() {
        return verifyNotNull(parentHandler, "parentHandler cannot be null at this point");
    }

    void setParentHandler(OWLEH<?, ?> handler) {
        parentHandler = handler;
    }

    void attribute(String localName, String value) {}

    void startElement(String name) {
        elementName = name;
    }

    String getElementName() {
        return elementName;
    }

    void handleChild(AxiomEH<? extends OWLAxiom, ?> h) {
        OWLAxiom axiom = h.getOWLObject();
        if (!axiom.isAnnotationAxiom() || handler.getConfiguration().shouldLoadAnnotations()) {
            handler.getOntology().add(axiom);
        }
    }

    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {}

    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {}

    @SuppressWarnings("unchecked")
    void handleChild(ObjectPropertyEH h) {
        if (builder instanceof SettableProperty) {
            ((SettableProperty<OWLObjectPropertyExpression, ?>) builder)
                .withProperty(h.getOWLObject(OWLObjectPropertyExpression.class));
        }
    }

    @SuppressWarnings("unchecked")
    void handleChild(DataPropertyEH h) {
        if (builder instanceof SettableProperty) {
            ((SettableProperty<OWLDataPropertyExpression, ?>) builder)
                .withProperty(h.getOWLObject(OWLDataPropertyExpression.class));
        }
    }

    void handleChild(IndividualEH h) {}

    void handleChild(LiteralEH h) {}

    void handleChild(AnnEH h) {
        ((BaseBuilder<?, ?>) builder).withAnnotation(h.getOWLObject());
    }

    void handleChild(ChainEH h) {}

    void handleChild(DatatypeFacetEH h) {}

    @SuppressWarnings("unchecked")
    void handleChild(AnnotationPropEH h) {
        if (builder instanceof SettableProperty) {
            ((SettableProperty<OWLAnnotationProperty, ?>) builder)
                .withProperty(h.getOWLObject(OWLAnnotationProperty.class));
        }
    }

    void handleChild(AnonEH h) {}

    void handleChild(IRIEH h) {}

    void handleChild(VariableEH h) {}

    void handleChild(AtomEH<?, ?> h) {}

    void handleChild(AtomListEH h) {}

    void ensureNotNull(@Nullable Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserElementNotFoundException(handler, message);
        }
    }

    void ensureAttributeNotNull(@Nullable Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserAttributeNotFoundException(handler, message);
        }
    }

    void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            sb.append(chars, start, length);
        }
    }

    String getText() {
        return sb.toString();
    }

    boolean isTextContentPossible() {
        return false;
    }

    void endElement() {
        child.run(parentHandler, this);
    }

    enum HandleChild {
        //@formatter:off
        /** AbstractOWLAxiomEH            */ ABSTRACTOWLAXIOMEH              ((parent, child) -> parent.handleChild((AxiomEH<?, ?>)     child)), 
        /** AbstractClassExpressionEH     */ ABSTRACTCLASSEXPRESSIONEH       ((parent, child) -> parent.handleChild((ClassEH<?, ?>)     child)), 
        /** AbstractOWLDataRangeHandler   */ ABSTRACTOWLDATARANGEHANDLER     ((parent, child) -> parent.handleChild((DataRangeEH<?, ?>) child)), 
        /** ObjectPropertyEH              */ OBJECTPROPERTYEH                ((parent, child) -> parent.handleChild((ObjectPropertyEH)  child)), 
        /** OWLDataPropertyEH             */ OWLDATAPROPERTYEH               ((parent, child) -> parent.handleChild((DataPropertyEH)    child)), 
        /** OWLIndividualEH               */ OWLINDIVIDUALEH                 ((parent, child) -> parent.handleChild((IndividualEH)      child)), 
        /** OWLLiteralEH                  */ OWLLITERALEH                    ((parent, child) -> parent.handleChild((LiteralEH)         child)), 
        /** OWLAnnotationEH               */ OWLANNOTATIONEH                 ((parent, child) -> parent.handleChild((AnnEH)             child)), 
        /** OWLSubObjectPropertyChainEH   */ OWLSUBOBJECTPROPERTYCHAINEH     ((parent, child) -> parent.handleChild((ChainEH)           child)),
        /** OWLDatatypeFacetRestrictionEH */ OWLDATATYPEFACETRESTRICTIONEH   ((parent, child) -> parent.handleChild((DatatypeFacetEH)   child)),
        /** OWLAnnotationPropertyEH       */ OWLANNOTATIONPROPERTYEH         ((parent, child) -> parent.handleChild((AnnotationPropEH)  child)),
        /** OWLAnonymousIndividualEH      */ OWLANONYMOUSINDIVIDUALEH        ((parent, child) -> parent.handleChild((AnonEH)            child)),
        /** AbstractIRIEH                 */ ABSTRACTIRIEH                   ((parent, child) -> parent.handleChild((IRIEH)             child)), 
        /** SWRLVariableEH                */ SWRLVARIABLEEH                  ((parent, child) -> parent.handleChild((VariableEH)        child)),
        /** SWRLAtomEH                    */ SWRLATOMEH                      ((parent, child) -> parent.handleChild((AtomEH<?, ?>)      child)), 
        /** SWRLAtomListEH                */ SWRLATOMLISTEH                  ((parent, child) -> parent.handleChild((AtomListEH)        child));
        //@formatter:on
        private BiConsumer<OWLEH<?, ?>, OWLEH<?, ?>> consumer;

        HandleChild(BiConsumer<OWLEH<?, ?>, OWLEH<?, ?>> c) {
            consumer = c;
        }

        final void run(OWLEH<?, ?> parent, OWLEH<?, ?> child) {
            consumer.accept(parent, child);
        }
    }
}


class ClassEH<X extends OWLClassExpression, B extends Builder<X>> extends OWLEH<X, B> {

    public ClassEH(Function<OWLDataFactory, B> b) {
        provider = b;
        child = HandleChild.ABSTRACTCLASSEXPRESSIONEH;
    }
}


class DataCardEH<X extends OWLClassExpression, B extends Builder<X> & SettableCardinality<?> & SettableProperty<OWLDataPropertyExpression, ?> & SettableRange<OWLDataRange, ?>>
    extends DataREH<X, B> {

    public DataCardEH(Function<OWLDataFactory, B> b) {
        super(b);
    }

    @Override
    void attribute(String localName, String value) {
        if ("cardinality".equals(localName)) {
            builder.withCardinality(Integer.parseInt(value));
        }
    }
}


class AxiomEH<X extends OWLAxiom, B extends Builder<X>> extends OWLEH<X, B> {

    AxiomEH(Function<OWLDataFactory, B> b) {
        provider = b;
        child = HandleChild.ABSTRACTOWLAXIOMEH;
    }
}


abstract class DataRangeEH<X extends OWLDataRange, B extends Builder<X>> extends OWLEH<X, B> {

    public DataRangeEH(Function<OWLDataFactory, B> b) {
        provider = b;
        child = HandleChild.ABSTRACTOWLDATARANGEHANDLER;
    }
}


class ObjectCardEH<X extends OWLClassExpression, B extends Builder<X> & SettableCardinality<?> & SettableProperty<OWLObjectPropertyExpression, ?> & SettableRange<OWLClassExpression, ?>>
    extends ObjectREH<X, B> {

    public ObjectCardEH(Function<OWLDataFactory, B> b) {
        super(b);
    }

    @Override
    void attribute(String localName, String value) {
        if ("cardinality".equals(localName)) {
            builder.withCardinality(Integer.parseInt(value));
        }
    }
}


class ObjectPEH extends OWLEH<OWLObjectProperty, BuilderObjectProperty>
    implements ObjectPropertyEH {

    public ObjectPEH() {
        provider = BuilderObjectProperty::new;
        child = HandleChild.OBJECTPROPERTYEH;
    }
}


class DataREH<X extends OWLClassExpression, B extends Builder<X> & SettableProperty<OWLDataPropertyExpression, ?> & SettableRange<OWLDataRange, ?>>
    extends ClassEH<X, B> {

    public DataREH(Function<OWLDataFactory, B> b) {
        super(b);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        builder.withRange(df.getTopDatatype());
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withRange(h.getOWLObject());
    }
}


class ObjectREH<X extends OWLClassExpression, B extends Builder<X> & SettableProperty<OWLObjectPropertyExpression, ?> & SettableRange<OWLClassExpression, ?>>
    extends ClassEH<X, B> {

    public ObjectREH(Function<OWLDataFactory, B> b) {
        super(b);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        builder.withRange(df.getOWLThing());
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withRange(h.getOWLObject());
    }
}


class IRIEH extends OWLEH<IRI, Builder<IRI>> {

    Supplier<IRI> p;

    public IRIEH(boolean abbreviated) {
        child = HandleChild.ABSTRACTIRIEH;
        p = abbreviated ? this::shortIri : this::longIri;
    }

    @Override
    boolean isTextContentPossible() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IRI getOWLObject() {
        return p.get();
    }

    IRI longIri() {
        return handler.getIRI(getText().trim());
    }

    IRI shortIri() {
        return handler.getAbbreviatedIRI(getText().trim());
    }
}


class UnionOfEH extends OWLEH<OWLClassExpression, Builder<OWLClassExpression>> {

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        // We simply pass on to our parent, which MUST be an OWLDisjointUnionOf
        getParentHandler().handleChild(h);
    }

    @Override
    void endElement() {
        // nothing to do here
    }

    @Override
    public <T> T getOWLObject() {
        throw new OWLRuntimeException("getOWLObject should not be called on OWLUnionOfEH");
    }
}


class LegacyEntityAnnEH extends AxiomEH<OWLAnnotationAssertionAxiom, BuilderAnnotationAssertion> {

    LegacyEntityAnnEH() {
        super(BuilderAnnotationAssertion::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withSubject(h.getOWLObject(OWLClassExpression.class).asOWLClass());
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withSubject(h.getOWLObject(OWLDataPropertyExpression.class).asOWLDataProperty());
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withSubject(h.getOWLObject(OWLAnonymousIndividual.class));
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder
            .withSubject(h.getOWLObject(OWLObjectPropertyExpression.class).asOWLObjectProperty());
    }

    @Override
    void handleChild(AnnEH h) {
        if (builder.getSubject() == null) {
            super.handleChild(h);
        } else {
            OWLAnnotation o = h.getOWLObject();
            builder.withProperty(o.getProperty()).withValue(o.getValue());
        }
    }
}


class AnnotationAxiomEH extends AxiomEH<OWLAnnotationAssertionAxiom, BuilderAnnotationAssertion> {

    AnnotationAxiomEH() {
        super(BuilderAnnotationAssertion::new);
    }

    @Override
    void handleChild(IRIEH h) {
        internalSet(h.getOWLObject());
    }

    public <T extends OWLAnnotationSubject & OWLAnnotationValue> void internalSet(T h) {
        if (builder.getSubject() == null) {
            builder.withSubject(h);
        } else {
            builder.withValue(h);
        }
    }

    @Override
    void handleChild(AnonEH h) {
        internalSet(h.getOWLObject());
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withValue(h.getOWLObject());
    }
}


class AnnEH extends OWLEH<OWLAnnotation, BuilderAnnotation> {

    public AnnEH() {
        provider = BuilderAnnotation::new;
        child = HandleChild.OWLANNOTATIONEH;
    }

    @Override
    void attribute(String localName, String value) {
        super.attribute(localName, value);
        // Legacy Handling
        if (localName.equals(ANNOTATION_URI.getShortForm())) {
            builder.withProperty(handler.getIRI(value));
        }
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withValue(h.getOWLObject());
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withValue(h.getOWLObject());
    }

    @Override
    void handleChild(IRIEH h) {
        builder.withValue(h.getOWLObject());
    }
}


class AnnDomainEH
    extends AxiomEH<OWLAnnotationPropertyDomainAxiom, BuilderAnnotationPropertyDomain> {

    AnnDomainEH() {
        super(BuilderAnnotationPropertyDomain::new);
    }

    @Override
    void handleChild(IRIEH h) {
        builder.withDomain(h.getOWLObject());
    }
}


class AnnotationPropEH extends OWLEH<OWLAnnotationProperty, BuilderAnnotationProperty> {

    public AnnotationPropEH() {
        provider = BuilderAnnotationProperty::new;
        child = HandleChild.OWLANNOTATIONPROPERTYEH;
    }

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class AnnotationRangeEH
    extends AxiomEH<OWLAnnotationPropertyRangeAxiom, BuilderAnnotationPropertyRange> {

    AnnotationRangeEH() {
        super(BuilderAnnotationPropertyRange::new);
    }

    @Override
    void handleChild(IRIEH h) {
        builder.withRange(h.getOWLObject());
    }
}


class AnonEH extends OWLEH<OWLAnonymousIndividual, BuilderAnonymousIndividual> {

    public AnonEH() {
        this(BuilderAnonymousIndividual::new);
    }

    public AnonEH(Function<OWLDataFactory, BuilderAnonymousIndividual> b) {
        provider = b;
        child = HandleChild.OWLANONYMOUSINDIVIDUALEH;
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals(NODE_ID.getShortForm())) {
            builder.withId(value.trim());
        } else {
            super.attribute(localName, value);
        }
    }
}


class ClassAssertAxiomEH extends AxiomEH<OWLClassAssertionAxiom, BuilderClassAssertion> {

    ClassAssertAxiomEH() {
        super(BuilderClassAssertion::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withClass(h.getOWLObject());
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withIndividual(h.getOWLObject());
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withIndividual(h.getOWLObject());
    }
}


class OWLClassEH extends ClassEH<OWLClass, BuilderClass> {

    public OWLClassEH() {
        super(BuilderClass::new);
    }

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class DataComplEH extends DataRangeEH<OWLDataComplementOf, BuilderDataComplementOf> {

    public DataComplEH() {
        super(BuilderDataComplementOf::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withRange(h.getOWLObject(OWLDataRange.class));
    }
}


class DataHasValueEH extends ClassEH<OWLDataHasValue, BuilderDataHasValue> {

    public DataHasValueEH() {
        super(BuilderDataHasValue::new);
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withLiteral(h.getOWLObject());
    }
}


class DataIntersectionOfEH extends DataRangeEH<OWLDataIntersectionOf, BuilderDataIntersectionOf> {

    public DataIntersectionOfEH() {
        super(BuilderDataIntersectionOf::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class DataOneOfEH extends DataRangeEH<OWLDataOneOf, BuilderDataOneOf> {

    public DataOneOfEH() {
        super(BuilderDataOneOf::new);
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class DataPropertyAxiomEH
    extends AxiomEH<OWLDataPropertyAssertionAxiom, BuilderDataPropertyAssertion> {

    DataPropertyAxiomEH() {
        super(BuilderDataPropertyAssertion::new);
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withSubject(h.getOWLObject());
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withSubject(h.getOWLObject());
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withValue(h.getOWLObject());
    }
}


class DataPropertyDomainEH extends AxiomEH<OWLDataPropertyDomainAxiom, BuilderDataPropertyDomain> {

    DataPropertyDomainEH() {
        super(BuilderDataPropertyDomain::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withDomain(h.getOWLObject());
    }
}


class DataPropertyEH extends OWLEH<OWLDataProperty, BuilderDataProperty> {

    public DataPropertyEH() {
        provider = BuilderDataProperty::new;
        child = HandleChild.OWLDATAPROPERTYEH;
    }

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class DataPropertyRangeEH extends AxiomEH<OWLDataPropertyRangeAxiom, BuilderDataPropertyRange> {

    DataPropertyRangeEH() {
        super(BuilderDataPropertyRange::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withRange(h.getOWLObject(OWLDataRange.class));
    }
}


class DataRestrictionEH extends DataRangeEH<OWLDatatypeRestriction, BuilderDatatypeRestriction> {

    @Nullable
    BuilderFacetRestriction oneRestriction;

    public DataRestrictionEH() {
        super(BuilderDatatypeRestriction::new);
    }

    @Override
    void setHandler(OWLXMLPH handler) {
        super.setHandler(handler);
        oneRestriction = new BuilderFacetRestriction(df);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withDatatype(h.getOWLObject(OWLDataRange.class).asOWLDatatype());
    }

    @Override
    void handleChild(LiteralEH h) {
        verifyNotNull(oneRestriction).withLiteral(h.getOWLObject());
    }

    @Override
    void handleChild(DatatypeFacetEH h) {
        builder.withItem(h.getOWLObject());
    }

    @Override
    void attribute(String localName, String value) {
        super.attribute(localName, value);
        if ("facet".equals(localName)) {
            verifyNotNull(oneRestriction).withFacet(OWLFacet.getFacet(handler.getIRI(value)));
        }
    }
}


class DataUnionOfEH extends DataRangeEH<OWLDataUnionOf, BuilderDataUnionOf> {

    public DataUnionOfEH() {
        super(BuilderDataUnionOf::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class DatatypeDefinitionEH extends AxiomEH<OWLDatatypeDefinitionAxiom, BuilderDatatypeDefinition> {

    DatatypeDefinitionEH() {
        super(BuilderDatatypeDefinition::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        OWLDataRange handledDataRange = h.getOWLObject();
        if (handledDataRange.isOWLDatatype() && builder.getType() == null) {
            builder.with(handledDataRange.asOWLDatatype());
        } else {
            builder.withType(handledDataRange);
        }
    }
}


class DatatypeEH extends DataRangeEH<OWLDatatype, BuilderDatatype> {

    public DatatypeEH() {
        super(BuilderDatatype::new);
    }

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class DatatypeFacetEH extends OWLEH<OWLFacetRestriction, BuilderFacetRestriction> {

    public DatatypeFacetEH() {
        provider = BuilderFacetRestriction::new;
        child = HandleChild.OWLDATATYPEFACETRESTRICTIONEH;
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withLiteral(h.getOWLObject());
    }

    @Override
    void attribute(String localName, String value) {
        if ("facet".equals(localName)) {
            builder.withFacet(OWLFacet.getFacet(df.getIRI(value)));
        }
    }
}


class DatatypeRestrictionEH
    extends DataRangeEH<OWLDatatypeRestriction, BuilderDatatypeRestriction> {

    public DatatypeRestrictionEH() {
        super(BuilderDatatypeRestriction::new);
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        OWLDataRange dr = h.getOWLObject();
        if (dr.isOWLDatatype()) {
            builder.withDatatype(dr.asOWLDatatype());
        }
    }

    @Override
    void handleChild(DatatypeFacetEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class DeclarationEH extends AxiomEH<OWLDeclarationAxiom, BuilderDeclaration> {

    DeclarationEH() {
        super(BuilderDeclaration::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withEntity(h.getOWLObject(OWLClassExpression.class).asOWLClass());
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder.withEntity(h.getOWLObject(OWLObjectPropertyExpression.class).asOWLObjectProperty());
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withEntity(h.getOWLObject(OWLDataPropertyExpression.class).asOWLDataProperty());
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.withEntity(h.getOWLObject(OWLDataRange.class).asOWLDatatype());
    }

    @Override
    void handleChild(AnnotationPropEH h) {
        builder.withEntity(h.getOWLObject(OWLAnnotationProperty.class).asOWLAnnotationProperty());
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withEntity(h.getOWLObject(OWLIndividual.class).asOWLNamedIndividual());
    }
}


class DifferentIndividualsEH
    extends AxiomEH<OWLDifferentIndividualsAxiom, BuilderDifferentIndividuals> {

    DifferentIndividualsEH() {
        super(BuilderDifferentIndividuals::new);
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withItem(h.getOWLObject());
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class DisjointClassesEH extends AxiomEH<OWLDisjointClassesAxiom, BuilderDisjointClasses> {

    DisjointClassesEH() {
        super(BuilderDisjointClasses::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class DisjointDataPropertiesEH
    extends AxiomEH<OWLDisjointDataPropertiesAxiom, BuilderDisjointDataProperties> {

    DisjointDataPropertiesEH() {
        super(BuilderDisjointDataProperties::new);
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class DisjointObjectPropertiesEH
    extends AxiomEH<OWLDisjointObjectPropertiesAxiom, BuilderDisjointObjectProperties> {

    DisjointObjectPropertiesEH() {
        super(BuilderDisjointObjectProperties::new);
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class DisjointUnionEH extends AxiomEH<OWLDisjointUnionAxiom, BuilderDisjointUnion> {

    DisjointUnionEH() {
        super(BuilderDisjointUnion::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        if (builder.getClassExpression() == null) {
            builder.withClass(h.getOWLObject(OWLClassExpression.class).asOWLClass());
        } else {
            builder.withItem(h.getOWLObject());
        }
    }
}


class EquivalentClassesEH extends AxiomEH<OWLEquivalentClassesAxiom, BuilderEquivalentClasses> {

    EquivalentClassesEH() {
        super(BuilderEquivalentClasses::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class EqDataPropertiesEH
    extends AxiomEH<OWLEquivalentDataPropertiesAxiom, BuilderEquivalentDataProperties> {

    EqDataPropertiesEH() {
        super(BuilderEquivalentDataProperties::new);
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class EqObjectPropertiesEH
    extends AxiomEH<OWLEquivalentObjectPropertiesAxiom, BuilderEquivalentObjectProperties> {

    EqObjectPropertiesEH() {
        super(BuilderEquivalentObjectProperties::new);
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class HasKeyEH extends AxiomEH<OWLHasKeyAxiom, BuilderHasKey> {

    HasKeyEH() {
        super(BuilderHasKey::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withClass(h.getOWLObject());
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class IndividualEH extends OWLEH<OWLNamedIndividual, BuilderNamedIndividual> {

    public IndividualEH() {
        provider = BuilderNamedIndividual::new;
        child = HandleChild.OWLINDIVIDUALEH;
    }

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class InverseObjectAxiomEH
    extends AxiomEH<OWLInverseObjectPropertiesAxiom, BuilderInverseObjectProperties> {

    InverseObjectAxiomEH() {
        super(BuilderInverseObjectProperties::new);
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        if (builder.getProperty() == null) {
            builder.withProperty(h.getOWLObject());
        } else {
            builder.withInverseProperty(h.getOWLObject());
        }
    }
}


class InverseObjectEH extends OWLEH<OWLObjectInverseOf, BuilderObjectInverseOf>
    implements ObjectPropertyEH {

    public InverseObjectEH() {
        provider = BuilderObjectInverseOf::new;
        child = HandleChild.OBJECTPROPERTYEH;
    }
}


class LiteralEH extends OWLEH<OWLLiteral, BuilderLiteral> {

    public LiteralEH() {
        provider = BuilderLiteral::new;
        child = HandleChild.OWLLITERALEH;
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals(DATATYPE_IRI.getShortForm())) {
            IRI iri = handler.getIRI(value);
            OWLDatatype type = df.getOWLDatatype(iri);
            // do not set the type for string types - it overrides the language
            // tag if one exists
            if (!OWL2Datatype.RDF_LANG_STRING.matches(type)
                && !OWL2Datatype.RDF_PLAIN_LITERAL.matches(type)
                && !OWL2Datatype.XSD_STRING.matches(type)) {
                builder.withDatatype(iri);
            }
        } else if ("lang".equals(localName)) {
            builder.withLanguage(value);
        }
    }

    @Override
    void endElement() {
        builder.withLiteralForm(getText());
        getParentHandler().handleChild(this);
    }

    @Override
    boolean isTextContentPossible() {
        return true;
    }
}


class NegDataPropertyAxiomEH
    extends AxiomEH<OWLNegativeDataPropertyAssertionAxiom, BuilderNegativeDataPropertyAssertion> {

    NegDataPropertyAxiomEH() {
        super(BuilderNegativeDataPropertyAssertion::new);
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withSubject(h.getOWLObject());
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withSubject(h.getOWLObject());
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.withValue(h.getOWLObject());
    }
}


class NegObjectPropertyAxiomEH extends
    AxiomEH<OWLNegativeObjectPropertyAssertionAxiom, BuilderNegativeObjectPropertyAssertion> {

    NegObjectPropertyAxiomEH() {
        super(BuilderNegativeObjectPropertyAssertion::new);
    }

    @Override
    void handleChild(AnonEH h) {
        internalSet(h.getOWLObject());
    }

    public void internalSet(OWLIndividual h) {
        if (builder.getSubject() == null) {
            builder.withSubject(h);
        } else if (builder.getValue() == null) {
            builder.withValue(h);
        }
    }

    @Override
    void handleChild(IndividualEH h) {
        internalSet(h.getOWLObject());
    }
}


class ComplementOfEH extends ClassEH<OWLObjectComplementOf, BuilderComplementOf> {

    public ComplementOfEH() {
        super(BuilderComplementOf::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withClass(h.getOWLObject());
    }
}


class HasValueEH extends ClassEH<OWLObjectHasValue, BuilderObjectHasValue> {

    public HasValueEH() {
        super(BuilderObjectHasValue::new);
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withValue(h.getOWLObject());
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withValue(h.getOWLObject());
    }
}


class IntersectionOfEH extends ClassEH<OWLObjectIntersectionOf, BuilderObjectIntersectionOf> {

    public IntersectionOfEH() {
        super(BuilderObjectIntersectionOf::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class OneOfEH extends ClassEH<OWLObjectOneOf, BuilderOneOf> {

    public OneOfEH() {
        super(BuilderOneOf::new);
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class ObjectPropertyAxiomEH
    extends AxiomEH<OWLObjectPropertyAssertionAxiom, BuilderObjectPropertyAssertion> {

    ObjectPropertyAxiomEH() {
        super(BuilderObjectPropertyAssertion::new);
    }

    @Override
    void handleChild(AnonEH h) {
        internalSet(h.getOWLObject());
    }

    public void internalSet(OWLIndividual h) {
        if (builder.getSubject() == null) {
            builder.withSubject(h);
        } else if (builder.getValue() == null) {
            builder.withValue(h);
        }
    }

    @Override
    void handleChild(IndividualEH h) {
        internalSet(h.getOWLObject());
    }
}


class ObjectPropertyDomainEH
    extends AxiomEH<OWLObjectPropertyDomainAxiom, BuilderObjectPropertyDomain> {

    ObjectPropertyDomainEH() {
        super(BuilderObjectPropertyDomain::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withDomain(h.getOWLObject());
    }
}


class OWLObjectPropertyEH extends ObjectPEH {

    @Override
    void attribute(String localName, String value) {
        builder.withIRI(getIRIFromAttribute(localName, value));
    }
}


class ObjectPropertyRangeEH
    extends AxiomEH<OWLObjectPropertyRangeAxiom, BuilderObjectPropertyRange> {

    ObjectPropertyRangeEH() {
        super(BuilderObjectPropertyRange::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withRange(h.getOWLObject());
    }
}


class ObjectUnionOfEH extends ClassEH<OWLObjectUnionOf, BuilderUnionOf> {

    public ObjectUnionOfEH() {
        super(BuilderUnionOf::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.withItem(h.getOWLObject());
    }
}


class SameIndividualsEH extends AxiomEH<OWLSameIndividualAxiom, BuilderSameIndividual> {

    SameIndividualsEH() {
        super(BuilderSameIndividual::new);
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.withItem(h.getOWLObject());
    }

    @Override
    void handleChild(AnonEH h) {
        builder.withItem(h.getOWLObject());
    }
}


class SubAnnPropertyOfEH
    extends AxiomEH<OWLSubAnnotationPropertyOfAxiom, BuilderSubAnnotationPropertyOf> {

    SubAnnPropertyOfEH() {
        super(BuilderSubAnnotationPropertyOf::new);
    }

    @Override
    void handleChild(AnnotationPropEH h) {
        if (builder.getSub() == null) {
            builder.withSub(h.getOWLObject());
        } else if (builder.getSup() == null) {
            builder.withSup(h.getOWLObject());
        } else {
            ensureNotNull(null, "two annotation properties elements");
        }
    }
}


class SubClassEH extends AxiomEH<OWLSubClassOfAxiom, BuilderSubClass> {

    SubClassEH() {
        super(BuilderSubClass::new);
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        if (builder.getSub() == null) {
            builder.withSub(h.getOWLObject());
        } else if (builder.getSup() == null) {
            builder.withSup(h.getOWLObject());
        }
    }
}


class SubDataPropertyOfEH extends AxiomEH<OWLSubDataPropertyOfAxiom, BuilderSubDataProperty> {

    SubDataPropertyOfEH() {
        super(BuilderSubDataProperty::new);
    }

    @Override
    void handleChild(DataPropertyEH h) {
        if (builder.getSub() == null) {
            builder.withSub(h.getOWLObject());
        } else if (builder.getSup() == null) {
            builder.withSup(h.getOWLObject());
        }
    }
}


class ChainEH
    extends OWLEH<List<OWLObjectPropertyExpression>, Builder<List<OWLObjectPropertyExpression>>> {

    final List<OWLObjectPropertyExpression> propertyList = new ArrayList<>();

    public ChainEH() {
        child = HandleChild.OWLSUBOBJECTPROPERTYCHAINEH;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OWLObjectPropertyExpression> getOWLObject() {
        return propertyList;
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        propertyList.add(h.getOWLObject());
    }
}


class SubObjectPropertyOfEH extends AxiomEH<OWLSubObjectPropertyOfAxiom, BuilderSubObjectProperty> {

    @Nullable
    BuilderPropertyChain chain;

    SubObjectPropertyOfEH() {
        super(BuilderSubObjectProperty::new);
    }

    @Override
    void setHandler(OWLXMLPH handler) {
        super.setHandler(handler);
        chain = new BuilderPropertyChain(df);
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        OWLObjectPropertyExpression prop = h.getOWLObject();
        if (builder.getSub() == null && verifyNotNull(chain).chainSize() == 0) {
            builder.withSub(prop);
        } else if (builder.getSup() == null) {
            builder.withSup(prop);
            verifyNotNull(chain).withProperty(prop);
        } else {
            ensureNotNull(null, "Expected two object property expression elements");
        }
    }

    @Override
    void handleChild(AnnEH h) {
        super.handleChild(h);
        verifyNotNull(chain).withAnnotation(h.getOWLObject());
    }

    @Override
    void handleChild(ChainEH h) {
        verifyNotNull(chain).withPropertiesInChain(h.getOWLObject());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getOWLObject() {
        if (verifyNotNull(chain).chainSize() > 0) {
            return (T) verifyNotNull(chain).buildObject();
        }
        return (T) builder.buildObject();
    }
}


abstract class AtomEH<X extends SWRLAtom, B extends Builder<X>> extends OWLEH<X, B> {

    public AtomEH() {
        child = HandleChild.SWRLATOMEH;
    }

    protected SWRLLiteralArgument swrlLit(OWLLiteral lit) {
        return df.getSWRLLiteralArgument(lit);
    }

    protected SWRLIndividualArgument swrlInd(OWLIndividual i) {
        return df.getSWRLIndividualArgument(i);
    }
}


class AtomListEH extends OWLEH<List<SWRLAtom>, Builder<List<SWRLAtom>>> {

    final List<SWRLAtom> atoms = new ArrayList<>();

    public AtomListEH() {
        child = HandleChild.SWRLATOMLISTEH;
    }

    @Override
    void handleChild(AtomEH<?, ?> h) {
        atoms.add(h.getOWLObject(SWRLAtom.class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SWRLAtom> getOWLObject() {
        return atoms;
    }
}


class BuiltInAtomEH extends AtomEH<SWRLBuiltInAtom, BuilderSWRLBuiltInAtom> {

    public BuiltInAtomEH() {
        provider = BuilderSWRLBuiltInAtom::new;
    }

    @Override
    void attribute(String localName, String value) {
        builder.with(getIRIFromAttribute(localName, value));
    }

    @Override
    void handleChild(VariableEH h) {
        builder.with(h.getOWLObject(SWRLDArgument.class));
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.with(swrlLit(h.getOWLObject()));
    }
}


class ClassAtomEH extends AtomEH<SWRLClassAtom, BuilderSWRLClassAtom> {

    public ClassAtomEH() {
        provider = BuilderSWRLClassAtom::new;
    }

    @Override
    void handleChild(VariableEH h) {
        builder.with(h.getOWLObject(SWRLIArgument.class));
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        builder.with(h.getOWLObject(OWLClassExpression.class));
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.with(swrlInd(h.getOWLObject()));
    }
}


class DataPropertyAtomEH extends AtomEH<SWRLDataPropertyAtom, BuilderSWRLDataPropertyAtom> {

    public DataPropertyAtomEH() {
        provider = BuilderSWRLDataPropertyAtom::new;
    }

    @Override
    void handleChild(DataPropertyEH h) {
        builder.withProperty(h.getOWLObject());
    }

    @Override
    void handleChild(VariableEH h) {
        if (builder.getArg0() == null) {
            builder.with(h.getOWLObject(SWRLIArgument.class));
        } else if (builder.getArg1() == null) {
            builder.with(h.getOWLObject(SWRLDArgument.class));
        }
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.with(swrlLit(h.getOWLObject()));
    }

    @Override
    void handleChild(IndividualEH h) {
        builder.with(swrlInd(h.getOWLObject()));
    }

    @Override
    void handleChild(AnonEH h) {
        builder.with(swrlInd(h.getOWLObject()));
    }
}


class DataRangeAtomEH extends AtomEH<SWRLDataRangeAtom, BuilderSWRLDataRangeAtom> {

    public DataRangeAtomEH() {
        provider = BuilderSWRLDataRangeAtom::new;
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        builder.with(h.getOWLObject(OWLDataRange.class));
    }

    @Override
    void handleChild(VariableEH h) {
        builder.with(h.getOWLObject(SWRLDArgument.class));
    }

    @Override
    void handleChild(LiteralEH h) {
        builder.with(swrlLit(h.getOWLObject()));
    }
}


class IndividualsAtomEH<X extends SWRLBinaryAtom<SWRLIArgument, SWRLIArgument>, B extends BuilderSWRLIndividualsAtom<X, B>>
    extends AtomEH<X, B> {

    public IndividualsAtomEH(Function<OWLDataFactory, B> b) {
        provider = b;
    }

    @Override
    void handleChild(VariableEH h) {
        internalSet(h.getOWLObject());
    }

    public void internalSet(SWRLIArgument h) {
        if (builder.getArg0() == null) {
            builder.withArg0(h);
        } else if (builder.getArg1() == null) {
            builder.withArg1(h);
        }
    }

    @Override
    void handleChild(IndividualEH h) {
        internalSet(swrlInd(h.getOWLObject()));
    }
}


class ObjectPropertyAtomEH
    extends IndividualsAtomEH<SWRLObjectPropertyAtom, BuilderSWRLObjectPropertyAtom> {

    public ObjectPropertyAtomEH() {
        super(BuilderSWRLObjectPropertyAtom::new);
    }

    @Override
    void handleChild(ObjectPropertyEH h) {
        builder.withProperty(h.getOWLObject());
    }
}


class SWRLRuleEH extends AxiomEH<SWRLRule, BuilderSWRLRule> {

    SWRLRuleEH() {
        super(BuilderSWRLRule::new);
    }

    @Override
    void handleChild(AtomListEH h) {
        if (builder.bodySize() == 0) {
            builder.withBody(h.getOWLObject());
        } else if (builder.headSize() == 0) {
            builder.withHead(h.getOWLObject());
        }
    }
}


class VariableEH extends OWLEH<SWRLVariable, BuilderSWRLVariable> {

    public VariableEH() {
        child = HandleChild.SWRLVARIABLEEH;
        provider = BuilderSWRLVariable::new;
    }

    @Override
    void attribute(String localName, String value) {
        if (XMLUtils.isNCName(value)) {
            builder.with(df.getIRI("urn:swrl:var#", value));
        } else {
            builder.with(getIRIFromAttribute(localName, value));
        }
    }
}


class OntologyEH extends OWLEH<OWLOntology, Builder<OWLOntology>> {

    @Override
    void startElement(String name) {
        // nothing to do here
    }

    @Override
    void attribute(String localName, String value) {
        OWLOntology o = handler.getOntology();
        if ("ontologyIRI".equals(localName)) {
            o.applyChange(new SetOntologyID(o, df.getOWLOntologyID(
                Optional.ofNullable(df.getIRI(value)), o.getOntologyID().getVersionIRI())));
        }
        if ("versionIRI".equals(localName)) {
            o.applyChange(new SetOntologyID(o, df.getOWLOntologyID(
                o.getOntologyID().getOntologyIRI(), Optional.ofNullable(df.getIRI(value)))));
        }
    }

    @Override
    void handleChild(DataRangeEH<? extends OWLDataRange, ?> h) {
        // nothing to do here
    }

    @Override
    void handleChild(ClassEH<? extends OWLClassExpression, ?> h) {
        // nothing to do here
    }

    @Override
    void handleChild(AnnEH h) {
        OWLOntology o = handler.getOntology();
        o.applyChange(new AddOntologyAnnotation(o, h.getOWLObject()));
    }

    @Override
    void endElement() {
        // nothing to do here
    }

    @Override
    @SuppressWarnings("unchecked")
    public OWLOntology getOWLObject() {
        return handler.getOntology();
    }

    @Override
    void setParentHandler(OWLEH<?, ?> handler) {
        // nothing to do here
    }
}


class ImportsEH extends OWLEH<OWLOntology, Builder<OWLOntology>> {

    @Override
    void endElement() {
        IRI ontIRI = handler.getIRI(getText().trim());
        OWLImportsDeclaration decl = df.getOWLImportsDeclaration(ontIRI);
        handler.getOntology().applyChange(new AddImport(handler.getOntology(), decl));
        handler.getOWLOntologyManager().makeLoadImportRequest(decl, handler.getConfiguration());
    }

    @Override
    public <T> T getOWLObject() {
        throw new OWLRuntimeException("There is no OWLObject for imports handlers");
    }

    @Override
    boolean isTextContentPossible() {
        return true;
    }
}
