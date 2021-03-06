package org.hisrc.jsonix.configuration.exception;

import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.commons.lang3.Validate;

public class AmbiguousPackageMappingNameException extends ConfigurationException {

	private static final long serialVersionUID = 277619834810758946L;
	private final String packageName;
	private final String[] mappingNames;

	public AmbiguousPackageMappingNameException(String packageName,
			String... mappingNames) {
		super(
				MessageFormat
						.format("Package [{0}] is mapped using different mapping names [{1}].",
								Validate.notNull(packageName),
								Arrays.asList(
										Validate.noNullElements(mappingNames))
										.toString()));
		this.packageName = packageName;
		this.mappingNames = mappingNames;
	}

	public String getPackageName() {
		return packageName;
	}

	public String[] getMappingNames() {
		return mappingNames;
	}

}
