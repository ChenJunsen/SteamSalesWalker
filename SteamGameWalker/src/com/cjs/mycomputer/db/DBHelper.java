package com.cjs.mycomputer.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.cjs.mycomputer.db.annotation.Column;
import com.cjs.mycomputer.db.annotation.Id;
import com.cjs.mycomputer.db.annotation.Table;
import com.cjs.mycomputer.db.exception.IllegalIdColumnAnnotationException;
import com.cjs.mycomputer.db.exception.NotAnnotateTableException;
import com.cjs.mycomputer.tools.Log;
import com.cjs.mycomputer.tools.StringUtil;
import com.cjs.mycomputer.tools.TimeKit;

public class DBHelper {
	
	public static final String[] FIELD_TYPES_1={
			"int",
			"java.lang.String",
			"boolean",
			"char",
			"float",
			"double",
			"long",
			"short",
			"byte"
			};
	
	public static final String[] FIELD_TYPES_2={
			"Integer",
			"java.lang.String",
			"java.lang.Boolean",
			"java.lang.Character",
			"java.lang.Float",
			"java.lang.Double",
			"java.lang.Long",
			"java.lang.Short",
			"java.lang.Byte"};
	
	static final String TAG="DBHelper";
	
	public static String generateCreateTableSql(Object o){
		String sql="";
		if(o==null){
			throw new IllegalArgumentException("采用generateCreateTableSql(Object o)方法创建Sql语句的对象不能为空");
		}else {
			sql=generateCreateTableSql(o.getClass());
		}
		return sql;
	}
	
