/*----------------------------------------------------------------------
	FILE        : NumberUtil.java
	AUTHOR      : JavaApp1-Jun-2022 Group
	LAST UPDATE : 23.10.2022

	Utility class for numeric operations

	Copyleft (c) 1993 by C and System Programmers Association (CSD)
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.util.numeric;

import java.math.BigInteger;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.Math.*;

public final class NumberUtil {
	private static final String [] ONES;
	private static final String [] TENS;

	private static final BigInteger THREE = BigInteger.valueOf(3);
	private static final BigInteger FIVE = BigInteger.valueOf(5);
	private static final BigInteger SEVEN = BigInteger.valueOf(7);
	private static final BigInteger ELEVEN = BigInteger.valueOf(11);

	static {
		ONES = new String[]{"", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz"};
		TENS = new String[]{"", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan"};
	}

	private static int [] getDigits(long val, int n)
	{
		val = Math.abs(val);
		var result = new int[val == 0 ? 1 : (int)(Math.log10(val) / n) + 1];

		for (int i = result.length - 1; val != 0; result[i--] = (int)(val % (long)Math.pow(10, n)), val /= Math.pow(10, n))
			;

		return result;
	}

	private static String numberToText3DigitsTR(int val)
	{
		if (val == 0)
			return "sıfır";

		var text = val < 0 ? "eksi" : "";

		val = Math.abs(val);

		var a = val / 100;
		var b = val % 100 / 10;
		var c = val % 10;

		if (a != 0) {
			if (a != 1)
				text += ONES[a];
			text += "yüz";
		}

		if (b != 0)
			text += TENS[b];

		if (c != 0)
			text += ONES[c];

		return text;
	}

	private NumberUtil()
	{
	}

	public static int calculateDigitalRoot(int val)
	{
		var root = abs(val);
		
		while ((root = digitsSum(root)) > 9)
			;
		
		return root;
	}
	
	public static int countDigits(long val)
	{
		return val == 0 ? 1 : (int)log10(abs(val)) +  1;
	}
	
	public static int digitsSum(long val)
	{
		var sum = 0;
		
		while (val != 0) {
			sum += val % 10;
			val /= 10;
		}
		
		return Math.abs(sum);
	}

	public static BigInteger digitsSum(BigInteger val)
	{
		var sum = BigInteger.ZERO;

		while (!val.equals(BigInteger.ZERO)) {
			sum = sum.add(val.remainder(BigInteger.TEN));
			val = val.divide(BigInteger.TEN);
		}

		return sum.abs();
	}

	public static long factorial(int n)
	{
		return n > 1 ? LongStream.rangeClosed(2, n).reduce(1, (r, a) -> r * a) : 1;
	}

	public static BigInteger factorialBig(int n)
	{
		return n > 1 ? IntStream.rangeClosed(2, n)
				.mapToObj(BigInteger::valueOf)
				.reduce(BigInteger.ONE, BigInteger::multiply) : BigInteger.ONE;
	}
	
	public static int gcd(int a, int b)
	{
		var min = min(abs(a), abs(b));
		
		for (var i = min; i >= 2; --i)
			if (a % i == 0 && b % i == 0)
				return i;
		
		return 1;
	}

	public static int [] getDigits(long val)
	{
		return getDigits(val, 1);
	}

	public static int [] getDigitsInTwos(long val)
	{
		return getDigits(val, 2);
	}

	public static int [] getDigitsInThrees(long val)
	{
		return getDigits(val, 3);
	}
	
	public static int getDigitsPowSum(int val)
	{
		var n = countDigits(val);
		var sum = 0;
		
		while (val != 0) {
			sum += pow(val % 10, n);
			val /= 10;
		}		
		
		return sum;
	}
	
	public static int getDigitsFactorialSum(int n)
	{
		var sum = 0;
		
		while (n != 0) {
			sum += factorial(n % 10);
			n /= 10;
		}
		
		return sum;
	}
	
	public static int getIndexOfPrime(int n)
	{
		var i = 1;
		var val = 2;
		
		for (;;) {
			if (val == n)
				return i;
			
			if (isPrime(val))
				++i;
			
			++val;
		}
	}

	public static int getPrime(int n)
	{
		var count = 0;
		var val = 2;
		
		for (;;) {
			if (isPrime(val))
				++count;
			
			if (count == n)
				return val;
			
			++val;
		}		
	}
	
	public static int mid(int a, int b, int c)
	{
		if (a <= b && b <= c || c <= b && b <= a)
			return b;
		
		if (b <= a && a <= c || c <= a && a <= b)
			return a;
		
		return c;					
	}

	public static String numberToText(int val)
	{
		//TODO:
		return numberToText3DigitsTR(val);
	}

	public static int reverse(int val)
	{
		var result = 0;
		
		while (val != 0) {
			result = result * 10 + val % 10;
			val /= 10;
		}
		
		return result;
	}
	
	public static int sumFactors(int val)
	{
		if (val == 1)
			return 1;

		var result = 0;
		var sqrtVal = (int)sqrt(val);
		
		for (int i = 2; i <= sqrtVal; ++i)
			if (val % i == 0)
				result += (i == val / i) ? i : (i + val / i);
		
		return result + 1;
	}

	public static boolean isPrime(BigInteger val)
	{
		if (val.compareTo(BigInteger.ONE) <= 0)
			return false;

		if (val.remainder(BigInteger.TWO).equals(BigInteger.ZERO))
			return val.equals(BigInteger.TWO);

		if (val.remainder(THREE).equals(BigInteger.ZERO))
			return val.equals(THREE);

		if (val.remainder(FIVE).equals(BigInteger.ZERO))
			return val.equals(FIVE);

		if (val.remainder(SEVEN).equals(BigInteger.ZERO))
			return val.equals(SEVEN);

		var sqrtVal = val.sqrt();

		for (var i = ELEVEN; i.compareTo(sqrtVal) <= 0; i = i.add(BigInteger.TWO))
			if (val.remainder(i).equals(BigInteger.ZERO))
				return false;

		return true;
	}
	
	public static boolean isPrime(long val)
	{
		if (val <= 1)
			return false;
		
		if (val % 2 == 0)
			return val == 2;
		
		if (val % 3 == 0)
			return val == 3;
		
		if (val % 5 == 0)
			return val == 5;
		
		if (val % 7 == 0)
			return val == 7;

		var sqrtVal = (int)sqrt(val);
		
		for (var i = 11L; i <= sqrtVal; i += 2)
			if (val % i == 0)
				return false;
		
		return true;
	}
	
	public static boolean isPrimeX(long val)
	{
		boolean result;
		
		for (var sum = val; (result = isPrime(sum)) && sum > 9; sum = digitsSum(sum))
			;
		
		return result;
	}

	public static boolean isPrimeX(BigInteger val)
	{
		boolean result;
		var nine = BigInteger.valueOf(9);

		for (var sum = val; (result = isPrime(sum)) && sum.compareTo(nine) > 0; sum = digitsSum(sum))
			;

		return result;
	}
	
	public static boolean isSuperPrime(int n)
	{
		return isPrime(n) && isPrime(getIndexOfPrime(n));
	}

	public static OptionalInt toInt(String str)
	{
		return toInt(str, 10);
	}


	public static OptionalInt toInt(String str, int radix)
	{
		try {
			return OptionalInt.of(Integer.parseInt(str, radix));
		}
		catch (NumberFormatException ignore) {

		}

		return OptionalInt.empty();
	}

	public static OptionalLong toLong(String str)
	{
		return toLong(str, 10);
	}

	public static OptionalLong toLong(String str, int radix)
	{
		try {
			return OptionalLong.of(Long.parseLong(str, radix));
		}
		catch (NumberFormatException ignore) {

		}

		return OptionalLong.empty();
	}

	public static OptionalDouble toDouble(String str)
	{
		try {
			return OptionalDouble.of(Double.parseDouble(str));
		}
		catch (NumberFormatException ignore) {

		}

		return OptionalDouble.empty();
	}

	public static int toIntDefault(String str)
	{
		return toIntDefault(str, 10, 0);
	}

	public static int toIntDefault(String str, int defaultValue)
	{
		return toIntDefault(str, 10, defaultValue);
	}

	public static int toIntDefault(String str, int radix, int defaultValue)
	{
		try {
			return Integer.parseInt(str, radix);
		}
		catch (NumberFormatException ignore) {

		}

		return defaultValue;
	}

	public static long toLongDefault(String str)
	{
		return toLongDefault(str, 10, 0);
	}

	public static long toLongDefault(String str, long defaultValue)
	{
		return toLongDefault(str, 10, defaultValue);
	}

	public static long toLongDefault(String str, int radix, long defaultValue)
	{
		try {
			return Long.parseLong(str, radix);
		}
		catch (NumberFormatException ignore) {

		}

		return defaultValue;
	}


	public static short toShortDefault(String str)
	{
		return toShortDefault(str, 10, (short)0);
	}

	public static short toShortDefault(String str, short defaultValue)
	{
		return toShortDefault(str, 10, defaultValue);
	}

	public static short toShortDefault(String str, int radix, short defaultValue)
	{
		try {
			return Short.parseShort(str, radix);
		}
		catch (NumberFormatException ignore) {

		}

		return defaultValue;
	}

	public static double toDoubleDefault(String str)
	{
		return toDoubleDefault(str, 10);
	}

	public static double toDoubleDefault(String str, double defaultValue)
	{
		try {
			return Double.parseDouble(str);
		}
		catch (NumberFormatException ignore) {

		}

		return defaultValue;
	}



}