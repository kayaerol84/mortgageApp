package com.kayaerol84.mortgageApp.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.kayaerol84.mortgageApp", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTests {

    private static final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.kayaerol84.mortgageApp");

    @ArchTest
    static final ArchRule api_should_only_be_accessed_by_themselves =
            classes().that().resideInAPackage("..com.kayaerol84.mortgageApp.api..")
                    .should().onlyBeAccessed().byAnyPackage("..com.kayaerol84.mortgageApp.api..")
                    .because("Api should only be accessed by other classes in the application layer");

    @ArchTest
    static final ArchRule services_should_only_be_accessed_by_api =
            classes().that().resideInAPackage("..com.kayaerol84.mortgageApp.service..")
                    .should().onlyBeAccessed().byAnyPackage("..com.kayaerol84.mortgageApp.api..","..com.kayaerol84.mortgageApp.service..")
                    .because("Services should only be accessed by api");

    @ArchTest
    static final ArchRule repositories_should_only_be_accessed_by_services =
            classes().that().resideInAPackage("..com.kayaerol84.mortgageApp.repository..")
                    .should().onlyBeAccessed().byAnyPackage("..com.kayaerol84.mortgageApp.service..","..com.kayaerol84.mortgageApp.repository..")
                    .because("Repositories should only be accessed by services");

    @ArchTest
    static final ArchRule model_classes_should_not_have_resource_or_service_suffix =
            classes().that().resideInAPackage("..com.kayaerol84.mortgageApp.model..")
                    .should().haveSimpleNameNotEndingWith("Resource")
                    .andShould().haveSimpleNameNotEndingWith("Service")
                    .because("Model classes should not be named like api or services");

    @ArchTest
    static final ArchRule no_field_injection =
            classes().should().notBeAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
                    .because("Use constructor injection to enforce immutability and make dependencies explicit");
}
