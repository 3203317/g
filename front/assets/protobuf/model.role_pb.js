/**
 * @fileoverview
 * @enhanceable
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!

var jspb = require('google-protobuf');
var goog = jspb;
var global = Function('return this')();

goog.exportSymbol('proto.gws.model.RoleProtobuf', null, global);

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
proto.gws.model.RoleProtobuf = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.gws.model.RoleProtobuf, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.gws.model.RoleProtobuf.displayName = 'proto.gws.model.RoleProtobuf';
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
proto.gws.model.RoleProtobuf.prototype.toObject = function(opt_includeInstance) {
  return proto.gws.model.RoleProtobuf.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.gws.model.RoleProtobuf} msg The msg instance to transform.
 * @return {!Object}
 */
proto.gws.model.RoleProtobuf.toObject = function(includeInstance, msg) {
  var f, obj = {
    id: jspb.Message.getFieldWithDefault(msg, 1, ""),
    roleName: jspb.Message.getFieldWithDefault(msg, 2, "")
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
 * @return {!proto.gws.model.RoleProtobuf}
 */
proto.gws.model.RoleProtobuf.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.gws.model.RoleProtobuf;
  return proto.gws.model.RoleProtobuf.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.gws.model.RoleProtobuf} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.gws.model.RoleProtobuf}
 */
proto.gws.model.RoleProtobuf.deserializeBinaryFromReader = function(msg, reader) {
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
      var value = /** @type {string} */ (reader.readString());
      msg.setRoleName(value);
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
 * @param {!proto.gws.model.RoleProtobuf} message
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.model.RoleProtobuf.serializeBinaryToWriter = function(message, writer) {
  message.serializeBinaryToWriter(writer);
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.gws.model.RoleProtobuf.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  this.serializeBinaryToWriter(writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the message to binary data (in protobuf wire format),
 * writing to the given BinaryWriter.
 * @param {!jspb.BinaryWriter} writer
 */
proto.gws.model.RoleProtobuf.prototype.serializeBinaryToWriter = function (writer) {
  var f = undefined;
  f = this.getId();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = this.getRoleName();
  if (f.length > 0) {
    writer.writeString(
      2,
      f
    );
  }
};


/**
 * optional string id = 1;
 * @return {string}
 */
proto.gws.model.RoleProtobuf.prototype.getId = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.gws.model.RoleProtobuf.prototype.setId = function(value) {
  jspb.Message.setField(this, 1, value);
};


/**
 * optional string role_name = 2;
 * @return {string}
 */
proto.gws.model.RoleProtobuf.prototype.getRoleName = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/** @param {string} value */
proto.gws.model.RoleProtobuf.prototype.setRoleName = function(value) {
  jspb.Message.setField(this, 2, value);
};


goog.object.extend(exports, proto.gws.model);
