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
import scala.language.implicitConversions

sealed trait TokenType
case object Integer extends TokenType
case object Plus extends TokenType
case object EndOfFile extends TokenType

object Token {

  implicit class CharToInt(c: Char) {
     def charToInt: Int = Character.getNumericValue(c)
  }

  def apply[T](tpe: TokenType): Token[T] = Token[T](tpe, None)

  def apply[T](tpe: TokenType, value: T): Token[T] = Token[T](
    tpe, Option(value)
  )

}
case class Token[T](tpe: TokenType, value: Option[T] = None) {

  override def equals(any: Any): Boolean = any match {
    case that: Token[T] => that.canEqual(this) && this.hashCode == that.hashCode
    case _ => false
  }

  override def hashCode(): Int = {
    val prime = 31
    var result = 1 
    val valueHashCode = value match {
      case None => 0
      case _ => value.get.hashCode
    } 
    result = prime * result + tpe.hashCode
    result = prime * result + valueHashCode
    result
  }
}
