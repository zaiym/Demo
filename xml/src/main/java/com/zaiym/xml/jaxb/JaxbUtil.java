package com.zaiym.xml.jaxb;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Field;

/**
 * JAXB方式解析XML
 */
public class JaxbUtil {

	/**
	 * 默认编码UTF-8
	 */
	private static String ENCODING = "utf-8";

	/**
	 * 对象转XML（默认编码UTF-8，格式化输出）
	 * @param o 实体
	 * @return
	 */
	public static String toXml(Object o){
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			toXml(o, writer, ENCODING, true, false);
			return writer.toString();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 对象转XML并保存到文件（默认编码UTF-8，格式化输出）
	 * @param o    实体
	 * @param file 目标文件
	 * @return
	 */
	public static boolean toXml(Object o, File file){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),ENCODING));
			toXml(o, writer, ENCODING , true, false);
			return true;
		} catch (IOException e) {
			return false;
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 对象转XML并保存到文件（默认编码UTF-8）
	 * @param o    实体
	 * @param file 目标文件
	 * @param isFormate 是否格式化
	 * @return
	 */
	public static boolean toXml(Object o, File file, boolean isFormate){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),ENCODING));
			toXml(o, writer, ENCODING , isFormate, false);
			return true;
		} catch (IOException e) {
			return false;
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 对象转XML
	 * @param o        对象
	 * @param writer   写入字符流
	 * @param encoding 编码
	 * @param formate  是否格式化
	 * @param ignoreNullField 是否忽略null值字段
	 * @return
	 */
	public static void toXml(Object o, Writer writer, String encoding, boolean formate, boolean ignoreNullField) {
		try {
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller marshaller = context.createMarshaller();
			if (!ignoreNullField) {
				marshaller.setListener(new NullFieldListener());
			}
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formate);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
			marshaller.marshal(o, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 对象转XML
     * @param o        对象
     * @param writer   写入字符流
     * @param encoding 编码
     * @param formate  是否格式化
     * @param ignoreNullField 是否忽略null值字段
     * @return
     */
    @Deprecated
	public static String toXmlWithNameSpace(Object o, Writer writer, String encoding, boolean formate, boolean ignoreNullField){
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            if (!ignoreNullField) {
                marshaller.setListener(new NullFieldListener());
            }
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formate);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

            XMLFilterImpl nsfFilter = new PDEXMLFilterImpl();
            OutputFormat format = new OutputFormat();
            format.setIndent(true);
            format.setNewlines(true);
            format.setNewLineAfterDeclaration(false);
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            nsfFilter.setContentHandler(xmlWriter);
            marshaller.marshal(o, nsfFilter);
            //marshaller.marshal(o, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * XML转bean
	 * @param xml XML字符串
	 * @param c   转换目标类型
	 * @return
	 */
	public static <T> T toBean(String xml, Class<T> c){
		return toBean(new StringReader(xml), c);
	}

	/**
	 * XML转bean（默认UTF-8）
	 * @param xml XML文件
	 * @param c   转换目标类型
	 * @return
	 */
	public static <T> T toBean(File xml, Class<T> c){
		return toBean(xml, c, ENCODING);
	}

	/**
	 * XML转bean
	 * @param xml XML文件
	 * @param c   转换目标类型
	 * @param encode 以何种编码读取文件（默认UTF-8）
	 * @return
	 */
	public static <T> T toBean(File xml, Class<T> c, String encode){
		try {
			encode = (encode == null || encode.trim().equals("")) ? ENCODING : encode;
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(xml), encode));
			return toBean(reader, c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * XML转bean
	 * @param reader 读取字符流
	 * @param c      转换目标类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(Reader reader, Class<T> c){
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Null值的属性显示空节点
	 */
	static class NullFieldListener extends Marshaller.Listener {

		static final String BLANK_CHAR = "";

		@Override
		public void beforeMarshal(Object source) {
			super.beforeMarshal(source);
			Field[] fields = source.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					if (f.getType() == String.class && f.get(source) == null) {
						f.set(source, BLANK_CHAR);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

    /**
     * JAXB命名空间处理
     */
	static class PDEXMLFilterImpl extends XMLFilterImpl {

        private String rootNamespace = null;

        private boolean isRootElement = true;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (this.isRootElement){
                this.isRootElement = false;
                qName = "文件说明";
                localName += " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"" + uri + "\"";
                System.out.println(localName);
            }
            super.startElement(uri, localName, qName, atts);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
            if (this.rootNamespace != null)
                uri = this.rootNamespace;
            super.startPrefixMapping(prefix, uri);
        }
    }

}
