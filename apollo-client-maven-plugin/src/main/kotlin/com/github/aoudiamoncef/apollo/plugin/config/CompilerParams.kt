package com.github.aoudiamoncef.apollo.plugin.config

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.compiler.*
import java.io.File

/**
 * CompilerParams contains all the parameters needed to invoke the apollo compiler.
 *
 * The setters are present for backward compatibility with kotlin build scripts and will go away
 * in a future release.
 */
class CompilerParams {

    /**
     * Whether to generate Java or Kotlin models
     *
     * Default value: false
     */
    internal val generateKotlinModels: Boolean = false

    /**
     * Warn if using a deprecated field
     *
     * Default value: true
     */
    internal val warnOnDeprecatedUsages: Boolean = true

    /**
     * Fail the build if there are warnings. This is not named `allWarningAsErrors` to avoid nameclashes with the Kotlin options
     *
     * Default value: false
     */
    internal val failOnWarnings: Boolean = false

    /**
     * For custom scalar types like Date, map from the GraphQL type to the java/kotlin type.
     *
     * Default value: the empty map
     */
    var scalarsMapping: Map<String, ScalarMapping> = emptyMap()

    /**
     * By default, Apollo uses `Sha256` hashing algorithm to generate an ID for the query.
     * To provide a custom ID generation logic, pass an `instance` that implements the [OperationIdGenerator]. How the ID is generated is
     * indifferent to the compiler. It can be an hashing algorithm or generated by a backend.
     * Default value: [OperationIdGenerator.Sha256]
     */
    internal val operationIdGeneratorClass: String = ""

    /**
     * When true, the generated classes names will end with 'Query' or 'Mutation'.
     * If you write `query droid { ... }`, the generated class will be named 'DroidQuery'.
     *
     * Default value: true
     */
    internal val useSemanticNaming: Boolean = true

    /**
     * The package name of the models is computed from their folder hierarchy like for java sources.
     *
     * If you want, you can prepend a custom package name here to namespace your models.
     *
     * Default value: the empty string
     */
    internal var schemaPackageName: String = ""

    /**
     * Whether to generate Kotlin models with `internal` visibility modifier.
     *
     * Default value: false
     */
    internal val generateAsInternal: Boolean = false

    /**
     * A list of [Regex] patterns for GraphQL enums that should be generated as Kotlin sealed classes instead of the default Kotlin enums.
     *
     * Use this if you want your client to have access to the rawValue of the enum. This can be useful if new GraphQL enums are added but
     * the client was compiled against an older schema that doesn't have knowledge of the new enums.
     *
     * Default: emptyList()
     */
    internal val sealedClassesForEnumsMatching: List<String> = emptyList()

    /**
     * The format in which the operation manifest will be generated.
     *
     * Acceptable values:
     * - `none`: No manifest will be generated
     * - `operationOutput`: 'operationOutput' manifest format
     * - `persistedQueryManifest`: Apollo's persistent query manifest format
     *
     * Default value: "none"
     */
    internal val operationManifestFormat: String = MANIFEST_NONE

    /**
     * Whether or not to generate Apollo metadata. Apollo metadata is used for multi-module support. Set this to true if you want other
     * modules to be able to re-use fragments and types from this module.
     *
     * This is currently experimental and this API might change in the future.
     *
     * Default value: false
     */
    internal val generateApolloMetadata: Boolean = false

    /**
     * A list of [Regex] patterns for input/scalar/enum types that should be generated whether or not they are used by queries/fragments
     * in this module. When using multiple modules, Apollo Android will generate all the types by default in the root module
     * because the root module doesn't know what types are going to be used by dependent modules. This can be prohibitive in terms
     * of compilation speed for large projects. If that's the case, opt-in the types that are used by multiple dependent modules here.
     * You don't need to add types that are used by a single dependent module.
     *
     * This is currently experimental and this API might change in the future.
     *
     * Default value: if (generateApolloMetadata) listOf(".*") else listOf()
     */
    @ApolloExperimental
    internal var alwaysGenerateTypesMatching: Set<String> = setOf()

    /**
     * The package name of the models. The compiler will generate classes in
     *
     * - $packageName/SomeQuery.kt
     * - $packageName/fragment/SomeFragment.kt
     * - $packageName/type/CustomScalar.kt
     * - $packageName/type/SomeInputObject.kt
     * - $packageName/type/SomeEnum.kt
     *
     * Default value: ""
     */
    internal var packageName: String? = ""

    /**
     * The rootFolders where the graphqlFiles are located. The package name of each individual graphql query
     * will be the relative path to the root folders
     */
    internal var rootFolders: List<File> = emptyList()

    /**
     * Whether to generate default implementation classes for GraphQL fragments.
     * Default value is `false`, means only interfaces are been generated.
     *
     * Most of the time, fragment implementations are not needed because you can easily access fragments interfaces and read all
     * data from your queries. They are needed if you want to be able to build fragments outside an operation. For an exemple
     * to programmatically build a fragment that is reused in another part of your code or to read and write fragments to the cache.
     */
    internal val generateFragmentImplementations: Boolean = false

