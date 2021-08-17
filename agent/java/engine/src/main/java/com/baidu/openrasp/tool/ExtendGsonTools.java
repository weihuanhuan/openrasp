package com.baidu.openrasp.tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class ExtendGsonTools {

    public static JsonObject extendJsonObjectPreferFirst(JsonObject first, JsonObject second) throws ExtendConflictException {
        JsonObject jsonObject = new JsonObject();
        if (first != null) {
            extendJsonObject(jsonObject, first, ConflictStrategy.PREFER_FIRST_OBJ);
        }
        if (second != null) {
            extendJsonObject(jsonObject, second, ConflictStrategy.PREFER_FIRST_OBJ);
        }
        return jsonObject;
    }

    /**
     * Using a deep copy of merge element avoid shard object.
     */
    private static void extendJsonObject(JsonObject leftObj, JsonObject rightObj, ConflictStrategy strategy) throws ExtendConflictException {
        for (Map.Entry<String, JsonElement> rightEntry : rightObj.entrySet()) {
            String rightKey = rightEntry.getKey();
            JsonElement rightVal = rightEntry.getValue();
            if (leftObj.has(rightKey)) {
                //conflict
                JsonElement leftVal = leftObj.get(rightKey);
                if (leftVal.isJsonArray() && rightVal.isJsonArray()) {
                    JsonArray leftArr = leftVal.getAsJsonArray();
                    JsonArray rightArr = rightVal.getAsJsonArray();
                    //concat the arrays -- there cannot be a conflict in an array, it's just a collection of stuff
                    for (int i = 0; i < rightArr.size(); i++) {
                        leftArr.add(rightArr.get(i).deepCopy());
                    }
                } else if (leftVal.isJsonObject() && rightVal.isJsonObject()) {
                    //recursive merging
                    extendJsonObject(leftVal.getAsJsonObject(), rightVal.getAsJsonObject(), strategy);
                } else {//not both arrays or objects, normal merge with conflict resolution
                    handleConflict(rightKey, leftObj, leftVal, rightVal, strategy);
                }
            } else {//no conflict, add to the object
                leftObj.add(rightKey, rightVal.deepCopy());
            }
        }
    }

    /**
     * Using a deep copy of merge element avoid shard object.
     */
    private static void handleConflict(String key, JsonObject leftObj, JsonElement leftVal, JsonElement rightVal, ConflictStrategy strategy) throws ExtendConflictException {
        {
            switch (strategy) {
                case PREFER_FIRST_OBJ:
                    break;//do nothing, the right val gets thrown out
                case PREFER_SECOND_OBJ:
                    leftObj.add(key, rightVal.deepCopy());//right side auto-wins, replace left val with its val
                    break;
                case PREFER_NON_NULL:
                    //check if right side is not null, and left side is null, in which case we use the right val
                    if (leftVal.isJsonNull() && !rightVal.isJsonNull()) {
                        leftObj.add(key, rightVal.deepCopy());
                    }//else do nothing since either the left value is non-null or the right value is null
                    break;
                case THROW_EXCEPTION:
                    throw new ExtendConflictException("Key " + key + " exists in both objects and the conflict resolution strategy is " + strategy);
                default:
                    throw new UnsupportedOperationException("The conflict strategy " + strategy + " is unknown and cannot be processed");
            }
        }
    }

    public enum ConflictStrategy {
        THROW_EXCEPTION, PREFER_FIRST_OBJ, PREFER_SECOND_OBJ, PREFER_NON_NULL;
    }

}
