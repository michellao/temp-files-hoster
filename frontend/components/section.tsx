import { Flex, FlexProps, Heading, IconProps } from "@radix-ui/themes";
import TextSubtle from "./text-subtle";
import React from "react";
import styles from "./section-style.module.css";

export default function Section({
  icon: Icon,
  heading,
  subHeading,
  children,
  height
}: {
  icon?: React.ComponentType<IconProps>,
  heading: String,
  subHeading: String,
  children?: React.ReactNode,
  height?: FlexProps['height']
}) {
  return (
    <Flex className={styles.shadow} width={{
      initial: "100%",
      sm: "80%"
    }}>
      <Flex className={styles.margin}>
        <Flex direction="column" width="100%" height={height}>
          <Flex direction="column">
            <Heading as="h2">{Icon && <Icon width="20px" height="20px"/>} { heading }</Heading>
            <TextSubtle>{ subHeading }</TextSubtle>
          </Flex>
          { children }
        </Flex>
      </Flex>
    </Flex>
  );
}
