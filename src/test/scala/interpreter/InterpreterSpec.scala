/**
 * Licensed to the Apache Software Foundation (ASF) under one
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
package interpreter

import java.lang.{Integer => JInteger}
import org.scalatest._

class InterpreterSpec extends FlatSpec with Matchers {

  "Interpeter" should "return next token" in {
    val interpreter = Interpreter("1+2")

    val next1 = interpreter.nextToken
    assert(!None.equals(next1.token))
    assert(Token[Int](Integer, 1) == next1.token.get)

    val next2 = next1.nextToken
    assert(!None.equals(next2.token))
    assert(Token[Char](Plus, '+') == next2.token.get)

    val next3 = next2.nextToken
    assert(!None.equals(next3.token))
    assert(Token[Int](Integer, 2) == next3.token.get)

    val next4 = next3.nextToken
    assert(!None.equals(next4.token))
    assert(Token(EndOfFile) == next4.token.get)
  }

}
