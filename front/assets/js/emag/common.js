/**
 * @fileoverview
 * @enhanceable
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!


goog.provide('proto.gws.ErrorProtobuf');
goog.provide('proto.gws.PrimaryKeyProtobuf');
goog.provide('proto.gws.ReceiverProtobuf');
goog.provide('proto.gws.RequestProtobuf');
goog.provide('proto.gws.ResponseProtobuf');
goog.provide('proto.gws.SenderProtobuf');

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
proto.gws.SenderProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.SenderProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.SenderProtobuf.displayName = 'proto.gws.SenderProtobuf';
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
proto.gws.SenderProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.SenderProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.SenderProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.SenderProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    sender: jspb.Message.getFieldWithDefault(msg, 1, ""),
    data: (f = msg.getData()) && proto.gws.RequestProtobuf.toObject(includeInstance, f)
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
 * @return {!proto.gws.SenderProtobuf}
 */
proto.gws.SenderProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.SenderProtobuf;
  return proto.gws.SenderProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.SenderProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.SenderProtobuf}
 */
proto.gws.SenderProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setSender(value);
      break;
    case 2:
      var value = new proto.gws.RequestProtobuf;
      reader.readMessage(value,proto.gws.RequestProtobuf.deserializeBinaryFromReader);
      msg.setData(value);
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
 * @param {!proto.gws.SenderProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.SenderProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.SenderProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.SenderProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getSender();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getData();
  if (f != null) {
    writer.writeMessage(
      2,
      f,
      proto.gws.RequestProtobuf.serializeBinaryToWriter
    );
  }
};


/**
 * optional string sender = 1;
 * @return {string}
 */
proto.gws.SenderProtobuf.prototype.getSender = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.SenderProtobuf.prototype.setSender = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional RequestProtobuf data = 2;
 * @return {?proto.gws.RequestProtobuf}
 */
proto.gws.SenderProtobuf.prototype.getData = function() {
  return /** @type{?proto.gws.RequestProtobuf} */ (
    jspb.Message.getWrapperField(this, proto.gws.RequestProtobuf, 2));
};


/** @param {?proto.gws.RequestProtobuf|undefined} value */
proto.gws.SenderProtobuf.prototype.setData = function(value) {
  jspb.Message.setWrapperField(this, 2, value);
};


proto.gws.SenderProtobuf.prototype.clearData = function() {
  this.setData(undefined);
};


/**
 * Returns whether this field is set.
 * @return {!boolean}
 */
proto.gws.SenderProtobuf.prototype.hasData = function() {
  return jspb.Message.getField(this, 2) != null;
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
proto.gws.ReceiverProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.ReceiverProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.ReceiverProtobuf.displayName = 'proto.gws.ReceiverProtobuf';
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
proto.gws.ReceiverProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.ReceiverProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.ReceiverProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.ReceiverProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    receiver: jspb.Message.getFieldWithDefault(msg, 1, ""),
    data: (f = msg.getData()) && proto.gws.ResponseProtobuf.toObject(includeInstance, f)
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
 * @return {!proto.gws.ReceiverProtobuf}
 */
proto.gws.ReceiverProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.ReceiverProtobuf;
  return proto.gws.ReceiverProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.ReceiverProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.ReceiverProtobuf}
 */
