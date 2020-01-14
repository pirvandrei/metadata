package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.RDFSComment;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_STRING;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;
import org.semanticweb.owlapi6.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

public class LoadAnonymousTestCase extends TestBase {

    private static final String OBO_UO = "http://purl.obolibrary.org/obo/uo#";
    private static final String OBO = "http://purl.obolibrary.org/obo/";
    private static final String OBO_IN_OWL = "http://www.geneontology.org/formats/oboInOwl#";

    private static OWLLiteral literal(String s) {
        return Literal(s, XSD_STRING);
    }

    private static OWLAnnotationAssertionAxiom comment(OWLAnnotationSubject s,
        OWLAnnotationValue v) {
        return AnnotationAssertion(RDFSComment(), s, v);
    }

    private static OWLAnnotationAssertionAxiom label(OWLAnnotationSubject s, OWLAnnotationValue v) {
        return AnnotationAssertion(RDFSLabel(), s, v);
    }

    private static OWLAnnotationProperty ap(String s, String r) {
        return df.getOWLAnnotationProperty(s, r);
    }

    @Test
    public void shouldLoad() throws OWLOntologyCreationException {
        StringDocumentSource streamDocumentSource =
            new StringDocumentSource(TestFiles.loadOboAnonymous, new OBODocumentFormat());
        OntologyConfigurator loaderConfig = new OntologyConfigurator()
            .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology =
            m1.loadOntologyFromOntologyDocument(streamDocumentSource, loaderConfig);
        OWLAnnotationProperty date = ap(OBO_IN_OWL, "date");
        OWLAnnotationProperty mpathSlim = ap(OBO_UO, "mpath_slim");
        OWLAnnotationProperty subsetProperty = ap(OBO_IN_OWL, "SubsetProperty");
        OWLAnnotationProperty attributeSlim = ap(OBO_UO, "attribute_slim");
        OWLAnnotationProperty hasOBONamespace = ap(OBO_IN_OWL, "hasOBONamespace");
        OWLAnnotationProperty autogeneratedby = ap(OBO_IN_OWL, "auto-generated-by");
        OWLAnnotationProperty hasDbXref = ap(OBO_IN_OWL, "hasDbXref");
        OWLAnnotationProperty defaultnamespace = ap(OBO_IN_OWL, "default-namespace");
        OWLAnnotationProperty hasOBOFormatVersion = ap(OBO_IN_OWL, "hasOBOFormatVersion");
        OWLAnnotationProperty iao0000115 = ap(OBO, "IAO_0000115");
        OWLAnnotationProperty namespaceIdRule = ap(OBO_IN_OWL, "NamespaceIdRule");
        OWLAnnotationProperty createdBy = ap(OBO_IN_OWL, "created_by");
        OWLAnnotationProperty inSubset = ap(OBO_IN_OWL, "inSubset");
        OWLAnnotationProperty savedby = ap(OBO_IN_OWL, "saved-by");
        OWLClass pato0001708 = Class(IRI(OBO, "PATO_0001708"));
        OWLClass uo0 = Class(IRI(OBO, "UO_0000000"));
        OWLClass uo1 = Class(IRI(OBO, "UO_0000001"));
        OWLAnnotationProperty id = ap(OBO_IN_OWL, "id");
        OWLAnnotationProperty abnormalSlim = ap(OBO_UO, "abnormal_slim");
        OWLAnnotationProperty scalarSlim = ap(OBO_UO, "scalar_slim");
        OWLLiteral literal = literal("Wikipedia:Wikipedia");
        OWLAnnotationProperty unitSlim = ap(OBO_UO, "unit_slim");
        OWLAnnotationProperty absentSlim = ap(OBO_UO, "absent_slim");
        OWLObjectProperty isUnitOf = ObjectProperty(IRI(OBO_UO, "is_unit_of"));
        OWLAnnotationProperty cellQuality = ap(OBO_UO, "cell_quality");
        OWLAnnotationProperty unitGroupSlim = ap(OBO_UO, "unit_group_slim");
        OWLAnnotationProperty valueSlim = ap(OBO_UO, "value_slim");
        OWLAnnotationProperty prefixSlim = ap(OBO_UO, "prefix_slim");
        OWLAnnotationProperty dispositionSlim = ap(OBO_UO, "disposition_slim");
        OWLAnnotationProperty relationalSlim = ap(OBO_UO, "relational_slim");
        Set<OWLAxiom> expected = new HashSet<>(Arrays.asList(Declaration(date),
            Declaration(autogeneratedby), Declaration(hasDbXref), Declaration(defaultnamespace),
            Declaration(subsetProperty), Declaration(hasOBOFormatVersion), Declaration(iao0000115),
            Declaration(namespaceIdRule), Declaration(createdBy), Declaration(inSubset),
            Declaration(savedby), Declaration(pato0001708), Declaration(uo0),
            Declaration(RDFSComment()), Declaration(RDFSLabel()), Declaration(hasOBONamespace),
            Declaration(uo1), Declaration(id), SubAnnotationPropertyOf(mpathSlim, subsetProperty),
            AnnotationAssertion(hasOBONamespace, uo1.getIRI(), literal("unit.ontology")),
            comment(attributeSlim.getIRI(), literal("Attribute slim")),
            label(iao0000115.getIRI(), literal("definition")),
            AnnotationAssertion(hasOBONamespace, uo0.getIRI(), literal("unit.ontology")),
            SubAnnotationPropertyOf(unitSlim, subsetProperty),
            comment(valueSlim.getIRI(), literal("Value slim")),
            SubAnnotationPropertyOf(absentSlim, subsetProperty),
            SubAnnotationPropertyOf(abnormalSlim, subsetProperty),
            label(uo1.getIRI(), literal("length unit")),
            label(hasOBOFormatVersion.getIRI(), literal("has_obo_format_version")),
            label(namespaceIdRule.getIRI(), literal("namespace-id-rule")),
            SubClassOf(uo1, ObjectSomeValuesFrom(isUnitOf, pato0001708)),
            SubAnnotationPropertyOf(cellQuality, subsetProperty),
            comment(relationalSlim.getIRI(), literal(
                "Relational slim: types of quality that require an additional entity in order to exist")),
            SubAnnotationPropertyOf(prefixSlim, subsetProperty),
            SubAnnotationPropertyOf(scalarSlim, subsetProperty),
            comment(scalarSlim.getIRI(), literal("Scalar slim")),
            comment(abnormalSlim.getIRI(), literal("Abnormal/normal slim")),
            SubAnnotationPropertyOf(attributeSlim, subsetProperty),
            label(uo0.getIRI(), literal("unit")),
            SubAnnotationPropertyOf(dispositionSlim, subsetProperty),
            comment(unitSlim.getIRI(), literal("unit slim")),
            SubAnnotationPropertyOf(relationalSlim, subsetProperty),
            AnnotationAssertion(id, uo1.getIRI(), literal("UO:0000001")),
            comment(mpathSlim.getIRI(), literal("Pathology slim")),
            AnnotationAssertion(createdBy, uo1.getIRI(), literal("george gkoutos")),
            label(hasDbXref.getIRI(), literal("database_cross_reference")), SubClassOf(uo1, uo0),
            label(hasOBONamespace.getIRI(), literal("has_obo_namespace")),
            AnnotationAssertion(id, uo0.getIRI(), literal("UO:0000000")),
            AnnotationAssertion(createdBy, uo0.getIRI(), literal("george gkoutos")),
            comment(prefixSlim.getIRI(), literal("prefix slim")),
            comment(cellQuality.getIRI(), literal("cell_quality")),
            comment(absentSlim.getIRI(), literal("Absent/present slim")),
            label(subsetProperty.getIRI(), literal("subset_property")),
            SubAnnotationPropertyOf(unitGroupSlim, subsetProperty),
            comment(unitGroupSlim.getIRI(), literal("unit group slim")),
            comment(dispositionSlim.getIRI(), literal("Disposition slim")),
            label(inSubset.getIRI(), literal("in_subset")),
            SubAnnotationPropertyOf(valueSlim, subsetProperty),
            AnnotationAssertion(inSubset, uo1.getIRI(), unitGroupSlim.getIRI()),
            df.getOWLAnnotationAssertionAxiom(iao0000115, uo0.getIRI(),
                literal("A unit of measurement is a standardized quantity of a physical quality."),
                singleton(Annotation(hasDbXref, literal))),
            df.getOWLAnnotationAssertionAxiom(iao0000115, uo1.getIRI(),
                literal("A unit which is a standard measure of the distance between two points."),
                singleton(Annotation(hasDbXref, literal)))));
        assertEquals(expected, asUnorderedSet(ontology.axioms()));
    }
}
