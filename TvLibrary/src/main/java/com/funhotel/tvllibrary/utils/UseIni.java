package com.funhotel.tvllibrary.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 河北联通烽火通信获取认证登录成功后的数据接口
 */
public final class UseIni {
	public static final String NETWORKTYPE_PPPOE = "1";
	public static final String NETWORKTYPE_DHCP = "2";
	public static final String NETWORKTYPE_LAN = "3";
	public static final String NETWORKTYPE_DHCPPLUS = "4";

	public static String getProfileString(String file, String section,
			String variable, String defaultValue) throws IOException {
		String strLine, value = "";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		boolean isInSection = false;
		String variablec;
		if (null == defaultValue)
			defaultValue = "";
		try {
			while ((strLine = bufferedReader.readLine()) != null) {
				strLine = strLine.trim();
				strLine = strLine.split("[;]")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
					m = p.matcher(strLine);
					if (m.matches()) {
						isInSection = true;
					} else {
						isInSection = false;
					}
				}

				if (isInSection == true) {
					variablec = section + "." + variable;
					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					if (strArray.length == 1) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variablec)) {
							value = "";
							return value;
						}
					} else if (strArray.length == 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variablec)) {
							value = strArray[1].trim();
							return value;
						}
					} else if (strArray.length > 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variablec)) {
							value = strLine.substring(strLine.indexOf("=") + 1)
									.trim();
							return value;
						}
					}
				}
			}
		} finally {
			bufferedReader.close();
		}
		//bufferedReader.close();
		return defaultValue;
	}

	public static boolean setProfileString(String file, String section,
			String variable, String value) throws IOException {
		String fileContent, allLine, strLine, newLine, remarkStr;
		String getValue;
		String variablec;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		boolean isInSection = false;
		fileContent = "";
		try {
			while ((allLine = bufferedReader.readLine()) != null) {
				allLine = allLine.trim();
				if (allLine.split("[;]").length > 1) {
					remarkStr = ";" + allLine.split(";")[1];
				} else {
					remarkStr = "";
				}
				strLine = allLine.split(";")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
					m = p.matcher(strLine);
					if (m.matches()) {
						isInSection = true;

					} else {
						isInSection = false;

					}
				}
				if (isInSection == true) {
					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					getValue = strArray[0].trim();
					variablec = section + "." + variable;

					if (getValue.equalsIgnoreCase(variablec)) {

						newLine = getValue + "=" + value + remarkStr;
						fileContent += newLine + "\n";
						while ((allLine = bufferedReader.readLine()) != null) {
							fileContent += allLine + "\n";
						}
						//bufferedReader.close();

						File entryFile = new File(file);
						FileOutputStream fos = new FileOutputStream(entryFile);
						BufferedOutputStream dest = new BufferedOutputStream(
								fos, fileContent.length());
						byte[] byteContent = fileContent.getBytes();
						dest.write(byteContent, 0, fileContent.length());
						dest.flush();
						FileDescriptor fd = fos.getFD();
						fd.sync();

/*						BufferedWriter bufferedWriter = new BufferedWriter(
								new FileWriter(file, false));
						bufferedWriter.write(fileContent);
						bufferedWriter.flush();
						bufferedWriter.close();*/
						return true;
					}
				}
				fileContent += allLine + "\n";
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			bufferedReader.close();
		}
		//bufferedReader.close();
		return false;
	}

	public static boolean addProfileString(String file, String section,
			String variable, String value) throws IOException {
		String fileContent, allLine, strLine, newLine, remarkStr;
		String getValue;
		String variablec;
		variablec = section + "." + variable;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		boolean isInSection = false;
		boolean isSecExist = false;
		fileContent = "";
		try {
			while ((allLine = bufferedReader.readLine()) != null) {
				allLine = allLine.trim();
				if (allLine.split("[;]").length > 1) {
					remarkStr = ";" + allLine.split(";")[1];
				} else {
					remarkStr = "";
				}
				strLine = allLine.split(";")[0];

				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
					m = p.matcher(strLine);
					if (m.matches()) {
						isInSection = true;
						// Log.w("Maaaaaaaaaaaaaaaaaaad cow : ",
						// "is in Section : " + strLine);
					} else {
						isInSection = false;

					}
				}
				if (isInSection == true) {

					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					getValue = strArray[0].trim();
					// Log.w("Maaaaaaaaaaaaaaaaaaad chicken : ",
					// "is in Section : " + getValue);
					isSecExist = true;
					if (getValue.equalsIgnoreCase(variablec))
						return false; // already exist

				}
				fileContent += allLine + "\n";
			}

			if (isSecExist == true) {

				newLine = variablec + "=" + value;
				// fileContent += allLine + "\n";
				fileContent += newLine + "\n";

				while ((allLine = bufferedReader.readLine()) != null) {
					fileContent += allLine + "\n";
				}
				//bufferedReader.close();
				BufferedWriter bufferedWriter = new BufferedWriter(
						new FileWriter(file, false));
				bufferedWriter.write(fileContent);
				bufferedWriter.flush();
				bufferedWriter.close();
				return true;
			} else {
				// no such section add it to end
				fileContent += "[" + section + "]" + "\n";
				newLine = variablec + "=" + value;
				fileContent += newLine + "\n";

				BufferedWriter bufferedWriter = new BufferedWriter(
						new FileWriter(file, false));
				bufferedWriter.write(fileContent);
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			bufferedReader.close();
		}
		//bufferedReader.close();
		return false;
	}

	public static boolean delProfileString(String file, String section,
			String variable) throws IOException {
		String fileContent, allLine, strLine, remarkStr;
		String getValue;
		String variablec;
		int variableCount = 0;
		int totalCount = 0;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		boolean isInSection = false;
		boolean isVarFound = false;
		fileContent = "";
		try {

			while ((allLine = bufferedReader.readLine()) != null) {
				// Log.w("Maaaaaaaaaaaaaaaaaaad chicken : ", "asdfasdfa : "
				// + isVarFound);
				if (variableCount == 0)
					totalCount++;
				allLine = allLine.trim();
				if (allLine.split("[;]").length > 1) {
					remarkStr = ";" + allLine.split(";")[1];
				} else {
					remarkStr = "";
				}
				strLine = allLine.split(";")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
					m = p.matcher(strLine);
					if (m.matches()) {
						isInSection = true;

					} else {
						isInSection = false;

					}
				}
				if (isInSection == true) {
					variableCount++;
					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					getValue = strArray[0].trim();
					variablec = section + "." + variable;

					if (getValue.equalsIgnoreCase(variablec)) {

						while ((allLine = bufferedReader.readLine()) != null) {
							fileContent += allLine + "\n";
						}
						//bufferedReader.close();
						BufferedWriter bufferedWriter = new BufferedWriter(
								new FileWriter(file, false));
						bufferedWriter.write(fileContent);
						bufferedWriter.flush();
						bufferedWriter.close();
						isVarFound = true;
						// Log.w("Maaaaaaaaaaaaaaaaaaad chicken : ",
						// "isVarFound : " + isVarFound);
						return true;
					}
				}
				fileContent += allLine + "\n";
			}

			/*
			 * if (isVarFound == true) {
			 * 
			 * if (variableCount <= 2) {
			 * 
			 * BufferedReader bufReader = new BufferedReader( new
			 * FileReader(file)); String wholeFile = ""; for (int i = 0; i <
			 * totalCount - 1; i++) { allLine = bufReader.readLine(); wholeFile
			 * += allLine + "\n"; } bufReader.readLine(); while ((allLine =
			 * bufReader.readLine()) != null) { wholeFile += allLine + "\n"; }
			 * bufReader.close(); BufferedWriter bufferedWriter = new
			 * BufferedWriter( new FileWriter(file, false));
			 * bufferedWriter.write(wholeFile); bufferedWriter.flush();
			 * bufferedWriter.close(); return true; }
			 * 
			 * return true; }
			 */

		} catch (IOException ex) {
			throw ex;
		} finally {
			bufferedReader.close();
		}
		//bufferedReader.close();
		return false;
	}

}
