package org.fogbeam.example.sandbox.simulation.montecarlo;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.core.source32.JDKRandom;
import org.apache.commons.statistics.distribution.ContinuousDistribution.Sampler;
import org.apache.commons.statistics.distribution.UniformContinuousDistribution;
import org.apache.commons.statistics.distribution.UniformDiscreteDistribution;

public class MonteCarloPiMain 
{

	public static void main(String[] args) 
	{
		
		// generate n random points which fall inside our
		// bounding box
		int n = 1000000;
		UniformContinuousDistribution dist = new UniformContinuousDistribution( -1, 1 );
		UniformRandomProvider rng = new JDKRandom( System.currentTimeMillis() );
		Sampler pointsSource = dist.createSampler( rng );

		int pointsInsideCircle = 0;
		int pointsOutsideCircle = 0;
		
		for( int i = 0; i < n; i++ )
		{
			// generate a point
			double pointX = pointsSource.sample();
			double pointY = pointsSource.sample();
			
			// calculate the distance of the point
			// from the center of our inscribed circle (0,0)
			
			// distance formula
			// sqrt( (x_2 - x_1 )**2 + ( y_2 - y_1 ) ) 
			
			double originX = 0.0;
			double originY = 0.0;
			
			double distance = Math.sqrt( ( Math.pow( ( pointX - originX ), 2 ) + Math.pow( ( pointY - originY ), 2 ) ) );
			
			// System.out.println( "distance: " + distance );
			
			if( distance <= 1.0 )
			{
				// if the distance is 1 or less, it's inside the circle
				pointsInsideCircle++;
			}
			else
			{
				// if the distance is > 1, it's outside the circle, but 
				// still in the box (all generated points are in the box 
				// by definition)
				pointsOutsideCircle++;
			}
		}
		
		// calculate the ratio here which reflects our approximation
		// of PI
		
		System.out.println( "total points: " + n );
		System.out.println( "pointsOutsideCircle: " + pointsOutsideCircle );
		
		double PI = 4 - ( 4 * ( (double)pointsOutsideCircle / (double)n ) );
		
		System.out.println( "calculated PI as " + PI );
		System.out.println( "System value of PI is " + Math.PI );
		
		System.out.println( "done" );
	}

}
