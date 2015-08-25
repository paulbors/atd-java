/**
 * Copyright (C) atd-java (https://github.com/paulbors/atd-java)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ws.bors.atd.impl;

import ws.bors.atd.ISpellCheckerOptions;

/**
 * Default {@link ISpellCheckerOptions} that performs no text processing and it dose not ignore any reported misspelled
 * words.
 */
public class DefaultSpellCheckerOptions implements ISpellCheckerOptions {
    @Override
    public String processChars(String text) {
        return text;
    }

    @Override
    public boolean ignoreWord(String misspelledWord) {
        return false;
    }
}
