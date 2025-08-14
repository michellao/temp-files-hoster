import React from "react";
import { Flex, FlexProps } from "@radix-ui/themes";

const ResponsiveWidth = (props: FlexProps) => (
  <Flex width={{
    initial: '100%',
    md: '80%'
  }} {...props} />
);

export default ResponsiveWidth;