    /**
     * Whether to embed the query document in the [com.apollographql.apollo3.api.Operation]s. By default this is true as it is needed
     * to send the operations to the server.
     * If performance is critical and you have a way to whitelist/read the document from another place, disable this.
     */
    internal val generateQueryDocument: Boolean = true

    /**
     * Whether to generate the __Schema class. The __Schema class lists all composite
     * types in order to access __typename and/or possibleTypes
     */
    internal val generateSchema: Boolean = false

    /**
     * Whether to generate operation variables as [com.apollographql.apollo3.api.Optional]
     *
     * Using [com.apollographql.apollo3.api.Optional] allows to omit the variables if needed but makes the
     * callsite more verbose in most cases.
     *
     * Default: true
     */
    internal val generateOptionalOperationVariables: Boolean = true

    /**
     * Kotlin native will generate [Any?] for optional types
     * Setting generateFilterNotNull will generate extra `filterNotNull` functions that will help keep the type information
     */
    internal val generateFilterNotNull: Boolean = false

    /**
     * Whether to generate the compiled selections used to read/write from the normalized cache.
     * Disable this option if you don't use the normalized cache to save some bytecode
     */
    internal val generateResponseFields: Boolean = false

    /**
     * Target language version for the generated code.
     *
     * Only valid when [generateKotlinModels] is `true`
     * Must be either "KOTLIN_1_4" or "KOTLIN_1_5"
     *
     * Using an higher languageVersion allows generated code to use more language features like
     * sealed interfaces in Kotlin 1.5 for an example.
     *
     * See also https://kotlinlang.org/docs/gradle.html#attributes-common-to-jvm-and-js
     *
     * Default: TargetLanguage.JAVA.
     */
    internal val targetLanguage: TargetLanguage = TargetLanguage.JAVA

    /**
     * A list of files containing metadata from previous compilations
     */
    @ApolloExperimental
    internal val metadataFiles: List<File> = emptyList()

    /**
     * Whether to generate the type safe Data builders. These are mainly used for tests but can also be used for other use
     * cases too.
     *
     * Only valid when [generateKotlinModels] is true
     */
    internal val generateTestBuilders: Boolean = false

    /**
     * Whether to generate the type safe Data builders. These are mainly used for tests but can also be used for other use
     * cases too.
     *
     * Only valid when [generateKotlinModels] is true
     */
    internal val generateDataBuilders: Boolean = false

    /**
     * Whether to generate builders for java models
     *
     * Default value: false
     * Only valid when [generateKotlinModels] is false
     */
    internal val generateModelBuilders: Boolean = false

    /**
     * The style to use for fields that are nullable in the Java generated code.
     *
     * Only valid when [targetLanguage] is [TargetLanguage.JAVA]
     *
     * Acceptable values:
     * - `none`: Fields will be generated with the same type whether they are nullable or not
     * - `apolloOptional`: Fields will be generated as Apollo's `com.apollographql.apollo3.api.Optional<Type>` if nullable, or `Type` if not.
     * - `javaOptional`: Fields will be generated as Java's `java.util.Optional<Type>` if nullable, or `Type` if not.
     * - `guavaOptional`: Fields will be generated as Guava's `com.google.common.base.Optional<Type>` if nullable, or `Type` if not.
     * - `jetbrainsAnnotations`: Fields will be generated with Jetbrain's `org.jetbrains.annotations.Nullable` annotation if nullable, or
     * `org.jetbrains.annotations.NotNull` if not.
     * - `androidAnnotations`: Fields will be generated with Android's `androidx.annotation.Nullable` annotation if nullable, or
     * `androidx.annotation.NonNull` if not.
     * - `jsr305Annotations`: Fields will be generated with JSR 305's `javax.annotation.Nullable` annotation if nullable, or
     * `javax.annotation.Nonnull` if not.
     *
     * Default: `none`
     */
    internal val nullableFieldStyle: JavaNullable = JavaNullable.NONE

    // TODO to be handled
    /**
     * What codegen to use. One of "OPERATION", "RESPONSE" or "COMPATIBILITY"
     *
     * Default value: "OPERATION"
     */
    internal val codegenModels: Codegen = Codegen.OPERATION

    /**
     * Whether to flatten the models. File paths are limited on MacOSX to 256 chars and flattening can help keeping the path length manageable
     * The drawback is that some classes may nameclash in which case they will be suffixed with a number
     *
     * Default value: true for "operationBased" and "responseBased", false else
     */
    internal val flattenModels: Boolean = true

    internal val logger: ApolloCompiler.Logger = ApolloCompiler.NoOpLogger

    /**
     * The file where to write the metadata
     */
    internal var metadataOutputFile: File? = null
}
