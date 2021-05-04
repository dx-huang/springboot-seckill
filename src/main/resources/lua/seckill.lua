local userId = KEYS[1];
local goodsId = KEYS[2];

local cntKey = goodsId.."_count";
local goodsUserKey = goodsId.."_"..userId;

local result = redis.call("get",goodsUserKey);
if result and tonumber(result) == 1 then
    return 2;
end

local num = redis.call("get",cntKey);
if cntKey and tonumber(num) <= 0 then
    return 0;
else
    redis.call("decr",cntKey);
    redis.call("set",goodsUserKey,1);
end

return 1;