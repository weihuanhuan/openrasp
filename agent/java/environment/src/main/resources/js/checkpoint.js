/**
 * @file
 */
'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var CheckPointCommand = function () {
    function CheckPointCommand(data) {
        _classCallCheck(this, CheckPointCommand);

        this.command = data.command;
    }

    _createClass(CheckPointCommand, null, [{
        key: 'name',
        get: function get() {
            return 'command';
        }
    }]);

    return CheckPointCommand;
}();
Object.defineProperty(global, 'CheckPointCommand', {
    value: CheckPointCommand,
    enumerable: true
});

var CheckPointDirectory = function () {
    function CheckPointDirectory(data) {
        _classCallCheck(this, CheckPointDirectory);

        this.path = data.path;
        this.realpath = data.realpath;
    }

    _createClass(CheckPointDirectory, null, [{
        key: 'name',
        get: function get() {
            return 'directory';
        }
    }]);

    return CheckPointDirectory;
}();
Object.defineProperty(global, 'CheckPointDirectory', {
    value: CheckPointDirectory,
    enumerable: true
});

var CheckPointFileUpload = function () {
    function CheckPointFileUpload(data) {
        _classCallCheck(this, CheckPointFileUpload);

        this.filename = data.filename;
        this.content = data.content;
    }

    _createClass(CheckPointFileUpload, null, [{
        key: 'name',
        get: function get() {
            return 'fileUpload';
        }
    }]);

    return CheckPointFileUpload;
}();
Object.defineProperty(global, 'CheckPointFileUpload', {
    value: CheckPointFileUpload,
    enumerable: true
});

var CheckPointReadFile = function () {
    function CheckPointReadFile(data) {
        _classCallCheck(this, CheckPointReadFile);

        this.path = data.path;
        this.realpath = data.realpath;
    }

    _createClass(CheckPointReadFile, null, [{
        key: 'name',
        get: function get() {
            return 'readFile';
        }
    }]);

    return CheckPointReadFile;
}();
Object.defineProperty(global, 'CheckPointReadFile', {
    value: CheckPointReadFile,
    enumerable: true
});

var CheckPointRequest = function () {
    function CheckPointRequest(data) {
        _classCallCheck(this, CheckPointRequest);

        this.request = data.request;
    }

    _createClass(CheckPointRequest, null, [{
        key: 'name',
        get: function get() {
            return 'request';
        }
    }]);

    return CheckPointRequest;
}();
Object.defineProperty(global, 'CheckPointRequest', {
    value: CheckPointRequest,
    enumerable: true
});

var CheckPointSQL = function () {
    function CheckPointSQL(data) {
        _classCallCheck(this, CheckPointSQL);

        this.query = data.query;
        this.server = data.server;
    }

    _createClass(CheckPointSQL, null, [{
        key: 'name',
        get: function get() {
            return 'sql';
        }
    }]);

    return CheckPointSQL;
}();
Object.defineProperty(global, 'CheckPointSQL', {
    value: CheckPointSQL,
    enumerable: true
});

var CheckPointWriteFile = function () {
    function CheckPointWriteFile(data) {
        _classCallCheck(this, CheckPointWriteFile);

        this.name = data.name;
        this.realpath = data.realpath;
        this.content = data.content;
    }

    _createClass(CheckPointWriteFile, null, [{
        key: 'name',
        get: function get() {
            return 'writeFile';
        }
    }]);

    return CheckPointWriteFile;
}();
Object.defineProperty(global, 'CheckPointWriteFile', {
    value: CheckPointWriteFile,
    enumerable: true
});

var CheckPointDeleteFile = function () {
    function CheckPointDeleteFile(data) {
        _classCallCheck(this, CheckPointDeleteFile);
    }

    _createClass(CheckPointDeleteFile, null, [{
        key: 'name',
        get: function get() {
            return 'deleteFile';
        }
    }]);

    return CheckPointDeleteFile;
}();
Object.defineProperty(global, 'CheckPointDeleteFile', {
    value: CheckPointDeleteFile,
    enumerable: true
});

var CheckPointSSRFRedirect = function () {
    function CheckPointSSRFRedirect(data) {
        _classCallCheck(this, CheckPointSSRFRedirect);
    }

    _createClass(CheckPointSSRFRedirect, null, [{
        key: 'name',
        get: function get() {
            return 'ssrfRedirect';
        }
    }]);

    return CheckPointSSRFRedirect;
}();
Object.defineProperty(global, 'CheckPointSSRFRedirect', {
    value: CheckPointSSRFRedirect,
    enumerable: true
});

var CheckPointSQLException = function () {
    function CheckPointSQLException(data) {
        _classCallCheck(this, CheckPointSQLException);
    }

    _createClass(CheckPointSQLException, null, [{
        key: 'name',
        get: function get() {
            return 'sql_exception';
        }
    }]);

    return CheckPointSQLException;
}();
Object.defineProperty(global, 'CheckPointSQLException', {
    value: CheckPointSQLException,
    enumerable: true
});

