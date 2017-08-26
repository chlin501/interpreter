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

import java.lang.{ Integer => JInteger }

object Interpreter {

  def apply[T](text: String, pos: Int, token: Token[T]): Interpreter[T] = 
    Interpreter[T](text, pos, Option(token))
}
case class Interpreter[T](
  text: String, pos: Int = 0, token: Option[Token[T]] = None
) { 

  require(null != text && !"".equals(text), "Empty text found!")

  def this(text: String) = this(text, 0)

  def nextToken(): Interpreter[T] = {
    if(pos > text.length - 1) 
      return Interpreter[T](text, pos, Token(EndOfFile).asInstanceOf[Token[T]])
    val currentChar = text(pos) 
    import Token._ 
    if(currentChar.isDigit) return Interpreter[T](
      text, pos + 1, Token(Integer, currentChar.charToInt.asInstanceOf[T])
    )
    if('+' == currentChar) return Interpreter[T](
      text, pos + 1, Token(Plus, currentChar.asInstanceOf[T])
    )
    throw new RuntimeException("Error parsing input text!")
  }

  override def equals(any: Any): Boolean = any match {
    case that: Token[T] => that.canEqual(this) &&
      this.hashCode == that.hashCode
    case _ => false
  }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    val tokenHashCode = token match {
      case None => 0
      case _ => token.get.hashCode
    }

    result = prime * result + text.hashCode
    result = prime * result + pos.hashCode
    result = prime * result + tokenHashCode
    result
  }

}	
