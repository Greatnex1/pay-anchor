package com.payu.payly.validator;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payu.payly.model.ProductDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailListDeserializer extends JsonDeserializer<List<ProductDetail>> {
    @Override
    public List<ProductDetail> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            ObjectMapper mapper = (ObjectMapper) parser.getCodec();
            List<ProductDetail> productList = new ArrayList<>();

            if (parser.currentToken() == JsonToken.START_OBJECT) {
                ProductDetail product = mapper.readValue(parser, ProductDetail.class);
                productList.add(product);
            } else if (parser.currentToken() == JsonToken.START_ARRAY) {
                productList = mapper.readValue(parser, mapper.getTypeFactory().constructCollectionType(List.class, ProductDetail.class));
            }

            return productList;
        }
}
