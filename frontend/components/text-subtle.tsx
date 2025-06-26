import { Text, TextProps } from "@radix-ui/themes";
import React from "react";

const TextSubtle = (
  props: TextProps,
) => (<Text as="p" size="2" color="gray" {...props} />);

export default TextSubtle;
