package org.kokakiwi.ssell.fortressassault.entities;

import java.util.HashMap;
import java.util.Map;

public enum FAClass {
	NONE(),
	SCOUT(),
	DEMOMAN(),
	ENGINEER();
	
	private static final Map<String, FAClass> lookupName = new HashMap<String, FAClass>();
	
	private FAClass()
	{
		
	}
	
	public static FAClass getClass(String className)
	{
		String classId = className.toUpperCase();
		
		return lookupName.get(classId);
	}
	
	static
	{
		for(FAClass faClass : values())
		{
			lookupName.put(faClass.name(), faClass);
		}
	}
}
