package com.training.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(description = "Sunny Cloud Configuration Description", name = "Sunny Cloud Config")
public @interface SampleOsgiConfigurations {
    @AttributeDefinition(name = "Payment Gateway URL", description = "Payment Config url", type = AttributeType.STRING)
    String paymentUrl() default "https://www.payment.gateway.com";

    @AttributeDefinition(name = "JSOn  URL", description = "JSON Config url", type = AttributeType.STRING)
    String jsonUrl() default "https://dummyjson.com/todos/10";
    @AttributeDefinition(
            name = "String Label",
            description = "String Config Example Description",
            type = AttributeType.STRING)
    String config_string_example() default "Default String";

    @AttributeDefinition(
            name = "String[] Label",
            description = "String[] Config Example Description",
            type = AttributeType.STRING)
    String[] config_string_array_example() default {"item1", "item2"};

    @AttributeDefinition(
            name = "Long Label",
            description = "Long Config Example Description",
            type = AttributeType.LONG)
    long config_long_example() default 0L;

    @AttributeDefinition(
            name = "int Label",
            description = "innt Config Example Description",
            type = AttributeType.INTEGER)
    int config_number_example() default 0;

    @AttributeDefinition(
            name = "Short Label",
            description = "Short Config Example Description",
            type = AttributeType.SHORT)
    short config_short_example() default 0;


    @AttributeDefinition(
            name = "Byte Label",
            description = "Byte Config Example Description",
            type = AttributeType.BYTE)
    byte config_byte_example() default 0;

    @AttributeDefinition(
            name = "Double Label",
            description = "Double Config Example Description",
            type = AttributeType.DOUBLE)
    double config_double_example() default 0;

    @AttributeDefinition(
            name = "Float Label",
            description = "Float Config Example Description",
            type = AttributeType.FLOAT)
    float config_float_example() default 0;

    @AttributeDefinition(
            name = "Boolean Label",
            description = "Boolean Config Example Description",
            type = AttributeType.BOOLEAN)
    boolean config_boolean_example() default true;

    @AttributeDefinition(
            name = "Password Label",
            description = "Password Config Example Description",
            type = AttributeType.PASSWORD)
    String config_password_config_example() default "";

    /*
     * @AttributeDefinition(name = "Dropdown Label", description =
     * "Dropdown Config Example Description", options = {
     *
     * @Option(label = "PRODUCTION", value = "PRODUCTION"),
     *
     * @AttributeDefinition.Option(label = "STAGING", value = "STAGING"),
     *
     * @AttributeDefinition.Option(label = "UAT", value = "UAT"),
     *
     * @AttributeDefinition.Option(label = "QA", value = "QA"),
     *
     * @AttributeDefinition.Option(label = "DEVELOP", value = "DEVELOP") }) String
     * config_dropdown_example() default "DEVELOP";
     *
     * @AttributeDefinition(name = "Blog is hosted at?", options = { @Option(label =
     * "WordPress", value = "wordpress"),
     *
     * @Option(label = "Blogspot", value = "blogspot") }) String hostedAt() default
     * "";
     */

}
