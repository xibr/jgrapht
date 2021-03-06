/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* -----------------
 * PushRelabelMinimumSTCutTest.java
 * -----------------
 * (C) Copyright 2016, by Joris Kinable and Contributors.
 *
 * Original Author:  Joris Kinable
 * Contributor(s): -
 *
 * $Id$
 *
 * Changes
 * -------
 * Aug-2016 : Initial version (JK);
 */
package org.jgrapht.alg.flow;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.interfaces.MinimumSTCutAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;

/**
 * @author Joris Kinable
 */
public class PushRelabelMinimumSTCutTest extends MinimumSourceSinkCutTest{
    @Override
    MinimumSTCutAlgorithm<Integer, DefaultWeightedEdge> createSolver(Graph<Integer, DefaultWeightedEdge> network) {
        return new PushRelabelMFImpl<>(network);
    }

    public void testRandomDirectedGraphs(){
        for(int test=0; test<NR_RANDOM_TESTS; test++){
            DirectedGraph<Integer, DefaultWeightedEdge> network=generateDirectedGraph();
            int source=0;
            int sink=network.vertexSet().size()-1;

            MinimumSTCutAlgorithm<Integer, DefaultWeightedEdge> prSolver=this.createSolver(network);
            MinimumSTCutAlgorithm<Integer, DefaultWeightedEdge> ekSolver=new EdmondsKarpMFImpl<>(network);

            double expectedCutWeight=ekSolver.calculateMinCut(source, sink);

            double cutWeight=prSolver.calculateMinCut(source, sink);
            Set<Integer> sourcePartition=prSolver.getSourcePartition();
            Set<Integer> sinkPartition=prSolver.getSinkPartition();
            Set<DefaultWeightedEdge> cutEdges=prSolver.getCutEdges();

            this.verifyDirected(network, source, sink, expectedCutWeight, cutWeight, sourcePartition, sinkPartition, cutEdges);
        }
    }

    public void testRandomUndirectedGraphs(){
        for(int test=0; test<NR_RANDOM_TESTS; test++){
            UndirectedGraph<Integer, DefaultWeightedEdge> network=generateUndirectedGraph();
            int source=0;
            int sink=network.vertexSet().size()-1;

            MinimumSTCutAlgorithm<Integer, DefaultWeightedEdge> prSolver=this.createSolver(network);
            MinimumSTCutAlgorithm<Integer, DefaultWeightedEdge> ekSolver=new EdmondsKarpMFImpl<>(network);

            double expectedCutWeight=ekSolver.calculateMinCut(source, sink);

            double cutWeight=prSolver.calculateMinCut(source, sink);
            Set<Integer> sourcePartition=prSolver.getSourcePartition();
            Set<Integer> sinkPartition=prSolver.getSinkPartition();
            Set<DefaultWeightedEdge> cutEdges=prSolver.getCutEdges();

            this.verifyUndirected(network, source, sink, expectedCutWeight, cutWeight, sourcePartition, sinkPartition, cutEdges);
        }
    }
}
