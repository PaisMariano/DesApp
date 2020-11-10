package unq.edu.tpi.desapp.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {
    private JavaClasses importedClasses;

    @Before
    public void setUp(){
        importedClasses = new ClassFileImporter().importPackages("unq.edu.tpi.desapp");
    }

    //WEBSERVICE

    @Test
    public void shouldOnlyWebservicesAndItselfAccessPackageServices() {
        ArchRule myRule = classes()
                .that().resideInAPackage("..services..")
                .should().onlyBeAccessed().byAnyPackage("..webservices..", "..services..");

        myRule.check(importedClasses);
    }
    @Test
    public void shouldControllerClassesBeAnnotatedWithControllerOrRestControllerAnnotation() {
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith(RestController.class);

        myRule.check(importedClasses);
    }
    @Test
    public void shouldControllerClassesEndWithTheWordController() {
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("unq.edu.tpi.desapp.webservices");

        myRule.check(importedClasses);
    }
    @Test
    public void shouldLayersBeStructuredOneSideOnly() {
        ArchRule myRule = layeredArchitecture()
                .layer("Controller").definedBy("..webservices..")
                .layer("Service").definedBy("..services..")
                .layer("Persistence").definedBy("..repositories..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

        myRule.check(importedClasses);
    }

    //SERVICE

    @Test
    public void shouldServiceClassesBeAnnotatedWithServiceAndTransactionalAnnotation() {
        ArchRule myRule = classes()
                .that().areAnnotatedWith(Transactional.class)
                .should().resideInAPackage("..services..");
        myRule.check(importedClasses);
    }

    @Test
    public void shouldServiceClassesEndWithTheWordService() {
        ArchRule myRule = classes()
                .that().resideInAPackage("..services..")
                .should().haveSimpleNameEndingWith("Service");

        myRule.check(importedClasses);
    }

    //REPOSITORY

    @Test
    public void shouldRepositoryClassesEndWithTheWordRepository() {
        ArchRule myRule = classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository");

        myRule.check(importedClasses);
    }
    @Test
    public void shouldRepositoryClasesBeLocatedInTheCorrectPackage() {
        ArchRule myRule = classes()
                .that().areAnnotatedWith(Repository.class)
                .should().resideInAPackage("..repositories..");
        myRule.check(importedClasses);
    }

    //MODEL

    @Test
    public void shouldPackageModelBeIndependentOfOtherPackages() {
        ArchRule myRule = noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..repositories..", "..services..", "..aspects..","..webservices..");

        myRule.check(importedClasses);
    }

    //TESTS

    @Test
    public void shouldTestsOfModelOnlyBeInTheCorrectPackageAndEndWithTheWordTest() {
        JavaClasses importedClassesFromPath = new ClassFileImporter().importPath("./src/test/java/unq/edu/tpi/desapp");

        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Test")
                .should().resideInAPackage("unq.edu.tpi.desapp.model");

        myRule.check(importedClassesFromPath);
    }

}