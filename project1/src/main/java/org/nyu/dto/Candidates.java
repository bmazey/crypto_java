package org.nyu.dto;

public class Candidates {

	/**
	 * This class represents a Candidates POJO b.mazey@nyu.edu modified by sb6856
	 */

	private String[] candidates;
	private String[] original_candidates;

	public String[] getCandidates() {
		return candidates;
	}

	public String[] getOriginalCandidates() {
		return original_candidates;
	}

	public void setCandidates(String[] candidates) {
		this.candidates = candidates;
	}

	/**
	 * Modify candidates to be comma-seperated e.g abcde converted to a,b,c,d,e
	 */
	public void modifyCandidates() {

		original_candidates = new String[candidates.length];
		for (int j = 0; j < candidates.length; j++) {
			original_candidates[j] = candidates[j];
			candidates[j] = candidates[j].replaceAll(".(?!$)", "$0,");
		}
	}

	public String returnMatchString(String word) {

		String[] split = word.split(",");
		for (String s : original_candidates) {
			String temp = s.replaceAll(".(?!$)", "$0,");
			int count = 0;
			String[] split_s = temp.split(",");
			for (int i = 0; i < split.length; i++) {
				try {
					if (split[i].equalsIgnoreCase(split_s[i]))
						count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (count > 105)
					return s;
			}
		}
		return null;
	}

	@Override
	public String toString() {

		StringBuilder build = new StringBuilder("\n");
		for (String candidate : candidates) {
			build.append(candidate + "\n============\n");
		}
		return "Candidates [candidates=" + build + "]";
	}

}
