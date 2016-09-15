package me.dominik.oneversusone.utils;

/**
 * Created by Dominik on 07.08.2016.
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.*;

public class SuperScoreboard {

    private static int scoreboardCounter = 0;
    private int playerCounter = 0;

    private List<SuperScore> scores = new ArrayList<>();

    private Scoreboard scoreboard;
    private Objective objective;
    private int id = scoreboardCounter++;

    public SuperScoreboard(Scoreboard scoreboard) {
        this(scoreboard, null);
    }

    /**
     * Creates a new SuperScoreboard instance using a Bukkit scoreboard.
     * An objective will be created
     * @param scoreboard The scoreboard
     * @param title The objectives title
     */
    public SuperScoreboard(Scoreboard scoreboard, String title) {
        this.scoreboard = scoreboard;

        String objectiveName = String.format("SuperSB_%d", id);
        if ((this.objective = this.scoreboard.getObjective(objectiveName)) == null) {
            this.objective = this.scoreboard.registerNewObjective(objectiveName, "dummy");
        }
        if (title != null) this.setTitle(title);

        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public SuperScoreboard(String title) {
        this(Bukkit.getScoreboardManager().getNewScoreboard(), title);
    }

    /**
     * Creates a new SuperScoreboard instance using an existing objective
     * @param objective The objective
     */
    public SuperScoreboard(Objective objective) {
        this.scoreboard = (this.objective = objective).getScoreboard();
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    /**
     * Adds a row to the bottom and executes SuperScoreboard#sort()
     * @param text The rows text
     * @return The new score instance
     */
    public SuperScore addRow(String text) {
        SuperScore score = new SuperScore().setText(text).setScore(-1);
        sort();
        return score;
    }

    /**
     * @return The scores sorted as they are in the scoreboard
     */
    public List<SuperScore> getScores() {
        Collections.sort(scores);
        return new ArrayList<>(scores);
    }

    /**
     * Sets this objective's title
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.objective.setDisplayName(title);
    }

    /**
     * @return This objective's title
     */
    public String getTitle() {
        return this.objective.getDisplayName();
    }

    /**
     * Sorts the scoreboard by score and adjusts the scores (top: entries#size(); bottom: 1)
     */
    public void sort() {
        sort(null);
    }

    /**
     * Sorts the scoreboard entries follow the comparator's rules
     * @param comparator The comparator
     * @see SuperScoreboard#sort()
     */
    public void sort(Comparator<SuperScore> comparator) {
        Collections.sort(this.scores, comparator);
        for (int i = 0; i < this.scores.size(); i++) {
            this.scores.get(i).setScore(this.scores.size() - i);
        }
    }

    /**
     * Removes the scores and unregisters the objective
     */
    public void close() {
        for (int i = 0; i < scores.size(); i++) scores.get(i).remove();
        this.objective.unregister();
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public class SuperScore implements Comparable<SuperScore> {

        private int id = playerCounter++;

        private String name, text;
        private Team team;
        private Score score;

        private SuperScore() {
            this.name = Integer.toString(id).replaceAll("(?!$)", "§").replaceAll("$", "§r");

            String teamName = "SuperScore_" + id;
            this.team = scoreboard.getTeam(teamName);
            if (this.team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            this.team.addEntry(name);

            this.score = objective.getScore(this.getName());

            scores.add(this);
        }

        /**
         * Sets this score's display text
         * @param text The text
         * @return This score
         */
        public SuperScore setText(String text) {
            this.text = text;

            String prefix = text.substring(0, Math.min(text.length(), 16));
            if (prefix.endsWith("§")) {
                prefix = prefix.substring(0, prefix.length()-1);
            }

            team.setPrefix(prefix);

            String suffix = text.substring(prefix.length());

            if (text.length() > prefix.length()) {
                StringBuilder inheritedColor = new StringBuilder();

                if (!(suffix.startsWith("§") && ChatColor.getByChar(suffix.charAt(1)).isColor())) {

                    // transfer colors from prefix to suffix
                    Collection<ChatColor> format = new ArrayDeque<>();
                    ChatColor lastColor = null;

                    for (int index = 0; index < prefix.length(); index++) {
                        char currChar = prefix.charAt(index);
                        if (currChar != ChatColor.COLOR_CHAR) continue;

                        ChatColor color = ChatColor.getByChar(prefix.charAt(++index));
                        if (color == null) continue;

                        if (color.isColor()) {
                            format.clear();
                            lastColor = color;
                        } else if (color.isFormat()) {
                            if (color == ChatColor.RESET) {
                                format.clear();
                                lastColor = null;
                            } else {
                                format.add(color);
                            }
                        }
                    }

                    if (lastColor != null) inheritedColor.append(lastColor);
                    if (!format.isEmpty()) format.forEach(inheritedColor::append);
                    format.clear();
                }

                suffix = inheritedColor.append(suffix).toString();

                team.setSuffix(suffix.substring(0, Math.min(16, suffix.length())));
            } else {
                team.setSuffix("");
            }
            return this;
        }

        /**
         * Sets this score's scoreboard value
         * @param score The new score
         * @return This score
         */
        public SuperScore setScore(int score) {
            if (score != getScore()) {
                this.score.setScore(score);
            }
            return this;
        }

        /**
         * @return This score's score
         */
        public int getScore() {
            return this.score.getScore();
        }

        /**
         * Removes this score from scoreboard
         */
        public void remove() {
            scoreboard.resetScores(name);
            team.unregister();
            scores.remove(this);
        }

        /**
         * @return this score's underlying team
         */
        public Team getTeam() {
            return team;
        }

        @Override
        public int compareTo(SuperScore o) {
            return Integer.signum(o.getScore() - getScore());
        }

        @Override
        public String toString() {
            return String.format("SuperScore{text=%s,score=%d}", text, getScore());
        }

        public String getName() {
            return this.name;
        }

        public String getText() {
            return this.text;
        }
    }

}
