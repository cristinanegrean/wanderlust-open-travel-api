package cristina.tech.blog.travel.validation;

public enum PostalCodeFormat {
    AT("^[0-9]{4}$"),
    BE("^[0-9]{4}$"),
    CH("^[0-9]{4}$"),
    CZ("^[0-9]{3} ?[0-9]{2}$"),
    DE("^[0-9]{5}$"),
    DK("^[0-9]{4}$"),
    EE("^[0-9]{5}$"),
    ES("^ *((ES[- ]?)?[0-9]{5}|AD[0-9]{3}) *$"),
    IC("^ *(ES[- ]?)?(35|38)[0-9]{3} *$"),
    FI("^[0-9]{5}$"),
    FR("^ *((FR[- ]?)?[0-9]{5}|AD[0-9]{3}) *$"),
    GB("^[A-Z]{1,2}[0-9R][0-9A-Z]?( ?)[0-9][ABD-HJLNP-UW-Z]{2}$"),
    GR("^[0-9]{5}$"),
    HR("^[0-9]{5}$"),
    HU("^[0-9]{4}$"),
    IE("^.*$"),
    IT("^[0-9]{5}$"),
    LT("^[0-9]{5}$"),
    LU("^[0-9]{4}$"),
    LV("^[0-9]{4}$"),
    NL("^[0-9]{4} ?[A-Z]{2}$"),
    NO("^[0-9]{4}$"),
    PL("^[0-9]{2}-?[0-9]{3}$"),
    PT("^[0-9]{4}([- ][0-9]{3})?$"),
    RO("^[0-9]{6}$"),
    SE("^[0-9]{5}$"),
    SI("^[0-9]{4}$"),
    SK("^[0-9]{3} ?[0-9]{2}$"),
    TR("^[0-9]{5}$");

    private String format;

    PostalCodeFormat(String format) {
        this.format = format;
    }

    private boolean validate(String value) {
        return value.matches(format);
    }

    public static PostalCodeFormat safeValueOf(String country) {
        try {
            return PostalCodeFormat.valueOf(country);
        } catch (IllegalArgumentException e) {
            // this country is not in the list
            return null;
        }
    }

    public static boolean validate(String country, String postalCode) {
        PostalCodeFormat countryFormat = PostalCodeFormat.safeValueOf(country);
        return (countryFormat != null && postalCode != null && countryFormat.validate(postalCode.toUpperCase()));
    }
}