	/**
	 * 创建一个以动态时间改变的表名
	 * @param clazz
	 * @return
	 */
	public static String generateCreateTableSqlBasedOnSystemTime(Class<? extends Object> clazz){
		String sql="";
		if(!clazz.isAnnotationPresent(Table.class)){
			throw new NotAnnotateTableException();
		}else{
			String tableName=clazz.getAnnotation(Table.class).tableName();//通过注解获取表的名字
			if(StringUtil.isEmptyStr(tableName)){
				tableName=StringUtil.CamelTo_(clazz.getSimpleName());//如果没有设置表名就反射类名得到表名
			}
			tableName+=TimeKit.getFormatTime(System.currentTimeMillis(), TimeKit.FORMAT_SIMPLE_TIME4);//给表名加上系统时间
			Field[] fields=clazz.getDeclaredFields();
			List<Field> effectFields=new ArrayList<Field>();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				field.setAccessible(true);
				if(field.isAnnotationPresent(Column.class) && field.isAnnotationPresent(Id.class)){
					throw new IllegalIdColumnAnnotationException();
				}else if(field.isAnnotationPresent(Column.class)){
					effectFields.add(field);
				}else if(field.isAnnotationPresent(Id.class)){
					effectFields.add(field);
				}
			}
			int fieldCount=effectFields.size();
			Log.d(TAG, "有效注解字段长度:"+fieldCount);
			if(fieldCount>0){
				StringBuffer sb=new StringBuffer();
				sb.append("CREATE TABLE IF NOT EXISTS `");
				sb.append(tableName);
				sb.append("` (");
				String[] id=null;
				for(int i=0;i<fieldCount;i++){
					Field field=effectFields.get(i);
					field.setAccessible(true);
					if(field.isAnnotationPresent(Column.class)){
						sb.append(parseColumn(clazz, field));
					}else if(field.isAnnotationPresent(Id.class)){
						id=parseId(clazz, field);
						sb.append(id[0]);
					}
					if(i!=fieldCount-1){
						sb.append(",");
					}
				}
				if(id!=null && id.length==2){
					sb.append(",");
					sb.append(" PRIMARY KEY (`");
					sb.append(id[1]);
					sb.append("`)");
				}
				sb.append(")");
				sql=sb.toString();
			}	
		}
		return sql;
	}
	
	public static String generateCreateTableSql(Class<? extends Object> clazz){
		String sql="";
		if(!clazz.isAnnotationPresent(Table.class)){
			throw new NotAnnotateTableException();
		}else{
			String tableName=clazz.getAnnotation(Table.class).tableName();//通过注解获取表的名字
			if(StringUtil.isEmptyStr(tableName)){
				tableName=StringUtil.CamelTo_(clazz.getSimpleName());//如果没有设置表名就反射类名得到表名
			}
			Field[] fields=clazz.getDeclaredFields();
			List<Field> effectFields=new ArrayList<Field>();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				field.setAccessible(true);
				if(field.isAnnotationPresent(Column.class) && field.isAnnotationPresent(Id.class)){
					throw new IllegalIdColumnAnnotationException();
				}else if(field.isAnnotationPresent(Column.class)){
					effectFields.add(field);
				}else if(field.isAnnotationPresent(Id.class)){
					effectFields.add(field);
				}
			}
			int fieldCount=effectFields.size();
			Log.d(TAG, "有效注解字段长度:"+fieldCount);
			if(fieldCount>0){
				StringBuffer sb=new StringBuffer();
				sb.append("CREATE TABLE IF NOT EXISTS `");
				sb.append(tableName);
				sb.append("` (");
				String[] id=null;
				for(int i=0;i<fieldCount;i++){
					Field field=effectFields.get(i);
					field.setAccessible(true);
					if(field.isAnnotationPresent(Column.class)){
						sb.append(parseColumn(clazz, field));
					}else if(field.isAnnotationPresent(Id.class)){
						id=parseId(clazz, field);
						sb.append(id[0]);
					}
					if(i!=fieldCount-1){
						sb.append(",");
					}
				}
				if(id!=null && id.length==2){
					sb.append(",");
					sb.append(" PRIMARY KEY (`");
					sb.append(id[1]);
					sb.append("`)");
				}
				sb.append(")");
				sql=sb.toString();
			}	
		}
		return sql;
	}
	
	/**
	 * 解析{@link Column}
	 * @param clazz
	 * @param field
	 * @return
	 */
	private static String parseColumn(Class<? extends Object> clazz,Field field){
		String fieldTypeName=field.getType().getName();
		String fieldName=field.getName();
		Log.d(TAG, clazz.getSimpleName()+"含有字段:"+fieldTypeName+"  "+fieldName);
		
		Column columun=field.getAnnotation(Column.class);
		String annoName=columun.nameInDB();
		int length=columun.length();
		int floatPoint=columun.floatPoint();
		boolean allowNull=columun.allowNull();
		String defaultValue=columun.defaultValue();
		String comment=columun.comment();
		
		StringBuffer sb=new StringBuffer();
		sb.append("`");
		if(StringUtil.isEmptyStr(annoName)){
			sb.append(StringUtil.CamelTo_(fieldName));
		}else{
			sb.append(annoName);
		}
		sb.append("`");
		sb.append(" ");
		
		if("int".equalsIgnoreCase(fieldTypeName) || "Integer".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=10;
				sb.append("int(");
			}else if(length>11){//11是MySQL的int的最大长度，超过这个长度后要用bigInt
				sb.append("bigint(");
			}else{
				sb.append("int(");
			}
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					int val = Integer.parseInt(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("java.lang.String".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=20;
			}
			sb.append("varchar(");
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			sb.append("DEFAULT ");
			if("".equals(defaultValue)){
				sb.append("''");
			}else{
				sb.append(defaultValue);
			}
			sb.append(" ");
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("char".equalsIgnoreCase(fieldTypeName) || "java.lang.Character".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=2;
			}
			sb.append("varchar(");
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			sb.append("DEFAULT ");
			sb.append(defaultValue);
			sb.append(" ");
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("float".equalsIgnoreCase(fieldTypeName) || "java.lang.Float".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=5;
			}
			sb.append("float(");
			sb.append(length);
			sb.append(",");
			sb.append(floatPoint);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					float val = Float.parseFloat(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("double".equalsIgnoreCase(fieldTypeName) || "java.lang.Double".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=5;
			}
			sb.append("double(");
			sb.append(length);
			sb.append(",");
			sb.append(floatPoint);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					double val = Double.parseDouble(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("long".equalsIgnoreCase(fieldTypeName) || "java.lang.Long".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=20;
			}
			sb.append("bigint(");
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					long val = Long.parseLong(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("short".equalsIgnoreCase(fieldTypeName) || "java.lang.Short".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=2;
			}
			sb.append("smallint(");
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					short val = Short.parseShort(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}else if("byte".equalsIgnoreCase(fieldTypeName) || "java.lang.Byte".equalsIgnoreCase(fieldTypeName)){
			if(Column.DEFAULT_VALUE_INT==length){
				length=1;
			}
			sb.append("tinyint(");
			sb.append(length);
			sb.append(") ");
			if(allowNull){
				sb.append("NULL ");
			}else{
				sb.append("NOT NULL ");
			}
			if(!StringUtil.isEmptyStr(defaultValue)){
				try {
					byte val = Byte.parseByte(defaultValue);
					sb.append("DEFAULT ");
					sb.append(val);
					sb.append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isEmptyStr(comment)){
				sb.append("COMMENT '");
				sb.append(comment);
				sb.append("'");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 解析{@link Id}
	 * @param clazz
	 * @param field
	 * @return
	 */
	private static String[] parseId(Class<? extends Object> clazz,Field field){
		String[] res=new String[2];
		String fieldTypeName=field.getType().getName();
		String fieldName=field.getName();
		Log.d(TAG, clazz.getSimpleName()+"含有字段:"+fieldTypeName+"  "+fieldName);
		
		Id id=field.getAnnotation(Id.class);
		String annoName=id.nameInDB();
		String comment=id.comment();
		int length=id.length();
		
		StringBuffer sb=new StringBuffer();
		sb.append("`");
		String idFieldName="";
		if(StringUtil.isEmptyStr(annoName)){
			idFieldName=StringUtil.CamelTo_(fieldName);
		}else{
			idFieldName=annoName;
		}
		sb.append(idFieldName);
		sb.append("`");
		sb.append(" ");
		if(Column.DEFAULT_VALUE_INT==length){
			length=10;
			sb.append("int(");
		}else if(length>11){//11是MySQL的int的最大长度，超过这个长度后要用bigInt
			sb.append("bigint(");
		}else{
			sb.append("int(");
		}
		sb.append(length);
		sb.append(") ");
		sb.append("NOT NULL AUTO_INCREMENT ");
		if(!StringUtil.isEmptyStr(comment)){
			sb.append("COMMENT '");
			sb.append(comment);
			sb.append("'");
		}
		res[0]=sb.toString();
		res[1]=idFieldName;
		return res;
	}

}
