package org.hisrc.jsonix.configuration;

import java.text.MessageFormat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.hisrc.jsonix.analysis.ModelInfoGraphAnalyzer;
import org.hisrc.jsonix.log.Log;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

@XmlRootElement(name = ElementInfoConfiguration.LOCAL_ELEMENT_NAME)
@XmlType(propOrder = {})
public class ElementInfoConfiguration {

	public static final String LOCAL_ELEMENT_NAME = "element";

	private QName name;

	@XmlAttribute
	public QName getName() {
		return name;
	}

	public void setName(QName name) {
		this.name = name;
	}

	public <T, C extends T> MElementInfo<T, C> findElementInfo(Log log,
			ModelInfoGraphAnalyzer<T, C> analyzer, MPackageInfo packageInfo) {
		Validate.notNull(log);
		Validate.notNull(analyzer);
		Validate.notNull(packageInfo);
		final QName name = getName();
		if (name == null) {
			log.warn(MessageFormat
					.format("The [{0}] element does not provide the required [name] attribute and will be ignored.",
							LOCAL_ELEMENT_NAME));
			return null;
		} else {
			final MElementInfo<T, C> typeInfo = analyzer
					.findElementInfoByQName(name);
			if (typeInfo == null) {
				log.warn(MessageFormat
						.format("Could not find the element [{0}] in the package [{1}], the declaring [{2}] element will be ignored.",
								name, packageInfo.getPackageName(),
								LOCAL_ELEMENT_NAME));
			}
			return typeInfo;
		}

	}
}