proto.gws.ReceiverProtobuf.deserializeBinaryFromReader = function(msg, reader) {
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
      var value = new proto.gws.ResponseProtobuf;
      reader.readMessage(value,proto.gws.ResponseProtobuf.deserializeBinaryFromReader);
      msg.setData(value);
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
 * @param {!proto.gws.ReceiverProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ReceiverProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.ReceiverProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ReceiverProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getReceiver();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getData();
  if (f != null) {
    writer.writeMessage(
      2,
      f,
      proto.gws.ResponseProtobuf.serializeBinaryToWriter
    );
  }
};


/**
 * optional string receiver = 1;
 * @return {string}
 */
proto.gws.ReceiverProtobuf.prototype.getReceiver = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.ReceiverProtobuf.prototype.setReceiver = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional ResponseProtobuf data = 2;
 * @return {?proto.gws.ResponseProtobuf}
 */
proto.gws.ReceiverProtobuf.prototype.getData = function() {
  return /** @type{?proto.gws.ResponseProtobuf} */ (
    jspb.Message.getWrapperField(this, proto.gws.ResponseProtobuf, 2));
};


/** @param {?proto.gws.ResponseProtobuf|undefined} value */
proto.gws.ReceiverProtobuf.prototype.setData = function(value) {
  jspb.Message.setWrapperField(this, 2, value);
};


proto.gws.ReceiverProtobuf.prototype.clearData = function() {
  this.setData(undefined);
};


/**
 * Returns whether this field is set.
 * @return {!boolean}
 */
proto.gws.ReceiverProtobuf.prototype.hasData = function() {
  return jspb.Message.getField(this, 2) != null;
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
proto.gws.RequestProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.RequestProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.RequestProtobuf.displayName = 'proto.gws.RequestProtobuf';
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
proto.gws.RequestProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.RequestProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.RequestProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.RequestProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    version: jspb.Message.getFieldWithDefault(msg, 1, 0),
    method: jspb.Message.getFieldWithDefault(msg, 2, 0),
    seqid: jspb.Message.getFieldWithDefault(msg, 3, 0),
    timestamp: jspb.Message.getFieldWithDefault(msg, 4, 0),
    data: msg.getData_asB64()
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
 * @return {!proto.gws.RequestProtobuf}
 */
proto.gws.RequestProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.RequestProtobuf;
  return proto.gws.RequestProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.RequestProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.RequestProtobuf}
 */
proto.gws.RequestProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {number} */ (reader.readUint32());
      msg.setVersion(value);
      break;
    case 2:
      var value = /** @type {number} */ (reader.readUint32());
      msg.setMethod(value);
      break;
    case 3:
      var value = /** @type {number} */ (reader.readUint64());
      msg.setSeqid(value);
      break;
    case 4:
      var value = /** @type {number} */ (reader.readUint64());
      msg.setTimestamp(value);
      break;
    case 5:
      var value = /** @type {!Uint8Array} */ (reader.readBytes());
      msg.setData(value);
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
 * @param {!proto.gws.RequestProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.RequestProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.RequestProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.RequestProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getVersion();
  if (f !== 0) {
    writer.writeUint32(
      1,
      f
    );
  }
  f = this.getMethod();
  if (f !== 0) {
    writer.writeUint32(
      2,
      f
    );
  }
  f = this.getSeqid();
  if (f !== 0) {
    writer.writeUint64(
      3,
      f
    );
  }
  f = this.getTimestamp();
  if (f !== 0) {
    writer.writeUint64(
      4,
      f
    );
  }
  f = this.getData_asU8();
  if (f.length > 0) {
    writer.writeBytes(
      5,
      f
    );
  }
};


/**
 * optional uint32 version = 1;
 * @return {number}
 */
proto.gws.RequestProtobuf.prototype.getVersion = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 1, 0));
};


/** @param {number} value */
proto.gws.RequestProtobuf.prototype.setVersion = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional uint32 method = 2;
 * @return {number}
 */
proto.gws.RequestProtobuf.prototype.getMethod = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 2, 0));
};


/** @param {number} value */
proto.gws.RequestProtobuf.prototype.setMethod = function(value) {
  jspb.Message.setField(this, 2, value);
};


/**
 * optional uint64 seqId = 3;
 * @return {number}
 */
proto.gws.RequestProtobuf.prototype.getSeqid = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 3, 0));
};


/** @param {number} value */
proto.gws.RequestProtobuf.prototype.setSeqid = function(value) {
  jspb.Message.setField(this, 3, value);
};


/**
 * optional uint64 timestamp = 4;
 * @return {number}
 */
proto.gws.RequestProtobuf.prototype.getTimestamp = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 4, 0));
};


/** @param {number} value */
proto.gws.RequestProtobuf.prototype.setTimestamp = function(value) {
  jspb.Message.setField(this, 4, value);
};


/**
 * optional bytes data = 5;
 * @return {!(string|Uint8Array)}
 */
proto.gws.RequestProtobuf.prototype.getData = function() {
  return /** @type {!(string|Uint8Array)} */ (jspb.Message.getFieldWithDefault(this, 5, ""));
};


/**
 * optional bytes data = 5;
 * This is a type-conversion wrapper around `getData()`
 * @return {string}
 */
proto.gws.RequestProtobuf.prototype.getData_asB64 = function() {
  return /** @type {string} */ (jspb.Message.bytesAsB64(
      this.getData()));
};


/**
 * optional bytes data = 5;
 * Note that Uint8Array is not supported on all browsers.
 * @see http://caniuse.com/Uint8Array
 * This is a type-conversion wrapper around `getData()`
 * @return {!Uint8Array}
 */
