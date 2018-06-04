/*
 * Copyright 2017 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sjph.life.post;

/**
 * Basic object indicating a range of objects to retrieve. Default is 10 objects (starting at zero).
 * 
 * @author Shaohui Guo
 */
@SuppressWarnings("javadoc")
public class Range {

    private static final int SIZE  = 9;

    /**
     * the index of begin
     */
    public int               begin = 0;

    /**
     * the index of end
     */
    public int               end   = SIZE;

    /**
     * Construct a Range begin=0, end=-1;
     */
    public Range() {
        end = -1;
    }

    public Range(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public Range(int pageNumber) {
        this.begin = 0;
        this.end = pageNumber * SIZE;
    }

    public int getPages() {
        return (int) Math.round(Math.ceil(end / SIZE));
    }
}
