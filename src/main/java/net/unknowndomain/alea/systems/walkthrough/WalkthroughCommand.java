/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.walkthrough;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemDescriptor;
import net.unknowndomain.alea.roll.GenericRoll;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author journeyman
 */
public class WalkthroughCommand extends RpgSystemCommand
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WalkthroughCommand.class);
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("Walkthrough RPG", "wkt", "walkthrough");
    
    private static final String NO_SKILL_PARAM = "no-skill";
    private static final String BRONZE_LEVEL_PARAM = "bronze-level";
    private static final String SILVER_LEVEL_PARAM = "silver-level";
    private static final String GOLD_LEVEL_PARAM = "gold-level";
    private static final String FEATURE_PARAM = "feature-level";
    private static final String MEDALS_PARAM = "add-medals";
    private static final String NEGATIVE_ATTR_PARAM = "negative-attribute";
    
    private static final Options CMD_OPTIONS;
    
    static {
        
        CMD_OPTIONS = new Options();
        OptionGroup skillLevel = new OptionGroup();
        skillLevel.addOption(
                Option.builder("n")
                        .longOpt(NO_SKILL_PARAM)
                        .desc("The character has no skill")
                        .build()
        );
        skillLevel.addOption(
                Option.builder("b")
                        .longOpt(BRONZE_LEVEL_PARAM)
                        .desc("The character has a bronze skill level")
                        .build()
        );
        skillLevel.addOption(
                Option.builder("s")
                        .longOpt(SILVER_LEVEL_PARAM)
                        .desc("The character has a silver skill level")
                        .build()
        );
        skillLevel.addOption(
                Option.builder("g")
                        .longOpt(GOLD_LEVEL_PARAM)
                        .desc("The character has a gold skill level")
                        .build()
        );
        skillLevel.setRequired(true);
        CMD_OPTIONS.addOptionGroup(skillLevel);
        CMD_OPTIONS.addOption(
                Option.builder("m")
                        .longOpt(MEDALS_PARAM)
                        .desc("Number of spent medals")
                        .hasArg()
                        .argName("numberOfMedals")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("f")
                        .longOpt(FEATURE_PARAM)
                        .desc("Feature level of the character")
                        .hasArg()
                        .argName("featureLevel")
                        .required()
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("d")
                        .longOpt(NEGATIVE_ATTR_PARAM)
                        .desc("Set the enemy as specialized")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("h")
                        .longOpt( CMD_HELP )
                        .desc( "Print help")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("v")
                        .longOpt(CMD_VERBOSE)
                        .desc("Enable verbose output")
                        .build()
        );
    }
    
    public WalkthroughCommand()
    {
        
    }
    
    @Override
    public RpgSystemDescriptor getCommandDesc()
    {
        return DESC;
    }

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }
    
    @Override
    protected Optional<GenericRoll> safeCommand(String cmdParams)
    {
        Optional<GenericRoll> retVal;
        try
        {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(CMD_OPTIONS, cmdParams.split(" "));

            if (
                    cmd.hasOption(CMD_HELP)
                )
            {
                return Optional.empty();
            }


            Set<WalkthroughModifiers> mods = new HashSet<>();

            int s = 0;
            if (cmd.hasOption(CMD_VERBOSE))
            {
                mods.add(WalkthroughModifiers.VERBOSE);
            }
            if (cmd.hasOption(NEGATIVE_ATTR_PARAM))
            {
                mods.add(WalkthroughModifiers.NEGATIVE_ATTRIBUTE);
            }
            if (cmd.hasOption(NO_SKILL_PARAM))
            {
                mods.add(WalkthroughModifiers.SKILL_LEVEL_NONE);
            }
            if (cmd.hasOption(BRONZE_LEVEL_PARAM))
            {
                mods.add(WalkthroughModifiers.SKILL_LEVEL_BRONZE);
            }
            if (cmd.hasOption(SILVER_LEVEL_PARAM))
            {
                mods.add(WalkthroughModifiers.SKILL_LEVEL_SILVER);
            }
            if (cmd.hasOption(GOLD_LEVEL_PARAM))
            {
                mods.add(WalkthroughModifiers.SKILL_LEVEL_GOLD);
            }
            if (cmd.hasOption(MEDALS_PARAM))
            {
                s = Integer.parseInt(cmd.getOptionValue(MEDALS_PARAM));
            }
            int a = Integer.parseInt(cmd.getOptionValue(FEATURE_PARAM));
            GenericRoll roll = new WalkthroughRoll(a, s, mods);
            retVal = Optional.of(roll);
        } 
        catch (ParseException | NumberFormatException ex)
        {
            retVal = Optional.empty();
        }
        return retVal;
    }
    
    @Override
    public ReturnMsg getHelpMessage(String cmdName)
    {
        return HelpWrapper.printHelp(cmdName, CMD_OPTIONS, true);
    }
    
}
