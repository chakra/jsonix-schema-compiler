package org.hisrc.jsonix.compilation.mapping.typeinfo.builtin;

import java.util.Iterator;

import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.expression.JSAssignmentExpression;
import org.hisrc.jscm.codemodel.expression.JSObjectLiteral;
import org.hisrc.jsonix.compilation.mapping.MappingCompiler;
import org.hisrc.jsonix.compilation.mapping.typeinfo.BuiltinLeafInfoCompiler;
import org.jvnet.jaxb2_commons.xmlschema.XmlSchemaConstants;
import org.relaxng.datatype.ValidationContext;

import com.sun.xml.xsom.XmlString;

public class QNameTypeInfoCompiler<T, C extends T, O> extends BuiltinLeafInfoCompiler<T, C, O> {

	public QNameTypeInfoCompiler() {
		super("QName", XmlSchemaConstants.QNAME);
	}

	@Override
	public JSAssignmentExpression createValue(MappingCompiler<T, C> mappingCompiler, XmlString item) {
		final JSCodeModel codeModel = mappingCompiler.getCodeModel();
		final ValidationContext context = item.context;
		final QName value = DatatypeConverter.parseQName(item.value, new NamespaceContext() {

			@SuppressWarnings("rawtypes")
			@Override
			public Iterator getPrefixes(String namespaceURI) {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getPrefix(String namespaceURI) {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getNamespaceURI(String prefix) {
				return context.resolveNamespacePrefix(prefix);
			}
		});
		final JSObjectLiteral result = codeModel.object();
		if (!StringUtils.isEmpty(value.getPrefix())) {
			result.append("prefix", codeModel.string(value.getPrefix()));
		}
		if (!StringUtils.isEmpty(value.getNamespaceURI())) {
			result.append("namespaceURI", codeModel.string(value.getNamespaceURI()));
		}
		result.append("localPart", codeModel.string(value.getLocalPart()));
		return result;
	}
}
