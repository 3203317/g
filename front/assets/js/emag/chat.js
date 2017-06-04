/**
 * @fileoverview
 * @enhanceable
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!


goog.provide('proto.gws.chat.ChatProtobuf');
goog.provide('proto.gws.chat.SendProtobuf');

goog.require('jspb.Message');
goog.require('jspb.BinaryReader');
goog.require('jspb.BinaryWriter');


/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.gws.chat.ChatProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.chat.ChatProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.chat.ChatProtobuf.displayName = 'proto.gws.chat.ChatProtobuf';
}


if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto suitable for use in Soy templates.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     com.google.apps.jspb.JsClassTemplate.JS_RESERVED_WORDS.
 * @param {boolean=} opt_includeInstance Whether to include the JSPB instance
 *     for transitional soy proto support: http://goto/soy-param-migration
 * @return {!Object}
 */
proto.gws.chat.ChatProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.chat.ChatProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.chat.ChatProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.chat.ChatProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    id: jspb.Message.getFieldWithDefault(msg, 1, ""),
    timestamp: jspb.Message.getFieldWithDefault(msg, 2, 0),
    sender: jspb.Message.getFieldWithDefault(msg, 3, ""),
    receiver: jspb.Message.getFieldWithDefault(msg, 4, ""),
    comment: msg.getComment_asB64()
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.gws.chat.ChatProtobuf}
 */
proto.gws.chat.ChatProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.chat.ChatProtobuf;
  return proto.gws.chat.ChatProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.chat.ChatProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.chat.ChatProtobuf}
 */
proto.gws.chat.ChatProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setId(value);
      break;
    case 2:
      var value = /** @type {number} */ (reader.readUint64());
      msg.setTimestamp(value);
      break;
    case 3:
      var value = /** @type {string} */ (reader.readString());
      msg.setSender(value);
      break;
    case 4:
      var value = /** @type {string} */ (reader.readString());
      msg.setReceiver(value);
      break;
    case 5:
      var value = /** @type {!Uint8Array} */ (reader.readBytes());
      msg.setComment(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Class method variant: serializes the given message to binary data
 * (in protobuf wire format), writing to the given BinaryWriter.
 * @param {!proto.gws.chat.ChatProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.chat.ChatProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.chat.ChatProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.chat.ChatProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getId();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getTimestamp();
  if (f !== 0) {
    writer.writeUint64(
      2,
      f
    );
  }
  f = this.getSender();
  if (f.length > 0) {
    writer.writeString(
      3,
      f
    );
  }
  f = this.getReceiver();
  if (f.length > 0) {
    writer.writeString(
      4,
      f
    );
  }
  f = this.getComment_asU8();
  if (f.length > 0) {
    writer.writeBytes(
      5,
      f
    );
  }
};


/**
 * optional string id = 1;
 * @return {string}
 */
proto.gws.chat.ChatProtobuf.prototype.getId = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.chat.ChatProtobuf.prototype.setId = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional uint64 timestamp = 2;
 * @return {number}
 */
proto.gws.chat.ChatProtobuf.prototype.getTimestamp = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 2, 0));
};


/** @param {number} value */
proto.gws.chat.ChatProtobuf.prototype.setTimestamp = function(value) {
  jspb.Message.setField(this, 2, value);
};


/**
 * optional string sender = 3;
 * @return {string}
 */
proto.gws.chat.ChatProtobuf.prototype.getSender = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 3, ""));
};


/** @param {string} value */
proto.gws.chat.ChatProtobuf.prototype.setSender = function(value) {
  jspb.Message.setField(this, 3, value);
};


/**
 * optional string receiver = 4;
 * @return {string}
 */
proto.gws.chat.ChatProtobuf.prototype.getReceiver = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 4, ""));
};


/** @param {string} value */
proto.gws.chat.ChatProtobuf.prototype.setReceiver = function(value) {
  jspb.Message.setField(this, 4, value);
};


/**
 * optional bytes comment = 5;
 * @return {!(string|Uint8Array)}
 */
proto.gws.chat.ChatProtobuf.prototype.getComment = function() {
  return /** @type {!(string|Uint8Array)} */ (jspb.Message.getFieldWithDefault(this, 5, ""));
};


/**
 * optional bytes comment = 5;
 * This is a type-conversion wrapper around `getComment()`
 * @return {string}
 */
proto.gws.chat.ChatProtobuf.prototype.getComment_asB64 = function() {
  return /** @type {string} */ (jspb.Message.bytesAsB64(
      this.getComment()));
};


/**
 * optional bytes comment = 5;
 * Note that Uint8Array is not supported on all browsers.
 * @see http://caniuse.com/Uint8Array
 * This is a type-conversion wrapper around `getComment()`
 * @return {!Uint8Array}
 */
proto.gws.chat.ChatProtobuf.prototype.getComment_asU8 = function() {
  return /** @type {!Uint8Array} */ (jspb.Message.bytesAsU8(
      this.getComment()));
};


/** @param {!(string|Uint8Array)} value */
proto.gws.chat.ChatProtobuf.prototype.setComment = function(value) {
  jspb.Message.setField(this, 5, value);
};



/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.gws.chat.SendProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.chat.SendProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.chat.SendProtobuf.displayName = 'proto.gws.chat.SendProtobuf';
}


if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto suitable for use in Soy templates.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     com.google.apps.jspb.JsClassTemplate.JS_RESERVED_WORDS.
 * @param {boolean=} opt_includeInstance Whether to include the JSPB instance
 *     for transitional soy proto support: http://goto/soy-param-migration
 * @return {!Object}
 */
proto.gws.chat.SendProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.chat.SendProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.chat.SendProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.chat.SendProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    receiver: jspb.Message.getFieldWithDefault(msg, 1, ""),
    comment: jspb.Message.getFieldWithDefault(msg, 2, "")
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.gws.chat.SendProtobuf}
 */
proto.gws.chat.SendProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.chat.SendProtobuf;
  return proto.gws.chat.SendProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.chat.SendProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.chat.SendProtobuf}
 */
proto.gws.chat.SendProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setReceiver(value);
      break;
    case 2:
      var value = /** @type {string} */ (reader.readString());
      msg.setComment(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Class method variant: serializes the given message to binary data
 * (in protobuf wire format), writing to the given BinaryWriter.
 * @param {!proto.gws.chat.SendProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.chat.SendProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.chat.SendProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.chat.SendProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getReceiver();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getComment();
  if (f.length > 0) {
    writer.writeString(
      2,
      f
    );
  }
};


/**
 * optional string receiver = 1;
 * @return {string}
 */
proto.gws.chat.SendProtobuf.prototype.getReceiver = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.chat.SendProtobuf.prototype.setReceiver = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional string comment = 2;
 * @return {string}
 */
proto.gws.chat.SendProtobuf.prototype.getComment = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/** @param {string} value */
proto.gws.chat.SendProtobuf.prototype.setComment = function(value) {
  jspb.Message.setField(this, 2, value);
};