proto.gws.RequestProtobuf.prototype.getData_asU8 = function() {
  return /** @type {!Uint8Array} */ (jspb.Message.bytesAsU8(
      this.getData()));
};


/** @param {!(string|Uint8Array)} value */
proto.gws.RequestProtobuf.prototype.setData = function(value) {
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
proto.gws.ResponseProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.ResponseProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.ResponseProtobuf.displayName = 'proto.gws.ResponseProtobuf';
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
proto.gws.ResponseProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.ResponseProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.ResponseProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.ResponseProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    version: jspb.Message.getFieldWithDefault(msg, 1, 0),
    method: jspb.Message.getFieldWithDefault(msg, 2, 0),
    seqid: jspb.Message.getFieldWithDefault(msg, 3, 0),
    timestamp: jspb.Message.getFieldWithDefault(msg, 4, 0),
    data: msg.getData_asB64(),
    error: (f = msg.getError()) && proto.gws.ErrorProtobuf.toObject(includeInstance, f)
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
 * @return {!proto.gws.ResponseProtobuf}
 */
proto.gws.ResponseProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.ResponseProtobuf;
  return proto.gws.ResponseProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.ResponseProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.ResponseProtobuf}
 */
proto.gws.ResponseProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {number} */ (reader.readUint32());
      msg.setVersion(value);
      break;
    case 2:
      var value = /** @type {number} */ (reader.readUint32());
      msg.setMethod(value);
      break;
    case 3:
      var value = /** @type {number} */ (reader.readUint64());
      msg.setSeqid(value);
      break;
    case 4:
      var value = /** @type {number} */ (reader.readUint64());
      msg.setTimestamp(value);
      break;
    case 5:
      var value = /** @type {!Uint8Array} */ (reader.readBytes());
      msg.setData(value);
      break;
    case 6:
      var value = new proto.gws.ErrorProtobuf;
      reader.readMessage(value,proto.gws.ErrorProtobuf.deserializeBinaryFromReader);
      msg.setError(value);
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
 * @param {!proto.gws.ResponseProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ResponseProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.ResponseProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ResponseProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getVersion();
  if (f !== 0) {
    writer.writeUint32(
      1,
      f
    );
  }
  f = this.getMethod();
  if (f !== 0) {
    writer.writeUint32(
      2,
      f
    );
  }
  f = this.getSeqid();
  if (f !== 0) {
    writer.writeUint64(
      3,
      f
    );
  }
  f = this.getTimestamp();
  if (f !== 0) {
    writer.writeUint64(
      4,
      f
    );
  }
  f = this.getData_asU8();
  if (f.length > 0) {
    writer.writeBytes(
      5,
      f
    );
  }
  f = this.getError();
  if (f != null) {
    writer.writeMessage(
      6,
      f,
      proto.gws.ErrorProtobuf.serializeBinaryToWriter
    );
  }
};


/**
 * optional uint32 version = 1;
 * @return {number}
 */
proto.gws.ResponseProtobuf.prototype.getVersion = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 1, 0));
};


/** @param {number} value */
proto.gws.ResponseProtobuf.prototype.setVersion = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional uint32 method = 2;
 * @return {number}
 */
proto.gws.ResponseProtobuf.prototype.getMethod = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 2, 0));
};


/** @param {number} value */
proto.gws.ResponseProtobuf.prototype.setMethod = function(value) {
  jspb.Message.setField(this, 2, value);
};


/**
 * optional uint64 seqId = 3;
 * @return {number}
 */
proto.gws.ResponseProtobuf.prototype.getSeqid = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 3, 0));
};


/** @param {number} value */
proto.gws.ResponseProtobuf.prototype.setSeqid = function(value) {
  jspb.Message.setField(this, 3, value);
};


/**
 * optional uint64 timestamp = 4;
 * @return {number}
 */
proto.gws.ResponseProtobuf.prototype.getTimestamp = function() {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 4, 0));
};


/** @param {number} value */
proto.gws.ResponseProtobuf.prototype.setTimestamp = function(value) {
  jspb.Message.setField(this, 4, value);
};


/**
 * optional bytes data = 5;
 * @return {!(string|Uint8Array)}
 */
proto.gws.ResponseProtobuf.prototype.getData = function() {
  return /** @type {!(string|Uint8Array)} */ (jspb.Message.getFieldWithDefault(this, 5, ""));
};


