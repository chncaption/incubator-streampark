package com.streamxhub.common.util

import java.io.StringWriter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object JsonUtils {

  private val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

  def read[T](obj: AnyRef)(implicit manifest: Manifest[T]): T = {
    obj match {
      case str: String => mapper.readValue[T](str)
      case _ => mapper.readValue[T](write(obj))
    }
  }

  def write(obj: AnyRef): String = {
    val out = new StringWriter
    mapper.writeValue(out, obj)
    out.toString
  }

}