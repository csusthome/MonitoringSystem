package handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

	private Map<String, String> map = null;// 存储单个解析的完整对象
	private List<Map<String, String>> list = null;// 存储所有的解析对象
	private String currentTag = null;// 正在解析的元素的标签
	private String currentValue = null;// 解析当前元素的值
	private String nodeName = null;// 解析当前的节点名称

	public XMLHandler(String nodeName) {
		// TODO Auto-generated constructor stub
		this.nodeName = nodeName;
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		// 当读到第一个开始标签的时候，会触发这个方法
		list = new ArrayList<Map<String, String>>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// 当遇到文档的开头的时候，调用这个方法
		if (qName.equals(nodeName)) {
			map = new HashMap<String, String>();
		}
		if (attributes != null && map != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				map.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		currentTag = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		// 这个方法是用来处理xml文件所读取到的内容
		if (currentTag != null && map != null) {
			currentValue = new String(ch, start, length);
			if (currentValue != null && !currentValue.trim().equals("")
					&& !currentValue.trim().equals("\n")) {
				map.put(currentTag, currentValue);
			}
		}
		currentTag = null;// 把当前的节点的对应的值和标签设置为空
		currentValue = null;

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals(nodeName)) {
			list.add(map);
			map = null;
		}
		super.endElement(uri, localName, qName);
	}
}