var CheckPointLink = function () {
    function CheckPointLink(data) {
        _classCallCheck(this, CheckPointLink);
    }

    _createClass(CheckPointLink, null, [{
        key: 'name',
        get: function get() {
            return 'link';
        }
    }]);

    return CheckPointLink;
}();
Object.defineProperty(global, 'CheckPointLink', {
    value: CheckPointLink,
    enumerable: true
});

var CheckPointEval = function () {
    function CheckPointEval(data) {
        _classCallCheck(this, CheckPointEval);
    }

    _createClass(CheckPointEval, null, [{
        key: 'name',
        get: function get() {
            return 'eval';
        }
    }]);

    return CheckPointEval;
}();
Object.defineProperty(global, 'CheckPointEval', {
    value: CheckPointEval,
    enumerable: true
});

var CheckPointLoadLibrary = function () {
    function CheckPointLoadLibrary(data) {
        _classCallCheck(this, CheckPointLoadLibrary);
    }

    _createClass(CheckPointLoadLibrary, null, [{
        key: 'name',
        get: function get() {
            return 'loadLibrary';
        }
    }]);

    return CheckPointLoadLibrary;
}();
Object.defineProperty(global, 'CheckPointLoadLibrary', {
    value: CheckPointLoadLibrary,
    enumerable: true
});

var CheckPointResponse = function () {
    function CheckPointResponse(data) {
        _classCallCheck(this, CheckPointResponse);
    }

    _createClass(CheckPointResponse, null, [{
        key: 'name',
        get: function get() {
            return 'response';
        }
    }]);

    return CheckPointResponse;
}();
Object.defineProperty(global, 'CheckPointResponse', {
    value: CheckPointResponse,
    enumerable: true
});

var CheckPointXXE = function () {
    function CheckPointXXE(data) {
        _classCallCheck(this, CheckPointXXE);

        this.entity = data.entity;
    }

    _createClass(CheckPointXXE, null, [{
        key: 'name',
        get: function get() {
            return 'xxe';
        }
    }]);

    return CheckPointXXE;
}();
Object.defineProperty(global, 'CheckPointXXE', {
    value: CheckPointXXE,
    enumerable: true
});

var CheckPointOGNL = function () {
    function CheckPointOGNL(data) {
        _classCallCheck(this, CheckPointOGNL);

        this.expression = data.expression;
    }

    _createClass(CheckPointOGNL, null, [{
        key: 'name',
        get: function get() {
            return 'ognl';
        }
    }]);

    return CheckPointOGNL;
}();
Object.defineProperty(global, 'CheckPointOGNL', {
    value: CheckPointOGNL,
    enumerable: true
});

var CheckPointDeserialization = function () {
    function CheckPointDeserialization(data) {
        _classCallCheck(this, CheckPointDeserialization);

        this.clazz = data.clazz;
    }

    _createClass(CheckPointDeserialization, null, [{
        key: 'name',
        get: function get() {
            return 'deserialization';
        }
    }]);

    return CheckPointDeserialization;
}();
Object.defineProperty(global, 'CheckPointDeserialization', {
    value: CheckPointDeserialization,
    enumerable: true
});

var CheckPointWebdav = function () {
    function CheckPointWebdav(data) {
        _classCallCheck(this, CheckPointWebdav);

        this.clazz = data.clazz;
    }

    _createClass(CheckPointWebdav, null, [{
        key: 'name',
        get: function get() {
            return 'webdav';
        }
    }]);

    return CheckPointWebdav;
}();
Object.defineProperty(global, 'CheckPointWebdav', {
    value: CheckPointWebdav,
    enumerable: true
});

var CheckPointSSRF = function () {
    function CheckPointSSRF(data) {
        _classCallCheck(this, CheckPointSSRF);

        this.clazz = data.clazz;
    }

    _createClass(CheckPointSSRF, null, [{
        key: 'name',
        get: function get() {
            return 'ssrf';
        }
    }]);

    return CheckPointSSRF;
}();
Object.defineProperty(global, 'CheckPointSSRF', {
    value: CheckPointSSRF,
    enumerable: true
});

var CheckPointInclude = function () {
    function CheckPointInclude(data) {
        _classCallCheck(this, CheckPointInclude);

        this.clazz = data.clazz;
    }

    _createClass(CheckPointInclude, null, [{
        key: 'name',
        get: function get() {
            return 'include';
        }
    }]);

    return CheckPointInclude;
}();
Object.defineProperty(global, 'CheckPointInclude', {
    value: CheckPointInclude,
    enumerable: true
});

var CheckPointRename = function () {
    function CheckPointRename(data) {
        _classCallCheck(this, CheckPointRename);

        this.source = data.source;
        this.dest = data.dest;
    }

    _createClass(CheckPointRename, null, [{
        key: 'name',
        get: function get() {
            return 'rename';
        }
    }]);

    return CheckPointRename;
}();
Object.defineProperty(global, 'CheckPointRename', {
    value: CheckPointRename,
    enumerable: true
});