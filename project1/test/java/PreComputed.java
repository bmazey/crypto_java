package java;

import org.junit.Test;

import main.java.org.nyu.precomputed.ComputeKeys;

public class PreComputed {
	
	@Test
	public void testLoadComputeKeys() {
		ComputeKeys test = new ComputeKeys();
	}
	
	@Test
	public void testRandomKeyGeneration() {
		ComputeKeys test = new ComputeKeys();
		test.getRandomKeyAllocation();
	}
}
