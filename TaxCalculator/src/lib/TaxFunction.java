package lib;

public class TaxFunction {

    /**
     * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
     * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
     * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
     * 
     */

    public static int calculateTax(int baseMonthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        validateNumberOfMonths(numberOfMonthWorking); // Validasi jumlah bulan kerja

        int annualIncome = calculateAnnualIncome(baseMonthlySalary, otherMonthlyIncome, numberOfMonthWorking);
        int totalExemption = calculateTaxExemption(isMarried, numberOfChildren);

        double taxRate = 0.05;
        double taxableIncome = annualIncome - totalExemption;
        int tax = (int) Math.round(taxRate * taxableIncome);

        return Math.max(tax, 0); // Pastikan pajak tidak negatif
    }

    private static void validateNumberOfMonths(int numberOfMonthWorking) {
        if (numberOfMonthWorking > 12) {
            throw new IllegalArgumentException("Number of months working per year cannot exceed 12");
        }
    }

    private static int calculateAnnualIncome(int baseMonthlySalary, int otherMonthlyIncome, int numberOfMonthWorking) {
        return (baseMonthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
    }

    private static int calculateTaxExemption(boolean isMarried, int numberOfChildren) {
        int taxExemptionBase = 54000000;
        int taxExemptionMarried = 4500000;
        int taxExemptionPerChild = 1500000;

        int totalExemption = taxExemptionBase;
        if (isMarried) {
            totalExemption += taxExemptionMarried;
        }
        // Pengurangan tambahan untuk setiap anak, maksimum 3 anak
        totalExemption += Math.min(numberOfChildren, 3) * taxExemptionPerChild;

        return totalExemption;
    }
}