/**
 * optional bytes data = 5;
 * This is a type-conversion wrapper around `getData()`
 * @return {string}
 */
proto.gws.ResponseProtobuf.prototype.getData_asB64 = function() {
  return /** @type {string} */ (jspb.Message.bytesAsB64(
      this.getData()));
};


/**
 * optional bytes data = 5;
 * Note that Uint8Array is not supported on all browsers.
 * @see http://caniuse.com/Uint8Array
 * This is a type-conversion wrapper around `getData()`
 * @return {!Uint8Array}
 */
proto.gws.ResponseProtobuf.prototype.getData_asU8 = function() {
  return /** @type {!Uint8Array} */ (jspb.Message.bytesAsU8(
      this.getData()));
};


/** @param {!(string|Uint8Array)} value */
proto.gws.ResponseProtobuf.prototype.setData = function(value) {
  jspb.Message.setField(this, 5, value);
};


/**
 * optional ErrorProtobuf error = 6;
 * @return {?proto.gws.ErrorProtobuf}
 */
proto.gws.ResponseProtobuf.prototype.getError = function() {
  return /** @type{?proto.gws.ErrorProtobuf} */ (
    jspb.Message.getWrapperField(this, proto.gws.ErrorProtobuf, 6));
};


/** @param {?proto.gws.ErrorProtobuf|undefined} value */
proto.gws.ResponseProtobuf.prototype.setError = function(value) {
  jspb.Message.setWrapperField(this, 6, value);
};


proto.gws.ResponseProtobuf.prototype.clearError = function() {
  this.setError(undefined);
};


/**
 * Returns whether this field is set.
 * @return {!boolean}
 */
proto.gws.ResponseProtobuf.prototype.hasError = function() {
  return jspb.Message.getField(this, 6) != null;
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
proto.gws.ErrorProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.ErrorProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.ErrorProtobuf.displayName = 'proto.gws.ErrorProtobuf';
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
proto.gws.ErrorProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.ErrorProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.ErrorProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.ErrorProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    code: jspb.Message.getFieldWithDefault(msg, 1, ""),
    msg: jspb.Message.getFieldWithDefault(msg, 2, "")
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
 * @return {!proto.gws.ErrorProtobuf}
 */
proto.gws.ErrorProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.ErrorProtobuf;
  return proto.gws.ErrorProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.ErrorProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.ErrorProtobuf}
 */
proto.gws.ErrorProtobuf.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setCode(value);
      break;
    case 2:
      var value = /** @type {string} */ (reader.readString());
      msg.setMsg(value);
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
 * @param {!proto.gws.ErrorProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ErrorProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.ErrorProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.ErrorProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getCode();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getMsg();
  if (f.length > 0) {
    writer.writeString(
      2,
      f
    );
  }
};


/**
 * optional string code = 1;
 * @return {string}
 */
proto.gws.ErrorProtobuf.prototype.getCode = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.ErrorProtobuf.prototype.setCode = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional string msg = 2;
 * @return {string}
 */
proto.gws.ErrorProtobuf.prototype.getMsg = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/** @param {string} value */
proto.gws.ErrorProtobuf.prototype.setMsg = function(value) {
  jspb.Message.setField(this, 2, value);
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
proto.gws.PrimaryKeyProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.PrimaryKeyProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.PrimaryKeyProtobuf.displayName = 'proto.gws.PrimaryKeyProtobuf';
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
proto.gws.PrimaryKeyProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.PrimaryKeyProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.PrimaryKeyProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.PrimaryKeyProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    id: jspb.Message.getFieldWithDefault(msg, 1, "")
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
 * @return {!proto.gws.PrimaryKeyProtobuf}
 */
proto.gws.PrimaryKeyProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.PrimaryKeyProtobuf;
  return proto.gws.PrimaryKeyProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.PrimaryKeyProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.PrimaryKeyProtobuf}
 */
proto.gws.PrimaryKeyProtobuf.deserializeBinaryFromReader = function(msg, reader) {
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
 * @param {!proto.gws.PrimaryKeyProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.PrimaryKeyProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.PrimaryKeyProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.PrimaryKeyProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getId();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
};


/**
 * optional string id = 1;
 * @return {string}
 */
proto.gws.PrimaryKeyProtobuf.prototype.getId = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.PrimaryKeyProtobuf.prototype.setId = function(value) {
  jspb.Message.setField(this, 1, value);
};


