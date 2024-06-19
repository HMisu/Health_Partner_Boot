package com.bit.healthpartnerboot.service;

public class NutritionCalculator {
    public static double calculateBMR(double weight, double height, int age, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    public static double calculateTDEE(double bmr, String activityLevel) {
        return switch (activityLevel) {
            case "Very Low" -> bmr * 1.2;
            case "Low" -> bmr * 1.375;
            case "High" -> bmr * 1.725;
            case "Very High" -> bmr * 1.9;
            default -> bmr * 1.55;
        };
    }

    public static double[] calculateMacros(double tdee) {
        double proteinPercentage = 0.15;
        double carbsPercentage = 0.55;
        double fatPercentage = 0.30;

        double proteinCalories = tdee * proteinPercentage;
        double carbsCalories = tdee * carbsPercentage;
        double fatCalories = tdee * fatPercentage;

        double proteinGrams = proteinCalories / 4;
        double carbsGrams = carbsCalories / 4;
        double fatGrams = fatCalories / 9;

        return new double[]{proteinGrams, carbsGrams, fatGrams};
    }
}
