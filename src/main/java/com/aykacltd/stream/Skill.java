package com.aykacltd.stream;

/**
 * Represents a technical or professional skill held by a {@link Person}.
 *
 * <p>Each skill has a name, a proficiency level (BEGINNER through EXPERT),
 * and a category for classification. Used in Stream exercises for
 * flatMapping, nested collection processing, and multi-level grouping.</p>
 *
 * @param name     the name of the skill (e.g. "Java", "Project Management")
 * @param level    the proficiency level
 * @param category broad category (e.g. "Programming", "Leadership")
 */
public record Skill(
    String name,
    ProficiencyLevel level,
    String category
) {

    /**
     * Proficiency levels ordered from least to most experienced.
     * Each level carries a numeric weight for comparison and averaging.
     */
    public enum ProficiencyLevel {
        BEGINNER(1), INTERMEDIATE(2), ADVANCED(3), EXPERT(4);

        private final int weight;

        ProficiencyLevel(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }
}
