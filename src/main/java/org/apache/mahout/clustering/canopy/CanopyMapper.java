/* Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.mahout.clustering.canopy;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.Vector;
import org.apache.mahout.utils.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanopyMapper extends MapReduceBase implements
        Mapper<WritableComparable, Text, Text, Text> {

  private List<Canopy> canopies = new ArrayList<Canopy>();

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.hadoop.mapred.Mapper#map(org.apache.hadoop.io.WritableComparable,
   *      org.apache.hadoop.io.Writable,
   *      org.apache.hadoop.mapred.OutputCollector,
   *      org.apache.hadoop.mapred.Reporter)
   */
  public void map(WritableComparable key, Text values,
                  OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    Vector point = Point.decodePoint(values.toString());
    Canopy.emitPointToNewCanopies(point, canopies, output);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.hadoop.mapred.MapReduceBase#configure(org.apache.hadoop.mapred.JobConf)
   */
  @Override
  public void configure(JobConf job) {
    super.configure(job);
    Canopy.configure(job);
  }

}
