// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
	
	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations 
	
	// Gets the loan data and computes the periodical payment.
	public static void main(String[] args) {		
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);

		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Brute force
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Bisection
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes ending balance
	private static double endBalance(double loan, double rate, int n, double payment) {	
		double balance = loan;
		double r = rate / 100.0;   // ריבית תקופתית

		for (int i = 0; i < n; i++) {
			balance = balance * (1 + r) - payment;
		}
		return balance;
	}

	// Brute force search
	public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;

		double g = loan / n;   // f(g) > 0

		while (endBalance(loan, rate, n, g) > 0) {
			g += epsilon;
			iterationCounter++;
		}

		return g;
	}

	// Bisection search
	public static double bisectionSolver(double loan, double rate, int n, double epsilon) {  
		iterationCounter = 0;

		double lo = loan / n;   // f(lo) > 0
		double hi = loan;       // f(hi) < 0 ALWAYS  

		double fLo = endBalance(loan, rate, n, lo);

		while (hi - lo > epsilon) {
			iterationCounter++;

			double mid = (lo + hi) / 2;
			double fMid = endBalance(loan, rate, n, mid);

			if (fMid * fLo > 0) {
				lo = mid;
				fLo = fMid;
			} else {
				hi = mid;
			}
		}

		return (lo + hi) / 2;
	}
}
