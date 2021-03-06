/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.ls.util;

import com.google.common.collect.Lists;
import io.typefox.lsapi.CompletionItemKind;
import io.typefox.lsapi.CompletionList;
import io.typefox.lsapi.SymbolInformation;
import io.typefox.lsapi.SymbolKind;
import io.typefox.lsapi.builders.CompletionItemBuilder;
import io.typefox.lsapi.builders.CompletionListBuilder;
import io.typefox.lsapi.impl.CompletionListImpl;
import java.util.Set;

public final class CompletionUtils {
    public static CompletionList createCompletionListFromSymbols(Set<SymbolInformation> symbols) {
        if (symbols == null) {
            return new CompletionListImpl(false, Lists.newArrayList());
        }
        CompletionListBuilder builder = new CompletionListBuilder().isIncomplete(false);
        symbols.forEach(symbol -> {
            builder.item(new CompletionItemBuilder()
                    .label(symbol.getName())
                    .kind(symbolKindToCompletionItemKind(symbol.getKind()))
                    .build());
        });
        return builder.build();
    }

    @SuppressWarnings("checkstyle:cyclomaticcomplexity") // this is not complex behaviour
    public static CompletionItemKind symbolKindToCompletionItemKind(SymbolKind kind) {
        switch (kind) {
            case Class:
                return CompletionItemKind.Class;
            case Constructor:
                return CompletionItemKind.Constructor;
            case Enum:
                return CompletionItemKind.Enum;
            case Field:
                return CompletionItemKind.Field;
            case File:
                return CompletionItemKind.File;
            case Function:
                return CompletionItemKind.Function;
            case Interface:
                return CompletionItemKind.Interface;
            case Method:
                return CompletionItemKind.Method;
            case Property:
                return CompletionItemKind.Property;
            case String:
                return CompletionItemKind.Text;
            case Variable:
                return CompletionItemKind.Variable;
            case Array:
            case Boolean:
            case Constant:
            case Number:
                return CompletionItemKind.Value;
            case Module:
            case Namespace:
            case Package:
                return CompletionItemKind.Module;
            default:
                throw new IllegalArgumentException(String.format("Unsupported SymbolKind: %s", kind));
        }
    }

    private CompletionUtils() { }
}
